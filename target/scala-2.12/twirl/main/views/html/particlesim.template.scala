
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
	"""),format.raw/*8.2*/("""<link rel="stylesheet" media="screen" href=""""),_display_(/*8.47*/routes/*8.53*/.Assets.versioned("stylesheets/particles.css")),format.raw/*8.99*/("""">
	<div id="particle-menu"></div>
	<canvas id="particle-canvas" width="600" height="600">
	"""),format.raw/*14.5*/("""
	 """),format.raw/*15.3*/("""</canvas>
	<div id="particle-members"></div>
	<br>
			<button id = "sandButton">Sand</button>
			<button id = "waterButton">Water</button>
			<button id = "stoneButton">Stone</button>
			<button id = "metalButton">Metal</button>
			<button id = "acidButton">Acid</button>
			<br> <br>
			<button id = "eraseButton">Erase</button>
""")))}),format.raw/*25.2*/("""

"""),format.raw/*27.98*/("""
"""),format.raw/*28.1*/("""<script src=""""),_display_(/*28.15*/assetsFinder/*28.27*/.path("javascripts/particlesystem.js")),format.raw/*28.65*/("""" type="text/javascript"></script>"""))
      }
    }
  }

  def render(message:String,assetsFinder:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(message)(assetsFinder)

  def f:((String) => (AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (message) => (assetsFinder) => apply(message)(assetsFinder)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Mon May 07 08:01:10 CDT 2018
                  SOURCE: C:/Users/kayla/Documents/web-apps-project-s18-fallingsand/app/views/particlesim.scala.html
                  HASH: 7ea088fb30e133fbd1cb084f5b65cb7ddcb59497
                  MATRIX: 882->141|1031->195|1061->200|1122->253|1161->255|1190->258|1261->303|1275->309|1341->355|1463->534|1494->538|1865->879|1897->980|1926->982|1967->996|1988->1008|2047->1046
                  LINES: 24->5|29->5|31->7|31->7|31->7|32->8|32->8|32->8|32->8|35->14|36->15|46->25|48->27|49->28|49->28|49->28|49->28
                  -- GENERATED --
              */
          