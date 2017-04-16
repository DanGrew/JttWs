/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.buildresult;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jtt.model.jobs.BuildResultStatus;
import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.mvc.repository.PageJob;

public class BuildResultFilterTest {

   private List< PageJob > jobs;
   private PageJob job1;
   private PageJob job2;
   
   private BuildResultFilter systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      jobs = new ArrayList<>();
      jobs.add( job1 = new PageJob( new JenkinsJobImpl( "Job1" ) ) );
      jobs.add( job2 = new PageJob( new JenkinsJobImpl( "Job2" ) ) );
   
      systemUnderTest = new BuildResultFilter();
   }//End Method

   @Test public void shouldIncludeAllJobIfNoValueProvided() {
      assertThat( systemUnderTest.identifyExclusions( jobs, null ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldIncludeJob() {
      job1.association().setBuildStatus( BuildResultStatus.SUCCESS );
      assertThat( systemUnderTest.identifyExclusions( jobs, BuildResultStatus.SUCCESS.displayName() ), is( Arrays.asList( job2 ) ) );
   }//End Method
   
   @Test public void shouldIncludeMultipleJobs() {
      job1.association().setBuildStatus( BuildResultStatus.SUCCESS );
      job2.association().setBuildStatus( BuildResultStatus.UNSTABLE );
      assertThat( systemUnderTest.identifyExclusions( 
               jobs, 
               BuildResultStatus.SUCCESS.displayName() + ", " + BuildResultStatus.UNSTABLE.displayName() 
      ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldProvideFiltersForEachResultSortedActiveWhenNotConfigured(){
      List< PageFilter > entries = systemUnderTest.filterOptions( jobs, null );
      assertThat( entries, hasSize( BuildResultStatus.values().length ) );
      
      assertThat( entries.get( 0 ).name(), is( BuildResultStatus.ABORTED.displayName() ) );
      assertThat( entries.get( 0 ).isActive(), is( true ) );
      assertThat( entries.get( 1 ).name(), is( BuildResultStatus.FAILURE.displayName() ) );
      assertThat( entries.get( 1 ).isActive(), is( true ) );
      assertThat( entries.get( 2 ).name(), is( BuildResultStatus.NOT_BUILT.displayName() ) );
      assertThat( entries.get( 2 ).isActive(), is( true ) );
      assertThat( entries.get( 3 ).name(), is( BuildResultStatus.SUCCESS.displayName() ) );
      assertThat( entries.get( 3 ).isActive(), is( true ) );
      assertThat( entries.get( 4 ).name(), is( BuildResultStatus.UNKNOWN.displayName() ) );
      assertThat( entries.get( 4 ).isActive(), is( true ) );
      assertThat( entries.get( 5 ).name(), is( BuildResultStatus.UNSTABLE.displayName() ) );
      assertThat( entries.get( 5 ).isActive(), is( true ) );
   }//End Method
   
   @Test public void shouldIgnoreFiltersForExistingConfiguration(){
      List< PageFilter > entries = systemUnderTest.filterOptions( jobs, "anything" );
      assertThat( entries, hasSize( BuildResultStatus.values().length ) );
      
      assertThat( entries.get( 0 ).name(), is( BuildResultStatus.ABORTED.displayName() ) );
      assertThat( entries.get( 0 ).isActive(), is( false ) );
      assertThat( entries.get( 1 ).name(), is( BuildResultStatus.FAILURE.displayName() ) );
      assertThat( entries.get( 1 ).isActive(), is( false ) );
      assertThat( entries.get( 2 ).name(), is( BuildResultStatus.NOT_BUILT.displayName() ) );
      assertThat( entries.get( 2 ).isActive(), is( false ) );
      assertThat( entries.get( 3 ).name(), is( BuildResultStatus.SUCCESS.displayName() ) );
      assertThat( entries.get( 3 ).isActive(), is( false ) );
      assertThat( entries.get( 4 ).name(), is( BuildResultStatus.UNKNOWN.displayName() ) );
      assertThat( entries.get( 4 ).isActive(), is( false ) );
      assertThat( entries.get( 5 ).name(), is( BuildResultStatus.UNSTABLE.displayName() ) );
      assertThat( entries.get( 5 ).isActive(), is( false ) );
   }//End Method
   
   @Test public void shouldMakeUnusedFiltersInactive(){
      List< PageFilter > entries = systemUnderTest.filterOptions( jobs, "Failure, Not Built" );
      assertThat( entries, hasSize( BuildResultStatus.values().length ) );
      
      assertThat( entries.get( 0 ).name(), is( BuildResultStatus.ABORTED.displayName() ) );
      assertThat( entries.get( 0 ).isActive(), is( false ) );
      assertThat( entries.get( 1 ).name(), is( BuildResultStatus.FAILURE.displayName() ) );
      assertThat( entries.get( 1 ).isActive(), is( true ) );
      assertThat( entries.get( 2 ).name(), is( BuildResultStatus.NOT_BUILT.displayName() ) );
      assertThat( entries.get( 2 ).isActive(), is( true ) );
      assertThat( entries.get( 3 ).name(), is( BuildResultStatus.SUCCESS.displayName() ) );
      assertThat( entries.get( 3 ).isActive(), is( false ) );
      assertThat( entries.get( 4 ).name(), is( BuildResultStatus.UNKNOWN.displayName() ) );
      assertThat( entries.get( 4 ).isActive(), is( false ) );
      assertThat( entries.get( 5 ).name(), is( BuildResultStatus.UNSTABLE.displayName() ) );
      assertThat( entries.get( 5 ).isActive(), is( false ) );
   }//End Method
   
}//End Class
