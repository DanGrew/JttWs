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
import java.util.Arrays;
import java.util.List;

import org.springframework.ui.Model;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.core.jobtable.TableData;
import uk.dangrew.jttws.core.jobtable.common.WebUiParameterParsing;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationEntry;

/**
 * The {@link JobTableProperties} provides a method of converting {@link TableData} into the relevant
 * properties using the {@link Model} for the web ui.
 */
public class JobTableProperties {

   static final String COLUMNS = "columns";
   static final String DATA = "data";
   
   private final WebUiParameterParsing parsing;
   private final Model model;
   private final TableData tableData;
   private final JobTableParameters parameters;

   /**
    * Constructs a new {@link JobTableProperties}.
    * @param model the {@link Model} to populate.
    * @param data the {@link TableData}.
    * @param parameters the {@link JobTableParameters}.
    */
   public JobTableProperties( Model model, TableData data, JobTableParameters parameters ) {
      this.parsing = new WebUiParameterParsing();
      this.model = model;
      this.tableData = data;
      this.parameters = parameters;
   }//End Constructor

   /**
    * Method to populate the properties in the {@link Model}.
    */
   public void populate() {
      populateIncludedColumns();
      populateData();
      model.addAttribute( "jobs", Arrays.asList( new JwsJenkinsJob( new JenkinsJobImpl( "MyJob" ) ) ) );
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
      
      List< String > includedJobs = parsing.parseStringList( parameters.includedColumns() );
      for ( Column column : tableData.columns() ) {
         ConfigurationEntry entry = new ConfigurationEntry( column.name() );
         if ( !includedJobs.contains( column.name() ) ) {
            entry.inactive();
         }
         entries.add( entry );
      }
      
      model.addAttribute( COLUMNS, entries );
   }//End Method

}//End Class
