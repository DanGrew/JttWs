/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web.jobtable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsUser;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationProvider;
import uk.dangrew.jttws.mvc.web.configuration.CookieManager;

/**
 * The {@link JobListHandler} is responsible for handling the operations performed on the list of
 * {@link JwsJenkinsJob}s.
 */
@Component
public class JobListHandler {

   static final String ATTRIBUTE_JOB_ENTRIES = "job_entries";
   static final String ATTRIBUTE_USER_ENTRIES = "user_entries";
   static final String ATTRIBUTE_JOBS = "jobs";
   static final String ATTRIBUTE_SORT_OPTIONS = "sort_options";
   
   static final String PARAMETER_FILTERED_JOBS = "filteredJobsList";
   static final String PARAMETER_FILTERED_USERS = "filteredUsersList";
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
    * @param users the {@link JwsJenkinsUser}s available.
    * @param model the {@link Model} for updating attributes.
    * 
    * Not fully tested - in development of more general mechanism for multiple data columns
    */
   public void handleFiltering( 
            HttpServletRequest request, 
            HttpServletResponse response,
            List< JwsJenkinsJob > jobs,
            List< JwsJenkinsUser > users,
            Model model
   ){
      JobTableData data = new JobTableData();
      data.provide( jobs );
      
      final String includedJobs = cookieManager.retrieveCookie( PARAMETER_FILTERED_JOBS, request, response );
      Set< String > jobNamesSet = new HashSet<>();
      if ( includedJobs != null ) {
         String[] includedJobNames = includedJobs.replaceAll( ", ", "," ).split( "," );
         jobNamesSet.addAll( Arrays.asList( includedJobNames ) );
         data.filterJobName( includedJobNames );
      }
      
      final String includedUsers = cookieManager.retrieveCookie( PARAMETER_FILTERED_USERS, request, response );
      Set< String > userNamesSet = new HashSet<>();
      if ( includedUsers != null ) {
         String[] includedUserNames = includedUsers.replaceAll( ", ", "," ).split( "," );
         userNamesSet.addAll( Arrays.asList( includedUserNames ) );
         data.filterForCommitters( includedUserNames );
      }
      
      configurationProvider.provideConfigurationEntries( 
            ATTRIBUTE_JOB_ENTRIES, 
            model, 
            jobs, 
            job -> jobNamesSet.isEmpty() ? true : jobNamesSet.contains( job.name() ), 
            job -> job.name() 
      );
      
      configurationProvider.provideConfigurationEntries( 
            ATTRIBUTE_USER_ENTRIES, 
            model, 
            users, 
            u -> userNamesSet.isEmpty() ? true : userNamesSet.contains( u.name() ), 
            u -> u.name() 
      );
      
      model.addAttribute( ATTRIBUTE_JOBS, data.getDisplayedJobs() );      
   }//End Method
   
}//End Class
