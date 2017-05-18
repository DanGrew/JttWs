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
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import uk.dangrew.jtt.connection.api.handling.live.LiveStateFetcher;
import uk.dangrew.jtt.connection.api.sources.ExternalApi;
import uk.dangrew.jtt.connection.api.sources.JenkinsApiImpl;
import uk.dangrew.jtt.connection.synchronisation.time.BuildProgressCalculator;
import uk.dangrew.jtt.model.storage.database.JenkinsDatabase;
import uk.dangrew.jtt.model.storage.database.SystemWideJenkinsDatabaseImpl;
import uk.dangrew.jttws.mvc.repository.PageJob;
import uk.dangrew.jttws.mvc.repository.PageTranslator;
import uk.dangrew.jttws.mvc.repository.PageUser;

/**
 * Working progress!!! The only part thats still prototype.
 */
@Component
@EnableScheduling
public class JenkinsConnection implements ApplicationContextAware {
   
   static final long POLL_INTERVAL = 5000;
   static final long LOCAL_UPDATE_INTERVAL = 1000;
   
   private final LiveStateFetcher fetcher;
   private final BuildProgressCalculator calculator;
   
   private final PageTranslator translator;
   private final ThreadedJenkinsApiConnector connector;
   
   /**
    * Constructs a new {@link JenkinsConnection}.
    */
   public JenkinsConnection() {
      this( new JenkinsApiImpl(), new SystemWideJenkinsDatabaseImpl().get() );
   }//End Constructor
   
   /**
    * Constructs a new {@link JenkinsConnection}.
    * @param api the {@link ExternalApi} to connect to.
    * @param database the {@link JenkinsDatabase} for storing data.
    */
   private JenkinsConnection( ExternalApi api, JenkinsDatabase database ) {
      this(
               new PageTranslator( database ),
               new BuildProgressCalculator( database, Clock.systemUTC() ),
               new LiveStateFetcher( api ),
               new ThreadedJenkinsApiConnector( api )
      );
   }//End Class
   
   /**
    * Constructs a new {@link JenkinsConnection}.
    * @param translator the {@link PageTranslator} for translating {@link JenkinsDatabase} items.
    * @param calculator the {@link BuildProgressCalculator} for calculating local progress.
    * @param fetcher the {@link LiveStateFetcher} for fetching the data.
    * @param connector the {@link ThreadedJenkinsApiConnector} for connecting to jenkins.
    */
   JenkinsConnection( 
            PageTranslator translator, 
            BuildProgressCalculator calculator, 
            LiveStateFetcher fetcher, 
            ThreadedJenkinsApiConnector connector 
   ){
      this.translator = translator;
      this.calculator = calculator;
      this.fetcher = fetcher;
      this.connector = connector;
   }//End Class
   
   /**
    * {@inheritDoc}
    */
   @Override public void setApplicationContext( ApplicationContext applicationContext ) {
      connector.connect();
      fetcher.loadLastCompletedBuild();
   }//End Method
   
   /**
    * Getter for the {@link PageJob}s of {@link uk.dangrew.jtt.model.jobs.JenkinsJob}s currently
    * in the {@link JenkinsDatabase}.
    * @return the {@link List} of {@link PageJob}s.
    */
   public List< PageJob > getJobs() {
      return translator.translateJobs();
   }//End Method
   
   /**
    * Getter for the {@link PageUser}s of {@link uk.dangrew.jtt.model.users.JenkinsUser}s currently
    * in the {@link JenkinsDatabase}.
    * @return the {@link List} of {@link PageUser}s.
    */
   public List< PageUser > getUsers() {
      return translator.translateUsers();
   }//End Method
   
   /**
    * Method to regularly poll for new jenkins information.
    */
   @Scheduled( fixedRate = POLL_INTERVAL )
   public void updateDatabase(){
      fetcher.updateBuildState();
   }//End Method
   
   /**
    * Method to regularly update the build time of currently building jobs.
    */
   @Scheduled( fixedRate = LOCAL_UPDATE_INTERVAL )
   public void updateBuildTime(){
      calculator.run();
   }//End Method

}//End Class
