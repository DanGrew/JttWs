/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.parameters;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jttws.core.jobtable.jobname.JobNameColumn;

public class JobTableParametersTest {

   private static final String ANYTHING = "anything";
   
   private JobTableParameters systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new JobTableParameters();
   }//End Method

   @Test public void shouldHoldColumnToSortBy() {
      assertThat( systemUnderTest.columnToSortBy(), is( nullValue() ) );
      systemUnderTest.sortColumn( "anything" );
      assertThat( systemUnderTest.columnToSortBy(), is( "anything" ) );
   }//End Method
   
   @Test public void shouldHoldSortingFunction(){
      assertThat( systemUnderTest.sortingFunction(), is( nullValue() ) );
      systemUnderTest.sortBy( "anything" );
      assertThat( systemUnderTest.sortingFunction(), is( "anything" ) );
   }//End Method
   
   @Test public void shouldHoldFiltersByColumn(){
      assertThat( systemUnderTest.filterValueFor( ANYTHING ), is( nullValue() ) );
      systemUnderTest.filterBy( ANYTHING, "something" );
      assertThat( systemUnderTest.filterValueFor( ANYTHING ), is( "something" ) );
   }//End Method
   
   @Test public void shouldHoldIncludedColumns(){
      assertThat( systemUnderTest.includedColumns(), is( nullValue() ) );
      systemUnderTest.includeColumns( JobNameColumn.staticName() );
      assertThat( systemUnderTest.includedColumns(), is( JobNameColumn.staticName() ) );
   }//End Method

}//End Class
