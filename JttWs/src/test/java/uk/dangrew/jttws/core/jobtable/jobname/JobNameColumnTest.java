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
import org.mockito.MockitoAnnotations;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.core.jobtable.common.ReverseSorting;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.ColumnType;
import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.core.jobtable.web.PageSorting;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

public class JobNameColumnTest {

   private List< JwsJenkinsJob > jobs;
   private JwsJenkinsJob job1;
   private JwsJenkinsJob job2;
   private JobTableParameters parameters;
   
   private JobNameAlphabetical alphabetical;
   private SortingFunction reverseAlphabetical;
   
   private JobNameColumn systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      jobs = new ArrayList<>();
      jobs.add( job1 = new JwsJenkinsJob( new JenkinsJobImpl( "anything" ) ) );
      jobs.add( job2 = new JwsJenkinsJob( new JenkinsJobImpl( "zebra" ) ) );
      
      parameters = new JobTableParameters();
      alphabetical = new JobNameAlphabetical();
      reverseAlphabetical = new ReverseSorting( alphabetical );
      systemUnderTest = new JobNameColumn( alphabetical, reverseAlphabetical );
   }//End Method

   @Test public void shouldProvideName() {
      assertThat( systemUnderTest.name(), is( JobNameColumn.NAME ) );
      assertThat( JobNameColumn.staticName(), is( JobNameColumn.NAME ) );
   }//End Method
   
   @Test public void shouldProvideType() {
      assertThat( systemUnderTest.type(), is( ColumnType.String ) );
   }//End Method
   
   @Test public void shouldProvideValueForJob(){
      assertThat( systemUnderTest.valueForJob( job1 ), is( job1.name() ) );
      assertThat( systemUnderTest.valueForJob( job2 ), is( job2.name() ) );
   }//End Method
   
   @Test public void shouldSortJobsUsingSortName() {
      parameters.sortBy( reverseAlphabetical.name() );
      systemUnderTest.sort( jobs, parameters );
      
      assertThat( jobs, is( Arrays.asList( job2, job1 ) ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptSortJobsForInvalidName() {
      parameters.sortBy( "anything" );
      systemUnderTest.sort( jobs, parameters );
   }//End Method
   
   @Test public void shouldApplySingleFilterToExcludeJob(){
      parameters.filterBy( systemUnderTest.name(), job1.name() );
      systemUnderTest.filter( jobs, parameters );
      assertThat( jobs, is( Arrays.asList( job1 ) ) );
   }//End Method
   
   @Test public void shouldApplyFilterToExcludeAllJobs(){
      parameters.filterBy( systemUnderTest.name(), "" );
      systemUnderTest.filter( jobs, parameters );
      assertThat( jobs, is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldHandleNoConfiguration(){
      parameters.filterBy( systemUnderTest.name(), null );
      systemUnderTest.filter( jobs, parameters );
      assertThat( jobs, is( Arrays.asList( job1, job2 ) ) );
   }//End Method
   
   @Test public void shouldApplyMultipleFiltersToIncludeAllJobs(){
      parameters.filterBy( systemUnderTest.name(), job1.name() + ", " + job2.name() );
      systemUnderTest.filter( jobs, parameters );
      assertThat( jobs, is( Arrays.asList( job1, job2 ) ) );
   }//End Method
   
   @Test public void shouldProvideFiltersAllActive(){
      assertThat( systemUnderTest.filters( jobs, parameters ), is( new JobNameFilter().filterOptions( jobs, null ) ) );
   }//End Method
   
   @Test public void shouldProvideFiltersGivenValue(){
      parameters.filterBy( systemUnderTest.name(), "anything" );
      assertThat( systemUnderTest.filters( jobs, parameters ), is( new JobNameFilter().filterOptions( jobs, "anything" ) ) );
   }//End Method
   
   @Test public void shouldProvideSortOptionsAllInActive(){
      List< PageSorting > entries = systemUnderTest.sortOptions();
      assertThat( entries, hasSize( 2 ) );
      
      assertThat( entries.get( 0 ).name(), is( alphabetical.name() ) );
      assertThat( entries.get( 0 ).isActive(), is( false ) );
      assertThat( entries.get( 1 ).name(), is( reverseAlphabetical.name() ) );
      assertThat( entries.get( 1 ).isActive(), is( false ) );
   }//End Method
   
   @Test public void shouldProvideId(){
      assertThat( systemUnderTest.id(), is( JobNameColumn.ID ) );
      assertThat( JobNameColumn.staticId(), is( JobNameColumn.ID ) );
   }//End Method
   
}//End Class
