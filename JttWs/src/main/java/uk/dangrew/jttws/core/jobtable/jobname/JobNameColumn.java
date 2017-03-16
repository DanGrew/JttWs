/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.jobname;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.dangrew.jttws.core.jobtable.common.ReverseSorting;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

/**
 * The {@link JobNameColumn} provides a {@link Column} to display the job name in the job
 * table and the operations associated.
 */
public class JobNameColumn implements Column {

   static final String NAME = "Job Name";
   
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
   @Override public final String name() {
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
   @Override public String valueForJob( JwsJenkinsJob job ) {
      return job.name();
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void sort( List< JwsJenkinsJob > jobs, JobTableParameters parameters ) {
      Comparator< JwsJenkinsJob > function = sortingFunctions.get( parameters.sortingFunction() );
      if ( function == null ) {
         throw new IllegalArgumentException( "Invalid sorting function applied: " + parameters.sortingFunction() );
      }
      
      Collections.sort( jobs, function );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void filter( List< JwsJenkinsJob > jobs, JobTableParameters parameters ) {
      List< JwsJenkinsJob > excludedJobs = filter.identifyExclusions( jobs, parameters.filterValueFor( name() ) );
      jobs.removeAll( excludedJobs );
   }//End Method

}//End Class
