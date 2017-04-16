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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import uk.dangrew.jttws.core.jobtable.common.ReverseSorting;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.core.jobtable.structure.ColumnType;
import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.core.jobtable.web.PageSorting;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationEntry;

/**
 * The {@link JobNameColumn} provides a {@link Column} to display the job name in the job
 * table and the operations associated.
 */
public class JobNameColumn implements Column {

   static final String NAME = "Job Name";
   static final String ID = "job_name";
   
   private final JobNameFilter filter;
   private final Map< String, SortingFunction > sortingFunctions;

   /**
    * Constructs a new {@link JobNameColumn}.
    */
   public JobNameColumn() {
      this( new JobNameAlphabetical(), new ReverseSorting( new JobNameAlphabetical() ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link JobNameColumn}.
    * @param sortingFunctions the {@link SortingFunction}s available.
    */
   JobNameColumn( SortingFunction... sortingFunctions ) {
      this.sortingFunctions = new HashMap<>();
      this.filter = new JobNameFilter();
      
      for ( SortingFunction function : sortingFunctions ) {
         this.sortingFunctions.put( function.name(), function );
      }
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public String name() {
      return staticName();
   }//End Method
   
   /**
    * Provides static access to the name, if constant.
    * @return the constant name of the {@link JobNameColumn}.
    */
   public static final String staticName(){
      return NAME;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public String id() {
      return staticId();
   }//End Method
   
   /**
    * Provides static access to the id, if constant.
    * @return the id of the {@link JobNameColumn}.
    */
   public static String staticId(){
      return ID;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public ColumnType type() {
      return ColumnType.String;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public String valueForJob( JwsJenkinsJob job ) {
      return job.name();
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void sort( List< JwsJenkinsJob > jobs, JobTableParameters parameters ) {
      Comparator< JwsJenkinsJob > function = sortingFunctions.get( parameters.sorting() );
      if ( function == null ) {
         throw new IllegalArgumentException( "Invalid sorting function applied: " + parameters.sorting() );
      }
      
      Collections.sort( jobs, function );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public List< PageSorting > sortOptions() {
      List< PageSorting > entries = new ArrayList<>();
      for ( Entry< String, SortingFunction > entry : sortingFunctions.entrySet() ) {
         PageSorting config = new PageSorting( entry.getKey() );
         config.inactive();
         entries.add( config );
      }
      
      return entries;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void filter( List< JwsJenkinsJob > jobs, JobTableParameters parameters ) {
      List< JwsJenkinsJob > excludedJobs = filter.identifyExclusions( jobs, parameters.filterValueFor( name() ) );
      jobs.removeAll( excludedJobs );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public List< PageFilter > filters( List< JwsJenkinsJob > jobs, JobTableParameters parameters ) {
      return filter.filterOptions( jobs, parameters.filterValueFor( name() ) );
   }//End Method

}//End Class
