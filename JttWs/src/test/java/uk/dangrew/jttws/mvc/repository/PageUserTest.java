/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.repository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jtt.model.users.JenkinsUser;
import uk.dangrew.jtt.model.users.JenkinsUserImpl;

public class PageUserTest {

   private JenkinsUser user;
   private PageUser systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      user = new JenkinsUserImpl( "User" );
      systemUnderTest = new PageUser( user );
   }//End Method
   
   @Test public void shouldProvideAssociation(){
      assertThat( systemUnderTest.association(), is( user ) );
   }//End Method

   @Test public void shouldProvideName() {
      assertThat( systemUnderTest.name(), is( user.nameProperty().get() ) );
   }//End Method

}//End Class
