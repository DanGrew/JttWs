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

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

public class JobNameFilterTest {

   private List< JwsJenkinsJob > jobs;
   private JwsJenkinsJob job1;
   private JwsJenkinsJob job2;
   
   private JobNameFilter systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      jobs = new ArrayList<>();
      jobs.add( job1 = new JwsJenkinsJob( new JenkinsJobImpl( "Job1" ) ) );
      jobs.add( job2 = new JwsJenkinsJob( new JenkinsJobImpl( "Job2" ) ) );
      systemUnderTest = new JobNameFilter();
   }//End Method

   @Test public void shouldIncludeAllJobIfNoValueProvided() {
      assertThat( systemUnderTest.identifyExclusions( jobs, null ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldIncludeJob() {
      assertThat( systemUnderTest.identifyExclusions( jobs, job1.name() ), is( Arrays.asList( job2 ) ) );
   }//End Method
   
   @Test public void shouldIncludeMultipleJobs() {
      assertThat( systemUnderTest.identifyExclusions( jobs, job1.name() + ", " + job2.name() ), is( new ArrayList<>() ) );
   }//End Method

}//End Class
