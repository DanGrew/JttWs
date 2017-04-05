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
import uk.dangrew.jttws.core.jobtable.structure.ColumnType;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationEntry;

/**
 * The {@link TableData} represents the available information and operations associated
 * with the job table in the web interface.
 */
public class TableData {

   static final String UNKNOWN_ENTRY = "Unknown";
   
   private final List< Column > orderedTableColumns;
   private final Map< String, Column > columns;
   
   /**
    * Constructs a new {@link TableData}.
    */
   public TableData() {
      this( new JobNameColumn(), new BuildResultColumn() );
   }//End Constructor
   
   /**
    * Constructs a new {@link TableData}.
    * @param columns the {@link Column}s that are available.
    */
   TableData( Column... columns ) {
      this.columns = new HashMap<>();
      this.orderedTableColumns = new ArrayList<>();
      
      this.orderedTableColumns.addAll( Arrays.asList( columns ) );
      for ( Column column : columns ) {
         this.columns.put( column.name(), column );
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
    * @param jobs the {@link JwsJenkinsJob}s the filters are relevant to.
    * @param parameters the {@link JobTableParameters} for existing filters.
    * @return the {@link ConfigurationEntry}s.
    */
   public List< ConfigurationEntry > filtersFor( String columnName, List< JwsJenkinsJob > jobs, JobTableParameters parameters ) {
      Column column = columns.get( columnName );
      if ( column == null ) {
         return new ArrayList<>();
      }
      
      return column.filters( jobs, parameters );
   }//End Method
   
   /**
    * Method to provide the value to display in the table for the given column and job.
    * @param columnName the name of the {@link Column}.
    * @param job the {@link JwsJenkinsJob}.
    * @return the {@link String} value to display in the table.
    */
   public String valueForColumn( String columnName, JwsJenkinsJob job ) {
      Column column = columns.get( columnName );
      if ( column == null ) {
         return UNKNOWN_ENTRY;
      }
      
      return column.valueForJob( job );
   }//End Method
   
   /**
    * Method to provide the type to display for the given column.
    * @param columnName the name of the {@link Column}.
    * @return the {@link String} value to display in the table.
    */
   public ColumnType typeForColumn( String columnName ) {
      Column column = columns.get( columnName );
      if ( column == null ) {
         return ColumnType.String;
      }
      
      return column.type();
   }//End Method

   /**
    * Method to sort the given {@link JwsJenkinsJob}s given the {@link JobTableParameters}. This will
    * look up the relevant column and sorting function and apply.
    * @param jobs the {@link List} of {@link JwsJenkinsJob}s to sort.
    * @param parameters the {@link JobTableParameters} providing the sorting method.
    */
   public void sort( List< JwsJenkinsJob > jobs, JobTableParameters parameters ) {
      Column column = columns.get( parameters.sorting().getKey() );
      if ( column == null ) {
         throw new IllegalArgumentException( "Invalid column to sort by: " + parameters.sorting().getKey() );
      }
      
      column.sort( jobs, parameters );
   }//End Method

   /**
    * Method to filter the given {@link JwsJenkinsJob}s given the {@link JobTableParameters}. This will
    * apply {@link uk.dangrew.jttws.core.jobtable.structure.Filter}s for each {@link Column}. 
    * @param jobs the {@link List} of {@link JwsJenkinsJob}s to filter.
    * @param parameters the {@link JobTableParameters} providing the filtering configuration.
    */
   public void filter( List< JwsJenkinsJob > jobs, JobTableParameters parameters ) {
      for ( Column column : orderedTableColumns ) {
         column.filter( jobs, parameters );
      }
   }//End Method

}//End Class
