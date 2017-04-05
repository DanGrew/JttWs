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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import uk.dangrew.jttws.core.jobtable.common.ReverseSorting;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.core.jobtable.structure.ColumnType;
import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationEntry;

/**
 * The {@link BuildResultColumn} represents the column of data holding the {@link uk.dangrew.jtt.model.jobs.BuildResultStatus}
 * associated with the {@link JwsJenkinsJob}.
 */
public class BuildResultColumn implements Column {
   
   static final String NAME = "Result";
   
   private final Map< String, SortingFunction > sortings;
   private final BuildResultFilter filter;
   
   /**
    * Constructs a new {@link BuildResultColumn}.
    */
   public BuildResultColumn() {
      this.sortings = new HashMap<>();
      SortingFunction alphabetical = new BuildResultAlphabetical();
      this.sortings.put( alphabetical.name(), alphabetical );
      SortingFunction reverseAlphabetical = new ReverseSorting( new BuildResultAlphabetical() );
      this.sortings.put( reverseAlphabetical.name(), reverseAlphabetical );
      SortingFunction severity = new BuildResultSeverity();
      this.sortings.put( severity.name(), severity );
      SortingFunction reverseSeverity = new ReverseSorting( new BuildResultSeverity() );
      this.sortings.put( reverseSeverity.name(), reverseSeverity );
      
      this.filter = new BuildResultFilter();
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public String name() {
      return staticName();
   }//End Method
   
   /**
    * Provides static access to the name, if constant.
    * @return the constant name of the {@link BuildResultColumn}.
    */
   public static String staticName(){
      return NAME;
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
      return job.status().displayName();
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void sort( List< JwsJenkinsJob > jobs, JobTableParameters parameters ) {
      if ( !parameters.sorting().getKey().equals( name() ) ) {
         throw new IllegalArgumentException( "Sorting on " + name() + " instead of " + parameters.sorting().getKey() );
      }
      
      SortingFunction sorting = sortings.get( parameters.sorting().getValue() );
      if ( sorting == null ) {
         throw new IllegalArgumentException( "No sorting defined on " + name() + " for " + parameters.sorting().getValue() );
      }
      
      Collections.sort( jobs, sorting );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public List< ConfigurationEntry > sortOptions() {
      List< ConfigurationEntry > entries = new ArrayList<>();
      for ( Entry< String, SortingFunction > entry : sortings.entrySet() ) {
         ConfigurationEntry config = new ConfigurationEntry( entry.getKey() );
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
   @Override public List< ConfigurationEntry > filters( List< JwsJenkinsJob > jobs, JobTableParameters parameters ) {
      return filter.filterOptions( jobs, parameters.filterValueFor( name() ) );
   }//End Method

}//End Class
