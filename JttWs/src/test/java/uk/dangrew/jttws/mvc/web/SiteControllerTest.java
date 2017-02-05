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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

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

import uk.dangrew.jttws.mvc.repository.JenkinsJobDto;
import uk.dangrew.jttws.mvc.service.JenkinsJobService;

public class SiteControllerTest {

   private MockMvc mvc;
   @Mock private View view;
   
   @Mock private JenkinsJobService service;
   private SiteController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      systemUnderTest = new SiteController( service );
      mvc = MockMvcBuilders.standaloneSetup( systemUnderTest ).setSingleView( view ).build();
   }//End Method

   @Test public void shouldHaveControllerAnnotation() {
      assertThat( SiteController.class.getAnnotation( Controller.class ), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldAutowireJobService() throws NoSuchMethodException, SecurityException{
      assertThat( SiteController.class.getConstructor( JenkinsJobService.class ).getAnnotation( Autowired.class ), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldProvideJobsFromService() throws Exception{
      List<JenkinsJobDto> jobs = Arrays.asList(new JenkinsJobDto( "anything" ) );
      when(service.getJobs() ).thenReturn(jobs);
 
      mvc.perform( get("/") )
         .andExpect(status().isOk())
         .andExpect(model().attribute(SiteController.ATTRIBUTE_JOBS, jobs)
      );
   }//End Method
   
   @Test public void shouldProvideHomePage(){
      assertThat( systemUnderTest.home( mock( Model.class ) ), is( SiteController.PAGE_HOME ) );
   }//End Method

}//End Class
