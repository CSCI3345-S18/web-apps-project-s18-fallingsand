
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

object login extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[Form[NewUser],MessagesRequestHeader,Flash,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /*
 * This template takes a two arguments, a String containing a
 * message to display and an AssetsFinder to locate static assets.
 */
  def apply/*5.2*/(loginForm: Form[NewUser])(implicit request: MessagesRequestHeader, flash: Flash, assetsFinder: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*6.1*/("""
"""),_display_(/*7.2*/main("Particle Simulator Login/Register", assetsFinder)/*7.57*/ {_display_(Seq[Any](format.raw/*7.59*/("""
	"""),format.raw/*8.2*/("""<div class="centered fullscreen">
	<h2 class="centered">Particle Simulator</h2>
	<br>
	<h3 class="centered">Please login or register</h3>
	<form id="loginandregister">
    	"""),_display_(/*13.7*/helper/*13.13*/.inputText(loginForm("username"))),format.raw/*13.46*/("""
	    """),_display_(/*14.7*/helper/*14.13*/.inputPassword(loginForm("password"))),format.raw/*14.50*/("""
	    """),format.raw/*15.6*/("""<button type="submit" form="loginandregister" formaction=""""),_display_(/*15.65*/helper/*15.71*/.CSRF(routes.LoginController.login)),format.raw/*15.106*/("""" value="Login"></button>
	    <button type="submit" form="loginandregister" formaction=""""),_display_(/*16.65*/helper/*16.71*/.CSRF(routes.LoginController.register)),format.raw/*16.109*/("""" value="Register"></button>
	</form>
	<br>
	<h3 class="centered">NOTE: passwords are stored as plain text, don't expect confidentiality.</h3>
	</div>
""")))}),format.raw/*21.2*/("""
"""),format.raw/*22.1*/("""<script src=""""),_display_(/*22.15*/assetsFinder/*22.27*/.path("javascripts/websocket.js")),format.raw/*22.60*/("""" type="text/javascript"></script>
<script src=""""),_display_(/*23.15*/assetsFinder/*23.27*/.path("javascripts/particlesim.js")),format.raw/*23.62*/("""" type="text/javascript"></script>

"""))
      }
    }
  }

  def render(loginForm:Form[NewUser],request:MessagesRequestHeader,flash:Flash,assetsFinder:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(loginForm)(request,flash,assetsFinder)

  def f:((Form[NewUser]) => (MessagesRequestHeader,Flash,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (loginForm) => (request,flash,assetsFinder) => apply(loginForm)(request,flash,assetsFinder)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Sun Apr 15 20:32:38 CDT 2018
                  SOURCE: C:/Users/kayla/Documents/web-apps-project-s18-fallingsand/app/views/login.scala.html
                  HASH: 3627ccd359ef5ca08fbd6653f02a571c50d10a13
                  MATRIX: 908->137|1111->247|1138->249|1201->304|1240->306|1268->308|1468->482|1483->488|1537->521|1570->528|1585->534|1643->571|1676->577|1762->636|1777->642|1834->677|1951->767|1966->773|2026->811|2208->963|2236->964|2277->978|2298->990|2352->1023|2428->1072|2449->1084|2505->1119
                  LINES: 24->5|29->6|30->7|30->7|30->7|31->8|36->13|36->13|36->13|37->14|37->14|37->14|38->15|38->15|38->15|38->15|39->16|39->16|39->16|44->21|45->22|45->22|45->22|45->22|46->23|46->23|46->23
                  -- GENERATED --
              */
          