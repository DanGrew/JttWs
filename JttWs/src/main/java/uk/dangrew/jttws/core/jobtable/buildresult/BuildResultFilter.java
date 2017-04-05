/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.buildresult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import uk.dangrew.jtt.model.jobs.BuildResultStatus;
import uk.dangrew.jttws.core.jobtable.common.WebUiParameterParsing;
import uk.dangrew.jttws.core.jobtable.structure.Filter;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationEntry;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationProvider;

/**
 * The {@link BuildResultFilter} provides a {@link Filter} based on the {@link uk.dangrew.jtt.model.jobs.BuildResultStatus}.
 */
public class BuildResultFilter implements Filter {

   private final WebUiParameterParsing webParsing;
   private final ConfigurationProvider configuration;
   
   /**
    * Constructs a new {@link BuildResultFilter}.
    */
   public BuildResultFilter() {
      this.webParsing = new WebUiParameterParsing();
      this.configuration = new ConfigurationProvider();
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public List< ConfigurationEntry > filterOptions( List< JwsJenkinsJob > jobs, String existingFilters ) {
      Set< String > filtered = new LinkedHashSet<>( webParsing.parseStringList( existingFilters ) );
      List< ConfigurationEntry > entries = configuration.provideConfigurationEntries( 
               Arrays.asList( BuildResultStatus.values() ), 
               status -> filtered.isEmpty() || filtered.contains( status.displayName() ), 
               status -> status.displayName() 
      );
      
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
      
      Set< String > includedStatuss = new LinkedHashSet<>( webParsing.parseStringList( value ) );
      for ( JwsJenkinsJob job : jobs ) {
         if ( !includedStatuss.contains( job.status().displayName() ) ) {
            excludedJobs.add( job );
         }
      }
      
      return excludedJobs;
   }//End Method

}//End Class
