/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

/**
 * The {@link TableData} represents the available information and operations associated
 * with the job table in the web interface.
 */
public class TableData {

   private final List< Column > orderedTableColumns;
   private final Map< String, Column > columns;
   
   /**
    * Constructs a new {@link TableData}.
    * @param columns the {@link Column}s that are available.
    */
   TableData( Column... columns ) {
      this.columns = new HashMap<>();
      this.orderedTableColumns = new ArrayList<>();
      
      this.orderedTableColumns.addAll( Arrays.asList( columns ) );
      for ( Column column : columns ) {
         this.columns.put( column.name(), column );
      }
   }//End Constructor
   
   /**
    * Access to the ordered {@link Column}s. Note: defensive copy.
    * @return the {@link List} of {@link Column}s.
    */
   public List< Column > columns() {
      return new ArrayList<>( orderedTableColumns );
   }//End Method

   /**
    * Method to sort the given {@link JwsJenkinsJob}s given the {@link JobTableParameters}. This will
    * look up the relevant column and sorting function and apply.
    * @param jobs the {@link List} of {@link JwsJenkinsJob}s to sort.
    * @param parameters the {@link JobTableParameters} providing the sorting method.
    */
   public void sort( List< JwsJenkinsJob > jobs, JobTableParameters parameters ) {
      Column column = columns.get( parameters.columnToSortBy() );
      if ( column == null ) {
         throw new IllegalArgumentException( "Invalid column to sort by: " + parameters.columnToSortBy() );
      }
      
      column.sort( jobs, parameters );
   }//End Method

}//End Class
