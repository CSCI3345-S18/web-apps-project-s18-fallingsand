
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

object particlesim extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[String,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /*
 * This template takes a two arguments, a String containing a
 * message to display and an AssetsFinder to locate static assets.
 */
  def apply/*5.2*/(message: String)(implicit assetsFinder: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*5.56*/("""

"""),_display_(/*7.2*/main("Multiplayer Particle Simulation", assetsFinder)/*7.55*/ {_display_(Seq[Any](format.raw/*7.57*/("""
	"""),format.raw/*8.2*/("""<div id="particle-menu"></div>
	<canvas id="particle-canvas" class="fullscreen">
	"""),format.raw/*13.5*/("""
	 """),format.raw/*14.3*/("""</canvas>
	<div id="particle-members"></div>
""")))}),format.raw/*16.2*/("""

"""),format.raw/*18.1*/("""<script src=""""),_display_(/*18.15*/assetsFinder/*18.27*/.path("javascripts/websocket.js")),format.raw/*18.60*/("""" type="text/javascript"></script>
<script src=""""),_display_(/*19.15*/assetsFinder/*19.27*/.path("javascripts/particlesim.js")),format.raw/*19.62*/("""" type="text/javascript"></script>
"""))
      }
    }
  }

  def render(message:String,assetsFinder:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(message)(assetsFinder)

  def f:((String) => (AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (message) => (assetsFinder) => apply(message)(assetsFinder)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Mon Apr 02 19:18:45 CDT 2018
                  SOURCE: C:/Users/kayla/Documents/WebAppsFallingSand/app/views/particlesim.scala.html
                  HASH: 672c0e0d65a2401072e4668311076919ee55e1a7
                  MATRIX: 882->141|1031->195|1061->200|1122->253|1161->255|1190->258|1301->426|1332->430|1410->478|1441->482|1482->496|1503->508|1557->541|1634->591|1655->603|1711->638
                  LINES: 24->5|29->5|31->7|31->7|31->7|32->8|34->13|35->14|37->16|39->18|39->18|39->18|39->18|40->19|40->19|40->19
                  -- GENERATED --
              */
          