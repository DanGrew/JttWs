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
import uk.dangrew.jtt.model.jobs.JenkinsJob;
import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.core.jobtable.common.ReverseSorting;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.ColumnType;
import uk.dangrew.jttws.core.jobtable.web.PageSorting;
import uk.dangrew.jttws.mvc.repository.PageJob;

public class BuildResultColumnTest {

   private PageJob jwsJob;
   private JenkinsJob job;
   private PageJob jwsJob2;
   private JenkinsJob job2;
   private List< PageJob > jobs;
   
   private JobTableParameters parameters;
   private BuildResultColumn systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      jwsJob = new PageJob( job = new JenkinsJobImpl( "This is a job" ) );
      jwsJob2 = new PageJob( job2 = new JenkinsJobImpl( "This is another job" ) );
      jobs = new ArrayList<>();
      jobs.add( jwsJob );
      jobs.add( jwsJob2 );
      
      parameters = new JobTableParameters();
      systemUnderTest = new BuildResultColumn();
   }//End Method

   @Test public void shouldProvideName() {
      assertThat( systemUnderTest.name(), is( BuildResultColumn.NAME ) );
      assertThat( BuildResultColumn.staticName(), is( BuildResultColumn.NAME ) );
   }//End Method
   
   @Test public void shouldProvideType(){
      assertThat( systemUnderTest.type(), is( ColumnType.String ) );
   }//End Method
   
   @Test public void shouldGetValueForJob(){
      assertThat( systemUnderTest.valueForJob( jwsJob ), is( jwsJob.status().displayName() ) );
      job.setBuildStatus( BuildResultStatus.SUCCESS );
      assertThat( systemUnderTest.valueForJob( jwsJob ), is( BuildResultStatus.SUCCESS.displayName() ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptSortForInvalidSorting(){
      parameters.sortBy( "anything" );
      systemUnderTest.sort( jobs, parameters );
   }//End Method
   
   @Test public void shouldSortByAlphabetical(){
      job.setBuildStatus( BuildResultStatus.NOT_BUILT );
      job2.setBuildStatus( BuildResultStatus.FAILURE );
      
      parameters.sortBy( BuildResultAlphabetical.staticName() );
      systemUnderTest.sort( jobs, parameters );
      
      assertThat( jobs, is( Arrays.asList( jwsJob2, jwsJob ) ) );
   }//End Method
   
   @Test public void shouldSortByReverseAlphabetical(){
      job.setBuildStatus( BuildResultStatus.FAILURE );
      job2.setBuildStatus( BuildResultStatus.NOT_BUILT );
      
      parameters.sortBy( ReverseSorting.staticName( BuildResultAlphabetical.staticName() ) );
      systemUnderTest.sort( jobs, parameters );
      
      assertThat( jobs, is( Arrays.asList( jwsJob2, jwsJob ) ) );
   }//End Method
   
   @Test public void shouldSortBySeverity(){
      job.setBuildStatus( BuildResultStatus.FAILURE );
      job2.setBuildStatus( BuildResultStatus.NOT_BUILT );
      
      parameters.sortBy( BuildResultSeverity.staticName() );
      systemUnderTest.sort( jobs, parameters );
      
      assertThat( jobs, is( Arrays.asList( jwsJob2, jwsJob ) ) );
   }//End Method
   
   @Test public void shouldSortByReverseSeverity(){
      job.setBuildStatus( BuildResultStatus.FAILURE );
      job2.setBuildStatus( BuildResultStatus.NOT_BUILT );
      
      parameters.sortBy( ReverseSorting.staticName( BuildResultSeverity.staticName() ) );
      systemUnderTest.sort( jobs, parameters );
      
      assertThat( jobs, is( Arrays.asList( jwsJob, jwsJob2 ) ) );
   }//End Method

   @Test public void shouldApplySingleFilterToExcludeJob(){
      job.setBuildStatus( BuildResultStatus.SUCCESS );
      parameters.filterBy( systemUnderTest.name(), BuildResultStatus.SUCCESS.displayName() );
      systemUnderTest.filter( jobs, parameters );
      assertThat( jobs, is( Arrays.asList( jwsJob ) ) );
   }//End Method
   
   @Test public void shouldApplyFilterToExcludeAllJobs(){
      parameters.filterBy( systemUnderTest.name(), "" );
      systemUnderTest.filter( jobs, parameters );
      assertThat( jobs, is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldHandleNoConfiguration(){
      parameters.filterBy( systemUnderTest.name(), null );
      systemUnderTest.filter( jobs, parameters );
      assertThat( jobs, is( Arrays.asList( jwsJob, jwsJob2 ) ) );
   }//End Method
   
   @Test public void shouldApplyMultipleFiltersToExcludeAllJobs(){
      job.setBuildStatus( BuildResultStatus.SUCCESS );
      parameters.filterBy( 
               systemUnderTest.name(), 
               BuildResultStatus.SUCCESS.displayName() + ", " + BuildResultStatus.NOT_BUILT.displayName() 
      );
      systemUnderTest.filter( jobs, parameters );
      assertThat( jobs, is( Arrays.asList( jwsJob, jwsJob2 ) ) );
   }//End Method
   
   @Test public void shouldProvideFiltersAllActive(){
      assertThat( systemUnderTest.filters( jobs, parameters ), is( new BuildResultFilter().filterOptions( jobs, null ) ) );
   }//End Method
   
   @Test public void shouldProvideFiltersGivenValue(){
      parameters.filterBy( systemUnderTest.name(), "anything" );
      assertThat( systemUnderTest.filters( jobs, parameters ), is( new BuildResultFilter().filterOptions( jobs, "anything" ) ) );
   }//End Method
   
   @Test public void shouldProvideSortOptionsAllInActive(){
      List< PageSorting > entries = systemUnderTest.sortOptions();
      assertThat( entries, hasSize( 4 ) );
      
      assertThat( entries.get( 0 ).name(), is( BuildResultAlphabetical.staticName() ) );
      assertThat( entries.get( 0 ).isActive(), is( false ) );
      assertThat( entries.get( 1 ).name(), is( ReverseSorting.staticName( BuildResultAlphabetical.staticName() ) ) );
      assertThat( entries.get( 1 ).isActive(), is( false ) );
      assertThat( entries.get( 2 ).name(), is( BuildResultSeverity.staticName() ) );
      assertThat( entries.get( 2 ).isActive(), is( false ) );
      assertThat( entries.get( 3 ).name(), is( ReverseSorting.staticName( BuildResultSeverity.staticName() ) ) );
      assertThat( entries.get( 3 ).isActive(), is( false ) );
   }//End Method
   
   @Test public void shouldProvideId(){
      assertThat( systemUnderTest.id(), is( BuildResultColumn.ID ) );
   }//End Method
   
}//End Class
