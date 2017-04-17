/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import uk.dangrew.jttws.core.jobtable.TableSpecification;
import uk.dangrew.jttws.core.jobtable.common.WebUiParameterParsing;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.core.jobtable.web.PageColumn;
import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.core.jobtable.web.PageSorting;
import uk.dangrew.jttws.core.jobtable.web.PageTable;
import uk.dangrew.jttws.mvc.repository.PageJob;
import uk.dangrew.jttws.mvc.repository.PageUser;

/**
 * The {@link JobTableProperties} provides a method of converting {@link TableSpecification} into the relevant
 * properties using the {@link Model} for the web ui.
 */
@Component
public class JobTableProperties {

   static final String COLUMNS = "columns";
   static final String DATA = "data";
   static final String JOBS = "jobs";
   static final String FILTERS = "filters";
   
   private final WebUiParameterParsing parsing;
   
   private TableSpecification tableSpec;
   private PageTable table;
   private JobTableParameters parameters;
   private List< PageJob > jobs;
   private List< PageUser > users;

   /**
    * Constructs a new {@link JobTableProperties}.
    */
   public JobTableProperties() {
      this.parsing = new WebUiParameterParsing();
   }//End Constructor

   /**
    * Method to populate the properties in the {@link Model}.
    * @param table the {@link PageTable} to populate.
    * @param data the {@link TableSpecification}.
    * @param parameters the {@link JobTableParameters}.
    * @param jobs the {@link List} of {@link PageJob}s available.
    * @param users the {@link List} of {@link PageUser}s available.
    */
   public void populateTable( 
            PageTable table,
            TableSpecification data, 
            JobTableParameters parameters, 
            List< PageJob > jobs, 
            List< PageUser > users
   ) {
      this.table = table;
      this.tableSpec = data;
      this.parameters = parameters;
      this.jobs = new ArrayList<>( jobs );
      this.users = new ArrayList<>( users );
      
      populateIncludedColumns();
      populateSortOptions();
      populateFilteredJobs();
   }//End Method
   
   /**
    * Method to populate the table data in the {@link Model}.
    */
   public void populateAttributes( Model model, PageTable table ){
      model.addAttribute( DATA, table );
   }//End Method
   
   /**
    * Method to populate the {@link Column}s and whether they are in use or not.
    */
   private void populateIncludedColumns(){
      List< String > includedColumns = parsing.parseStringList( parameters.includedColumns() );
      
      for ( Column column : tableSpec.columns() ) {
         boolean active = includedColumns.isEmpty() || includedColumns.contains( column.name() );
         if ( !active ) {
            //exclude columns from data
            continue;
         }
         PageColumn pageColumn = new PageColumn( column, active );
         table.addColumn( pageColumn );
         
         List< PageFilter > columnFilters = column.filters( jobs, parameters );
         columnFilters.forEach( e -> table.addFilter( pageColumn, e ) );
      }
   }//End Method
   
   /**
    * Method to populate the jobs following the filtering configured.
    */
   private void populateFilteredJobs(){
      tableSpec.filter( jobs, parameters );
      tableSpec.sort( jobs, parameters );
      jobs.forEach( table::addJob );
   }//End Method
   
   /**
    * Method to populate the sorting options.
    */
   private void populateSortOptions(){
      for ( Column column : tableSpec.columns() ) {
         for ( PageSorting sorting : column.sortOptions() ) {
            boolean active = sorting.name().equals( parameters.sorting() );
            sorting.setActive( active );
            table.addSorting( sorting );
         }
      }
   }//End Method
   
}//End Class
