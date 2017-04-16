/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.structure;

import java.util.List;

import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.mvc.repository.PageJob;

/**
 * The {@link Filter} provides an interface for filtering {@link PageJob}s in the job table.
 */
public interface Filter {

   /**
    * Method to provide the {@link PageFilter}s for the possible filters for the given {@link PageJob}s
    * and existing filters.
    * @param jobs the {@link PageJob}s the filters are relevant for.
    * @param existingFilters the existing filter applied.
    * @return the {@link PageFilter}s.
    */
   public List< PageFilter > filterOptions( List< PageJob > jobs, String existingFilters );
   
   /**
    * Method to identify the exclusions from the given {@link PageJob}, without modifying the jobs.
    * @param jobs the {@link List} of {@link PageJob} to filter but no modifications made.
    * @param value the value to filter for. The implementation will handle the format of the value.
    * @return the {@link List} of {@link PageJob}s that would be excluded from the given.
    */
   public List< PageJob > identifyExclusions( List< PageJob > jobs, String value );

}//End Interface
