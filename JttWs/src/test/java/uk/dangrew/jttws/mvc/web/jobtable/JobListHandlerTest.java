/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web.jobtable;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationEntry;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationProvider;
import uk.dangrew.jttws.mvc.web.configuration.CookieManager;
import uk.dangrew.jttws.mvc.web.jobtable.JobListHandler;
import uk.dangrew.jttws.mvc.web.jobtable.JobTableSorting;
import uk.dangrew.jttws.mvc.web.jobtable.JobTableSortingConverter;

public class JobListHandlerTest {

   @Mock private CookieManager cookies;
   @Mock private JobTableSortingConverter sortingConverter;
   private List< JwsJenkinsJob > jobs;
   
   @Mock private HttpServletResponse response;
   @Mock private HttpServletRequest request;
   @Mock private Model model;
   
   @Captor private ArgumentCaptor< List< JwsJenkinsJob > > jobCaptor;
   @Captor private ArgumentCaptor< List< ConfigurationEntry > > entryCaptor;
   private JobListHandler systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      jobs = new ArrayList<>();
      jobs.add( new JwsJenkinsJob( new JenkinsJobImpl( "Job1" ) ) );
      jobs.add( new JwsJenkinsJob( new JenkinsJobImpl( "Job2" ) ) );
      jobs.add( new JwsJenkinsJob( new JenkinsJobImpl( "Job3" ) ) );
      jobs.add( new JwsJenkinsJob( new JenkinsJobImpl( "Job4" ) ) );
      
      systemUnderTest = new JobListHandler( cookies, new ConfigurationProvider(), sortingConverter );
   }//End Method

   @Test public void shouldTakeSortingAndApplyToModel() {
      ConfigurationProvider configuration = mock( ConfigurationProvider.class );
      systemUnderTest = new JobListHandler( cookies, configuration, sortingConverter );
      
      when( cookies.retrieveCookie( JobListHandler.PARAMETER_SORT, request, response ) ).thenReturn( JobTableSorting.NameDecreasing.name() );
      when( sortingConverter.convert( JobTableSorting.NameDecreasing.name() ) ).thenReturn( JobTableSorting.NameDecreasing );
      
      List< JwsJenkinsJob > toSort = new ArrayList<>( jobs );
      systemUnderTest.handleSorting( request, response, toSort, model );
      assertThat( toSort.get( 0 ), is( jobs.get( 3 ) ) );
      assertThat( toSort.get( 1 ), is( jobs.get( 2 ) ) );
      assertThat( toSort.get( 2 ), is( jobs.get( 1 ) ) );
      assertThat( toSort.get( 3 ), is( jobs.get( 0 ) ) );
      
      verify( configuration ).provideConfigurationEntries( 
               eq( JobListHandler.ATTRIBUTE_SORT_OPTIONS ), 
               eq( model ), 
               eq( Arrays.asList( JobTableSorting.values() ) ), 
               Mockito.any(),
               Mockito.any()
      );
   }//End Method
   
   @Test public void shouldProvideAppropriateConfigurationFunctions(){
      systemUnderTest = new JobListHandler( cookies, new ConfigurationProvider(), sortingConverter );
      when( sortingConverter.convert( Mockito.anyString() ) ).thenReturn( JobTableSorting.TimestampDecreasing );
      
      systemUnderTest.handleSorting( request, response, jobs, model );
      
      verify( model ).addAttribute( eq( JobListHandler.ATTRIBUTE_SORT_OPTIONS ), entryCaptor.capture() );
      List< ConfigurationEntry > entries = entryCaptor.getValue();
      assertThat( entries, hasSize( JobTableSorting.values().length ) );
      for ( int i = 0; i < JobTableSorting.values().length; i++ ) {
         JobTableSorting sorting = JobTableSorting.values()[ i ];
         assertThat( entries.get( i ).name(), is( sorting.name() ) );
         assertThat( entries.get( i ).isActive(), is( sorting != JobTableSorting.TimestampDecreasing ) );
      }
   }//End Method
   
   @Test public void shouldIncludeJobIfTicked(){
      when( cookies.retrieveCookie( JobListHandler.PARAMETER_FILTERED_JOBS, request, response ) ).thenReturn( jobs.get( 1 ).name() );
      systemUnderTest.handleFiltering( request, response, jobs, model );
      
      verify( model ).addAttribute( eq( JobListHandler.ATTRIBUTE_JOBS ), jobCaptor.capture() );
      
      List< JwsJenkinsJob > filtered = jobCaptor.getValue();
      assertThat( filtered, hasSize( 1 ) );
      assertThat( filtered, contains( jobs.get( 1 ) ) );
   }//End Method
   
   @Test public void shouldHaveActiveEntryIfIncluded(){
      when( cookies.retrieveCookie( JobListHandler.PARAMETER_FILTERED_JOBS, request, response ) ).thenReturn( jobs.get( 1 ).name() );
      systemUnderTest.handleFiltering( request, response, jobs, model );
      
      verify( model ).addAttribute( eq( JobListHandler.ATTRIBUTE_JOB_ENTRIES ), entryCaptor.capture() );
      
      List< ConfigurationEntry > entries = entryCaptor.getValue();
      assertThat( entries, hasSize( jobs.size() ) );
      assertThat( entries.get( 0 ).name(), is( jobs.get( 0 ).name() ) );
      assertThat( entries.get( 0 ).isActive(), is( false ) );
      assertThat( entries.get( 1 ).name(), is( jobs.get( 1 ).name() ) );
      assertThat( entries.get( 1 ).isActive(), is( true ) );
      assertThat( entries.get( 2 ).name(), is( jobs.get( 2 ).name() ) );
      assertThat( entries.get( 2 ).isActive(), is( false ) );
      assertThat( entries.get( 3 ).name(), is( jobs.get( 3 ).name() ) );
      assertThat( entries.get( 3 ).isActive(), is( false ) );
   }//End Method
   
   @Test public void shouldIncludeAllByDefault(){
      systemUnderTest.handleFiltering( request, response, jobs, model );
      
      verify( model ).addAttribute( eq( JobListHandler.ATTRIBUTE_JOB_ENTRIES ), entryCaptor.capture() );
      verify( model ).addAttribute( eq( JobListHandler.ATTRIBUTE_JOBS ), jobCaptor.capture() );
      
      List< JwsJenkinsJob > filtered = jobCaptor.getValue();
      assertThat( filtered, hasSize( jobs.size() ) );
      assertThat( filtered, is( jobs ) );
      
      List< ConfigurationEntry > entries = entryCaptor.getValue();
      assertThat( entries, hasSize( jobs.size() ) );
      for ( int i = 0; i < jobs.size(); i++ ) {
         assertThat( entries.get( i ).name(), is( jobs.get( i ).name() ) );
         assertThat( entries.get( i ).isActive(), is( true ) );
      }
   }//End Method

   @Test public void shouldNotIncludeIfNotTickedAndWithinAnotherName(){
      jobs.add( new JwsJenkinsJob( new JenkinsJobImpl( "o" ) ) );
      
      when( cookies.retrieveCookie( JobListHandler.PARAMETER_FILTERED_JOBS, request, response ) ).thenReturn( jobs.get( 1 ).name() );
      systemUnderTest.handleFiltering( request, response, jobs, model );
      
      verify( model ).addAttribute( eq( JobListHandler.ATTRIBUTE_JOB_ENTRIES ), entryCaptor.capture() );
      
      List< ConfigurationEntry > entries = entryCaptor.getValue();
      assertThat( entries, hasSize( jobs.size() ) );
      assertThat( entries.get( 0 ).name(), is( jobs.get( 0 ).name() ) );
      assertThat( entries.get( 0 ).isActive(), is( false ) );
      assertThat( entries.get( 1 ).name(), is( jobs.get( 1 ).name() ) );
      assertThat( entries.get( 1 ).isActive(), is( true ) );
      assertThat( entries.get( 4 ).name(), is( jobs.get( 4 ).name() ) );
      assertThat( entries.get( 4 ).isActive(), is( false ) );
   }//End Method
   
   @Test public void shouldHandleMultipleJobNamesBeingTicked(){
      when( cookies.retrieveCookie( JobListHandler.PARAMETER_FILTERED_JOBS, request, response ) ).thenReturn( 
               "Job1, Job2, Job3, Job4" 
      );
      shouldIncludeAllByDefault();
   }//End Method
}//End Class
