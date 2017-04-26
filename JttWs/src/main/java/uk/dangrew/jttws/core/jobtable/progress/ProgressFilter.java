/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.progress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import uk.dangrew.jttws.core.jobtable.common.WebUiParameterParsing;
import uk.dangrew.jttws.core.jobtable.structure.Filter;
import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.mvc.repository.PageJob;

/**
 * The {@link ProgressFilter} is a {@link Filter} for filtering {@link PageJob}s by progress.
 */
public class ProgressFilter implements Filter {
   
   private final WebUiParameterParsing webParsing;
   private final Set< ProgressRangeFilter > ranges;
   
   /**
    * Constructs a new {@link ProgressFilter}.
    */
   public ProgressFilter() {
      this( 
               new ProgressRangeFilter( 0, true, 10, false ),
               new ProgressRangeFilter( 10, true, 20, false ),
               new ProgressRangeFilter( 20, true, 30, false ),
               new ProgressRangeFilter( 30, true, 40, false ),
               new ProgressRangeFilter( 40, true, 50, false ),
               new ProgressRangeFilter( 50, true, 60, false ),
               new ProgressRangeFilter( 60, true, 70, false ),
               new ProgressRangeFilter( 70, true, 80, false ),
               new ProgressRangeFilter( 80, true, 90, false ),
               new ProgressRangeFilter( 90, true, 100, true )
      );
   }//End Constructor
   
   /**
    * Constructs a new {@link ProgressFilter} with the given {@link ProgressRangeFilter}s.
    * @param filters the {@link ProgressRangeFilter}s.
    */
   ProgressFilter( ProgressRangeFilter... filters ) {
      this.webParsing = new WebUiParameterParsing();
      this.ranges = new LinkedHashSet<>( Arrays.asList( filters ) );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public List< PageFilter > filterOptions( List< PageJob > jobs, String existingFilters ) {
      Set< String > individualFilters = new LinkedHashSet<>( webParsing.parseStringList( existingFilters ) );
      
      Set< ProgressRangeFilter > appliedFilters = new HashSet<>( ranges );
      if ( !individualFilters.isEmpty() ) {
         appliedFilters.removeIf( f -> !individualFilters.contains( f.name() ) );
      }
      
      List< PageFilter > entries = new ArrayList<>();
      for ( ProgressRangeFilter filter : ranges ) {
         if ( !individualFilters.contains( filter.name() ) ) {
            PageFilter pageFilter = new PageFilter( filter.name() );
            pageFilter.setActive( appliedFilters.contains( filter ) );
            entries.add( pageFilter );
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
   @Override public List< PageJob > identifyExclusions( List< PageJob > jobs, String value ) {
      List< PageJob > excludedJobs = new ArrayList<>();
      if ( value == null || value.isEmpty() ) {
         return excludedJobs;
      }
      
      Set< String > individualFilters = new LinkedHashSet<>( webParsing.parseStringList( value ) );
      Set< ProgressRangeFilter > appliedFilters = new HashSet<>( ranges );
      appliedFilters.removeIf( f -> !individualFilters.contains( f.name() ) );
      
      for ( PageJob job : jobs ) {
         if ( shouldBeExcluded( appliedFilters, job ) ) {
            excludedJobs.add( job );
         }
      }
      
      return excludedJobs;
   }//End Method
   
   /**
    * Method to determine whether the given {@link PageJob} should be excluded given the {@link ProgressRangeFilter}s
    * applied.
    * @param filters the {@link Set} of {@link ProgressRangeFilter}s applied.
    * @param job the {@link PageJob} in question.
    * @return true if the {@link PageJob} should be excluded.
    */
   private boolean shouldBeExcluded( Set< ProgressRangeFilter > filters, PageJob job ){
      for ( ProgressRangeFilter filter : filters ) {
         if ( filter.matches( job.progress() ) ) {
            return false;
         }
      }
      
      return true;
   }//End Method

}//End Class
