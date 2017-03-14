/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.jobname;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.core.jobtable.common.ReverseSorting;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

public class JobNameColumnTest {

   private List< JwsJenkinsJob > jobs;
   private JwsJenkinsJob job1;
   private JwsJenkinsJob job2;
   private JobTableParameters parameters;
   
   private JobNameAlphabetical alphabetical;
   private SortingFunction reverseAlphabetical;
   private JobNameColumn systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      jobs = new ArrayList<>();
      jobs.add( job1 = new JwsJenkinsJob( new JenkinsJobImpl( "anything" ) ) );
      jobs.add( job2 = new JwsJenkinsJob( new JenkinsJobImpl( "zebra" ) ) );
      
      parameters = new JobTableParameters();
      alphabetical = new JobNameAlphabetical();
      reverseAlphabetical = new ReverseSorting( alphabetical );
      systemUnderTest = new JobNameColumn( alphabetical, reverseAlphabetical );
   }//End Method

   @Test public void shouldProvideName() {
      assertThat( systemUnderTest.name(), is( JobNameColumn.NAME ) );
   }//End Method
   
   @Test public void shouldSortJobsUsingSortName() {
      parameters.sortBy( reverseAlphabetical.name() );
      systemUnderTest.sort( jobs, parameters );
      
      assertThat( jobs, is( Arrays.asList( job2, job1 ) ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptSortJobsForInvalidName() {
      parameters.sortBy( "anything" );
      systemUnderTest.sort( jobs, parameters );
   }//End Method

}//End Class
