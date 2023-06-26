// @GENERATOR:play-routes-compiler
// @SOURCE:conf/ems.routes

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:2
package controllers.javascript {

  // @LINE:2
  class ReverseEmployeeController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def updatePassword: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.EmployeeController.updatePassword",
      """
        function() {
          return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "update"})
        }
      """
    )
  
    // @LINE:3
    def getEmployee: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.EmployeeController.getEmployee",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:2
    def getAllEmployees: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.EmployeeController.getAllEmployees",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
    // @LINE:8
    def getEmployeeDailyAttendance: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.EmployeeController.getEmployeeDailyAttendance",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "attendance/daily"})
        }
      """
    )
  
    // @LINE:9
    def removeEmployee: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.EmployeeController.removeEmployee",
      """
        function(id0) {
          return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:5
    def signIn: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.EmployeeController.signIn",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "signin"})
        }
      """
    )
  
    // @LINE:4
    def addEmployee: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.EmployeeController.addEmployee",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + """"})
        }
      """
    )
  
    // @LINE:7
    def markAttendance: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.EmployeeController.markAttendance",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "clockin"})
        }
      """
    )
  
  }


}
