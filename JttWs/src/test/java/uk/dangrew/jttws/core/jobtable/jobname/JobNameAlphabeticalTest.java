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

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.core.jobtable.common.Comparison;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

public class JobNameAlphabeticalTest {

   private JwsJenkinsJob job1;
   private JwsJenkinsJob job2;
   private JobNameAlphabetical systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      job1 = new JwsJenkinsJob( new JenkinsJobImpl( "Job1" ) );
      job2 = new JwsJenkinsJob( new JenkinsJobImpl( "Job2" ) );
      systemUnderTest = new JobNameAlphabetical();
   }//End Method
   
   @Test public void shouldProvideName(){
      assertThat( systemUnderTest.name(), is( JobNameAlphabetical.NAME ) );
   }//End Method

   @Test public void shouldBeEqual() {
      job2.association().nameProperty().set( "Job1" );
      assertThat( systemUnderTest.compare( job1, job2 ), is( Comparison.Equal.value() ) );
   }//End Method
   
   @Test public void shouldBeLessThan() {
      assertThat( systemUnderTest.compare( job1, job2 ), is( Comparison.LessThan.value() ) );
   }//End Method
   
   @Test public void shouldBeGreaterThan() {
      assertThat( systemUnderTest.compare( job2, job1 ), is( Comparison.GreatherThan.value() ) );
   }//End Method

}//End Class
