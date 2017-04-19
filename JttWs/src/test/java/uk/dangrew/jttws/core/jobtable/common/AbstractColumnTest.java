/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.common;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.core.jobtable.jobname.JobNameAlphabetical;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.ColumnType;
import uk.dangrew.jttws.core.jobtable.structure.Filter;
import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.core.jobtable.web.PageSorting;
import uk.dangrew.jttws.mvc.repository.PageJob;

public class AbstractColumnTest {

   private static final String NAME = "some name";
   private static final String ID = "uniqueness";
   private static final ColumnType TYPE = ColumnType.ProgressBar;
   
   private List< PageJob > jobs;
   private JobTableParameters parameters;
   
   private SortingFunction alphabetical;
   private SortingFunction reverseAlphabetical;
   @Mock private Filter filter;
   
   private AbstractColumn systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      jobs = new ArrayList<>();
      jobs.add( new PageJob( new JenkinsJobImpl( "anything" ) ) );
      jobs.add( new PageJob( new JenkinsJobImpl( "zebra" ) ) );
      
      parameters = new JobTableParameters();
      alphabetical = new JobNameAlphabetical();
      reverseAlphabetical = new ReverseSorting( alphabetical );
      
      systemUnderTest = constructSut( filter );
   }//End Method
   
   protected AbstractColumn constructSut( Filter filter ){
      return new AbstractColumn( NAME, ID, TYPE, filter, alphabetical, reverseAlphabetical ) {
         
         @Override public String valueForJob( PageJob job ) {
            return null;
         }//End Method
      };
   }//End Method
   
   protected AbstractColumn constructFullSut(){
      return constructSut( filter );
   }//End Method

   @Test public void shouldProvideName() {
      assertThat( systemUnderTest.name(), is( NAME ) );
   }//End Method
   
   @Test public void shouldProvideType() {
      assertThat( systemUnderTest.type(), is( TYPE ) );
   }//End Method
   
   @Test public void shouldProvideId(){
      assertThat( systemUnderTest.id(), is( ID ) );
   }//End Method
   
   @Test public void shouldSortJobs() {
      SortingFunction sorting = alphabetical;
      ReverseSorting reverse = new ReverseSorting( sorting );
      Collections.sort( jobs, reverse );
      List< PageJob > originalJobs = new ArrayList<>( jobs );
      
      parameters.sortBy( sorting.name() );
      systemUnderTest.sort( jobs, parameters );
      
      assertThat( jobs, is( not( originalJobs ) ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptSortJobsForInvalidName() {
      parameters.sortBy( "anything" );
      systemUnderTest.sort( jobs, parameters );
   }//End Method
   
   @Test public void shouldProvideSortOptionsAllInActive(){
      List< PageSorting > entries = systemUnderTest.sortOptions();
      
      assertThat( entries, hasSize( 2 ) );
      
      assertThat( entries.get( 0 ).name(), is( alphabetical.name() ) );
      assertThat( entries.get( 0 ).isActive(), is( false ) );
      assertThat( entries.get( 1 ).name(), is( reverseAlphabetical.name() ) );
      assertThat( entries.get( 1 ).isActive(), is( false ) );
   }//End Method
   
   @Test public void shouldFilterJobs(){
      String filterValue = "something filtery";
      parameters.filterBy( systemUnderTest.name(), filterValue );
      
      List< PageJob > spiedJobs = spy( jobs );
      List< PageJob > alternateList = Arrays.asList( mock( PageJob.class ) );
      when( filter.identifyExclusions( spiedJobs, filterValue ) ).thenReturn( alternateList );
      
      systemUnderTest.filter( spiedJobs, parameters );
      verify( spiedJobs ).removeAll( alternateList );
   }//End Method

   @Test public void shouldProvideFilters(){
      String filterValue = "something filtery";
      parameters.filterBy( systemUnderTest.name(), filterValue );
      
      List< PageFilter > filters = Arrays.asList( mock( PageFilter.class ) );
      when( filter.filterOptions( jobs, filterValue ) ).thenReturn( filters );
      assertThat( systemUnderTest.filters( jobs, parameters ), is( filters ) );
   }//End Method

}//End Class
