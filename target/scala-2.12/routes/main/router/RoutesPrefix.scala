// @GENERATOR:play-routes-compiler
// @SOURCE:/users/jburnett/Local/HTML-Documents/WebApps/finalProj/web-apps-project-s18-fallingsand/conf/routes
// @DATE:Sun May 06 18:06:52 CDT 2018


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
