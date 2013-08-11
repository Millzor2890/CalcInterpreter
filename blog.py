#-------------------------------------------------------------------------------
# Name:        blog.py
# Purpose:
#
# Author:      Millzor
#
# Created:     8/10/2013
# Copyright:   (c) Millzor 2013
#-------------------------------------------------------------------------------
import os
import re
import cgi
from string import letters
import random
import string
import hashlib
import hmac
import json
import time
import logging
import webapp2
import jinja2
import secret

from google.appengine.ext import db
from google.appengine.api import memcache


PAGE_RE = r'(/(?:[a-zA-Z0-9_-]+/?)*)'

template_dir = os.path.join(os.path.dirname(__file__), 'templates')
jinja_env = jinja2.Environment(loader = jinja2.FileSystemLoader(template_dir),
                               autoescape = True)

class MainHandler(webapp2.RequestHandler):
    def render_str(self, template, **params):
        t = jinja_env.get_template(template)
        return t.render(params)

    def render(self, template, **kw):
        self.response.out.write(self.render_str(template, **kw))

    def make_salt(self):
        return ''.join(random.choice(string.letters) for x in xrange(5))

    def make_pw_hash(self, name, pw, salt=None):
        if salt == None:
            salt = self.make_salt()
        h = hashlib.sha256( name +pw+ salt).hexdigest()
        return '%s,%s' % (h, salt)

    def valid_pw(self, name, pw, h):
        cats = h.split(",")
        if self.make_pw_hash(name,pw,cats[1]) == h:
            return True

    def hash_str(self, s):
        return hmac.new(secret.SECRET,s).hexdigest()

    def make_secure_val(self, s):
        return "%s|%s" % (s, self.hash_str(s))

    def check_secure_val(self, h):
        if h != None:
            val = h.split('|')[0]
            if h == self.make_secure_val(val):
                return val
        return None



class HomePage(MainHandler):
    def get(self):
        self.render("mainpage.html")

# This was the first little tool I
# worked on.  Its  super impressive,
# but it lays a lot of the groudwork for future
# crypto stuff
class ROT13Handler(MainHandler):

    # This renders the page
    def get(self):
        self.render("ROT13.html")

    def shift(self,s):
        #cypher all the letters
        news = ""
        for ch in s:
            if ord(ch)<91 and ord(ch)>64:
                ch = chr(ord(ch)+13)
                if ord(ch) > 90:
                    ch = chr(64 + (ord(ch)-90))
            if ord(ch)<123 and ord(ch)>96:
                ch = chr(ord(ch)+13)
                if ord(ch) > 122:
                    ch = chr(96 + (ord(ch)-122))
            news+=ch
        #escape everything
        news = cgi.escape(news)
        #return something that the user would see
        return news

    def post(self):
	self.render("ROT13.html", secret_text = self.shift(self.request.get('text')))

# This handles the welcome page, right now its only useful for a user
# thats logged in, and I'm the only user that can
class WelcomePage(MainHandler):

    # Gets user information from the cookie and welcomes them
    # only works for valid users, and redirects others to blog
    def get(self):
        #validate cookie
        user = self.request.cookies.get('user_id')
        name = self.check_secure_val(user)
        if name:
            self.render('welcome.html', username = name)
        else:
            self.redirect('/blog')

# This is a database class for Blog posts
# it is used to collect certain so that it can be stored in the datastore
class BlogPost(db.Model):
    title = db.StringProperty(required = True)
    blog_content = db.TextProperty(required = True)
    date_created = db.DateTimeProperty(auto_now_add = True)
    last_modified = db.DateTimeProperty(auto_now = True)

    # This method just reguritates a database entry as a json entry
    def as_json(self):
        time_format = '%c'
        d = {'subject': self.title,
             'content' : self.blog_content,
             'created' : self.date_created.strftime(time_format),
             'last_modified' : self.last_modified.strftime(time_format)
            }
        return d

# This is a database class for Users, currently im the only user, but I might want
# to expand this one day.
class User(db.Model):
    username = db.StringProperty(required = True)
    password = db.StringProperty(required = True)

# This class handles the main blog page
# It will try to get the latest 10 posts from memcache, but if it cant, it will
# hit the database
class BlogPage(MainHandler):

    # Try to always hit the memcache and never the database
    def get(self):
        posts = memcache.get('front')
        if (posts == None):
            posts = db.GqlQuery('SELECT * from BlogPost order by date_created desc limit 10')
            memcache.set('front', posts)
        self.render('blog.html', posts = posts)

# This class handles the permalink pages
# It will try to get the specific post from memcache, but if it cant, it will
# hit the database
class PermaPost(MainHandler):

    # Try to always hit the memcache and never the database
    def get(self, post_id):
        posts = memcache.get(str(post_id))
        if (posts == None):
            key = db.Key.from_path('BlogPost', int(post_id))
            posts = db.get(key)
            memcache.set(str(post_id), posts)
        self.render('permalink.html', posts = posts)

# This handles outputting the blog in .json format
class Blog2Json(webapp2.RequestHandler):

    # Try to always hit the memcache and never the database
    def get(self):
        posts = memcache.get('front')
        if (posts == None):
            posts = db.GqlQuery('SELECT * from BlogPost order by date_created desc limit 10')
            memcache.set('front', posts)
        finalj = json.dumps([p.as_json() for p in posts])
        self.response.headers['Content-Type'] = 'application/json; charset=UTF-8'
        self.response.write(finalj)

# This handles outputting the permalink post in .json format
class Perma2Json(webapp2.RequestHandler):

    # Try to always hit the memcache and never the database
    def get(self, post_id):
        posts = memcache.get(str(post_id))
        if (posts == None):
            key = db.Key.from_path('BlogPost', int(post_id))
            posts = db.get(key)
            memcache.set(str(post_id), posts)
        finalj = json.dumps(posts.as_json())
        self.response.headers['Content-Type'] = 'application/json; charset=UTF-8'
        self.response.write(finalj)

# This class handles a secure log in, but I am the only one who can register.
# If a login is successful a cookie is written with the users information
# which does NOT contain their password in any form
class LoginPage(MainHandler):

    # Render the basic login page
    def get(self):
        self.render('login.html')

    # Validate the user SECURELY!
    def post(self):
        self.username = self.request.get('username')
        self.password = self.request.get('password')
        user = db.GqlQuery("SELECT * FROM User WHERE username IN ('"+self.username+"')").get()
        if user and self.valid_pw(self.username, self.password, user.password):
            self.response.headers.add_header('Set-Cookie',
            str('user_id='+ self.make_secure_val(self.request.get('username'))+'; Path=/'))
            self.redirect('/blog/newpost')
        else:
            self.render('login.html',login_error='Incorrect username or password')

# This is a handler for the logout link
class LogoutPage(MainHandler):

    # Delete cookie and  redirect to blog
    def get(self):
        self.response.headers.add_header('Set-Cookie',str('user_id=; Path=/'))
        self.redirect('/blog')

# This will flush the memcache.  This can only be performed by a logged in user
class Flusher(MainHandler):

    # Flush the memcache if the suer is logged in
    def get(self):
        user = self.request.cookies.get('user_id')
        if self.check_secure_val(user) != None:
            memcache.flush_all()
            self.redirect('/blog' )
        else:
            self.redirect('/blog' )

# This handles the creation of a new blog posting
# Only users that are logged in can access this page.
class NewPost(MainHandler):

    # If a user is logged in allow the to compose a blog post
    def get(self):
        user = self.request.cookies.get('user_id')
        if self.check_secure_val(user) != None:
            self.render('newpost.html')
        else:
            self.redirect("/blog")

    # Put the blog post into the database and update the cache
    # prompt the user again if nothing is entered
    def post(self):
        user = self.request.cookies.get('user_id')
        if self.check_secure_val(user) != None:
            old_title = self.request.get('subject')
            obp = self.request.get('content')
            if old_title and obp:
                bp = BlogPost(title = old_title, blog_content = obp)
                bp.put()
                posts = db.GqlQuery('SELECT * from BlogPost order by date_created desc limit 10')
                memcache.delete('front')
                memcache.set('front', posts)
                self.redirect('/blog/'+str(bp.key().id()))
            else:
                self.render('newpost.html', old_title = old_title, old_blog_post = obp)
        else:
            self.response.out.write("Unauthorized User, punk!")

# This page should not be accessable by anyone, but if I wanted to expand this
# app in the future its useful to have
class SignupPage(MainHandler):
    USER_RE = re.compile(r"^[a-zA-Z0-9_-]{3,20}$")
    PW_RE = re.compile(r"^.{3,20}$")
    EMAIL_RE = re.compile(r"^[\S]+@[\S]+\.[\S]+$")

    # Validate the username
    def valid_username(self, username):
        return self.USER_RE.match(username)

    # Validate the password
    def valid_password(self, password):
        return self.PW_RE.match(password)

    # Validate the password
    def valid_password1(self, password, password1):
        return password == password1

    # Validate the email
    def valid_email(self, email):
        return self.EMAIL_RE.match(email) or email == ""

    # render basic signup page
    def get(self):
        self.render('signup.html')

    # Verify a users information is correct
    # Thier password must match and their email is optional.
    def post(self):
        vflag = True
        oldtext1 = self.request.get('username')
        usernameerror1 = ""
        passworderror1 = ""
        passworderror3 = ""
        emailerror1 =""
        if not(self.valid_username(self.request.get('username'))):
            usernameerror1 = 'Thats not a valid username'
            vflag = False
        if not(self.valid_password(self.request.get('password'))):
            passworderror1 = 'Thats not a valid password'
            vflag = False
        # This should be the thing that checks they are the same
        if not(self.valid_password1(self.request.get('password'),self.request.get('verify'))):
            passworderror3 = 'Those passwords dont match'
            vflag = False
        #This just checks the email
        if not(self.valid_email(self.request.get('email'))):
            emailerror1 = "That is not a valid email"
            vflag = False
        #else:
        if vflag:
            key = db.Key.from_path('User', oldtext1)
            if db.get(key):
                self.render('signup.html', oldtext ='',
                usernameerror="User already exists.",
                passworderror= passworderror1,
                passworderror2=passworderror3,emailerror=emailerror1)
            else:
                newuser = User(username = oldtext1, password = self.make_pw_hash(oldtext1,self.request.get('password')))
                newuser.put()
                self.response.headers.add_header('Set-Cookie',str('user_id='+self.make_secure_val(self.request.get('username'))+'; Path=/'))
                self.redirect('/welcome')
        else:
            self.render('signup.html', oldtext =oldtext1,usernameerror=usernameerror1
                                       , passworderror= passworderror1, passworderror2 =passworderror3,
                                       emailerror =emailerror1)



# URL mappings
application = webapp2.WSGIApplication([('/', HomePage),
                                        ('/rot13', ROT13Handler),
                                       #('/blog/signup', SignupPage),
                                       ('/welcome', WelcomePage),
                                       ('/blog/newpost', NewPost),
                                       ('/blog/flush', Flusher),
                                       ('/blog', BlogPage),
                                       ('/blog/login', LoginPage),
                                       ('/blog/logout', LogoutPage),
                                       ('/blog\.json', Blog2Json),
                                       ('/blog/([0-9]+)', PermaPost),
                                       ('/blog/([0-9]+)\.json', Perma2Json),
                                       ], debug=True)
