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
import org.mockito.MockitoAnnotations;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jtt.model.users.JenkinsUser;
import uk.dangrew.jtt.model.users.JenkinsUserImpl;
import uk.dangrew.jttws.core.jobtable.common.ReverseSorting;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.ColumnType;
import uk.dangrew.jttws.core.jobtable.structure.Filter;
import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.core.jobtable.web.PageSorting;
import uk.dangrew.jttws.mvc.repository.PageJob;

public class CommittersColumnTest {
   
   private List< PageJob > jobs;
   private PageJob job1;
   private PageJob job2;
   private JenkinsUser user1;
   private JenkinsUser user2;
   private JobTableParameters parameters;
   
   private Filter filter;
   private SortingFunction alphabetical;
   private SortingFunction reverseAlphabetical;
   
   private CommittersColumn systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      jobs = new ArrayList<>();
      jobs.add( job1 = new PageJob( new JenkinsJobImpl( "anything" ) ) );
      jobs.add( job2 = new PageJob( new JenkinsJobImpl( "zebra" ) ) );
      job1.association().culprits().add( user1 = new JenkinsUserImpl( "Dan" ) );
      job2.association().culprits().add( user2 = new JenkinsUserImpl( "Jess" ) );
      
      parameters = new JobTableParameters();
      filter = new CommittersFilter();
      alphabetical = new CommittersAlphabetical();
      reverseAlphabetical = new ReverseSorting( alphabetical );
      systemUnderTest = new CommittersColumn( filter, alphabetical, reverseAlphabetical );
   }//End Method

   @Test public void shouldProvideName() {
      assertThat( systemUnderTest.name(), is( CommittersColumn.NAME ) );
      assertThat( CommittersColumn.staticName(), is( CommittersColumn.NAME ) );
   }//End Method
   
   @Test public void shouldProvideType() {
      assertThat( systemUnderTest.type(), is( ColumnType.String ) );
   }//End Method
   
   @Test public void shouldProvideValueForJob(){
      assertThat( systemUnderTest.valueForJob( job1 ), is( job1.committers() ) );
      assertThat( systemUnderTest.valueForJob( job2 ), is( job2.committers() ) );
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
      parameters.filterBy( systemUnderTest.name(), user1.nameProperty().get() );
      systemUnderTest.filter( jobs, parameters );
      assertThat( jobs, is( Arrays.asList( job1 ) ) );
   }//End Method
   
   @Test public void shouldApplyFilterToExcludeNoJobs(){
      parameters.filterBy( systemUnderTest.name(), "" );
      systemUnderTest.filter( jobs, parameters );
      assertThat( jobs, is( Arrays.asList( job1, job2 ) ) );
   }//End Method
   
   @Test public void shouldHandleNoConfiguration(){
      parameters.filterBy( systemUnderTest.name(), null );
      systemUnderTest.filter( jobs, parameters );
      assertThat( jobs, is( Arrays.asList( job1, job2 ) ) );
   }//End Method
   
   @Test public void shouldApplyMultipleFiltersToIncludeAllJobs(){
      parameters.filterBy( systemUnderTest.name(), user1.nameProperty().get() + ", " + user2.nameProperty().get() );
      systemUnderTest.filter( jobs, parameters );
      assertThat( jobs, is( Arrays.asList( job1, job2 ) ) );
   }//End Method
   
   @Test public void shouldProvideFiltersAllActive(){
      assertThat( systemUnderTest.filters( jobs, parameters ), is( new CommittersFilter().filterOptions( jobs, null ) ) );
   }//End Method
   
   @Test public void shouldProvideFiltersGivenValue(){
      parameters.filterBy( systemUnderTest.name(), "anything" );
      assertThat( systemUnderTest.filters( jobs, parameters ), is( new CommittersFilter().filterOptions( jobs, "anything" ) ) );
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
      assertThat( systemUnderTest.id(), is( CommittersColumn.ID ) );
      assertThat( CommittersColumn.staticId(), is( CommittersColumn.ID ) );
   }//End Method
}//End Class
