/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.committers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jtt.model.users.JenkinsUser;
import uk.dangrew.jtt.model.users.JenkinsUserImpl;
import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.mvc.repository.PageJob;

public class CommittersFilterTest {

   private List< PageJob > jobs;
   private PageJob job1;
   private PageJob job2;
   private JenkinsUser user1;
   private JenkinsUser user2;
   private JenkinsUser user3;
   
   private CommittersFilter systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      jobs = new ArrayList<>();
      jobs.add( job1 = new PageJob( new JenkinsJobImpl( "Job1" ) ) );
      jobs.add( job2 = new PageJob( new JenkinsJobImpl( "Job2" ) ) );
      job1.association().culprits().add( user1 = new JenkinsUserImpl( "Dan" ) );
      job1.association().culprits().add( user2 = new JenkinsUserImpl( "Jess" ) );
      job2.association().culprits().add( user3 = new JenkinsUserImpl( "Lydia" ) );
      systemUnderTest = new CommittersFilter();
   }//End Method

   @Test public void shouldIncludeAllJobIfNoValueProvided() {
      assertThat( systemUnderTest.identifyExclusions( jobs, null ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldIncludeAllJobIfEmptyValueProvided() {
      assertThat( systemUnderTest.identifyExclusions( jobs, "" ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldIncludeJobIfNoCulprits() {
      job1.association().culprits().clear();
      assertThat( systemUnderTest.identifyExclusions( jobs, user3.nameProperty().get() ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldIncludeJob() {
      assertThat( systemUnderTest.identifyExclusions( jobs, user1.nameProperty().get() ), is( Arrays.asList( job2 ) ) );
   }//End Method
   
   @Test public void shouldIncludeMultipleJobs() {
      assertThat( systemUnderTest.identifyExclusions( 
               jobs, 
               user1.nameProperty().get() + ", " + user3.nameProperty().get() 
      ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldProvideFilterOptionPerCulpritWhenNotConfigured(){
      List< PageFilter > entries = systemUnderTest.filterOptions( jobs, null );
      assertThat( entries, hasSize( 3 ) );
      
      assertThat( entries.get( 0 ).name(), is( user1.nameProperty().get() ) );
      assertThat( entries.get( 0 ).isActive(), is( true ) );
      assertThat( entries.get( 1 ).name(), is( user2.nameProperty().get() ) );
      assertThat( entries.get( 1 ).isActive(), is( true ) );
      assertThat( entries.get( 2 ).name(), is( user3.nameProperty().get() ) );
      assertThat( entries.get( 2 ).isActive(), is( true ) );
   }//End Method
   
   //Efficient to test alphabetical order here too.
   @Test public void shouldProvideFilterOptionForExistingFiltersAndInAlphabeticalOrder(){
      List< PageFilter > entries = systemUnderTest.filterOptions( jobs, "aaaa" );
      assertThat( entries, hasSize( 4 ) );
      
      assertThat( entries.get( 0 ).name(), is( "aaaa" ) );
      assertThat( entries.get( 0 ).isActive(), is( true ) );
      
      assertThat( entries.get( 1 ).name(), is( user1.nameProperty().get() ) );
      assertThat( entries.get( 1 ).isActive(), is( false ) );
      assertThat( entries.get( 2 ).name(), is( user2.nameProperty().get() ) );
      assertThat( entries.get( 2 ).isActive(), is( false ) );
      assertThat( entries.get( 3 ).name(), is( user3.nameProperty().get() ) );
      assertThat( entries.get( 3 ).isActive(), is( false ) );
   }//End Method
   
   @Test public void shouldProvideFilterOptionForExistingFiltersOfValidJob(){
      List< PageFilter > entries = systemUnderTest.filterOptions( jobs, user1.nameProperty().get() );
      assertThat( entries, hasSize( 3 ) );
      
      assertThat( entries.get( 0 ).name(), is( user1.nameProperty().get() ) );
      assertThat( entries.get( 0 ).isActive(), is( true ) );
      assertThat( entries.get( 1 ).name(), is( user2.nameProperty().get() ) );
      assertThat( entries.get( 1 ).isActive(), is( false ) );
   }//End Method
   
}//End Class
