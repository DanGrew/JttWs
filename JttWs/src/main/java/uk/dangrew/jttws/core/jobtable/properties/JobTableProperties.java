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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import uk.dangrew.jttws.core.jobtable.TableData;
import uk.dangrew.jttws.core.jobtable.common.WebUiParameterParsing;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsUser;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationEntry;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationProvider;

/**
 * The {@link JobTableProperties} provides a method of converting {@link TableData} into the relevant
 * properties using the {@link Model} for the web ui.
 */
@Component
public class JobTableProperties {

   static final String COLUMNS = "columns";
   static final String DATA = "data";
   static final String JOBS = "jobs";
   static final String FILTERS = "filters";
   
   private final WebUiParameterParsing parsing;
   private final ConfigurationProvider configuration;
   
   private Model model;
   private TableData tableData;
   private JobTableParameters parameters;
   private List< JwsJenkinsJob > jobs;
   private List< JwsJenkinsUser > users;

   /**
    * Constructs a new {@link JobTableProperties}.
    */
   public JobTableProperties() {
      this.parsing = new WebUiParameterParsing();
      this.configuration = new ConfigurationProvider();
   }//End Constructor

   /**
    * Method to populate the properties in the {@link Model}.
    * @param model the {@link Model} to populate.
    * @param data the {@link TableData}.
    * @param parameters the {@link JobTableParameters}.
    * @param jobs the {@link List} of {@link JwsJenkinsJob}s available.
    * @param users the {@link List} of {@link JwsJenkinsUser}s available.
    */
   public void populate( 
            Model model, 
            TableData data, 
            JobTableParameters parameters, 
            List< JwsJenkinsJob > jobs, 
            List< JwsJenkinsUser > users
   ) {
      this.model = model;
      this.tableData = data;
      this.parameters = parameters;
      this.jobs = new ArrayList<>( jobs );
      this.users = new ArrayList<>( users );
      
      populateIncludedColumns();
      populateData();
      populateFilteredJobs();
      
      //populate sorting config
   }//End Method
   
   /**
    * Method to populate the table data in the {@link Model}.
    */
   private void populateData(){
      model.addAttribute( DATA, tableData );
   }//End Method
   
   /**
    * Method to populate the {@link Column}s and whether they are in use or not.
    */
   private void populateIncludedColumns(){
      List< ConfigurationEntry > entries = new ArrayList<>();
      Map< String, List< ConfigurationEntry > > filters = new HashMap<>();
      
      List< String > includedJobs = parsing.parseStringList( parameters.includedColumns() );
      
      for ( Column column : tableData.columns() ) {
         ConfigurationEntry entry = new ConfigurationEntry( column.name() );
         if ( !includedJobs.isEmpty() && !includedJobs.contains( column.name() ) ) {
            entry.inactive();
         } else {
            filters.put( column.name(), tableData.filtersFor( column.name(), jobs, parameters ) );
         }
         entries.add( entry );
      }
      
      model.addAttribute( COLUMNS, entries );
      model.addAttribute( FILTERS, filters );
   }//End Method
   
   /**
    * Method to populate the jobs following the filtering configured.
    */
   private void populateFilteredJobs(){
      tableData.filter( jobs, parameters );
      model.addAttribute( JOBS, jobs );
   }//End Method
   
}//End Class
