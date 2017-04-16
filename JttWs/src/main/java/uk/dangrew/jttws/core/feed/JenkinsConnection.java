/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.feed;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sun.javafx.application.PlatformImpl;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.jtt.api.handling.live.LiveStateFetcher;
import uk.dangrew.jtt.api.sources.ExternalApi;
import uk.dangrew.jtt.api.sources.JenkinsApiImpl;
import uk.dangrew.jtt.model.jobs.BuildState;
import uk.dangrew.jtt.model.jobs.JenkinsJob;
import uk.dangrew.jttws.core.bean.JwsJenkinsDatabase;
import uk.dangrew.jttws.core.login.JenkinsCredentials;
import uk.dangrew.jttws.core.login.JenkinsLoginPrompt;
import uk.dangrew.jttws.mvc.repository.PageJob;
import uk.dangrew.jttws.mvc.repository.PageUser;

/**
 * Working progress!!! The only part thats still prototype.
 */
@Component
@EnableScheduling
public class JenkinsConnection implements ApplicationContextAware {
   
   private static final Logger logger = LoggerFactory.getLogger( JenkinsConnection.class );
   private List< PageJob > jobs;
   private List< PageUser > users;
   private Clock clock;
   
   @Autowired
   private JwsJenkinsDatabase database;
   private LiveStateFetcher fetcher;
   
   @Override public void setApplicationContext( ApplicationContext applicationContext ) {
      this.jobs = new ArrayList<>();
      this.users = new ArrayList<>();
      this.clock = Clock.systemUTC();
      
      logger.debug( "calling on: " + toString() );
      
      ObjectProperty< JenkinsCredentials > credentials = new SimpleObjectProperty<>();
      
      PlatformImpl.startup( () -> {} );
      PlatformImpl.setImplicitExit( false );
      PlatformImpl.runAndWait( () -> {
         // Create the custom dialog.
         credentials.set( new JenkinsLoginPrompt().showAndWait().get() );
      } );
      
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
            PageJob dto = new PageJob( j );
            jobs.add( dto );
         } );
         
         database.jenkinsUsers().forEach( u -> {
            PageUser dto = new PageUser( u );
            users.add( dto );
         } );
         
         this.fetcher = fetcher;
   }
   
   public List< PageJob > getJobs() {
      return new ArrayList<>( jobs );
   }
   
   public List< PageUser > getUsers() {
      return new ArrayList<>( users );
   }
   
   @Scheduled( fixedRate = 5000 )
   public void updateDatabase(){
      if ( fetcher != null ) {
         fetcher.updateBuildState();
      }
   }//End Method
   
   @Scheduled( fixedRate = 1000 )
   public void updateBuildTime(){
      for ( JenkinsJob job : database.jenkinsJobs() ) {
         if ( job.buildStateProperty().get().equals( BuildState.Built ) ) {
            continue;
         }
         
         long currentTime = clock.millis();
         long timestamp = job.buildTimestampProperty().get();
         if ( timestamp > currentTime ) {
            continue;
         }
         
         job.currentBuildTimeProperty().set( currentTime - timestamp );
      }
   }//End Method

}//End Class
