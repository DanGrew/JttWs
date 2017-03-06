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
import static org.mockito.Mockito.mock;
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
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.View;

import uk.dangrew.jtt.model.jobs.JenkinsJobImpl;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;
import uk.dangrew.jttws.mvc.service.JenkinsService;
import uk.dangrew.jttws.mvc.web.jobtable.JobListHandler;
import uk.dangrew.jttws.mvc.web.jobtable.JobTableColumns;

public class SiteControllerTest {

   private MockMvc mvc;
   @Mock private View view;
   
   private List< JwsJenkinsJob > jobs;
   
   @Mock private JenkinsService service;
   @Mock private JobListHandler handler;
   private SiteController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      jobs = Arrays.asList( new JwsJenkinsJob( new JenkinsJobImpl( "anything" ) ) );
      when( service.getJobs() ).thenReturn( jobs );
      
      systemUnderTest = new SiteController( service, handler );
      mvc = MockMvcBuilders.standaloneSetup( systemUnderTest ).setSingleView( view ).build();
   }//End Method

   @Test public void shouldHaveControllerAnnotation() {
      assertThat( SiteController.class.getAnnotation( Controller.class ), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldAutowireJobService() throws NoSuchMethodException, SecurityException{
      assertThat( SiteController.class.getConstructor( JenkinsService.class, JobListHandler.class ).getAnnotation( Autowired.class ), is( notNullValue() ) );
   }//End Method
   
   @Test public void homeShouldProvideJobsFromService() throws Exception{
      mvc.perform( get( SiteController.HOME_ADDRESS ) )
         .andExpect( status().isOk() )
         .andExpect( model().attribute( SiteController.JOB_COLUMNS, JobTableColumns.values() )
      );
   }//End Method
   
   @Test public void refreshTableShouldProvideJobsFromService() throws Exception{
      mvc.perform( get( SiteController.JOBS_TABLE_ADDRESS ) )
         .andExpect( status().isOk() )
         .andExpect( model().attribute( SiteController.JOB_COLUMNS, JobTableColumns.values() )
      );
   }//End Method
   
   @Test public void shouldProvideHomePage(){
      assertThat( systemUnderTest.home( 
               mock( HttpServletRequest.class ), 
               mock( HttpServletResponse.class ), 
               mock( Model.class ) 
      ), is( SiteController.PAGE_HOME ) );
   }//End Method
   
   @Test public void shouldProvideTableRefreshPage(){
      assertThat( systemUnderTest.refreshTable( 
               mock( HttpServletRequest.class ), 
               mock( HttpServletResponse.class ), 
               mock( Model.class ) 
      ), is( SiteController.PAGE_TABLE ) );
   }//End Method
   
   @Test public void homeShouldUseJobHandler(){
      HttpServletRequest request = mock( HttpServletRequest.class ); 
      HttpServletResponse response = mock( HttpServletResponse.class ); 
      Model model = mock( Model.class );
      
      systemUnderTest.home( request, response, model );
      verify( handler ).handleSorting( request, response, jobs, model );
      verify( handler ).handleFiltering( request, response, jobs, model );
   }//End Method
   
   @Test public void refeshTableShouldUseJobHandler(){
      HttpServletRequest request = mock( HttpServletRequest.class ); 
      HttpServletResponse response = mock( HttpServletResponse.class ); 
      Model model = mock( Model.class );
      
      systemUnderTest.refreshTable( request, response, model );
      verify( handler ).handleSorting( request, response, jobs, model );
      verify( handler ).handleFiltering( request, response, jobs, model );
   }//End Method

}//End Class
