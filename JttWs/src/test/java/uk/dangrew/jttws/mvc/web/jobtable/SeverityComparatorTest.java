/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web.jobtable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jtt.model.jobs.BuildResultStatus;

public class SeverityComparatorTest {

   private SeverityComparator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new SeverityComparator();
   }//End Method

   @Test public void shouldCompareToProvideSeverity() {
      assertThat( systemUnderTest.compare( BuildResultStatus.ABORTED, BuildResultStatus.ABORTED ), is( 0 ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.ABORTED, BuildResultStatus.FAILURE ), is( SeverityComparator.SECOND_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.ABORTED, BuildResultStatus.NOT_BUILT ), is( SeverityComparator.FIRST_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.ABORTED, BuildResultStatus.SUCCESS ), is( SeverityComparator.FIRST_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.ABORTED, BuildResultStatus.UNKNOWN ), is( SeverityComparator.FIRST_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.ABORTED, BuildResultStatus.UNSTABLE ), is( SeverityComparator.SECOND_MORE_SEVERE ) );
      
      assertThat( systemUnderTest.compare( BuildResultStatus.FAILURE, BuildResultStatus.ABORTED ), is( SeverityComparator.FIRST_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.FAILURE, BuildResultStatus.FAILURE ), is( 0 ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.FAILURE, BuildResultStatus.NOT_BUILT ), is( SeverityComparator.FIRST_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.FAILURE, BuildResultStatus.SUCCESS ), is( SeverityComparator.FIRST_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.FAILURE, BuildResultStatus.UNKNOWN ), is( SeverityComparator.FIRST_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.FAILURE, BuildResultStatus.UNSTABLE ), is( SeverityComparator.FIRST_MORE_SEVERE ) );
      
      assertThat( systemUnderTest.compare( BuildResultStatus.NOT_BUILT, BuildResultStatus.ABORTED ), is( SeverityComparator.SECOND_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.NOT_BUILT, BuildResultStatus.FAILURE ), is( SeverityComparator.SECOND_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.NOT_BUILT, BuildResultStatus.NOT_BUILT ), is( 0 ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.NOT_BUILT, BuildResultStatus.SUCCESS ), is( SeverityComparator.FIRST_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.NOT_BUILT, BuildResultStatus.UNKNOWN ), is( SeverityComparator.FIRST_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.NOT_BUILT, BuildResultStatus.UNSTABLE ), is( SeverityComparator.SECOND_MORE_SEVERE ) );
      
      assertThat( systemUnderTest.compare( BuildResultStatus.SUCCESS, BuildResultStatus.ABORTED ), is( SeverityComparator.SECOND_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.SUCCESS, BuildResultStatus.FAILURE ), is( SeverityComparator.SECOND_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.SUCCESS, BuildResultStatus.NOT_BUILT ), is( SeverityComparator.SECOND_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.SUCCESS, BuildResultStatus.SUCCESS ), is( 0 ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.SUCCESS, BuildResultStatus.UNKNOWN ), is( SeverityComparator.SECOND_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.SUCCESS, BuildResultStatus.UNSTABLE ), is( SeverityComparator.SECOND_MORE_SEVERE ) );
      
      assertThat( systemUnderTest.compare( BuildResultStatus.UNKNOWN, BuildResultStatus.ABORTED ), is( SeverityComparator.SECOND_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.UNKNOWN, BuildResultStatus.FAILURE ), is( SeverityComparator.SECOND_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.UNKNOWN, BuildResultStatus.NOT_BUILT ), is( SeverityComparator.SECOND_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.UNKNOWN, BuildResultStatus.SUCCESS ), is( SeverityComparator.FIRST_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.UNKNOWN, BuildResultStatus.UNKNOWN ), is( 0 ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.UNKNOWN, BuildResultStatus.UNSTABLE ), is( SeverityComparator.SECOND_MORE_SEVERE ) );
      
      assertThat( systemUnderTest.compare( BuildResultStatus.UNSTABLE, BuildResultStatus.ABORTED ), is( SeverityComparator.FIRST_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.UNSTABLE, BuildResultStatus.FAILURE ), is( SeverityComparator.SECOND_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.UNSTABLE, BuildResultStatus.NOT_BUILT ), is( SeverityComparator.FIRST_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.UNSTABLE, BuildResultStatus.SUCCESS ), is( SeverityComparator.FIRST_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.UNSTABLE, BuildResultStatus.UNKNOWN ), is( SeverityComparator.FIRST_MORE_SEVERE ) );
      assertThat( systemUnderTest.compare( BuildResultStatus.UNSTABLE, BuildResultStatus.UNSTABLE ), is( 0 ) );
   }//End Method

}//End Class
