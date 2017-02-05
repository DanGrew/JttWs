/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.feed;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.sun.javafx.application.PlatformImpl;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.jtt.api.handling.live.LiveStateFetcher;
import uk.dangrew.jtt.api.sources.ExternalApi;
import uk.dangrew.jtt.api.sources.JenkinsApiImpl;
import uk.dangrew.jtt.model.users.JenkinsUser;
import uk.dangrew.jtt.storage.database.JenkinsDatabase;
import uk.dangrew.jtt.storage.database.JenkinsDatabaseImpl;
import uk.dangrew.jttws.core.login.JenkinsCredentials;
import uk.dangrew.jttws.core.login.JenkinsLoginPrompt;
import uk.dangrew.jttws.mvc.repository.JenkinsJobDto;

/**
 * Working progress!!! The only part thats still prototype.
 */
@Component
public class JenkinsConnection implements ApplicationContextAware {
   
   private static final Logger logger = LoggerFactory.getLogger( JenkinsConnection.class );
   private List< JenkinsJobDto > jobs;
   private JenkinsDatabase database;
   
   @Override public void setApplicationContext( ApplicationContext applicationContext ) {
      this.jobs = new ArrayList<>();
      
      logger.debug( "calling on: " + toString() );
      
      ObjectProperty< JenkinsCredentials > credentials = new SimpleObjectProperty<>();
      
      PlatformImpl.startup( () -> {} );
      PlatformImpl.setImplicitExit( false );
      PlatformImpl.runAndWait( () -> {
         // Create the custom dialog.
         credentials.set( new JenkinsLoginPrompt().showAndWait().get() );
      } );
      
      database = new JenkinsDatabaseImpl();
      final ObjectProperty< ExternalApi > property = new SimpleObjectProperty<>();
         ExternalApi api = new JenkinsApiImpl();
         HttpClient client = api.attemptLogin( credentials.get().getLocation(), credentials.get().getUsername(), credentials.get().getPassword() );
                  
         if ( client == null ) {
            System.out.println( "FAILED!!!!!!!!!!!!" );
            System.exit( 0 );
         }
         
         property.set( api );
         
         LiveStateFetcher fetcher = new LiveStateFetcher( database, property.get() );
         fetcher.loadLastCompletedBuild();
         database.jenkinsJobs().forEach( j -> {
            JenkinsJobDto dto = new JenkinsJobDto( j.nameProperty().get() );
            dto.setBuildTimestamp( j.buildTimestampProperty().get() );
            dto.setStatus( j.getBuildStatus() );
            
            StringBuilder builder = new StringBuilder();
            for( JenkinsUser user : j.culprits() ) {
               builder.append( user.nameProperty().get() ).append( ", " );
            }
            if ( builder.length() > 2 ) {
               builder.setLength( builder.length() - 2 );
            }
            dto.setCommitters( builder.toString() );
            jobs.add( dto );
         } );
   }
   
   public List< JenkinsJobDto > getJobs() {
      return jobs;
   }

}//End Class
