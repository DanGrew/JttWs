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

import uk.dangrew.jttws.core.jobtable.TableData;
import uk.dangrew.jttws.core.jobtable.buildresult.BuildResultColumn;
import uk.dangrew.jttws.core.jobtable.jobname.JobNameColumn;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsUser;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationEntry;

public class JobTablePropertiesTest {

   @Mock private Model model;
   @Captor private ArgumentCaptor< List< ConfigurationEntry > > entryCaptor;
   @Captor private ArgumentCaptor< Map< String, List< ConfigurationEntry > > > filtersCaptor;
   
   private JobTableParameters parameters;
   @Mock private TableData data;
   private List< JwsJenkinsJob > jobs;
   private List< JwsJenkinsUser > users;
   private JobTableProperties systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      when( data.columns() ).thenReturn( Arrays.asList( new JobNameColumn(), new BuildResultColumn() ) );
      
      parameters = new JobTableParameters();
      jobs = new ArrayList<>();
      users = new ArrayList<>();
      systemUnderTest = new JobTableProperties();
   }//End Method

   @Test public void shouldPopulateColumnsInParameters() {
      parameters.includeColumns( JobNameColumn.staticName() );
      systemUnderTest.populate( model, data, parameters, jobs, users );
      
      verify( model ).addAttribute( eq( JobTableProperties.COLUMNS ), entryCaptor.capture() );
      
      List< ConfigurationEntry > entries = entryCaptor.getValue();
      assertThat( entries, hasSize( 2 ) );
      assertThat( entries.get( 0 ).name(), is( JobNameColumn.staticName() ) );
      assertThat( entries.get( 0 ).isActive(), is( true ) );
      assertThat( entries.get( 1 ).name(), is( BuildResultColumn.staticName() ) );
      assertThat( entries.get( 1 ).isActive(), is( false ) );
   }//End Method
   
   @Test public void shouldPopulateColumnsInParametersWhenNothingConfigured() {
      systemUnderTest.populate( model, data, parameters, jobs, users );
      
      verify( model ).addAttribute( eq( JobTableProperties.COLUMNS ), entryCaptor.capture() );
      
      List< ConfigurationEntry > entries = entryCaptor.getValue();
      assertThat( entries, hasSize( 2 ) );
      assertThat( entries.get( 0 ).name(), is( JobNameColumn.staticName() ) );
      assertThat( entries.get( 0 ).isActive(), is( true ) );
      assertThat( entries.get( 1 ).name(), is( BuildResultColumn.staticName() ) );
      assertThat( entries.get( 1 ).isActive(), is( true ) );
   }//End Method
   
   @Test public void shouldProvideTableData(){
      systemUnderTest.populate( model, data, parameters, jobs, users );
      verify( model ).addAttribute( JobTableProperties.DATA, data );
   }//End Method
   
   @Test public void shouldFilterJobsAndAddToModel(){
      systemUnderTest.populate( model, data, parameters, jobs, users );
      verify( data ).filter( jobs, parameters );
      verify( model ).addAttribute( JobTableProperties.JOBS, jobs );
   }//End Method
   
   @Test public void shouldAddFilterForColumnIncluded(){
      List< ConfigurationEntry > entries = new ArrayList<>();
      when( data.filtersFor( JobNameColumn.staticName(), jobs, parameters ) ).thenReturn( entries );
      
      parameters.includeColumns( JobNameColumn.staticName() );
      systemUnderTest.populate( model, data, parameters, jobs, users );
      
      verify( model ).addAttribute( eq( JobTableProperties.FILTERS ), filtersCaptor.capture() );
      
      Map< String, List< ConfigurationEntry > > filters = filtersCaptor.getValue();
      assertThat( filters, hasKey( JobNameColumn.staticName() ) );
      assertThat( filters.get( JobNameColumn.staticName() ), is( entries ) );
      
      assertThat( filters.get( BuildResultColumn.staticName() ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldBeSpringComponent(){
      assertThat( JobTableProperties.class.getAnnotation( Component.class ), is( notNullValue() ) );
   }//End Method
   
}//End Class
