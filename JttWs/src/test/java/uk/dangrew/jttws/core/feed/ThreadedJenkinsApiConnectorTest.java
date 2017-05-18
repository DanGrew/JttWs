/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.feed;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jtt.connection.api.JenkinsApiConnector;
import uk.dangrew.jtt.connection.api.sources.ExternalApi;
import uk.dangrew.jupa.javafx.platform.PlatformLifecycle;
import uk.dangrew.jupa.javafx.platform.PlatformLifecycleImpl;

public class ThreadedJenkinsApiConnectorTest {

   @Mock private PlatformLifecycleImpl lifecycle;
   @Mock private JenkinsApiConnector connector;
   private ThreadedJenkinsApiConnector systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      PlatformLifecycle.setInstance( lifecycle );
      systemUnderTest = new ThreadedJenkinsApiConnector( connector );
   }//End Method

   @Test public void shouldInvokeConnector() {
      systemUnderTest.connect();
      verify( connector ).connect( Mockito.any() );
   }//End Method
   
   @Test public void shouldShutdownIfConnectionFails() {
      systemUnderTest.connect();
      verify( lifecycle ).shutdownPlatform();
   }//End Method
   
   @Test public void shouldNotShutdownIfConnectionSucceeds() {
      when( connector.connect( Mockito.any() ) ).thenReturn( mock( ExternalApi.class ) );
      systemUnderTest.connect();
      verifyZeroInteractions( lifecycle );
   }//End Method

}//End Class
