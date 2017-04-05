/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.jobname;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationEntry;

public class JobNameFilterTest {

   private List< JwsJenkinsJob > jobs;
   private JwsJenkinsJob job1;
   private JwsJenkinsJob job2;
   
   private JobNameFilter systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      jobs = new ArrayList<>();
      jobs.add( job1 = new JwsJenkinsJob( new JenkinsJobImpl( "Job1" ) ) );
      jobs.add( job2 = new JwsJenkinsJob( new JenkinsJobImpl( "Job2" ) ) );
      systemUnderTest = new JobNameFilter();
   }//End Method

   @Test public void shouldIncludeAllJobIfNoValueProvided() {
      assertThat( systemUnderTest.identifyExclusions( jobs, null ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldIncludeJob() {
      assertThat( systemUnderTest.identifyExclusions( jobs, job1.name() ), is( Arrays.asList( job2 ) ) );
   }//End Method
   
   @Test public void shouldIncludeMultipleJobs() {
      assertThat( systemUnderTest.identifyExclusions( jobs, job1.name() + ", " + job2.name() ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldProvideFilterOptionPerJobActiveWhenNotConfigured(){
      List< ConfigurationEntry > entries = systemUnderTest.filterOptions( jobs, null );
      assertThat( entries, hasSize( jobs.size() ) );
      
      for ( int i = 0; i < jobs.size(); i++ ) {
         assertThat( entries.get( i ).name(), is( jobs.get( i ).name() ) );
         assertThat( entries.get( i ).isActive(), is( true ) );
      }
   }//End Method
   
   //Efficient to test alphabetical order here too.
   @Test public void shouldProvideFilterOptionForExistingFiltersAndInAlphabeticalOrder(){
      List< ConfigurationEntry > entries = systemUnderTest.filterOptions( jobs, "aaaa" );
      assertThat( entries, hasSize( jobs.size() + 1 ) );
      
      assertThat( entries.get( 0 ).name(), is( "aaaa" ) );
      assertThat( entries.get( 0 ).isActive(), is( true ) );
      
      for ( int i = 1; i <= jobs.size(); i++ ) {
         assertThat( entries.get( i ).name(), is( jobs.get( i - 1 ).name() ) );
         assertThat( entries.get( i ).isActive(), is( false ) );
      }
   }//End Method
   
   @Test public void shouldProvideFilterOptionForExistingFiltersOfValidJob(){
      List< ConfigurationEntry > entries = systemUnderTest.filterOptions( jobs, job1.name() );
      assertThat( entries, hasSize( 2 ) );
      
      assertThat( entries.get( 0 ).name(), is( jobs.get( 0 ).name() ) );
      assertThat( entries.get( 0 ).isActive(), is( true ) );
      assertThat( entries.get( 1 ).name(), is( jobs.get( 1 ).name() ) );
      assertThat( entries.get( 1 ).isActive(), is( false ) );
   }//End Method
   
}//End Class
