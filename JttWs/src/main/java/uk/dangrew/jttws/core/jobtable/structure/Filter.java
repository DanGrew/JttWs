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

import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

/**
 * The {@link Filter} provides an interface for filtering {@link JwsJenkinsJob}s in the job table.
 */
public interface Filter {

   /**
    * Method to identify the exclusions from the given {@link JwsJenkinsJob}, without modifying the jobs.
    * @param jobs the {@link List} of {@link JwsJenkinsJob} to filter but no modifications made.
    * @param value the value to filter for. The implementation will handle the format of the value.
    * @return the {@link List} of {@link JwsJenkinsJob}s that would be excluded from the given.
    */
   public List< JwsJenkinsJob > identifyExclusions( List< JwsJenkinsJob > jobs, String value );

}//End Interface
