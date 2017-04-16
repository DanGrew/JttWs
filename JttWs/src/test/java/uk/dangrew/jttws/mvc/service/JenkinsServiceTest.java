/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.service;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.dangrew.jttws.mvc.repository.JenkinsRepository;
import uk.dangrew.jttws.mvc.repository.PageJob;
import uk.dangrew.jttws.mvc.repository.PageUser;

public class JenkinsServiceTest {

   @Mock private JenkinsRepository repository;
   private JenkinsService systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new JenkinsService( repository );
   }//End Method

   @Test public void shouldBeSpringService(){
      assertThat( JenkinsService.class.getAnnotation( Service.class ), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldAutowireConstructorWithRepo() throws NoSuchMethodException, SecurityException {
      assertThat( JenkinsService.class.getConstructor( JenkinsRepository.class ).getAnnotation( Autowired.class ), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldRetrieveJobsFromRepository(){
      @SuppressWarnings("unchecked") //mocking only 
      List< PageJob > list = mock( List.class );
      when( repository.getJenkinsJobs() ).thenReturn( list );
      
      assertThat( systemUnderTest.getJobs(), is( list ) );
   }//End Method
   
   @Test public void shouldRetrieveUsersFromRepository(){
      @SuppressWarnings("unchecked") //mocking only 
      List< PageUser > list = mock( List.class );
      when( repository.getJenkinsUsers() ).thenReturn( list );
      
      assertThat( systemUnderTest.getUsers(), is( list ) );
   }//End Method

}//End Class
