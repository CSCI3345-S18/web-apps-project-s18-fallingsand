// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/kayla/Documents/web-apps-project-s18-fallingsand/conf/routes
// @DATE:Sun Apr 15 18:54:38 CDT 2018


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
