/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.core.jobtable.buildresult.BuildResultColumn;
import uk.dangrew.jttws.core.jobtable.jobname.JobNameAlphabetical;
import uk.dangrew.jttws.core.jobtable.jobname.JobNameColumn;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.core.jobtable.structure.ColumnType;
import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

public class TableDataTest {

   private JwsJenkinsJob job;
   private List< JwsJenkinsJob > jobs;
   private JobTableParameters parameters;

   @Mock private List< PageFilter > filters;
   @Spy private JobNameColumn jobNameColumn;
   @Spy private BuildResultColumn buildResultColumn;
   private TableData systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      when( jobNameColumn.name() ).thenReturn( JobNameColumn.staticName() );
      when( buildResultColumn.name() ).thenReturn( BuildResultColumn.staticName() );
      
      jobs = new ArrayList<>();
      job = new JwsJenkinsJob( new JenkinsJobImpl( "Badminton" ) );
      parameters = new JobTableParameters();
      
      systemUnderTest = new TableData( jobNameColumn, buildResultColumn );
   }//End Method

   @Test public void shouldProvideColumns() {
      assertThat( systemUnderTest.columns(), is( Arrays.asList( jobNameColumn, buildResultColumn ) ) );
   }//End Method
   
   @Test public void shouldSortByColumn(){
      parameters.sortBy( JobNameAlphabetical.staticName() );
      systemUnderTest.sort( jobs, parameters );
      verify( jobNameColumn ).sort( jobs, parameters );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAllowSortByInvalidSortingFunction(){
      parameters.sortBy( "anything" );
      systemUnderTest.sort( jobs, parameters );
      verify( jobNameColumn ).sort( jobs, parameters );
   }//End Method
   
   @Test public void shouldFilterForAllColumns(){
      systemUnderTest.filter( jobs, parameters );
      verify( jobNameColumn ).filter( jobs, parameters );
      verify( buildResultColumn ).filter( jobs, parameters );
   }//End Method
   
   @Test public void shouldProvideValueForColumn(){
      when( buildResultColumn.valueForJob( job ) ).thenReturn( job.name() );
      assertThat( systemUnderTest.valueForColumn( BuildResultColumn.staticName(), job ), is( job.name() ) );
   }//End Method
   
   @Test public void shouldHandleInvalidColumnNameWhenValueRequested(){
      assertThat( systemUnderTest.valueForColumn( "anything", job ), is( TableData.UNKNOWN_ENTRY ) );
   }//End Method
   
   @Test public void shouldProvideTypeForColumn(){
      when( buildResultColumn.type() ).thenReturn( ColumnType.ProgressBar );
      assertThat( systemUnderTest.typeForColumn( BuildResultColumn.staticName() ), is( ColumnType.ProgressBar ) );
   }//End Method
   
   @Test public void shouldHandleInvalidColumnNameWhenTypeRequested(){
      assertThat( systemUnderTest.typeForColumn( "anything" ), is( ColumnType.String ) );
   }//End Method
   
   @Test public void shouldProvideFilterConfigurationForColumn(){
      when( jobNameColumn.filters( jobs, parameters ) ).thenReturn( filters );
      assertThat( systemUnderTest.filtersFor( jobNameColumn.name(), jobs, parameters ), is( filters ) );
   }//End Method
   
   @Test public void shouldProvideEmptyConfigurationForInvalidColumn(){
      assertThat( systemUnderTest.filtersFor( "anything", jobs, parameters ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldProvideIdForColumn(){
      doCallRealMethod().when( jobNameColumn ).id();
      assertThat( systemUnderTest.idForColumn( jobNameColumn.name() ), is( jobNameColumn.id() ) );
   }//End Method
   
   @Test public void shouldNotAcceptInvalidColumnForId(){
      assertThat( systemUnderTest.idForColumn( "anything" ), is( TableData.UNKNOWN_ENTRY ) );
   }//End Method
   
   @Test public void shouldProvideColumnForId(){
      systemUnderTest = new TableData();
      for ( Column column : systemUnderTest.columns() ) {
         assertThat( systemUnderTest.columnForId( column.id() ), is( column ) );
      }
      
      assertThat( systemUnderTest.columnForId( "anything" ), is( nullValue() ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptDuplicateSortingFunctions(){
      new TableData( new JobNameColumn(), new JobNameColumn() );
   }//End Method
   
   @Test public void shouldProvideSortingOptions(){
      assertThat( systemUnderTest.sortingOptionsFor( jobNameColumn.name(), parameters ), is( jobNameColumn.sortOptions() ) );
   }//End Method
   
   @Test public void shouldProvideEmptySortingOptionsForInvalidColumn(){
      assertThat( systemUnderTest.sortingOptionsFor( "anything", parameters ), is( new ArrayList<>() ) );
   }//End Method
   
}//End Class
