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
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jtt.model.jobs.BuildResultStatus;
import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.core.jobtable.common.Comparison;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;
import uk.dangrew.jttws.mvc.web.jobtable.SeverityComparator;

public class BuildResultSeverityTest {

   private JwsJenkinsJob job1;
   private JwsJenkinsJob job2;
   @Mock private SeverityComparator severity;
   private BuildResultSeverity systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      job1 = new JwsJenkinsJob( new JenkinsJobImpl( "Job1" ) );
      job2 = new JwsJenkinsJob( new JenkinsJobImpl( "Job2" ) );
      systemUnderTest = new BuildResultSeverity( severity );
   }//End Method
   
   @Test public void shouldProvideName(){
      assertThat( systemUnderTest.name(), is( BuildResultSeverity.NAME ) );
      assertThat( BuildResultSeverity.staticName(), is( BuildResultSeverity.NAME ) );
   }//End Method

   @Test public void shouldBeEqual() {
      assertThat( systemUnderTest.compare( job1, job2 ), is( Comparison.Equal.value() ) );
   }//End Method
   
   @Test public void shouldBeLessThan() {
      systemUnderTest = new BuildResultSeverity();
      job2.association().setBuildStatus( BuildResultStatus.UNSTABLE );
      assertThat( systemUnderTest.compare( job1, job2 ), is( Comparison.LessThan.value() ) );
   }//End Method
   
   @Test public void shouldBeGreaterThan() {
      systemUnderTest = new BuildResultSeverity();
      job2.association().setBuildStatus( BuildResultStatus.SUCCESS );
      assertThat( systemUnderTest.compare( job1, job2 ), is( greaterThanOrEqualTo( Comparison.GreaterThan.value() ) ) );
   }//End Method
   
   @Test public void shouldBeLessThanUnconditionally() {
      when( severity.compare( Mockito.any(), Mockito.any() ) ).thenReturn( Comparison.LessThan.value() );
      assertThat( systemUnderTest.compare( job1, job2 ), is( Comparison.LessThan.value() ) );
   }//End Method
   
   @Test public void shouldBeGreaterThanUnconditionally() {
      when( severity.compare( Mockito.any(), Mockito.any() ) ).thenReturn( Comparison.GreaterThan.value() );
      assertThat( systemUnderTest.compare( job1, job2 ), is( greaterThanOrEqualTo( Comparison.GreaterThan.value() ) ) );
   }//End Method

}//End Class
