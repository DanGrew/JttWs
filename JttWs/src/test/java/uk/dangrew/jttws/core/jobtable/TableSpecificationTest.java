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
import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.mvc.repository.PageJob;

public class TableSpecificationTest {

   private PageJob job;
   private List< PageJob > jobs;
   private JobTableParameters parameters;

   @Mock private List< PageFilter > filters;
   @Spy private JobNameColumn jobNameColumn;
   @Spy private BuildResultColumn buildResultColumn;
   private TableSpecification systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      when( jobNameColumn.name() ).thenReturn( JobNameColumn.staticName() );
      when( buildResultColumn.name() ).thenReturn( BuildResultColumn.staticName() );
      
      jobs = new ArrayList<>();
      job = new PageJob( new JenkinsJobImpl( "Badminton" ) );
      parameters = new JobTableParameters();
      
      systemUnderTest = new TableSpecification( jobNameColumn, buildResultColumn );
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
   
   @Test public void shouldProvideColumnForId(){
      systemUnderTest = new TableSpecification();
      for ( Column column : systemUnderTest.columns() ) {
         assertThat( systemUnderTest.columnForId( column.id() ), is( column ) );
      }
      
      assertThat( systemUnderTest.columnForId( "anything" ), is( nullValue() ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptDuplicateSortingFunctions(){
      new TableSpecification( new JobNameColumn(), new JobNameColumn() );
   }//End Method
   
}//End Class
