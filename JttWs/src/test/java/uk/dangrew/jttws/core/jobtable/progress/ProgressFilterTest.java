/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.progress;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.mvc.repository.PageJob;

public class ProgressFilterTest {

   private List< PageJob > jobs;
   private PageJob job1;
   private PageJob job2;
   
   private ProgressRangeFilter filter1;
   private ProgressRangeFilter filter2;
   private ProgressFilter systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      jobs = new ArrayList<>();
      jobs.add( job1 = new PageJob( new JenkinsJobImpl( "Job1" ) ) );
      jobs.add( job2 = new PageJob( new JenkinsJobImpl( "Job2" ) ) );
      job1.association().currentBuildTimeProperty().set( 40 );
      job1.association().expectedBuildTimeProperty().set( 100 );
      job2.association().currentBuildTimeProperty().set( 60 );
      job2.association().expectedBuildTimeProperty().set( 100 );
      
      filter1 = new ProgressRangeFilter( 0, true, 50, false );
      filter2 = new ProgressRangeFilter( 50, false, 100, true );
      systemUnderTest = new ProgressFilter( filter1, filter2 );
   }//End Method

   @Test public void shouldIncludeAllJobIfNoValueProvided() {
      assertThat( systemUnderTest.identifyExclusions( jobs, null ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldIncludeAllJobIfEmptyValueProvided() {
      assertThat( systemUnderTest.identifyExclusions( jobs, "" ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldIncludeJob() {
      assertThat( systemUnderTest.identifyExclusions( jobs, filter1.name() ), is( Arrays.asList( job2 ) ) );
   }//End Method
   
   @Test public void shouldIncludeMultipleJobs() {
      assertThat( systemUnderTest.identifyExclusions( jobs, filter1.name() + ", " + filter2.name() ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldProvideFilterOptionPerFilterActiveWhenNotConfigured(){
      List< PageFilter > entries = systemUnderTest.filterOptions( jobs, null );
      assertThat( entries, hasSize( 2 ) );
      
      assertThat( entries.get( 0 ).name(), is( filter1.name() ) );
      assertThat( entries.get( 0 ).isActive(), is( true ) );
      assertThat( entries.get( 1 ).name(), is( filter2.name() ) );
      assertThat( entries.get( 1 ).isActive(), is( true ) );
   }//End Method
   
   //Efficient to test alphabetical order here too.
   @Test public void shouldProvideFilterOptionForExistingFiltersAndInAlphabeticalOrder(){
      List< PageFilter > entries = systemUnderTest.filterOptions( jobs, "aaaa" );
      assertThat( entries, hasSize( jobs.size() + 1 ) );
      
      assertThat( entries.get( 0 ).name(), is( filter1.name() ) );
      assertThat( entries.get( 0 ).isActive(), is( false ) );
      assertThat( entries.get( 1 ).name(), is( filter2.name() ) );
      assertThat( entries.get( 1 ).isActive(), is( false ) );
      
      assertThat( entries.get( 2 ).name(), is( "aaaa" ) );
      assertThat( entries.get( 2 ).isActive(), is( true ) );
   }//End Method
   
   @Test public void shouldProvideFilterOptionForExistingFilters(){
      List< PageFilter > entries = systemUnderTest.filterOptions( jobs, filter1.name() );
      assertThat( entries, hasSize( 2 ) );
      
      assertThat( entries.get( 0 ).name(), is( filter1.name() ) );
      assertThat( entries.get( 0 ).isActive(), is( true ) );
      assertThat( entries.get( 1 ).name(), is( filter2.name() ) );
      assertThat( entries.get( 1 ).isActive(), is( false ) );
   }//End Method
   
}//End Class
