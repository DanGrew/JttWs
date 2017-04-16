/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import uk.dangrew.jttws.core.jobtable.TableData;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.parameters.ParametersPopulator;
import uk.dangrew.jttws.core.jobtable.properties.JobTableProperties;
import uk.dangrew.jttws.core.jobtable.web.PageTable;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsUser;
import uk.dangrew.jttws.mvc.service.JenkinsService;
import uk.dangrew.jttws.mvc.web.jobtable.JobTableColumns;

/**
 * The {@link SiteController} provides a {@link Controller} for handling requests to load pages. 
 * This is a global controller for now but could be broken down at a later stage.
 */
@Controller
public class SiteController {

   static final String HOME_ADDRESS = "/";
   static final String JOBS_TABLE_ADDRESS = "/jobs-table";
   
	static final String PAGE_HOME = "home";
	static final String PAGE_TABLE = "job-table";
	
	static final String JOB_COLUMNS = "columns";
	
	private final JenkinsService jenkinsJobs;
	private final JobTableProperties properties;
	private final ParametersPopulator paramsPopulator;
	
	/**
	 * Constructs a new {@link SiteController}.
	 * @param jenkinsJobs the {@link JenkinsService}.
	 * @param properties the {@link JobTableProperties}.
	 * @param paramsPopulator the {@link ParametersPopulator}.
	 */
	@Autowired
	public SiteController(
	         JenkinsService jenkinsJobs, 
	         JobTableProperties properties,
	         ParametersPopulator paramsPopulator 
   ) {
		this.jenkinsJobs = jenkinsJobs;
		this.properties = properties;
		this.paramsPopulator = paramsPopulator;
	}//End Constructor

	/**
	 * Method to handle the request for the home page.
	 * @param request the {@link HttpServletRequest}.
	 * @param response the {@link HttpServletResponse}.
	 * @param model the {@link Model} to provide variables in.
	 * @return the name of the jsp to provide.
	 */
	@GetMapping( value = HOME_ADDRESS )
	public String home( 
	         HttpServletRequest request,
	         HttpServletResponse response,
	         Model model 
   ) {
	   refreshTable( request, response, model );
      return PAGE_HOME;
   }// End Method

   /**
    * Method to handle the request for refreshing just the jobs table.
    * @param request the {@link HttpServletRequest}.
    * @param response the {@link HttpServletResponse}.
    * @param model the {@link Model} to provide variables in.
    * @return the name of the jsp to provide.
    */
   @GetMapping( value = JOBS_TABLE_ADDRESS ) 
   public String refreshTable( 
            HttpServletRequest request, 
            HttpServletResponse response, 
            Model model 
   ) {
      List< JwsJenkinsJob > jobs = jenkinsJobs.getJobs();
      List< JwsJenkinsUser > users = jenkinsJobs.getUsers();
      
      model.addAttribute( JOB_COLUMNS, JobTableColumns.values() );
      
      TableData data = new TableData();
      JobTableParameters parameters = paramsPopulator.construct( data, request, response );
      
      PageTable table = new PageTable();
      properties.populateTable( table, data, parameters, jobs, users );
      properties.populateAttributes( model, table );
      return PAGE_TABLE;
   }// End Method

}// End Class