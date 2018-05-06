
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._

object main extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[String,AssetsFinder,Html,play.twirl.api.HtmlFormat.Appendable] {

  /*
 * This template is called from the `index` template. This template
 * handles the rendering of the page header and body tags. It takes
 * three arguments, a `String` for the title of the page and an `Html`
 * object to insert into the body of the page and an `AssetFinder`
 * to define to reverse route static assets.
 */
  def apply/*8.2*/(title: String, assetsFinder: AssetsFinder)(content: Html):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*9.1*/("""
"""),format.raw/*10.1*/("""<!DOCTYPE html>
<html lang="en">
    <head>
        """),format.raw/*13.62*/("""
        """),format.raw/*14.9*/("""<title>"""),_display_(/*14.17*/title),format.raw/*14.22*/("""</title>
        <link rel="stylesheet" media="screen" href=""""),_display_(/*15.54*/assetsFinder/*15.66*/.path("stylesheets/main.css")),format.raw/*15.95*/("""">
        <link rel="stylesheet" media="screen" href=""""),_display_(/*16.54*/assetsFinder/*16.66*/.path("stylesheets/tasks.css")),format.raw/*16.96*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(/*17.59*/assetsFinder/*17.71*/.path("images/favicon.png")),format.raw/*17.98*/("""">
        <script src=""""),_display_(/*18.23*/assetsFinder/*18.35*/.path("javascripts/main.js")),format.raw/*18.63*/("""" type="text/javascript"></script>
    </head>
    <body>
        """),format.raw/*22.32*/("""
        """),_display_(/*23.10*/content),format.raw/*23.17*/("""
    """),format.raw/*24.5*/("""</body>
</html>
"""))
      }
    }
  }

  def render(title:String,assetsFinder:AssetsFinder,content:Html): play.twirl.api.HtmlFormat.Appendable = apply(title,assetsFinder)(content)

  def f:((String,AssetsFinder) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (title,assetsFinder) => (content) => apply(title,assetsFinder)(content)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Sun May 06 18:06:52 CDT 2018
                  SOURCE: /users/jburnett/Local/HTML-Documents/WebApps/finalProj/web-apps-project-s18-fallingsand/app/views/main.scala.html
                  HASH: c1b95da2c05c3081371f3440c75662f506e3072c
                  MATRIX: 1067->327|1219->386|1247->387|1327->492|1363->501|1398->509|1424->514|1513->576|1534->588|1584->617|1667->673|1688->685|1739->715|1827->776|1848->788|1896->815|1948->840|1969->852|2018->880|2112->1036|2149->1046|2177->1053|2209->1058
                  LINES: 27->8|32->9|33->10|36->13|37->14|37->14|37->14|38->15|38->15|38->15|39->16|39->16|39->16|40->17|40->17|40->17|41->18|41->18|41->18|44->22|45->23|45->23|46->24
                  -- GENERATED --
              */
          