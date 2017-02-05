package uk.dangrew.jttws.core.login;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class JenkinsCredentialsTest {

   private JenkinsCredentials systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new JenkinsCredentials();
   }//End Method

   @Test public void shouldHoldLocation() {
      systemUnderTest.setLocation( "location" );
      assertThat( systemUnderTest.getLocation(), is( "location" ) );
   }//End Method
   
   @Test public void shouldHoldUsername() {
      systemUnderTest.setUsername( "username" );
      assertThat( systemUnderTest.getUsername(), is( "username" ) );
   }//End Method
   
   @Test public void shouldHoldPassword() {
      systemUnderTest.setPassword( "password" );
      assertThat( systemUnderTest.getPassword(), is( "password" ) );
   }//End Method

}//End Class
