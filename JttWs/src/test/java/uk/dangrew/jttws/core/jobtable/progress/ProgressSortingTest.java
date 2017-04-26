/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.progress;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.core.jobtable.common.Comparison;
import uk.dangrew.jttws.mvc.repository.PageJob;

public class ProgressSortingTest {

   private PageJob job1;
   private PageJob job2;
   private ProgressSorting systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      job1 = new PageJob( new JenkinsJobImpl( "Job1" ) );
      job2 = new PageJob( new JenkinsJobImpl( "Job2" ) );
      systemUnderTest = new ProgressSorting();
   }//End Method
   
   @Test public void shouldProvideName(){
      assertThat( systemUnderTest.name(), is( ProgressSorting.NAME ) );
      assertThat( ProgressSorting.staticName(), is( ProgressSorting.NAME ) );
   }//End Method

   @Test public void shouldBeEqual() {
      job1.association().currentBuildTimeProperty().set( 50 );
      job1.association().expectedBuildTimeProperty().set( 100 );
      job2.association().currentBuildTimeProperty().set( 50 );
      job2.association().expectedBuildTimeProperty().set( 100 );
      assertThat( systemUnderTest.compare( job1, job2 ), is( Comparison.Equal.value() ) );
   }//End Method
   
   @Test public void shouldBeLessThan() {
      job1.association().currentBuildTimeProperty().set( 49 );
      job1.association().expectedBuildTimeProperty().set( 100 );
      job2.association().currentBuildTimeProperty().set( 50 );
      job2.association().expectedBuildTimeProperty().set( 100 );
      assertThat( systemUnderTest.compare( job1, job2 ), is( Comparison.LessThan.value() ) );
   }//End Method
   
   @Test public void shouldBeGreaterThan() {
      job1.association().currentBuildTimeProperty().set( 50 );
      job1.association().expectedBuildTimeProperty().set( 100 );
      job2.association().currentBuildTimeProperty().set( 51 );
      job2.association().expectedBuildTimeProperty().set( 100 );
      assertThat( systemUnderTest.compare( job2, job1 ), is( Comparison.GreaterThan.value() ) );
   }//End Method

}//End Class
