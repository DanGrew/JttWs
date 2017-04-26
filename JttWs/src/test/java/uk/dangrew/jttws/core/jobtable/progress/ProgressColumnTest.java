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
import uk.dangrew.jttws.core.jobtable.common.ReverseSorting;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.ColumnType;
import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.core.jobtable.web.PageSorting;
import uk.dangrew.jttws.mvc.repository.PageJob;

public class ProgressColumnTest {

   private List< PageJob > jobs;
   private PageJob job1;
   private PageJob job2;
   private JobTableParameters parameters;
   
   private ProgressSorting sorting;
   private SortingFunction reverseSorting;
   private String filterRange1;
   private String filterRange7;
   
   private ProgressColumn systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      jobs = new ArrayList<>();
      jobs.add( job1 = new PageJob( new JenkinsJobImpl( "anything" ) ) );
      jobs.add( job2 = new PageJob( new JenkinsJobImpl( "zebra" ) ) );
      job1.association().currentBuildTimeProperty().set( 7 );
      job1.association().expectedBuildTimeProperty().set( 100 );
      job2.association().currentBuildTimeProperty().set( 78 );
      job2.association().expectedBuildTimeProperty().set( 100 );
      
      parameters = new JobTableParameters();
      sorting = new ProgressSorting();
      reverseSorting = new ReverseSorting( sorting );
      systemUnderTest = new ProgressColumn( sorting, reverseSorting );
      
      filterRange1 = systemUnderTest.filters( jobs, parameters ).get( 0 ).name();
      filterRange7 = systemUnderTest.filters( jobs, parameters ).get( 7 ).name();
   }//End Method

   @Test public void shouldProvideName() {
      assertThat( systemUnderTest.name(), is( ProgressColumn.NAME ) );
      assertThat( ProgressColumn.staticName(), is( ProgressColumn.NAME ) );
   }//End Method
   
   @Test public void shouldProvideType() {
      assertThat( systemUnderTest.type(), is( ColumnType.ProgressBar ) );
   }//End Method
   
   @Test public void shouldProvideValueForJob(){
      assertThat( systemUnderTest.valueForJob( job1 ), is( "7" ) );
      assertThat( systemUnderTest.valueForJob( job2 ), is( "78" ) );
   }//End Method
   
   @Test public void shouldSortJobsUsingSortName() {
      parameters.sortBy( reverseSorting.name() );
      systemUnderTest.sort( jobs, parameters );
      
      assertThat( jobs, is( Arrays.asList( job2, job1 ) ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptSortJobsForInvalidName() {
      parameters.sortBy( "anything" );
      systemUnderTest.sort( jobs, parameters );
   }//End Method
   
   @Test public void shouldApplySingleFilterToExcludeJob(){
      parameters.filterBy( systemUnderTest.name(), filterRange7 );
      systemUnderTest.filter( jobs, parameters );
      assertThat( jobs, is( Arrays.asList( job2 ) ) );
   }//End Method
   
   @Test public void shouldApplyFilterToExcludeAllJobs(){
      parameters.filterBy( systemUnderTest.name(), "somethin" );
      systemUnderTest.filter( jobs, parameters );
      assertThat( jobs, is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldHandleNoConfiguration(){
      parameters.filterBy( systemUnderTest.name(), null );
      systemUnderTest.filter( jobs, parameters );
      assertThat( jobs, is( Arrays.asList( job1, job2 ) ) );
   }//End Method
   
   @Test public void shouldApplyMultipleFiltersToIncludeAllJobs(){
      parameters.filterBy( systemUnderTest.name(), filterRange1 + ", " + filterRange7 );
      systemUnderTest.filter( jobs, parameters );
      assertThat( jobs, is( Arrays.asList( job1, job2 ) ) );
   }//End Method
   
   @Test public void shouldProvideFiltersAllActive(){
      assertThat( systemUnderTest.filters( jobs, parameters ), is( new ProgressFilter().filterOptions( jobs, null ) ) );
   }//End Method
   
   @Test public void shouldProvideFiltersGivenValue(){
      parameters.filterBy( systemUnderTest.name(), "anything" );
      assertThat( systemUnderTest.filters( jobs, parameters ), is( new ProgressFilter().filterOptions( jobs, "anything" ) ) );
   }//End Method
   
   @Test public void shouldProvideSortOptionsAllInActive(){
      List< PageSorting > entries = systemUnderTest.sortOptions();
      assertThat( entries, hasSize( 2 ) );
      
      assertThat( entries.get( 0 ).name(), is( sorting.name() ) );
      assertThat( entries.get( 0 ).isActive(), is( false ) );
      assertThat( entries.get( 1 ).name(), is( reverseSorting.name() ) );
      assertThat( entries.get( 1 ).isActive(), is( false ) );
   }//End Method
   
   @Test public void shouldProvideId(){
      assertThat( systemUnderTest.id(), is( ProgressColumn.ID ) );
      assertThat( ProgressColumn.staticId(), is( ProgressColumn.ID ) );
   }//End Method
   
}//End Class
