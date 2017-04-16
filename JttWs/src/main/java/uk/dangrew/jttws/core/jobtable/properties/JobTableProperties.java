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
import uk.dangrew.jttws.core.jobtable.web.PageTable;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsUser;

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
   
   private TableSpecification tableData;
   private PageTable table;
   private JobTableParameters parameters;
   private List< JwsJenkinsJob > jobs;
   private List< JwsJenkinsUser > users;

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
    * @param jobs the {@link List} of {@link JwsJenkinsJob}s available.
    * @param users the {@link List} of {@link JwsJenkinsUser}s available.
    */
   public void populateTable( 
            PageTable table,
            TableSpecification data, 
            JobTableParameters parameters, 
            List< JwsJenkinsJob > jobs, 
            List< JwsJenkinsUser > users
   ) {
      this.table = table;
      this.tableData = data;
      this.parameters = parameters;
      this.jobs = new ArrayList<>( jobs );
      this.users = new ArrayList<>( users );
      
      populateIncludedColumns();
      populateFilteredJobs();
      
      //populate sorting config
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
      List< String > includedJobs = parsing.parseStringList( parameters.includedColumns() );
      
      for ( Column column : tableData.columns() ) {
         boolean inactive = !includedJobs.isEmpty() && !includedJobs.contains( column.name() );
         PageColumn pageColumn = new PageColumn( column, !inactive );
         table.addColumn( pageColumn );
         if ( !inactive ) {
            List< PageFilter > columnFilters = tableData.filtersFor( column.name(), jobs, parameters );
            columnFilters.forEach( e -> table.addFilter( pageColumn, e ) );
         }
      }
   }//End Method
   
   /**
    * Method to populate the jobs following the filtering configured.
    */
   private void populateFilteredJobs(){
      tableData.filter( jobs, parameters );
      jobs.forEach( table::addJob );
   }//End Method
   
   private void populateSortOptions(){
      //remove id fields
      //refactor 'jws' to 'page'
      //refactor calls in pages for new interface
      //tidy up table data interface
      //implement populating sortings
      //refactor inactive()
   }//End Method
   
}//End Class
