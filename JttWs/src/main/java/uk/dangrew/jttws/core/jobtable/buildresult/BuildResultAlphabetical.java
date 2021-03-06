/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.buildresult;

import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.mvc.repository.PageJob;

/**
 * {@link BuildResultAlphabetical} is a {@link SortingFunction} for the {@link uk.dangrew.jtt.model.jobs.BuildResultStatus}, ordering 
 * jobs by their status alphabetically.
 */
public class BuildResultAlphabetical implements SortingFunction {

   static final String NAME = "Result Aphabetical";
   
   /**
    * {@inheritDoc}
    */
   @Override public String name() {
      return staticName();
   }//End Method
   
   /**
    * Access to the static name of the {@link SortingFunction}.
    * @return the name.
    */
   public static String staticName(){
      return NAME;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public int compare( PageJob o1, PageJob o2 ) {
      return o1.status().displayName().compareTo( o2.status().displayName() );
   }//End Method

}//End Class
