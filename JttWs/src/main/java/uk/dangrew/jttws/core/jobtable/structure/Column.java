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

import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

/**
 * The {@link Column} provides an interface for a representation of a data column
 * in the jobs table
 */
public interface Column {

   /**
    * Access to the name of the column, that can be displayed.
    * @return the column name.
    */
   public String name();

   /**
    * Method to sort the given {@link List} given the {@link JobTableParameters} provided.
    * @param jobs the {@link List} of {@link JwsJenkinsJob}s to sort.
    * @param parameters the {@link JobTableParameters} for configuring the sort.
    */
   public void sort( List< JwsJenkinsJob > jobs, JobTableParameters parameters );
   
   /**
    * Method to filter the given {@link List} given the {@link JobTableParameters} provided.
    * @param jobs the {@link List} of {@link JwsJenkinsJob}s to filter.
    * @param parameters the {@link JobTableParameters} for configuring the filter.
    */
   public void filter( List< JwsJenkinsJob > jobs, JobTableParameters parameters );

}//End Interface
