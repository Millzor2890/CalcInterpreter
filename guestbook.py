import os
import re
import cgi
from string import letters

import webapp2
import jinja2

from google.appengine.ext import db

template_dir = os.path.join(os.path.dirname(__file__), 'templates')
jinja_env = jinja2.Environment(loader = jinja2.FileSystemLoader(template_dir),
                               autoescape = True)

def render_str(template, **params):
    t = jinja_env.get_template(template)
    return t.render(params)

form = """
<form method="post">
<textarea rows="5" cols="30" name="text"></textarea>
<br>
<input type="submit">

</form>
"""

form1 = """
<form method="post">
<textarea rows="5" cols="30" name="text">%s</textarea>
<br>
<input type="submit">

</form>
"""


class MainPage(webapp2.RequestHandler):

    def get(self):
        #self.response.headers['Content-Type'] = 'text/plain'
        self.response.write(form)
        
    def wtta(self,s):
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
	#self.response.headers['Content-Type'] = 'text/plain'
	self.response.write(form1 % self.wtta(self.request.get('text')))

	
class WelcomePage(webapp2.RequestHandler):
    def get(self):
        self.response.write(render_str('welcome.html', username = self.request.get('username')))

        
class BlogPost(db.Model):
    title = db.StringProperty(required = True)
    blog_content = db.TextProperty(required = True)
    date_created = db.DateTimeProperty(auto_now_add = True)

class BlogPage(webapp2.RequestHandler):
    def get(self):
        posts = db.GqlQuery('SELECT * from BlogPost order by date_created desc')
        self.response.write(render_str('blog.html', posts = posts))

class PermaPost(webapp2.RequestHandler):
    def get(self, post_id):
        ## search for the post in the database using GQL
        ## Use that post to generate a page with a single blog post.
        key = db.Key.from_path('BlogPost', int(post_id))
        posts = db.get(key)
        self.response.write(render_str('permalink.html', posts = posts))
        

class NewPost(webapp2.RequestHandler):
    def get(self):
        self.response.write(render_str('newpost.html'))

    def post(self):
        old_title = self.request.get('subject')
        obp = self.request.get('content')
        if old_title and obp:
            bp = BlogPost(title = old_title, blog_content = obp)
            bp.put()
            # get the ID and pass it to the redirect page
            self.redirect('/blog/'+str(bp.key().id()) )
        else: 
            self.response.write(render_str('newpost.html', old_title = old_title, old_blog_post = obp))
        

class SignupPage(webapp2.RequestHandler):

    USER_RE = re.compile(r"^[a-zA-Z0-9_-]{3,20}$")
    PW_RE = re.compile(r"^.{3,20}$")
    EMAIL_RE = re.compile(r"^[\S]+@[\S]+\.[\S]+$")
    ##
    # Validate the username
    # @param: username
    # @returns: true if valid username, otherwise false    
    def valid_username(self, username):
        return self.USER_RE.match(username)

    ##
    # Validate the password
    # @param: password
    # @returns: true if valid paasword, otherwise false    
    def valid_password(self, password):
        return self.PW_RE.match(password)

    ##
    # Validate the password
    # @param: password
    # @returns: true if valid paasword, otherwise false    
    def valid_password1(self, password, password1):
        return password == password1

    ##
    # Validate the email
    # @param: email
    # @returns: true if valid email, otherwise false    
    def valid_email(self, email):
        return self.EMAIL_RE.match(email) or email == ""
    
    def get(self):
        #self.response.headers['Content-Type'] = 'text/plain'
        self.response.write(render_str('signup.html'))
    	
    def post(self):
        flag = True
        oldtext1 = self.request.get('username')
        usernameerror1 = ""
        passworderror1 = ""
        passworderror3 = ""
        emailerror1 =""
        if not(self.valid_username(self.request.get('username'))):
            usernameerror1 = 'Thats not a valid username'
            flag = False
        if not(self.valid_password(self.request.get('password'))):
            passworderror1 = 'Thats not a valid password'
            flag = False
        # This should be the thing that checks they are the same
        if not(self.valid_password1(self.request.get('password'),self.request.get('verify'))):
            passworderror3 = 'Those passwords dont match'
            flag = False
        #This just checks the email
        if not(self.valid_email(self.request.get('email'))):
            emailerror1 = "That is not a valid email"
            flag = False
            
        #else:
        if flag:
            self.redirect('/welcome?username='+oldtext1)
        else:
            self.response.write(render_str('signup.html', oldtext =oldtext1,usernameerror=usernameerror1
                                       , passworderror= passworderror1, passworderror2 =passworderror3,
                                       emailerror =emailerror1))
		


application = webapp2.WSGIApplication([('/', MainPage),('/signup', SignupPage),('/welcome', WelcomePage),('/blog/newpost', NewPost)
                                       ,('/blog', BlogPage), ('/blog/([0-9]+)', PermaPost)], debug=True)
