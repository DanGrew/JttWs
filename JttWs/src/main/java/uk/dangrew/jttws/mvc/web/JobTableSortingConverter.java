/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web;

/**
 * The {@link JobTableSortingConverter} provides a conversion for the {@link JobTableSorting}
 * options used by the web ui.
 */
public class JobTableSortingConverter {

   static final JobTableSorting DEFAULT_SORTING = JobTableSorting.NameIncreasing;
   
   /**
    * Method to convert the given {@link String} value.
    * @param sortValue the value to convert, can be null.
    * @return the {@link JobTableSorting} provided in {@link String} form, or the default
    * if no valid provided.
    */
   public JobTableSorting convert( String sortValue ) {
      JobTableSorting sorting = null;
      if ( sortValue == null ) {
         sorting = JobTableSorting.NameIncreasing;
      } else {
         try {
            sorting = JobTableSorting.valueOf( sortValue );
         } catch ( IllegalArgumentException exception ) {
            sorting = JobTableSorting.NameIncreasing;
         }
      }
      return sorting;
   }//End Method
   
}//End Class
