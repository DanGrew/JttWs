/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.View;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jtt.model.users.JenkinsUserImpl;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.parameters.ParametersPopulator;
import uk.dangrew.jttws.core.jobtable.properties.JobTableProperties;
import uk.dangrew.jttws.mvc.repository.PageJob;
import uk.dangrew.jttws.mvc.repository.PageUser;
import uk.dangrew.jttws.mvc.service.JenkinsService;
import uk.dangrew.jttws.mvc.web.jobtable.JobTableColumns;

public class SiteControllerTest {

   private MockMvc mvc;
   @Mock private View view;
   @Mock private Model model;
   @Mock private HttpServletRequest request;
   @Mock private HttpServletResponse response;
   
   private JobTableParameters parameters;
   private List< PageJob > jobs;
   private List< PageUser > users;
   
   @Mock private JobTableProperties properties;
   @Mock private ParametersPopulator paramsPopulator;
   @Mock private JenkinsService service;
   private SiteController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      parameters = new JobTableParameters();
      when( paramsPopulator.construct( Mockito.any(), eq( request ), eq( response ) ) ).thenReturn( parameters );
      
      jobs = Arrays.asList( new PageJob( new JenkinsJobImpl( "anything" ) ) );
      when( service.getJobs() ).thenReturn( jobs );
      users = Arrays.asList( new PageUser( new JenkinsUserImpl( "anyone" ) ) );
      when( service.getUsers() ).thenReturn( users );
      
      systemUnderTest = new SiteController( service, properties, paramsPopulator );
      mvc = MockMvcBuilders.standaloneSetup( systemUnderTest ).setSingleView( view ).build();
   }//End Method

   @Test public void shouldHaveControllerAnnotation() {
      assertThat( SiteController.class.getAnnotation( Controller.class ), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldAutowireJobService() throws NoSuchMethodException, SecurityException{
      assertThat( SiteController.class.getConstructor( 
               JenkinsService.class, JobTableProperties.class, ParametersPopulator.class 
      ).getAnnotation( Autowired.class ), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldProvideHomePage(){
      assertThat( systemUnderTest.home( 
               request, response, model 
      ), is( SiteController.PAGE_HOME ) );
   }//End Method
   
   @Test public void shouldProvideTableRefreshPage(){
      assertThat( systemUnderTest.refreshTable( 
               request, response, model 
      ), is( SiteController.PAGE_TABLE ) );
   }//End Method
   
   @Test public void shouldPopulateProperties(){
      systemUnderTest.refreshTable( request, response, model );
      verify( properties ).populateTable( Mockito.any(), Mockito.any(), eq( parameters ), eq( jobs ), eq( users ) );
      verify( properties ).populateAttributes( eq( model ), Mockito.any() );
   }//End Method
   
   @Test public void shouldPopulateParameters(){
      systemUnderTest.refreshTable( request, response, model );
      verify( paramsPopulator ).construct( Mockito.any(), eq( request ), eq( response ) );
   }//End Method
   
}//End Class
