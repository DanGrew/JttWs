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

import uk.dangrew.jttws.mvc.repository.JenkinsJobRepository;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

/**
 * The {@link JenkinsJobService} provides a {@link Service} for interacting with the {@link JenkinsJobDtoRepository}.
 */
@Service
public class JenkinsJobService {

	private JenkinsJobRepository repository;
	
	/**
	 * Constructs a new {@link JenkinsJobService}.
	 * @param repository the {@link JenkinsJobRepository} bean.
	 */
	@Autowired
	public JenkinsJobService( JenkinsJobRepository repository ) {
	   this.repository = repository;
   }//End Constructor
	
	/**
	 * Getter the {@link JwsJenkinsJob}s.
	 * @return the {@link JwsJenkinsJob}s.
	 */
	public List< JwsJenkinsJob > getJobs(){
	   return repository.getJenkinsJobs();
	}//End Method

}//End Class