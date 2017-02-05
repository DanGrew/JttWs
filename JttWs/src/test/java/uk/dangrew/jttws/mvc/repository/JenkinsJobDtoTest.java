package uk.dangrew.jttws.mvc.repository;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jtt.model.jobs.BuildResultStatus;

public class JenkinsJobDtoTest {

   private static final String NAME = "any name";
   private JenkinsJobDto systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new JenkinsJobDto( NAME );
   }//End Method

   @Test public void shouldProvideName() {
      assertThat( systemUnderTest.name(), is( NAME ) );
   }//End Method
   
   @Test public void shouldProvideProgress() {
      assertThat( systemUnderTest.progress(), is( 0 ) );
      systemUnderTest.setProgress( 3678 );
      assertThat( systemUnderTest.progress(), is( 3678 ) );
   }//End Method
   
   @Test public void shouldProvideTimestamp() {
      assertThat( systemUnderTest.timestamp(), is( nullValue() ) );
      systemUnderTest.setBuildTimestamp( 1000L );
      assertThat( systemUnderTest.timestamp(), is( 1000L ) );
   }//End Method
   
   @Test public void shouldProvideStatus() {
      assertThat( systemUnderTest.status(), is( nullValue() ) );
      systemUnderTest.setStatus( BuildResultStatus.FAILURE );
      assertThat( systemUnderTest.status(), is( BuildResultStatus.FAILURE ) );
   }//End Method
   
   @Test public void shouldProvideCommitters() {
      assertThat( systemUnderTest.committers(), is( nullValue() ) );
      systemUnderTest.setCommitters( "me, you and irene" );
      assertThat( systemUnderTest.committers(), is( "me, you and irene" ) );
   }//End Method

}//End Class
