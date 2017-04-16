/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.dangrew.jttws.core.jobtable.buildresult.BuildResultColumn;
import uk.dangrew.jttws.core.jobtable.jobname.JobNameColumn;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.core.jobtable.web.PageSorting;
import uk.dangrew.jttws.mvc.repository.PageJob;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationEntry;

/**
 * The {@link TableSpecification} represents the available information and operations associated
 * with the job table in the web interface.
 */
public class TableSpecification {

   static final String UNKNOWN_ENTRY = "Unknown";
   
   private final List< Column > orderedTableColumns;
   private final Map< String, Column > columns;
   private final Map< String, Column > ids;
   private final Map< String, Column > sortings;
   
   /**
    * Constructs a new {@link TableSpecification}.
    */
   public TableSpecification() {
      this( new JobNameColumn(), new BuildResultColumn() );
   }//End Constructor
   
   /**
    * Constructs a new {@link TableSpecification}.
    * @param columns the {@link Column}s that are available.
    */
   TableSpecification( Column... columns ) {
      this.columns = new HashMap<>();
      this.ids = new HashMap<>();
      this.orderedTableColumns = new ArrayList<>();
      this.sortings = new HashMap<>();
      
      this.orderedTableColumns.addAll( Arrays.asList( columns ) );
      for ( Column column : columns ) {
         this.columns.put( column.name(), column );
         this.ids.put( column.id(), column );
         
         for ( ConfigurationEntry sort : column.sortOptions() ) {
            if ( this.sortings.containsKey( sort.name() ) ) {
               throw new IllegalArgumentException( "Sorting already exists: " + sort.name() );
            }
            this.sortings.put( sort.name(), column );
         }
      }
   }//End Constructor
   
   /**
    * Access to the ordered {@link Column}s. Note: defensive copy.
    * @return the {@link List} of {@link Column}s.
    */
   public List< Column > columns() {
      return new ArrayList<>( orderedTableColumns );
   }//End Method
   
   /**
    * Method to provide the {@link ConfigurationEntry}s for the {@link uk.dangrew.jttws.core.jobtable.structure.Filter}s available for the 
    * given {@link Column}.
    * @param columnName the name of the {@link Column}.
    * @param jobs the {@link PageJob}s the filters are relevant to.
    * @param parameters the {@link JobTableParameters} for existing filters.
    * @return the {@link ConfigurationEntry}s.
    */
   public List< PageFilter > filtersFor( String columnName, List< PageJob > jobs, JobTableParameters parameters ) {
      Column column = columns.get( columnName );
      if ( column == null ) {
         return new ArrayList<>();
      }
      
      return column.filters( jobs, parameters );
   }//End Method
   
   /**
    * Method to provide the {@link PageSorting}s for the {@link uk.dangrew.jttws.core.jobtable.structure.Filter}s available for the 
    * given {@link Column}.
    * @param columnName the name of the {@link Column}.
    * @param jobs the {@link PageJob}s the filters are relevant to.
    * @param parameters the {@link JobTableParameters} for existing filters.
    * @return the {@link PageSorting}s.
    */
   public List< PageSorting > sortingOptionsFor( String columnName, JobTableParameters parameters ) {
      Column column = columns.get( columnName );
      if ( column == null ) {
         return new ArrayList<>();
      }
      
      return column.sortOptions();
   }//End Method
   
   /**
    * Method to provide the id to use for the given column.
    * @param id the id of the {@link Column}.
    * @return the {@link Column} found, or null.
    */
   public Column columnForId( String id ) {
      return ids.get( id );
   }//End Method

   /**
    * Method to sort the given {@link PageJob}s given the {@link JobTableParameters}. This will
    * look up the relevant column and sorting function and apply.
    * @param jobs the {@link List} of {@link PageJob}s to sort.
    * @param parameters the {@link JobTableParameters} providing the sorting method.
    */
   public void sort( List< PageJob > jobs, JobTableParameters parameters ) {
      Column column = sortings.get( parameters.sorting() );
      if ( column == null ) {
         throw new IllegalArgumentException( "Invalid column sort to apply: " + parameters.sorting() );
      }
      
      column.sort( jobs, parameters );
   }//End Method

   /**
    * Method to filter the given {@link PageJob}s given the {@link JobTableParameters}. This will
    * apply {@link uk.dangrew.jttws.core.jobtable.structure.Filter}s for each {@link Column}. 
    * @param jobs the {@link List} of {@link PageJob}s to filter.
    * @param parameters the {@link JobTableParameters} providing the filtering configuration.
    */
   public void filter( List< PageJob > jobs, JobTableParameters parameters ) {
      for ( Column column : orderedTableColumns ) {
         column.filter( jobs, parameters );
      }
   }//End Method

}//End Class
