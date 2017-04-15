/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.parameters;

import java.util.HashMap;
import java.util.Map;

import javafx.util.Pair;
import uk.dangrew.jttws.core.jobtable.jobname.JobNameAlphabetical;
import uk.dangrew.jttws.core.jobtable.jobname.JobNameColumn;

/**
 * The {@link JobTableParameters} provides a representation of the configuration made
 * by the user in the web interface.
 */
public class JobTableParameters {

   private String includedColumns;
   private String columnSort;

   private final Map< String, String > columnFilters;
   
   /**
    * Constructs a new {@link JobTableParameters}.
    */
   public JobTableParameters() {
      this.columnFilters = new HashMap<>();
      this.columnSort = JobNameAlphabetical.staticName();
   }//End Constructor
   
   /**
    * Method to set which column to sort by.
    * @param sortingFunction the name of the sorting function.
    */
   public void sortBy( String sortingFunction ) {
      columnSort = sortingFunction;
   }//End Method
   
   /**
    * Access to the value configuring the sort associated with a column.
    * @return the sorting function.
    */
   public String sorting() {
      return columnSort;
   }//End Method

   /**
    * Method to filter by a specific column using the given value.
    * @param column the column name.
    * @param filterValue the configuration value for filtering.
    */
   public void filterBy( String column, String filterValue ) {
      columnFilters.put( column, filterValue );
   }//End Method

   /**
    * Access to the value configuring the filter associated with the given column.
    * @param column the column filtered for.
    * @return the configuration value for filtering.
    */
   public String filterValueFor( String column ) {
      return columnFilters.get( column );
   }//End Method

   /**
    * Method to provide the included columns parameter.
    * @param columns the parameter for the columns to include.
    */
   public void includeColumns( String columns ) {
      includedColumns = columns;
   }//End Method

   /**
    * Access to the included columns parameter.
    * @return the parameter.
    */
   public String includedColumns() {
      return includedColumns;
   }//End Method

}//End Class
