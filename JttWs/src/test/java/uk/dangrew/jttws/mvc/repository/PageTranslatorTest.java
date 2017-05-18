/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.repository;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jtt.model.jobs.JenkinsJob;
import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jtt.model.storage.database.JenkinsDatabase;
import uk.dangrew.jtt.model.storage.database.TestJenkinsDatabaseImpl;
import uk.dangrew.jtt.model.users.JenkinsUser;
import uk.dangrew.jtt.model.users.JenkinsUserImpl;

public class PageTranslatorTest {

   private JenkinsDatabase database;
   private JenkinsJob job1;
   private JenkinsJob job2;
   private JenkinsJob job3;
   private JenkinsUser user1;
   private JenkinsUser user2;
   private JenkinsUser user3;
   
   private PageTranslator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      database = new TestJenkinsDatabaseImpl();
      
      job1 = new JenkinsJobImpl( "Of" );
      job2 = new JenkinsJobImpl( "Mice" );
      job3 = new JenkinsJobImpl( "& Men" );
      
      user1 = new JenkinsUserImpl( "Pierce" );
      user2 = new JenkinsUserImpl( "the" );
      user3 = new JenkinsUserImpl( "Veil" );
      
      systemUnderTest = new PageTranslator( database );
   }//End Method

   @Test public void shouldProvidePageJobs() {
      assertThat( systemUnderTest.translateJobs(), is( new ArrayList<>() ) );
      
      database.store( job1 );
      assertPageJobsArePresentFor( systemUnderTest.translateJobs(), job1 );
      
      database.store( job2 );
      database.store( job3 );
      assertPageJobsArePresentFor( systemUnderTest.translateJobs(), job1, job2, job3 );
      
      database.removeJenkinsJob( job2 );
      assertPageJobsArePresentFor( systemUnderTest.translateJobs(), job1, job3 );
   }//End Method
   
   private void assertPageJobsArePresentFor( List< PageJob > pageJobs, JenkinsJob... jobs ) {
      assertThat( pageJobs, hasSize( jobs.length ) );
      
      for ( int i = 0; i < pageJobs.size(); i++ ) {
         assertThat( pageJobs.get( i ).association(), is( jobs[ i ] ) );
      }
   }//End Method
   
   @Test public void shouldProvidePageUsers() {
      assertThat( systemUnderTest.translateUsers(), is( new ArrayList<>() ) );
      
      database.store( user1 );
      assertPageUsersArePresentFor( systemUnderTest.translateUsers(), user1 );
      
      database.store( user2 );
      database.store( user3 );
      assertPageUsersArePresentFor( systemUnderTest.translateUsers(), user1, user2, user3 );
      
      database.removeJenkinsUser( user2 );
      assertPageUsersArePresentFor( systemUnderTest.translateUsers(), user1, user3 );
   }//End Method
   
   private void assertPageUsersArePresentFor( List< PageUser > pageUsers, JenkinsUser... users ) {
      assertThat( pageUsers, hasSize( users.length ) );
      
      for ( int i = 0; i < pageUsers.size(); i++ ) {
         assertThat( pageUsers.get( i ).association(), is( users[ i ] ) );
      }
   }//End Method

}//End Method
