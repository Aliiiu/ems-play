// @GENERATOR:play-routes-compiler
// @SOURCE:conf/ems.routes

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:2
package controllers {

  // @LINE:2
  class ReverseEmployeeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def updatePassword(): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "update")
    }
  
    // @LINE:3
    def getEmployee(id:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
    // @LINE:2
    def getAllEmployees(): Call = {
      
      Call("GET", _prefix)
    }
  
    // @LINE:8
    def getEmployeeDailyAttendance(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "attendance/daily")
    }
  
    // @LINE:9
    def removeEmployee(id:Integer): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
    // @LINE:5
    def signIn(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "signin")
    }
  
    // @LINE:4
    def addEmployee(): Call = {
      
      Call("POST", _prefix)
    }
  
    // @LINE:7
    def markAttendance(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "clockin")
    }
  
  }


}
