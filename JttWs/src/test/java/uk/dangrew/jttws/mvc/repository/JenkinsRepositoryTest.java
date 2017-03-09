/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.repository;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import uk.dangrew.jttws.core.feed.JenkinsConnection;

public class JenkinsRepositoryTest {

   @Mock private JenkinsConnection connection;
   @Mock private Logger logger;
   private JenkinsRepository systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new JenkinsRepository( logger );
   }//End Method

   @Test public void shouldHaveRepoAnnotationForBoot() {
      assertThat( JenkinsRepository.class.getAnnotation( Repository.class ), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldReportNoConnectionAvailableForJobs(){
      assertThat( systemUnderTest.getJenkinsJobs(), is( new ArrayList<>() ) );
      verify( logger ).error( JenkinsRepository.CONNECTION_ERROR );
   }//End Method
   
   @Test public void shouldRetrieveJenkinsConnectionAndUseForJobs(){
      ApplicationContext context = mock( ApplicationContext.class );
      when( context.getBean( JenkinsConnection.class ) ).thenReturn( connection );
      
      @SuppressWarnings("unchecked") //mocking only 
      List< JwsJenkinsJob > list = mock( List.class );
      when( connection.getJobs() ).thenReturn( list );
      
      systemUnderTest.setApplicationContext( context );
      List< JwsJenkinsJob > jobsRetrieved = systemUnderTest.getJenkinsJobs();
      assertThat( jobsRetrieved, is( list ) );
      assertThat( jobsRetrieved, is( not( new ArrayList<>() ) ) );
   }//End Method
   
   @Test public void shouldReportNoConnectionAvailableForUsers(){
      assertThat( systemUnderTest.getJenkinsUsers(), is( new ArrayList<>() ) );
      verify( logger ).error( JenkinsRepository.CONNECTION_ERROR );
   }//End Method
   
   @Test public void shouldRetrieveJenkinsConnectionAndUseForUsers(){
      ApplicationContext context = mock( ApplicationContext.class );
      when( context.getBean( JenkinsConnection.class ) ).thenReturn( connection );
      
      @SuppressWarnings("unchecked") //mocking only 
      List< JwsJenkinsUser > list = mock( List.class );
      when( connection.getUsers() ).thenReturn( list );
      
      systemUnderTest.setApplicationContext( context );
      List< JwsJenkinsUser > retrieved = systemUnderTest.getJenkinsUsers();
      assertThat( retrieved, is( list ) );
      assertThat( retrieved, is( not( new ArrayList<>() ) ) );
   }//End Method

}//End Class
