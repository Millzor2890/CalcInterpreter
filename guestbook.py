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
        return self.USER_RE.match(password)

    ##
    # Validate the email
    # @param: email
    # @returns: true if valid email, otherwise false    
    def valid_email(self, email):
        return self.USER_RE.match(email)
    
    def get(self):
        #self.response.headers['Content-Type'] = 'text/plain'
        self.response.write(render_str('signup.html'))
    	
    def post(self):
	#self.response.headers['Content-Type'] = 'text/plain'
	#self.response.write(form1 % self.wtta(self.request.get('text')))
        if not(self.valid_username(self.request.get('username'))):
            self.response.out.write(render_str('signup.html', oldtext = self.request.get('username'),usernameerror = 'Thats not a valid username'))
        else:
            
            self.response.out.write(render_str('signup.html'))
		


application = webapp2.WSGIApplication([('/', MainPage),('/signup', SignupPage)], debug=True)
