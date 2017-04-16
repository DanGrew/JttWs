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
 * Custom extension to separate the types of {@link ConfigurationEntry}.
 */
public class PageFilter extends ConfigurationEntry {

   /**
    * Constructs a new {@link PageFilter}.
    * @param name the filter name.
    */
   public PageFilter( String name ) {
      super( name );
   }//End Constructor

}//End Class
