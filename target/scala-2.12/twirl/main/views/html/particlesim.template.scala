
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


Seq[Any](format.raw/*6.1*/("""
"""),_display_(/*7.2*/main("Multiplayer Particle Simulation", assetsFinder)/*7.55*/ {_display_(Seq[Any](format.raw/*7.57*/("""
	"""),format.raw/*8.2*/("""<div id="particle-menu"></div>
	<canvas id="particle-canvas" width="600" height="600">
	"""),format.raw/*13.5*/("""
	 """),format.raw/*14.3*/("""</canvas>
	<div id="particle-members"></div>
""")))}),format.raw/*16.2*/("""

"""),format.raw/*18.98*/("""
"""),format.raw/*19.1*/("""<script src=""""),_display_(/*19.15*/assetsFinder/*19.27*/.path("javascripts/particlesystem.js")),format.raw/*19.65*/("""" type="text/javascript"></script>
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
                  DATE: Sun May 06 18:10:31 CDT 2018
                  SOURCE: /users/jburnett/Local/HTML-Documents/WebApps/finalProj/web-apps-project-s18-fallingsand/app/views/particlesim.scala.html
                  HASH: 6a465ea77b07d63fa5896e0a104907324f01acdd
                  MATRIX: 879->137|1027->192|1054->194|1115->247|1154->249|1182->251|1297->420|1327->423|1403->469|1433->568|1461->569|1502->583|1523->595|1582->633
                  LINES: 24->5|29->6|30->7|30->7|30->7|31->8|33->13|34->14|36->16|38->18|39->19|39->19|39->19|39->19
                  -- GENERATED --
              */
          