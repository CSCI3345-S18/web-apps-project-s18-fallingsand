
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
	"""),_display_(/*12.3*/if(loginForm.hasGlobalErrors)/*12.32*/{_display_(Seq[Any](format.raw/*12.33*/("""
	"""),format.raw/*13.2*/("""<br>
	<h3 class="centered">"""),_display_(/*14.24*/(loginForm.globalError.get.message)),format.raw/*14.59*/("""</h3>
	""")))}),format.raw/*15.3*/("""
	"""),format.raw/*16.2*/("""<form id="loginandregister">
		"""),_display_(/*17.4*/if(loginForm.hasErrors && loginForm.error("username").isDefined)/*17.68*/ {_display_(Seq[Any](format.raw/*17.70*/("""
		"""),format.raw/*18.3*/("""<h3 class="centered">"""),_display_(/*18.25*/(loginForm.error("username").get.message)),format.raw/*18.66*/("""</h3>
		<br>
		""")))}),format.raw/*20.4*/("""
    	"""),_display_(/*21.7*/helper/*21.13*/.inputText(loginForm("username"))),format.raw/*21.46*/("""
	    """),_display_(/*22.7*/if(loginForm.hasErrors && loginForm.error("password").isDefined)/*22.71*/ {_display_(Seq[Any](format.raw/*22.73*/("""
	    """),format.raw/*23.6*/("""<br>
	    <h3 class="centered">"""),_display_(/*24.28*/(loginForm.error("password").get.message)),format.raw/*24.69*/("""</h3>
	    <br>
	    """)))}),format.raw/*26.7*/("""
	    """),_display_(/*27.7*/helper/*27.13*/.inputPassword(loginForm("password"))),format.raw/*27.50*/("""
	    """),format.raw/*28.6*/("""<button type="submit" form="loginandregister" formaction=""""),_display_(/*28.65*/helper/*28.71*/.CSRF(routes.LoginController.login)),format.raw/*28.106*/("""" value="Login">Login</button>
	    <button type="submit" form="loginandregister" formaction=""""),_display_(/*29.65*/helper/*29.71*/.CSRF(routes.LoginController.register)),format.raw/*29.109*/("""" value="Register">Register</button>
	</form>
	<br>
	<h3 class="centered">NOTE: passwords are stored as plain text, don't expect confidentiality.</h3>
	</div>
""")))}),format.raw/*34.2*/("""

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
                  DATE: Sun Apr 22 20:51:31 CDT 2018
                  SOURCE: C:/Users/kayla/Documents/web-apps-project-s18-fallingsand/app/views/login.scala.html
                  HASH: 6f64033176cda7391393e4f41c6ea3cd3be2416d
                  MATRIX: 908->137|1111->247|1138->249|1201->304|1240->306|1268->308|1434->448|1472->477|1511->478|1540->480|1595->508|1651->543|1689->551|1718->553|1776->585|1849->649|1889->651|1919->654|1968->676|2030->717|2076->733|2109->740|2124->746|2178->779|2211->786|2284->850|2324->852|2357->858|2416->890|2478->931|2530->953|2563->960|2578->966|2636->1003|2669->1009|2755->1068|2770->1074|2827->1109|2949->1204|2964->1210|3024->1248|3214->1408
                  LINES: 24->5|29->6|30->7|30->7|30->7|31->8|35->12|35->12|35->12|36->13|37->14|37->14|38->15|39->16|40->17|40->17|40->17|41->18|41->18|41->18|43->20|44->21|44->21|44->21|45->22|45->22|45->22|46->23|47->24|47->24|49->26|50->27|50->27|50->27|51->28|51->28|51->28|51->28|52->29|52->29|52->29|57->34
                  -- GENERATED --
              */
          