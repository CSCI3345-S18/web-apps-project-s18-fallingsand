// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/kayla/Documents/WebAppsFallingSand/conf/routes
// @DATE:Mon Apr 02 19:18:45 CDT 2018

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:5
  PSController_0: controllers.PSController,
  // @LINE:6
  WSController_2: controllers.WSController,
  // @LINE:9
  Assets_1: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:5
    PSController_0: controllers.PSController,
    // @LINE:6
    WSController_2: controllers.WSController,
    // @LINE:9
    Assets_1: controllers.Assets
  ) = this(errorHandler, PSController_0, WSController_2, Assets_1, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, PSController_0, WSController_2, Assets_1, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.PSController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """socket""", """controllers.WSController.socket"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(file:String)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:5
  private[this] lazy val controllers_PSController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_PSController_index0_invoker = createInvoker(
    PSController_0.index,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.PSController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """""",
      Seq()
    )
  )

  // @LINE:6
  private[this] lazy val controllers_WSController_socket1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("socket")))
  )
  private[this] lazy val controllers_WSController_socket1_invoker = createInvoker(
    WSController_2.socket,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.WSController",
      "socket",
      Nil,
      "GET",
      this.prefix + """socket""",
      """""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val controllers_Assets_versioned2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned2_invoker = createInvoker(
    Assets_1.versioned(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:5
    case controllers_PSController_index0_route(params@_) =>
      call { 
        controllers_PSController_index0_invoker.call(PSController_0.index)
      }
  
    // @LINE:6
    case controllers_WSController_socket1_route(params@_) =>
      call { 
        controllers_WSController_socket1_invoker.call(WSController_2.socket)
      }
  
    // @LINE:9
    case controllers_Assets_versioned2_route(params@_) =>
      call(params.fromPath[String]("file", None)) { (file) =>
        controllers_Assets_versioned2_invoker.call(Assets_1.versioned(file))
      }
  }
}
