/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.structure;

import java.util.Comparator;

import uk.dangrew.jttws.mvc.repository.PageJob;

/**
 * The {@link SortingFunction} provides an interface for sorting {@link PageJob}s.
 */
public interface SortingFunction extends Comparator< PageJob > {

   /**
    * Access to the name of the {@link SortingFunction}.
    * @return the name of the {@link SortingFunction}.
    */
   public String name();
   
}//End Interface
