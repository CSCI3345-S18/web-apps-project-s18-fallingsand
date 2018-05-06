// @GENERATOR:play-routes-compiler
// @SOURCE:/users/jburnett/Local/HTML-Documents/WebApps/finalProj/web-apps-project-s18-fallingsand/conf/routes
// @DATE:Sun May 06 18:06:52 CDT 2018

import play.api.mvc.Call


import _root_.controllers.Assets.Asset

// @LINE:5
package controllers {

  // @LINE:5
  class ReversePSController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:5
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
    // @LINE:11
    def test(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "test")
    }
  
  }

  // @LINE:7
  class ReverseLoginController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def logout(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "logout")
    }
  
    // @LINE:7
    def index(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "access")
    }
  
    // @LINE:9
    def register(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "register")
    }
  
    // @LINE:8
    def login(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "login")
    }
  
  }

  // @LINE:6
  class ReverseWSController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def socket(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "socket")
    }
  
  }

  // @LINE:14
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:14
    def versioned(file:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[String]].unbind("file", file))
    }
  
  }


}
