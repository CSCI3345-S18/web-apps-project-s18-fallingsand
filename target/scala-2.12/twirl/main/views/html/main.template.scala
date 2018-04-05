
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


Seq[Any](format.raw/*8.60*/("""

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
                  DATE: Mon Apr 02 19:18:45 CDT 2018
                  SOURCE: C:/Users/kayla/Documents/WebAppsFallingSand/app/views/main.scala.html
                  HASH: 9c591a1ec36106bc8e84564c22a5ac164d6f9a26
                  MATRIX: 1073->334|1226->392|1257->396|1340->504|1377->514|1412->522|1438->527|1528->590|1549->602|1599->631|1683->688|1704->700|1755->730|1844->792|1865->804|1913->831|1966->857|1987->869|2036->897|2133->1057|2171->1068|2199->1075|2232->1081
                  LINES: 27->8|32->8|34->10|37->13|38->14|38->14|38->14|39->15|39->15|39->15|40->16|40->16|40->16|41->17|41->17|41->17|42->18|42->18|42->18|45->22|46->23|46->23|47->24
                  -- GENERATED --
              */
          