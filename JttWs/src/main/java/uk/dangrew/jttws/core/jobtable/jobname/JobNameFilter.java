/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.jobname;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import uk.dangrew.jttws.core.jobtable.common.WebUiParameterParsing;
import uk.dangrew.jttws.core.jobtable.structure.Filter;
import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

/**
 * The {@link JobNameFilter} is a {@link Filter} for filtering {@link JwsJenkinsJob}s by name.
 */
public class JobNameFilter implements Filter {
   
   private final WebUiParameterParsing webParsing;
   
   /**
    * Constructs a new {@link JobNameFilter}.
    */
   public JobNameFilter() {
      this.webParsing = new WebUiParameterParsing();
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public List< PageFilter > filterOptions( List< JwsJenkinsJob > jobs, String existingFilters ) {
      Set< String > individualFilters = new LinkedHashSet<>( webParsing.parseStringList( existingFilters ) );
      
      List< PageFilter > entries = new ArrayList<>();
      for ( JwsJenkinsJob job : jobs ) {
         if ( !individualFilters.contains( job.name() ) ) {
            PageFilter entry = new PageFilter( job.name() );
            if ( !individualFilters.isEmpty() ) {
               entry.inactive();
            }
            entries.add( entry );
         }
      }
      
      for ( String existing : individualFilters ) {
         entries.add( new PageFilter( existing ) );
      }
      
      Collections.sort( entries );
      return entries;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public List< JwsJenkinsJob > identifyExclusions( List< JwsJenkinsJob > jobs, String value ) {
      List< JwsJenkinsJob > excludedJobs = new ArrayList<>();
      if ( value == null ) {
         return excludedJobs;
      }
      
      Set< String > includedJobs = new LinkedHashSet<>( webParsing.parseStringList( value ) );
      for ( JwsJenkinsJob job : jobs ) {
         if ( !includedJobs.contains( job.name() ) ) {
            excludedJobs.add( job );
         }
      }
      
      return excludedJobs;
   }//End Method

}//End Class
