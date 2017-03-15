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

/**
 * The {@link JobTableParameters} provides a representation of the configuration made
 * by the user in the web interface.
 */
public class JobTableParameters {

   private String columnToSortBy;
   private String sortingFunction;
   private String includedColumns;
   
   private final Map< String, String > columnFilters;
   
   /**
    * Constructs a new {@link JobTableParameters}.
    */
   public JobTableParameters() {
      this.columnFilters = new HashMap<>();
   }//End Constructor
   
   /**
    * Method to set which column to sort by.
    * @param columnName the name of the column to sort by.
    */
   public void sortColumn( String columnName ) {
      this.columnToSortBy = columnName;
   }//End Method

   /**
    * Method to set which function to use when sorting.
    * @param sortingFunction the name of the sorting function.
    */
   public void sortBy( String sortingFunction ) {
      this.sortingFunction = sortingFunction;
   }//End Method

   /**
    * Access to the name of the column to sort by.
    * @return the column name.
    */
   public String columnToSortBy() {
      return columnToSortBy;
   }//End Method

   /**
    * Access to the name of the sorting function to use.
    * @return the name of the sorting function.
    */
   public String sortingFunction() {
      return sortingFunction;
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
