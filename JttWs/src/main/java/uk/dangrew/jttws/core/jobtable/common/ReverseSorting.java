/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.common;

import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

/**
 * The {@link ReverseSorting} is a general mechanism for reversing a {@link SortingFunction}.
 */
public class ReverseSorting implements SortingFunction {

   private static final String REVERSE_SUFFIX = " (Reverse)";
   private final SortingFunction function;
   
   /**
    * Constructs a new {@link ReverseSorting}.
    * @param function the {@link SortingFunction} to reverse.
    */
   public ReverseSorting( SortingFunction function ) {
      this.function = function;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public String name() {
      return staticName( function.name() );
   }//End Method
   
   /**
    * Method to deduce the static name of the {@link SortingFunction} from the given name.
    * @param sortingName the {@link SortingFunction} name.
    * @return the static name of the reversed sorting.
    */
   public static String staticName( String sortingName ) {
      return sortingName + REVERSE_SUFFIX;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public int compare( JwsJenkinsJob o1, JwsJenkinsJob o2 ) {
      return function.compare( o2, o1 );
   }//End Method

}//End Class
