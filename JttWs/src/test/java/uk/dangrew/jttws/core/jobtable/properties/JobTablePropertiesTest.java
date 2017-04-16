/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.properties;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.core.jobtable.TableSpecification;
import uk.dangrew.jttws.core.jobtable.buildresult.BuildResultColumn;
import uk.dangrew.jttws.core.jobtable.jobname.JobNameColumn;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.web.PageFilter;
import uk.dangrew.jttws.core.jobtable.web.PageTable;
import uk.dangrew.jttws.mvc.repository.PageJob;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsUser;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationEntry;

public class JobTablePropertiesTest {

   @Mock private Model model;
   @Captor private ArgumentCaptor< List< ConfigurationEntry > > entryCaptor;
   @Captor private ArgumentCaptor< Map< String, List< ConfigurationEntry > > > filtersCaptor;
   
   private JobTableParameters parameters;
   @Mock private TableSpecification data;
   private PageTable table;
   private List< PageJob > jobs;
   private List< JwsJenkinsUser > users;
   private JobTableProperties systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      when( data.columns() ).thenReturn( Arrays.asList( new JobNameColumn(), new BuildResultColumn() ) );

      table = new PageTable();
      parameters = new JobTableParameters();
      jobs = Arrays.asList(
               new PageJob( new JenkinsJobImpl( "My Job" ) ),
               new PageJob( new JenkinsJobImpl( "Your Job" ) ),
               new PageJob( new JenkinsJobImpl( "Their Job" ) )
      );
      users = new ArrayList<>();
      systemUnderTest = new JobTableProperties();
   }//End Method

   @Test public void shouldPopulateColumnsInParameters() {
      parameters.includeColumns( JobNameColumn.staticName() );
      systemUnderTest.populateTable( table, data, parameters, jobs, users );
      
      assertThat( table.columns(), hasSize( 2 ) );
      assertThat( table.columns().get( 0 ).name(), is( JobNameColumn.staticName() ) );
      assertThat( table.columns().get( 0 ).isActive(), is( true ) );
      assertThat( table.columns().get( 1 ).name(), is( BuildResultColumn.staticName() ) );
      assertThat( table.columns().get( 1 ).isActive(), is( false ) );
   }//End Method
   
   @Test public void shouldPopulateColumnsInParametersWhenNothingConfigured() {
      systemUnderTest.populateTable( table, data, parameters, jobs, users );
      
      assertThat( table.columns(), hasSize( 2 ) );
      assertThat( table.columns().get( 0 ).name(), is( JobNameColumn.staticName() ) );
      assertThat( table.columns().get( 0 ).isActive(), is( true ) );
      assertThat( table.columns().get( 1 ).name(), is( BuildResultColumn.staticName() ) );
      assertThat( table.columns().get( 1 ).isActive(), is( true ) );
   }//End Method
   
   @Test public void shouldProvideTableData(){
      systemUnderTest.populateAttributes( model, table );
      verify( model ).addAttribute( JobTableProperties.DATA, table );
   }//End Method
   
   @Test public void shouldFilterJobsAndAddToModel(){
      systemUnderTest.populateTable( table, data, parameters, jobs, users );
      verify( data ).filter( jobs, parameters );
      
      assertThat( table.jobs(), hasSize( jobs.size() ) );
      for( int i = 0; i < jobs.size(); i++ ) {
         assertThat( table.jobs().get( i ), is( jobs.get( i ) ) );
      }
   }//End Method
   
   @Test public void shouldAddFilterForColumnIncluded(){
      List< PageFilter > entries = Arrays.asList( new PageFilter( "anything" ) );
      when( data.filtersFor( JobNameColumn.staticName(), jobs, parameters ) ).thenReturn( entries );
      
      parameters.includeColumns( JobNameColumn.staticName() );
      systemUnderTest.populateTable( table, data, parameters, jobs, users );
      
      assertThat( table.filtersFor( table.columns().get( 0 ) ), is( entries ) );
      assertThat( table.filtersFor( table.columns().get( 1 ) ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldBeSpringComponent(){
      assertThat( JobTableProperties.class.getAnnotation( Component.class ), is( notNullValue() ) );
   }//End Method
   
}//End Class
