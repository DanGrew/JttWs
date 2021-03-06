/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.dangrew.jttws.mvc.repository.JenkinsRepository;
import uk.dangrew.jttws.mvc.repository.PageJob;
import uk.dangrew.jttws.mvc.repository.PageUser;

/**
 * The {@link JenkinsService} provides a {@link Service} for interacting with the {@link JenkinsRepository}.
 */
@Service
public class JenkinsService {

	private JenkinsRepository repository;
	
	/**
	 * Constructs a new {@link JenkinsService}.
	 * @param repository the {@link JenkinsRepository} bean.
	 */
	@Autowired
	public JenkinsService( JenkinsRepository repository ) {
	   this.repository = repository;
   }//End Constructor
	
	/**
	 * Getter the {@link PageJob}s.
	 * @return the {@link PageJob}s.
	 */
	public List< PageJob > getJobs(){
	   return repository.getJenkinsJobs();
	}//End Method
	
	/**
    * Getter the {@link PageUser}s.
    * @return the {@link PageUser}s.
    */
   public List< PageUser > getUsers(){
      return repository.getJenkinsUsers();
   }//End Method

}//End Class