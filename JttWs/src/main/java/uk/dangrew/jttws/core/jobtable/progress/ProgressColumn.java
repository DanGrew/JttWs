/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.progress;

import uk.dangrew.jttws.core.jobtable.common.AbstractColumn;
import uk.dangrew.jttws.core.jobtable.common.ReverseSorting;
import uk.dangrew.jttws.core.jobtable.structure.ColumnType;
import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.mvc.repository.PageJob;

/**
 * The {@link ProgressColumn} provides a {@link Column} to display the job progress in the job
 * table and the operations associated.
 */
public class ProgressColumn extends AbstractColumn {

   static final String NAME = "Progress";
   static final String ID = "progress";
   
   /**
    * Constructs a new {@link ProgressColumn}.
    */
   public ProgressColumn() {
      this( new ProgressSorting(), new ReverseSorting( new ProgressSorting() ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link ProgressColumn}.
    * @param sortingFunctions the {@link SortingFunction}s available.
    */
   ProgressColumn( SortingFunction... sortingFunctions ) {
      super( staticName(), staticId(), ColumnType.ProgressBar, new ProgressFilter(), sortingFunctions );
   }//End Constructor

   /**
    * Provides static access to the name, if constant.
    * @return the constant name of the {@link ProgressColumn}.
    */
   public static final String staticName(){
      return NAME;
   }//End Method
   
   /**
    * Provides static access to the id, if constant.
    * @return the id of the {@link ProgressColumn}.
    */
   public static String staticId(){
      return ID;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public String valueForJob( PageJob job ) {
      return Integer.toString( job.progress() );
   }//End Method

}//End Class
