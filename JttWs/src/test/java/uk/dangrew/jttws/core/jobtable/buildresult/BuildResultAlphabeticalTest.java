/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.buildresult;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jtt.model.jobs.BuildResultStatus;
import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.core.jobtable.common.Comparison;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

public class BuildResultAlphabeticalTest {

   private JwsJenkinsJob job1;
   private JwsJenkinsJob job2;
   private BuildResultAlphabetical systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      job1 = new JwsJenkinsJob( new JenkinsJobImpl( "Job1" ) );
      job2 = new JwsJenkinsJob( new JenkinsJobImpl( "Job2" ) );
      systemUnderTest = new BuildResultAlphabetical();
   }//End Method
   
   @Test public void shouldProvideName(){
      assertThat( systemUnderTest.name(), is( BuildResultAlphabetical.NAME ) );
      assertThat( BuildResultAlphabetical.staticName(), is( BuildResultAlphabetical.NAME ) );
   }//End Method

   @Test public void shouldBeEqual() {
      assertThat( systemUnderTest.compare( job1, job2 ), is( Comparison.Equal.value() ) );
   }//End Method
   
   @Test public void shouldBeLessThan() {
      job2.association().setBuildStatus( BuildResultStatus.UNSTABLE );
      assertThat( systemUnderTest.compare( job1, job2 ), is( Comparison.LessThan.value() ) );
   }//End Method
   
   @Test public void shouldBeGreaterThan() {
      job2.association().setBuildStatus( BuildResultStatus.ABORTED );
      assertThat( systemUnderTest.compare( job1, job2 ), is( greaterThanOrEqualTo( Comparison.GreaterThan.value() ) ) );
   }//End Method

}//End Class
