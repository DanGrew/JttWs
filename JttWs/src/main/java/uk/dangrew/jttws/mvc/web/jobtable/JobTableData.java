/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web.jobtable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import uk.dangrew.jtt.model.users.JenkinsUser;
import uk.dangrew.jttws.mvc.repository.PageJob;

/**
 * {@link JobTableData} provides the data to be held in the job table based on configuration
 * made by the user.
 */
public class JobTableData {

   private final Set< PageJob > displayedJobs;
   
   /**
    * Constructs a new {@link JobTableData}.
    */
   public JobTableData() {
      this.displayedJobs = new LinkedHashSet<>();
   }//End Constructor
   
   /**
    * Method to provide a {@link List} of {@link PageJob}s to show in the table.
    * @param jobs the {@link PageJob}s to show. These will be appended without duplicates.
    */
   public void provide( List< PageJob > jobs ) {
      displayedJobs.addAll( jobs );
   }//End Method

   /**
    * Getter for the {@link PageJob}s to display based on the filtering applied.
    * @return the {@link List} of {@link PageJob}s to display.
    */
   public List< PageJob > getDisplayedJobs() {
      return new ArrayList<>( displayedJobs );
   }//End Method

   /**
    * Method to filter {@link PageJob}s by their name.
    * @param jobs the array of names of {@link PageJob}s to include.
    */
   public void filterJobName( String... jobs ) {
      Set< String > include = new HashSet<>( Arrays.asList( jobs ) );
      
      final Set< PageJob > toRemove = new HashSet<>();
      displayedJobs.forEach( j -> {
         if ( !include.contains( j.name() ) ) {
            toRemove.add( j );
         }
      } );
      
      displayedJobs.removeAll( toRemove );
   }//End Method

   /**
    * Method to filter the {@link PageJob}s to display given the committers.
    * @param committers the array of committers names that are relevant.
    */
   public void filterForCommitters( String... committers ) {
      Set< String > include = new HashSet<>( Arrays.asList( committers ) );
      
      final Set< PageJob > toRemove = new HashSet<>();
      displayedJobs.forEach( j -> {
         if ( j.association().culprits().isEmpty() ) {
            return;
         }
         
         for ( JenkinsUser u : j.association().culprits() ) {
            if ( include.contains( u.nameProperty().get() ) ) {
               return;
            }
         }
         
         toRemove.add( j );
      } );
      
      displayedJobs.removeAll( toRemove );
   }//End Method

}//End Class
