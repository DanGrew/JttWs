/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web.configuration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.WebUtils;

/**
 * The {@link CookieManager} is responsible for managing the application and usage of {@link Cookie}s when
 * responding to a request from the web ui.
 */
public class CookieManager {

   /**
    * Method to retrieve the value associated with the cookie for the parameter given. This will assume the 
    * parameter value provided over the existing cookie value, or null if no value supplied anywhere.
    * @param parameter the parameter in the {@link HttpServletRequest}.
    * @param request the {@link HttpServletRequest}.
    * @param response the {@link HttpServletResponse} to add the {@link Cookie} to.
    * @return the value found for the parameter.
    */
   public String retrieveCookie( String parameter, HttpServletRequest request, HttpServletResponse response ) {
      String parameterValue = request.getParameter( parameter );
      if ( parameterValue != null ) {
         saveCookie( parameter, parameterValue, response );
         return parameterValue;
      } else {
         Cookie cookie = WebUtils.getCookie( request, parameter );
         if ( cookie == null ) {
            return null;
         } else {
            return cookie.getValue();
         }
      }
   }//End Method
   
   /**
    * Method to save a parameter and value as a {@link Cookie}.
    * @param parameter the parameter name.
    * @param value the parameter value.
    * @param response the {@link HttpServletResponse} to add the {@link Cookie} to.
    */
   public void saveCookie( String parameter, String value, HttpServletResponse response ) {
      response.addCookie( new Cookie( parameter, value ) );
   }//End Method
   
}//End Class
