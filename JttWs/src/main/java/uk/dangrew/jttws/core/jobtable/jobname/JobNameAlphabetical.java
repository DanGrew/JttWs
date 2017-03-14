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
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

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
      return NAME;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public int compare( JwsJenkinsJob o1, JwsJenkinsJob o2 ) {
      return o1.name().compareTo( o2.name() );
   }//End Method

}//End Class
