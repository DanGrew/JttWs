/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.web;

import uk.dangrew.jttws.mvc.web.configuration.ConfigurationEntry;

/**
 * Custom extension for the ui sorting option.
 */
public class PageSorting extends ConfigurationEntry {

   /**
    * Constructs a new {@link PageSorting}.
    * @param name the name of the sorting.
    */
   public PageSorting( String name ) {
      super( name );
   }//End Constructor
   
}//End Class
