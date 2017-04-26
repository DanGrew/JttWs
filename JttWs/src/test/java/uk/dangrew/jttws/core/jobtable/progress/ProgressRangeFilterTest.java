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

import org.junit.Test;

public class ProgressRangeFilterTest {

   private ProgressRangeFilter systemUnderTest;
   
   @Test public void shouldMatchInsideRange() {
      systemUnderTest = new ProgressRangeFilter( 50, true, 60, true );
      for ( int i = 51; i < 60; i++ ) {
         assertThat( systemUnderTest.matches( i ), is( true ) );
      }
   }//End Method
   
   @Test public void shouldNotMatchBeforeRange() {
      systemUnderTest = new ProgressRangeFilter( 50, true, 60, true );
      assertThat( systemUnderTest.matches( 0 ), is( false ) );
      assertThat( systemUnderTest.matches( 49 ), is( false ) );
   }//End Method
   
   @Test public void shouldNotMatchAfterRange() {
      systemUnderTest = new ProgressRangeFilter( 50, true, 60, true );
      assertThat( systemUnderTest.matches( 70 ), is( false ) );
      assertThat( systemUnderTest.matches( 61 ), is( false ) );
   }//End Method
   
   @Test public void shouldMatchOnStartIfSet() {
      systemUnderTest = new ProgressRangeFilter( 50, true, 60, true );
      assertThat( systemUnderTest.matches( 50 ), is( true ) );
   }//End Method
   
   @Test public void shouldNotMatchOnStartIfSet() {
      systemUnderTest = new ProgressRangeFilter( 50, false, 60, true );
      assertThat( systemUnderTest.matches( 50 ), is( false ) );
   }//End Method
   
   @Test public void shouldMatchOnEndIfSet() {
      systemUnderTest = new ProgressRangeFilter( 50, true, 60, true );
      assertThat( systemUnderTest.matches( 60 ), is( true ) );
   }//End Method
   
   @Test public void shouldNotMatchOnEndIfSet() {
      systemUnderTest = new ProgressRangeFilter( 50, true, 60, false );
      assertThat( systemUnderTest.matches( 60 ), is( false ) );
   }//End Method
   
   @Test public void shouldProvideName(){
      systemUnderTest = new ProgressRangeFilter( 50, true, 60, true );
      assertThat( systemUnderTest.name(), is( "50 <= x <= 60" ) );
      systemUnderTest = new ProgressRangeFilter( 50, true, 60, false );
      assertThat( systemUnderTest.name(), is( "50 <= x < 60" ) );
      systemUnderTest = new ProgressRangeFilter( 50, false, 60, true );
      assertThat( systemUnderTest.name(), is( "50 < x <= 60" ) );
      systemUnderTest = new ProgressRangeFilter( 50, false, 60, false );
      assertThat( systemUnderTest.name(), is( "50 < x < 60" ) );
   }//End Method

}//End Class
