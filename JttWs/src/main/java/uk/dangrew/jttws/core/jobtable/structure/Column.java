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
import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.core.jobtable.web.PageSorting;
import uk.dangrew.jttws.mvc.repository.PageJob;

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
    * Access to the {@link ColumnType} associated with this {@link Column}.
    * @return the {@link ColumnType}.
    */
   public ColumnType type();
   
   /**
    * Access to a unique id for the {@link Column} that may be different to the name, and 
    * compatible with jsp and html fsyntax.
    * @return the id.
    */
   public String id();
   
   /**
    * Method to construct the {@link PageSorting}s for sorting options.
    * @return the {@link PageSorting}s for the possible sorting options on the {@link Column}.
    */
   public List< PageSorting > sortOptions();
   
   /**
    * Method to construct the {@link PageSorting}s for the filters available for the given {@link PageJob}s
    * and {@link JobTableParameters}. All jobs given should be accounted for and parameters should be used to preserve
    * existing filters even if they are not relevant.
    * @param jobs the {@link PageJob} the filters are for.
    * @param parameters the parameters containing existing filters.
    * @return the {@link ConfigurationEntry}s.
    */
   public List< PageFilter > filters( List< PageJob > jobs, JobTableParameters parameters );;
   
   /**
    * Method to provide the value to display in the table for the given job.
    * @param job the {@link PageJob}.
    * @return the {@link String} value to display in the table.
    */
   public String valueForJob( PageJob job );

   /**
    * Method to sort the given {@link List} given the {@link JobTableParameters} provided.
    * @param jobs the {@link List} of {@link PageJob}s to sort.
    * @param parameters the {@link JobTableParameters} for configuring the sort.
    */
   public void sort( List< PageJob > jobs, JobTableParameters parameters );
   
   /**
    * Method to filter the given {@link List} given the {@link JobTableParameters} provided.
    * @param jobs the {@link List} of {@link PageJob}s to filter.
    * @param parameters the {@link JobTableParameters} for configuring the filter.
    */
   public void filter( List< PageJob > jobs, JobTableParameters parameters );

}//End Interface
