/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web.configuration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jttws.mvc.web.configuration.ConfigurationEntry;

public class ConfigurationEntryTest {

   private ConfigurationEntry systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new ConfigurationEntry( "anything" );
   }//End Method

   @Test public void shouldProvideName() {
      assertThat( systemUnderTest.name(), is( "anything" ) );
   }//End Method
   
   @Test public void shouldBeActiveInitially() {
      assertThat( systemUnderTest.isActive(), is( true ) );
   }//End Method
   
   @Test public void shouldBeDeactivated() {
      systemUnderTest.inactive();
      assertThat( systemUnderTest.isActive(), is( false ) );
   }//End Method

}//End Class
