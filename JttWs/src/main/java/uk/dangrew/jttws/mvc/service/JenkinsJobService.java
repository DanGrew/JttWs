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

import uk.dangrew.jttws.mvc.repository.JenkinsJobDto;
import uk.dangrew.jttws.mvc.repository.JenkinsJobDtoRepository;

/**
 * The {@link JenkinsJobService} provides a {@link Service} for interacting with the {@link JenkinsJobDtoRepository}.
 */
@Service
public class JenkinsJobService {

	private JenkinsJobDtoRepository repository;
	
	/**
	 * Constructs a new {@link JenkinsJobService}.
	 * @param repository the {@link JenkinsJobDtoRepository} bean.
	 */
	@Autowired
	public JenkinsJobService( JenkinsJobDtoRepository repository ) {
	   this.repository = repository;
   }//End Constructor
	
	/**
	 * Getter the {@link JenkinsJobDto}s.
	 * @return the {@link JenkinsJobDto}s.
	 */
	public List< JenkinsJobDto > getJobs(){
	   return repository.getJenkinsJobs();
	}//End Method

}//End Class