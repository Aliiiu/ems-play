// @GENERATOR:play-routes-compiler
// @SOURCE:conf/ems.routes

package ems

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:2
  EmployeeController_0: controllers.EmployeeController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:2
    EmployeeController_0: controllers.EmployeeController
  ) = this(errorHandler, EmployeeController_0, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    ems.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, EmployeeController_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.EmployeeController.getAllEmployees()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """""" + "$" + """id<[^/]+>""", """controllers.EmployeeController.getEmployee(id:Integer)"""),
    ("""POST""", this.prefix, """controllers.EmployeeController.addEmployee(request:Request)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """signin""", """controllers.EmployeeController.signIn(request:Request)"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """update""", """controllers.EmployeeController.updatePassword(request:Request)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """clockin""", """controllers.EmployeeController.markAttendance(request:Request)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """attendance/daily""", """controllers.EmployeeController.getEmployeeDailyAttendance()"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """""" + "$" + """id<[^/]+>""", """controllers.EmployeeController.removeEmployee(id:Integer)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:2
  private[this] lazy val controllers_EmployeeController_getAllEmployees0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_EmployeeController_getAllEmployees0_invoker = createInvoker(
    EmployeeController_0.getAllEmployees(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "ems",
      "controllers.EmployeeController",
      "getAllEmployees",
      Nil,
      "GET",
      this.prefix + """""",
      """""",
      Seq()
    )
  )

  // @LINE:3
  private[this] lazy val controllers_EmployeeController_getEmployee1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_EmployeeController_getEmployee1_invoker = createInvoker(
    EmployeeController_0.getEmployee(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "ems",
      "controllers.EmployeeController",
      "getEmployee",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:4
  private[this] lazy val controllers_EmployeeController_addEmployee2_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_EmployeeController_addEmployee2_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      EmployeeController_0.addEmployee(fakeValue[play.mvc.Http.Request]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "ems",
      "controllers.EmployeeController",
      "addEmployee",
      Seq(classOf[play.mvc.Http.Request]),
      "POST",
      this.prefix + """""",
      """""",
      Seq()
    )
  )

  // @LINE:5
  private[this] lazy val controllers_EmployeeController_signIn3_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("signin")))
  )
  private[this] lazy val controllers_EmployeeController_signIn3_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      EmployeeController_0.signIn(fakeValue[play.mvc.Http.Request]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "ems",
      "controllers.EmployeeController",
      "signIn",
      Seq(classOf[play.mvc.Http.Request]),
      "POST",
      this.prefix + """signin""",
      """""",
      Seq()
    )
  )

  // @LINE:6
  private[this] lazy val controllers_EmployeeController_updatePassword4_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("update")))
  )
  private[this] lazy val controllers_EmployeeController_updatePassword4_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      EmployeeController_0.updatePassword(fakeValue[play.mvc.Http.Request]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "ems",
      "controllers.EmployeeController",
      "updatePassword",
      Seq(classOf[play.mvc.Http.Request]),
      "PUT",
      this.prefix + """update""",
      """""",
      Seq()
    )
  )

  // @LINE:7
  private[this] lazy val controllers_EmployeeController_markAttendance5_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("clockin")))
  )
  private[this] lazy val controllers_EmployeeController_markAttendance5_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      EmployeeController_0.markAttendance(fakeValue[play.mvc.Http.Request]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "ems",
      "controllers.EmployeeController",
      "markAttendance",
      Seq(classOf[play.mvc.Http.Request]),
      "POST",
      this.prefix + """clockin""",
      """""",
      Seq()
    )
  )

  // @LINE:8
  private[this] lazy val controllers_EmployeeController_getEmployeeDailyAttendance6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("attendance/daily")))
  )
  private[this] lazy val controllers_EmployeeController_getEmployeeDailyAttendance6_invoker = createInvoker(
    EmployeeController_0.getEmployeeDailyAttendance(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "ems",
      "controllers.EmployeeController",
      "getEmployeeDailyAttendance",
      Nil,
      "GET",
      this.prefix + """attendance/daily""",
      """""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val controllers_EmployeeController_removeEmployee7_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_EmployeeController_removeEmployee7_invoker = createInvoker(
    EmployeeController_0.removeEmployee(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "ems",
      "controllers.EmployeeController",
      "removeEmployee",
      Seq(classOf[Integer]),
      "DELETE",
      this.prefix + """""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:2
    case controllers_EmployeeController_getAllEmployees0_route(params@_) =>
      call { 
        controllers_EmployeeController_getAllEmployees0_invoker.call(EmployeeController_0.getAllEmployees())
      }
  
    // @LINE:3
    case controllers_EmployeeController_getEmployee1_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        controllers_EmployeeController_getEmployee1_invoker.call(EmployeeController_0.getEmployee(id))
      }
  
    // @LINE:4
    case controllers_EmployeeController_addEmployee2_route(params@_) =>
      call { 
        controllers_EmployeeController_addEmployee2_invoker.call(
          req => EmployeeController_0.addEmployee(req))
      }
  
    // @LINE:5
    case controllers_EmployeeController_signIn3_route(params@_) =>
      call { 
        controllers_EmployeeController_signIn3_invoker.call(
          req => EmployeeController_0.signIn(req))
      }
  
    // @LINE:6
    case controllers_EmployeeController_updatePassword4_route(params@_) =>
      call { 
        controllers_EmployeeController_updatePassword4_invoker.call(
          req => EmployeeController_0.updatePassword(req))
      }
  
    // @LINE:7
    case controllers_EmployeeController_markAttendance5_route(params@_) =>
      call { 
        controllers_EmployeeController_markAttendance5_invoker.call(
          req => EmployeeController_0.markAttendance(req))
      }
  
    // @LINE:8
    case controllers_EmployeeController_getEmployeeDailyAttendance6_route(params@_) =>
      call { 
        controllers_EmployeeController_getEmployeeDailyAttendance6_invoker.call(EmployeeController_0.getEmployeeDailyAttendance())
      }
  
    // @LINE:9
    case controllers_EmployeeController_removeEmployee7_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        controllers_EmployeeController_removeEmployee7_invoker.call(EmployeeController_0.removeEmployee(id))
      }
  }
}
