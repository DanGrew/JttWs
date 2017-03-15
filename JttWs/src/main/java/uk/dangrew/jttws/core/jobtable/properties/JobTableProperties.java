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

import org.springframework.ui.Model;

import uk.dangrew.jttws.core.jobtable.TableData;
import uk.dangrew.jttws.core.jobtable.common.WebUiParameterParsing;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationEntry;

/**
 * The {@link JobTableProperties} provides a method of converting {@link TableData} into the relevant
 * properties using the {@link Model} for the web ui.
 */
public class JobTableProperties {

   public static final String COLUMNS = "columns";
   
   private final WebUiParameterParsing parsing;
   private final Model model;
   private final TableData data;
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
      this.data = data;
      this.parameters = parameters;
   }//End Constructor

   /**
    * Method to populate the properties in the {@link Model}.
    */
   public void populate() {
      populateIncludedColumns();
   }//End Method
   
   /**
    * Method to populate the {@link Column}s and whether they are in use or not.
    */
   private void populateIncludedColumns(){
      List< ConfigurationEntry > entries = new ArrayList<>();
      
      List< String > includedJobs = parsing.parseStringList( parameters.includedColumns() );
      for ( Column column : data.columns() ) {
         ConfigurationEntry entry = new ConfigurationEntry( column.name() );
         if ( !includedJobs.contains( column.name() ) ) {
            entry.inactive();
         }
         entries.add( entry );
      }
      
      model.addAttribute( COLUMNS, entries );
   }//End Method

}//End Class
