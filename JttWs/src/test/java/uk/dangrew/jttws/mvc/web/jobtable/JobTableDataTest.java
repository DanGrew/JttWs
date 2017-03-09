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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jtt.model.users.JenkinsUserImpl;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsUser;

public class JobTableDataTest {

   private JwsJenkinsUser oliver;
   private JwsJenkinsUser felicity;
   private JwsJenkinsUser diggle;
   private List< JwsJenkinsUser > users;
   
   private JwsJenkinsJob city;
   private JwsJenkinsJob thea;
   private JwsJenkinsJob hood;
   private List< JwsJenkinsJob > jobs;
   private JobTableData systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new JobTableData();
      
      users = new ArrayList<>();
      users.add( oliver = new JwsJenkinsUser( new JenkinsUserImpl( "Oliver" ) ) );
      users.add( felicity = new JwsJenkinsUser( new JenkinsUserImpl( "Felicity" ) ) );
      users.add( diggle = new JwsJenkinsUser( new JenkinsUserImpl( "Diggle" ) ) );
      
      jobs = new ArrayList<>();
      jobs.add( city = new JwsJenkinsJob( new JenkinsJobImpl( "Save City" ) ) );
      jobs.add( thea = new JwsJenkinsJob( new JenkinsJobImpl( "Save Thea" ) ) );
      jobs.add( hood = new JwsJenkinsJob( new JenkinsJobImpl( "Repair Holes In Hood" ) ) );
      
      systemUnderTest.provide( jobs );
   }//End Method

   @Test public void shouldStoreFilteredJobs() {
      assertThat( systemUnderTest.getDisplayedJobs(), is( jobs ) );
   }//End Method
   
   @Test public void shouldAppendFilteredJobs() {
      assertThat( systemUnderTest.getDisplayedJobs(), is( jobs ) );
      
      JwsJenkinsJob anything = new JwsJenkinsJob( new JenkinsJobImpl( "anything" ) );
      systemUnderTest.provide( Arrays.asList( anything ) );
      assertThat( systemUnderTest.getDisplayedJobs(), is( 
               Arrays.asList( jobs.get( 0 ), jobs.get( 1 ), jobs.get( 2 ), anything ) 
      ) );
   }//End Method
   
   @Test public void shouldNotDuplicateAppendedJobs() {
      assertThat( systemUnderTest.getDisplayedJobs(), is( jobs ) );
      systemUnderTest.provide( jobs );
      assertThat( systemUnderTest.getDisplayedJobs(), is( jobs ) );
   }//End Method
   
   @Test public void shouldIncludeDisplayedJobSpecifically() {
      systemUnderTest.filterJobName( thea.name() );
      assertThat( systemUnderTest.getDisplayedJobs(), is( Arrays.asList( thea ) ) );
   }//End Method
   
   @Test public void shouldIncludeMultipleDisplayedJobSpecifically() {
      systemUnderTest.filterJobName( thea.name(), hood.name() );
      assertThat( systemUnderTest.getDisplayedJobs(), is( Arrays.asList( thea, hood ) ) );
   }//End Method
   
   @Test public void shouldIncludeDisplayedJobIfCommittersFiltered() {
      city.association().culprits().add( oliver.association() );
      thea.association().culprits().add( felicity.association() );
      hood.association().culprits().add( diggle.association() );
      
      systemUnderTest.filterForCommitters( felicity.name() );
      assertThat( systemUnderTest.getDisplayedJobs(), is( Arrays.asList( thea ) ) );
   }//End Method
   
   @Test public void shouldIncludeDisplayedJobIfMultipleCommittersFiltered() {
      city.association().culprits().add( oliver.association() );
      thea.association().culprits().add( felicity.association() );
      hood.association().culprits().add( diggle.association() );
      
      systemUnderTest.filterForCommitters( oliver.name(), diggle.name() );
      assertThat( systemUnderTest.getDisplayedJobs(), is( Arrays.asList( city, hood ) ) );
   }//End Method
   
   @Test public void shouldNotIncludeDisplayedJobIfNoCommitters() {
      systemUnderTest.filterForCommitters( felicity.name() );
      assertThat( systemUnderTest.getDisplayedJobs(), is( Arrays.asList( city, thea, hood ) ) );
   }//End Method
   
   @Test public void shouldNotIncludeDisplayedJobIfAtLeastOneCommittersFiltered() {
      city.association().culprits().add( oliver.association() );
      city.association().culprits().add( felicity.association() );
      thea.association().culprits().add( felicity.association() );
      hood.association().culprits().add( diggle.association() );
      
      systemUnderTest.filterForCommitters( felicity.name() );
      assertThat( systemUnderTest.getDisplayedJobs(), is( Arrays.asList( city, thea ) ) );
   }//End Method
   
}//End Class
