/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

/**
 * The {@link JobListHandler} is responsible for handling the operations performed on the list of
 * {@link JwsJenkinsJob}s.
 */
@Component
public class JobListHandler {

   static final String ATTRIBUTE_JOB_ENTRIES = "entries";
   static final String ATTRIBUTE_JOBS = "jobs";
   static final String ATTRIBUTE_SORT_OPTIONS = "sort_options";
   
   static final String PARAMETER_FILTERED_JOBS = "filteredJobsList";
   static final String PARAMETER_SORT = "sort";
   
   private final CookieManager cookieManager;
   private final ConfigurationProvider configurationProvider;
   private final JobTableSortingConverter sortingConverter;
   
   /**
    * Constructs a new {@link JobListHandler}.
    */
   public JobListHandler() {
      this( new CookieManager(), new ConfigurationProvider(), new JobTableSortingConverter() );
   }//End Constructor
   
   /**
    * Constructs a new {@link JobListHandler}.
    * @param cookieManager the {@link CookieManager}.
    * @param configurationProvider the {@link ConfigurationProvider}.
    * @param sortConverter the {@link JobTableSortingConverter}.
    */
   JobListHandler( 
            CookieManager cookieManager, 
            ConfigurationProvider configurationProvider, 
            JobTableSortingConverter sortConverter 
   ) {
      this.cookieManager = cookieManager;
      this.configurationProvider = configurationProvider;
      this.sortingConverter = sortConverter;
   }//End Constructor
   
   /**
    * Method to handle the sorting on the table.
    * @param request the {@link HttpServletRequest}.
    * @param response the {@link HttpServletResponse}.
    * @param jobs the {@link JwsJenkinsJob} to sort.
    * @param model the {@link Model} for updating attributes.
    */
   public void handleSorting( 
            HttpServletRequest request, 
            HttpServletResponse response,
            List< JwsJenkinsJob > jobs,
            Model model
   ){
      String sortValue = cookieManager.retrieveCookie( PARAMETER_SORT, request, response );
      
      final JobTableSorting chosenSorting = sortingConverter.convert( sortValue );
      chosenSorting.sort( jobs );
      
      configurationProvider.provideConfigurationEntries( 
               ATTRIBUTE_SORT_OPTIONS, 
               model, 
               Arrays.asList( JobTableSorting.values() ), 
               s -> s != chosenSorting, 
               s -> s.name() 
      );
   }//End Method
   
   /**
    * Method to handle the filtering in the table.
    * @param request the {@link HttpServletRequest}.
    * @param response the {@link HttpServletResponse}.
    * @param jobs the {@link JwsJenkinsJob} to filter.
    * @param model the {@link Model} for updating attributes.
    */
   public void handleFiltering( 
            HttpServletRequest request, 
            HttpServletResponse response,
            List< JwsJenkinsJob > jobs, 
            Model model
   ){
      String includedJobs = cookieManager.retrieveCookie( PARAMETER_FILTERED_JOBS, request, response ); 
      
      Set< String > includedJobNames = new HashSet<>();
      if ( includedJobs != null ) {
         includedJobNames.addAll( Arrays.asList( includedJobs.replaceAll( ", ", "," ).split( "," ) ) );
      }
      
      List< ConfigurationEntry > entries = new ArrayList<>();
      List< JwsJenkinsJob > filteredJobs = new ArrayList<>();
      for ( JwsJenkinsJob job : jobs ) {
         ConfigurationEntry entry = new ConfigurationEntry( job.name() );
         entries.add( entry );
         if ( includedJobs != null ) {
            if ( includedJobNames.contains( job.name() ) ) {
               filteredJobs.add( job );
            } else {
               entry.inactive();
            }
         } else {
            filteredJobs.add( job );
         }
      }
      model.addAttribute( ATTRIBUTE_JOBS, filteredJobs );      
      model.addAttribute( ATTRIBUTE_JOB_ENTRIES, entries );
   }//End Method
   
}//End Class
