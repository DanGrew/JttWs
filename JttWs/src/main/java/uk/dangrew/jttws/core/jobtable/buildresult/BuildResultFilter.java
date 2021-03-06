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
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import uk.dangrew.jtt.model.jobs.BuildResultStatus;
import uk.dangrew.jttws.core.jobtable.common.WebUiParameterParsing;
import uk.dangrew.jttws.core.jobtable.structure.Filter;
import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.mvc.repository.PageJob;

/**
 * The {@link BuildResultFilter} provides a {@link Filter} based on the {@link uk.dangrew.jtt.model.jobs.BuildResultStatus}.
 */
public class BuildResultFilter implements Filter {

   private final WebUiParameterParsing webParsing;
   
   /**
    * Constructs a new {@link BuildResultFilter}.
    */
   public BuildResultFilter() {
      this.webParsing = new WebUiParameterParsing();
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public List< PageFilter > filterOptions( List< PageJob > jobs, String existingFilters ) {
      Set< String > filtered = new LinkedHashSet<>( webParsing.parseStringList( existingFilters ) );
      
      List< PageFilter > filters = new ArrayList<>();
      for ( BuildResultStatus status : BuildResultStatus.values() ) {
         PageFilter entry = new PageFilter( status.displayName() );
         filters.add( entry );
         
         if ( !filtered.isEmpty() && !filtered.contains( status.displayName() ) ) {
            entry.setActive( false );
         }
      }
      
      Collections.sort( filters );
      return filters;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public List< PageJob > identifyExclusions( List< PageJob > jobs, String value ) {
      List< PageJob > excludedJobs = new ArrayList<>();
      if ( value == null || value.isEmpty() ) {
         return excludedJobs;
      }
      
      Set< String > includedStatuss = new LinkedHashSet<>( webParsing.parseStringList( value ) );
      for ( PageJob job : jobs ) {
         if ( !includedStatuss.contains( job.status().displayName() ) ) {
            excludedJobs.add( job );
         }
      }
      
      return excludedJobs;
   }//End Method

}//End Class
