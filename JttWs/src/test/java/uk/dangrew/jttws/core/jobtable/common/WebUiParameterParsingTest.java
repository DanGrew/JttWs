/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.common;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class WebUiParameterParsingTest {

   private WebUiParameterParsing systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new WebUiParameterParsing();
   }//End Method

   @Test public void shouldParseListOfStrings() {
      List< String > parsed = systemUnderTest.parseStringList( "Job1, Job2" );
      assertThat( parsed, is( Arrays.asList( "Job1", "Job2" ) ) );
   }//End Method
   
   @Test public void shouldHandleNullValue(){
      assertThat( systemUnderTest.parseStringList( null ), is( new ArrayList<>() ) );
   }//End Method

}//End Class
