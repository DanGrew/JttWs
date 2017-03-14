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

public class JobTableParametersTest {

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

}//End Class
