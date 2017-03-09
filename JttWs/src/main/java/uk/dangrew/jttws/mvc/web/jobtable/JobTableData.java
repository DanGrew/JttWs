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
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

/**
 * {@link JobTableData} provides the data to be held in the job table based on configuration
 * made by the user.
 */
public class JobTableData {

   private final Set< JwsJenkinsJob > displayedJobs;
   
   /**
    * Constructs a new {@link JobTableData}.
    */
   public JobTableData() {
      this.displayedJobs = new LinkedHashSet<>();
   }//End Constructor
   
   /**
    * Method to provide a {@link List} of {@link JwsJenkinsJob}s to show in the table.
    * @param jobs the {@link JwsJenkinsJob}s to show. These will be appended without duplicates.
    */
   public void provide( List< JwsJenkinsJob > jobs ) {
      displayedJobs.addAll( jobs );
   }//End Method

   /**
    * Getter for the {@link JwsJenkinsJob}s to display based on the filtering applied.
    * @return the {@link List} of {@link JwsJenkinsJob}s to display.
    */
   public List< JwsJenkinsJob > getDisplayedJobs() {
      return new ArrayList<>( displayedJobs );
   }//End Method

   /**
    * Method to filter {@link JwsJenkinsJob}s by their name.
    * @param jobs the array of names of {@link JwsJenkinsJob}s to include.
    */
   public void filterJobName( String... jobs ) {
      Set< String > include = new HashSet<>( Arrays.asList( jobs ) );
      
      final Set< JwsJenkinsJob > toRemove = new HashSet<>();
      displayedJobs.forEach( j -> {
         if ( !include.contains( j.name() ) ) {
            toRemove.add( j );
         }
      } );
      
      displayedJobs.removeAll( toRemove );
   }//End Method

   /**
    * Method to filter the {@link JwsJenkinsJob}s to display given the committers.
    * @param committers the array of committers names that are relevant.
    */
   public void filterForCommitters( String... committers ) {
      Set< String > include = new HashSet<>( Arrays.asList( committers ) );
      
      final Set< JwsJenkinsJob > toRemove = new HashSet<>();
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
