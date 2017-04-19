/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.committers;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jtt.model.users.JenkinsUser;
import uk.dangrew.jtt.model.users.JenkinsUserImpl;
import uk.dangrew.jttws.core.jobtable.common.Comparison;
import uk.dangrew.jttws.mvc.repository.PageJob;

public class CommittersAlphabeticalTest {

   private PageJob job1;
   private PageJob job2;
   private JenkinsUser user1;
   private JenkinsUser user2;
   private CommittersAlphabetical systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      job1 = new PageJob( new JenkinsJobImpl( "Job1" ) );
      job2 = new PageJob( new JenkinsJobImpl( "Job2" ) );
      user1 = new JenkinsUserImpl( "Dan" );
      user2 = new JenkinsUserImpl( "Jess" );
      systemUnderTest = new CommittersAlphabetical();
   }//End Method
   
   @Test public void shouldProvideName(){
      assertThat( systemUnderTest.name(), is( CommittersAlphabetical.NAME ) );
      assertThat( CommittersAlphabetical.staticName(), is( CommittersAlphabetical.NAME ) );
   }//End Method

   @Test public void shouldBeEqual() {
      assertThat( systemUnderTest.compare( job1, job2 ), is( Comparison.Equal.value() ) );
      
      job1.association().culprits().add( user1 );
      job2.association().culprits().add( user1 );
      assertThat( systemUnderTest.compare( job1, job2 ), is( Comparison.Equal.value() ) );
   }//End Method
   
   @Test public void shouldBeLessThan() {
      job1.association().culprits().add( user1 );
      job2.association().culprits().add( user2 );
      assertThat( systemUnderTest.compare( job1, job2 ), is( lessThanOrEqualTo( Comparison.LessThan.value() ) ) );
   }//End Method
   
   @Test public void shouldBeGreaterThan() {
      job1.association().culprits().add( user2 );
      job2.association().culprits().add( user1 );
      assertThat( systemUnderTest.compare( job1, job2 ), is( greaterThanOrEqualTo( Comparison.GreaterThan.value() ) ) );
   }//End Method

}//End Class
