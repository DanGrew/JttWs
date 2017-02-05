/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import uk.dangrew.jttws.mvc.service.JenkinsJobService;

/**
 * The {@link SiteController} provides a {@link Controller} for handling requests to load pages. 
 * This is a global controller for now but could be broken down at a later stage.
 */
@Controller
public class SiteController {

	static final String PAGE_HOME = "home";
   static final String ATTRIBUTE_JOBS = "jobs";
	
	private final JenkinsJobService jenkinsJobs;

	/**
	 * Constructs a new {@link SiteController}.
	 * @param jenkinsJobs the {@link JenkinsJobService}.
	 */
	@Autowired
	public SiteController(JenkinsJobService jenkinsJobs) {
		this.jenkinsJobs = jenkinsJobs;
	}//End Constructor

	/**
	 * Method to handle the request for the home page.
	 * @param model the {@link Model} to provide variables in.
	 * @return the name of the jsp to provide.
	 */
	@GetMapping( value = "/" )
	public String home( Model model ) {
		model.addAttribute( ATTRIBUTE_JOBS, jenkinsJobs.getJobs() );
		return PAGE_HOME;
	}//End Method

}//End Class