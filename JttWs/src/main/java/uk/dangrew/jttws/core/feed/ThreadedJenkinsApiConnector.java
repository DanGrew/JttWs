/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.feed;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.jtt.connection.api.JenkinsApiConnector;
import uk.dangrew.jtt.connection.api.sources.ExternalApi;
import uk.dangrew.jupa.javafx.platform.PlatformLifecycle;
import uk.dangrew.sd.viewer.basic.DigestViewer;

/**
 * The {@link ThreadedJenkinsApiConnector} is a {@link JenkinsApiConnector} wrapped in the 
 * {@link PlatformImpl} for the JavaFx threading.
 */
public class ThreadedJenkinsApiConnector {
   
   private final JenkinsApiConnector connector;;
   
   /**
    * Constructs a new {@link ThreadedJenkinsApiConnector}.
    * @param api the {@link ExternalApi} to connect with.
    */
   public ThreadedJenkinsApiConnector( ExternalApi api ) {
      this( new JenkinsApiConnector( api ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link ThreadedJenkinsApiConnector}.
    * @param connector the {@link JenkinsApiConnector} to connect with.
    */
   ThreadedJenkinsApiConnector( JenkinsApiConnector connector ) {
      this.connector = connector;
   }//End Constructor
   
   /**
    * Method to connect using the {@link JenkinsApiConnector} within the JavaFx thread.
    */
   public void connect(){
      PlatformImpl.startup( () -> {} );
      PlatformImpl.runAndWait( () -> {
         ExternalApi result = connector.connect( new DigestViewer() );
         if ( result == null ) {
            PlatformLifecycle.shutdown();
         }
      } );
   }//End Method

}//End Class
