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

import uk.dangrew.jtt.model.jobs.BuildResultStatus;
import uk.dangrew.jtt.model.jobs.JenkinsJob;
import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jtt.model.users.JenkinsUser;
import uk.dangrew.jtt.model.users.JenkinsUserImpl;

public class JwsJenkinsJobTest {

   private JenkinsJob job;
   private JwsJenkinsJob systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      job = new JenkinsJobImpl( "anything" );
      systemUnderTest = new JwsJenkinsJob( job );
   }//End Method

   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullJob() {
      new JwsJenkinsJob( null );
   }//End Method
   
   @Test public void shouldProvideName() {
      assertThat( systemUnderTest.name(), is( job.nameProperty().get() ) );
   }//End Method
   
   @Test public void shouldProvideBuildResultStatus() {
      job.setBuildStatus( BuildResultStatus.SUCCESS );
      assertThat( systemUnderTest.status(), is( BuildResultStatus.SUCCESS ) );
   }//End Method
   
   @Test public void shouldProvideTimestampValue() {
      job.buildTimestampProperty().set( 4567L );
      assertThat( systemUnderTest.timestampValue(), is( 4567L ) );
   }//End Method
   
   @Test public void shouldProvideFormattedTimestamp() {
      job.buildTimestampProperty().set( 4567L );
      assertThat( systemUnderTest.timestamp(), is( "01:00:04-01/01/70" ) );
   }//End Method
   
   @Test public void shouldProvideCalculatedProgress() {
      job.expectedBuildTimeProperty().set( 1000 );
      job.currentBuildTimeProperty().set( 500 );
      assertThat( systemUnderTest.progress(), is( 50 ) );
      
      job.currentBuildTimeProperty().set( 10 );
      assertThat( systemUnderTest.progress(), is( 1 ) );
      
      job.currentBuildTimeProperty().set( 999 );
      assertThat( systemUnderTest.progress(), is( 99 ) );
   }//End Method
   
   @Test public void shouldProvideNoCommitters() {
      assertThat( systemUnderTest.committers(), is( JwsJenkinsJob.NO_SUSPECTS ) );
   }//End Method
   
   @Test public void shouldProvideSingleCommitter() {
      JenkinsUser user = new JenkinsUserImpl( "Rick" );
      job.culprits().add( user );
      assertThat( systemUnderTest.committers(), is( user.nameProperty().get() ) );
   }//End Method
   
   @Test public void shouldProvideMultipleCommitters() {
      JenkinsUser rick = new JenkinsUserImpl( "Rick" );
      JenkinsUser glenn = new JenkinsUserImpl( "Glenn" );
      JenkinsUser carl = new JenkinsUserImpl( "Carl" );
      job.culprits().addAll( rick, glenn, carl );
      assertThat( systemUnderTest.committers(), is( "Rick, Glenn, Carl" ) );
   }//End Method
}//End Class
