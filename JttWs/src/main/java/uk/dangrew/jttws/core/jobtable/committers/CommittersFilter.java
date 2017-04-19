/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.committers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import uk.dangrew.jtt.model.users.JenkinsUser;
import uk.dangrew.jttws.core.jobtable.common.WebUiParameterParsing;
import uk.dangrew.jttws.core.jobtable.structure.Filter;
import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.mvc.repository.PageJob;

/**
 * The {@link CommittersFilter} is a {@link Filter} for filtering {@link PageJob}s by committers.
 */
public class CommittersFilter implements Filter {
   
   private final WebUiParameterParsing webParsing;
   
   /**
    * Constructs a new {@link CommittersFilter}.
    */
   public CommittersFilter() {
      this.webParsing = new WebUiParameterParsing();
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public List< PageFilter > filterOptions( List< PageJob > jobs, String existingFilters ) {
      Set< String > individualFilters = new LinkedHashSet<>( webParsing.parseStringList( existingFilters ) );
      
      Set< PageFilter > entries = new LinkedHashSet<>();
      for ( PageJob job : jobs ) {
         addEntriesForCulprits( job, individualFilters, entries );
      }
      
      for ( String existing : individualFilters ) {
         entries.add( new PageFilter( existing ) );
      }
      
      List< PageFilter > filters = new ArrayList<>( entries );
      Collections.sort( filters );
      return filters;
   }//End Method
   
   /**
    * Method to add the {@link PageFilter}s for the given {@link PageJob}'s culprits.
    * @param job the {@link PageJob} in question.
    * @param existingFilters the {@link Set} of {@link String} {@link PageFilter}s already applied.
    * @param filters the {@link PageFilter}s constructed for the current data.
    */
   private void addEntriesForCulprits( PageJob job, Set< String > existingFilters, Set< PageFilter > filters ){
      for ( JenkinsUser user : job.association().culprits() ) {
         if ( existingFilters.contains( user.nameProperty().get() ) ) {
            continue;
         }
               
         PageFilter entry = new PageFilter( user.nameProperty().get() );
         if ( !existingFilters.isEmpty() ) {
            entry.setActive( false );
         }
         filters.add( entry );
      }
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public List< PageJob > identifyExclusions( List< PageJob > jobs, String value ) {
      List< PageJob > excludedJobs = new ArrayList<>();
      if ( value == null || value.length() == 0 ) {
         return excludedJobs;
      }
      
      Set< String > includedUsers = new LinkedHashSet<>( webParsing.parseStringList( value ) );
      for ( PageJob job : jobs ) {
         if ( job.association().culprits().isEmpty() ) {
            continue;
         }
         
         boolean included = shouldIncludeJob( job, includedUsers );
         if ( !included ) {
            excludedJobs.add( job );
         }
      }
      
      return excludedJobs;
   }//End Method
   
   /**
    * Method to determine whether the given {@link PageJob} should be included.
    * @param job the {@link PageJob} in question.
    * @param includedUsers the {@link Set} of {@link String} {@link PageFilter}s applied.
    * @return true if the job should be included.
    */
   private boolean shouldIncludeJob( PageJob job, Set< String > includedUsers ){
      for ( JenkinsUser user : job.association().culprits() ) {
         if ( includedUsers.contains( user.nameProperty().get() ) ) {
            return true;
         }
      }
      return false;
   }//End Method

}//End Class
