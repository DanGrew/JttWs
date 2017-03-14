/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link WebUiParameterParsing} provides parsing utilities for web ui related paramaters.
 */
public class WebUiParameterParsing {

   /**
    * Method to parse a list of {@link String}s separated by commas.
    * @param value the value such as 'me, you, irene'.
    * @return the {@link List} of separated {@link String}s, trimmed.
    */
   public List< String > parseStringList( String value ) {
      if ( value == null ) {
         return new ArrayList<>();
      }
      String[] splitValue = value.replaceAll( ", ", "," ).split( "," );
      return Arrays.asList( splitValue );
   }//End Method

}//End Class
