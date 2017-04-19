/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.committers;

import uk.dangrew.jttws.core.jobtable.common.AbstractColumn;
import uk.dangrew.jttws.core.jobtable.common.ReverseSorting;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.core.jobtable.structure.ColumnType;
import uk.dangrew.jttws.core.jobtable.structure.Filter;
import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.mvc.repository.PageJob;

/**
 * The {@link CommittersColumn} provides a {@link Column} to display the committers of a job in the job
 * table and the operations associated.
 */
public class CommittersColumn extends AbstractColumn implements Column {

   static final String NAME = "Committers";
   static final String ID = NAME;
   
   /**
    * Constructs a new {@link CommittersColumn}.
    */
   public CommittersColumn() {
      this( 
               new CommittersFilter(), 
               new CommittersAlphabetical(), 
               new ReverseSorting( new CommittersAlphabetical() ) 
      );
   }//End Constructor
   
   /**
    * Constructs a new {@link CommittersColumn}.
    * @param filter the associated {@link Filter}.
    * @param sortings the {@link SortingFunction}s available on the {@link Column}.
    */
   CommittersColumn( Filter filter, SortingFunction... sortings ){
      super( 
               staticName(), 
               staticId(), 
               ColumnType.String, 
               filter, 
               sortings
      );
   }//End Constructor

   /**
    * Static access to the name.
    * @return the name.
    */
   public static String staticName(){
      return NAME;
   }//End Method
   
   /**
    * Static access to the id.
    * @return the id.
    */
   public static String staticId(){
      return ID;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public String valueForJob( PageJob job ) {
      return job.committers();
   }//End Method
   
}//End Class
