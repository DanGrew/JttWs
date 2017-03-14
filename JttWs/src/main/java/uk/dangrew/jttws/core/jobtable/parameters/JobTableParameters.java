/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.parameters;

/**
 * The {@link JobTableParameters} provides a representation of the configuration made
 * by the user in the web interface.
 */
public class JobTableParameters {

   private String columnToSortBy;
   private String sortingFunction;
   
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

}//End Class
