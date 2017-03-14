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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jttws.core.jobtable.buildresult.BuildResultColumn;
import uk.dangrew.jttws.core.jobtable.jobname.JobNameColumn;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

public class TableDataTest {

   private List< JwsJenkinsJob > jobs;
   private JobTableParameters parameters;

   @Mock private JobNameColumn jobNameColumn;
   @Mock private BuildResultColumn buildResultColumn;
   private TableData systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      jobs = new ArrayList<>();
      parameters = new JobTableParameters();
      
      systemUnderTest = new TableData( jobNameColumn, buildResultColumn );
   }//End Method

   @Test public void shouldProvideColumns() {
      assertThat( systemUnderTest.columns(), is( Arrays.asList( jobNameColumn, buildResultColumn ) ) );
   }//End Method
   
   @Test public void shouldSortByColumn(){
      parameters.sortColumn( jobNameColumn.name() );
      systemUnderTest.sort( jobs, parameters );
      verify( jobNameColumn ).sort( jobs, parameters );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAllowSortByInvalidColumnName(){
      parameters.sortColumn( "anything" );
      systemUnderTest.sort( jobs, parameters );
      verify( jobNameColumn ).sort( jobs, parameters );
   }//End Method
   
   @Test public void shouldFilterForAllColumns(){
      systemUnderTest.filter( jobs, parameters );
      verify( jobNameColumn ).filter( jobs, parameters );
      verify( buildResultColumn ).filter( jobs, parameters );
   }//End Method
   
}//End Class
