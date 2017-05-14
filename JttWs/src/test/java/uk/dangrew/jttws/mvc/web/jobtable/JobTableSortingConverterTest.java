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

public class JobTableSortingConverterTest {

   private JobTableSortingConverter systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new JobTableSortingConverter();
   }//End Method

   @Test public void shouldSafelyValueOfNull(){
      assertThat( systemUnderTest.convert( null ), is( JobTableSortingConverter.DEFAULT_SORTING ) );
   }//End Method
   
   @Test public void shouldSafelyValueOfValidSorting(){
      assertThat( systemUnderTest.convert( JobTableSorting.TimestampDecreasing.name() ), is( JobTableSorting.TimestampDecreasing ) );
   }//End Method
   
   @Test public void shouldSafelyValueOfInvalidSortingAndProvideDefault(){
      assertThat( systemUnderTest.convert( "anything" ), is( JobTableSortingConverter.DEFAULT_SORTING ) );
   }//End Method

}//End Class
