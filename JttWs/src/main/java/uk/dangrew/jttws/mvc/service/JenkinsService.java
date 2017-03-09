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
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsUser;

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
	 * Getter the {@link JwsJenkinsJob}s.
	 * @return the {@link JwsJenkinsJob}s.
	 */
	public List< JwsJenkinsJob > getJobs(){
	   return repository.getJenkinsJobs();
	}//End Method
	
	/**
    * Getter the {@link JwsJenkinsUser}s.
    * @return the {@link JwsJenkinsUser}s.
    */
   public List< JwsJenkinsUser > getUsers(){
      return repository.getJenkinsUsers();
   }//End Method

}//End Class