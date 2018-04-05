// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/kayla/Documents/WebAppsFallingSand/conf/routes
// @DATE:Mon Apr 02 19:18:45 CDT 2018


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
