/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.jobname;

import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.mvc.repository.PageJob;

/**
 * {@link JobNameAlphabetical} is a {@link SortingFunction} for the job name, ordering 
 * jobs alphabetically.
 */
public class JobNameAlphabetical implements SortingFunction {

   static final String NAME = "Job Name Aphabetical";
   
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
      return o1.name().compareTo( o2.name() );
   }//End Method

}//End Class
