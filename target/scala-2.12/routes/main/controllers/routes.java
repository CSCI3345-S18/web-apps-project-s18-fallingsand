// @GENERATOR:play-routes-compiler
// @SOURCE:/users/jburnett/Local/HTML-Documents/WebApps/finalProj/web-apps-project-s18-fallingsand/conf/routes
// @DATE:Sun May 06 18:06:52 CDT 2018

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReversePSController PSController = new controllers.ReversePSController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseLoginController LoginController = new controllers.ReverseLoginController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseWSController WSController = new controllers.ReverseWSController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseAssets Assets = new controllers.ReverseAssets(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReversePSController PSController = new controllers.javascript.ReversePSController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseLoginController LoginController = new controllers.javascript.ReverseLoginController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseWSController WSController = new controllers.javascript.ReverseWSController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseAssets Assets = new controllers.javascript.ReverseAssets(RoutesPrefix.byNamePrefix());
  }

}
