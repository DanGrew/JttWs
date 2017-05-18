/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.feed;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;

import uk.dangrew.jtt.connection.api.handling.live.LiveStateFetcher;
import uk.dangrew.jtt.connection.synchronisation.time.BuildProgressCalculator;
import uk.dangrew.jttws.mvc.repository.PageJob;
import uk.dangrew.jttws.mvc.repository.PageTranslator;
import uk.dangrew.jttws.mvc.repository.PageUser;

public class JenkinsConnectionTest {

   @Mock private PageTranslator translator;
   @Mock private BuildProgressCalculator calculator;
   @Mock private LiveStateFetcher fetcher;
   @Mock private ThreadedJenkinsApiConnector connector;
   private JenkinsConnection systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new JenkinsConnection( 
               translator, calculator, fetcher, connector
      );
   }//End Method

   @Test public void shouldConnectAndFetchLastCompletedBuilds() {
      systemUnderTest.setApplicationContext( mock( ApplicationContext.class ) );
      InOrder order = inOrder( connector, fetcher );
      order.verify( connector ).connect();
      order.verify( fetcher ).loadLastCompletedBuild();
   }//End Method
   
   @Test public void shouldProvideTranslatedJobs() {
      List< PageJob > jobs = Arrays.asList( mock( PageJob.class ), mock( PageJob.class ) );
      when( translator.translateJobs() ).thenReturn( jobs );
      
      assertThat( systemUnderTest.getJobs(), is( jobs ) );
   }//End Method
   
   @Test public void shouldProvideTranslatedUsers() {
      List< PageUser > users = Arrays.asList( mock( PageUser.class ), mock( PageUser.class ) );
      when( translator.translateUsers() ).thenReturn( users );
      
      assertThat( systemUnderTest.getUsers(), is( users ) );
   }//End Method
   
   @Test public void shouldUpdateBuildStates() {
      systemUnderTest.updateDatabase();
      verify( fetcher ).updateBuildState();
   }//End Method
   
   @Test public void shouldUpdateBuildStateInIntervals() throws NoSuchMethodException, SecurityException {
      Method method = JenkinsConnection.class.getDeclaredMethod( "updateDatabase" );
      assertThat( method.getAnnotation( Scheduled.class ), is( notNullValue() ) );
      assertThat( method.getAnnotation( Scheduled.class ).fixedRate(), is( JenkinsConnection.POLL_INTERVAL ) );
   }//End Method
   
   @Test public void shouldUpdateBuildTime() {
      systemUnderTest.updateBuildTime();
      verify( calculator ).run();
   }//End Method
   
   @Test public void shouldUpdateBuildTimeInIntervals() throws NoSuchMethodException, SecurityException {
      Method method = JenkinsConnection.class.getDeclaredMethod( "updateBuildTime" );
      assertThat( method.getAnnotation( Scheduled.class ), is( notNullValue() ) );
      assertThat( method.getAnnotation( Scheduled.class ).fixedRate(), is( JenkinsConnection.LOCAL_UPDATE_INTERVAL ) );
   }//End Method

}//End Class
