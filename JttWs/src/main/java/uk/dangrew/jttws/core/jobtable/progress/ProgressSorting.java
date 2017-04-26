/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.progress;

import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.mvc.repository.PageJob;

/**
 * {@link ProgressSorting} is a {@link SortingFunction} for the job progress, ordering 
 * jobs by increasing progress.
 */
public class ProgressSorting implements SortingFunction {

   static final String NAME = "Progress";
   
   /**
    * {@inheritDoc}
    */
   @Override public String name() {
      return staticName();
   }//End Method
   
   /**
    * Access to the static name.
    * @return the name.
    */
   public static String staticName(){
      return NAME;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public int compare( PageJob o1, PageJob o2 ) {
      return Integer.compare( o1.progress(), o2.progress() );
   }//End Method

}//End Class
