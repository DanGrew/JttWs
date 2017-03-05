/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.bean;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Component;

import uk.dangrew.jtt.storage.database.JenkinsDatabase;

public class JwsJenkinsDatabaseTest {

   private JwsJenkinsDatabase systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new JwsJenkinsDatabase();
   }//End Method

   @Test public void shouldBeJenkinsDatabaseAsComponent() {
      assertThat( systemUnderTest, is( instanceOf( JenkinsDatabase.class ) ) );
      assertThat( JwsJenkinsDatabase.class.getAnnotation( Component.class ), is( notNullValue() ) );
   }//End Method

}//End Class
