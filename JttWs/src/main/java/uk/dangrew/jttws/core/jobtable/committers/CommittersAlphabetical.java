/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.committers;

import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.mvc.repository.PageJob;

/**
 * {@link CommittersAlphabetical} is a {@link SortingFunction} for the committers on a job, ordering 
 * committers alphabetically.
 */
public class CommittersAlphabetical implements SortingFunction {

   static final String NAME = "Committers Aphabetical";
   
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
      return o1.committers().compareTo( o2.committers() );
   }//End Method

}//End Class
