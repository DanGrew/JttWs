/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.core.jobtable.structure.ColumnType;
import uk.dangrew.jttws.core.jobtable.structure.Filter;
import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.core.jobtable.web.PageSorting;
import uk.dangrew.jttws.mvc.repository.PageJob;

/**
 * The {@link AbstractColumn} provides a {@link Column} with a base implementation common to 
 * most {@link Column}s.
 */
public abstract class AbstractColumn implements Column {

   private final String name;
   private final String id;
   private final ColumnType type;
   
   private final Filter filter;
   private final Map< String, SortingFunction > sortingFunctions;

   /**
    * Constructs a new {@link AbstractColumn}.
    * @param name the name of the {@link Column}.
    * @param id the unique id to refer to client server properties.
    * @param type the {@link ColumnType}.
    * @param filter the {@link Filter} associated.
    * @param sortingFunctions the {@link SortingFunction}s available on the {@link Column}.
    */
   protected AbstractColumn( String name, String id, ColumnType type, Filter filter, SortingFunction... sortingFunctions ) {
      this.name = name;
      this.id = id;
      this.type = type;
      this.filter = filter;
      this.sortingFunctions = new HashMap<>();
      
      for ( SortingFunction function : sortingFunctions ) {
         this.sortingFunctions.put( function.name(), function );
      }
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public String name() {
      return name;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public String id() {
      return id;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public ColumnType type() {
      return type;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void sort( List< PageJob > jobs, JobTableParameters parameters ) {
      Comparator< PageJob > function = sortingFunctions.get( parameters.sorting() );
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
         config.setActive( false );
         entries.add( config );
      }
      
      return entries;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void filter( List< PageJob > jobs, JobTableParameters parameters ) {
      List< PageJob > excludedJobs = filter.identifyExclusions( jobs, parameters.filterValueFor( name() ) );
      jobs.removeAll( excludedJobs );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public List< PageFilter > filters( List< PageJob > jobs, JobTableParameters parameters ) {
      return filter.filterOptions( jobs, parameters.filterValueFor( name() ) );
   }//End Method

}//End Class
