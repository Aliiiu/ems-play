// @GENERATOR:play-routes-compiler
// @SOURCE:conf/ems.routes

package controllers;

import ems.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseEmployeeController EmployeeController = new controllers.ReverseEmployeeController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseEmployeeController EmployeeController = new controllers.javascript.ReverseEmployeeController(RoutesPrefix.byNamePrefix());
  }

}
