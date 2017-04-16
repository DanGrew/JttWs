/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import uk.dangrew.jttws.core.feed.JenkinsConnection;

/**
 * The {@link JenkinsRepository} provides a {@link Repository} for accessing the {@link uk.dangrew.jtt.storage.database.JenkinsDatabase}
 * that is being populated by a connection to jenkins.
 */
@Repository 
public class JenkinsRepository implements ApplicationContextAware {

   static final String CONNECTION_ERROR = "JenkinsRepository not connected to JenkinsConnection.";
   
   private final Logger logger;
   private JenkinsConnection connection;

   /**
    * Constructs a new {@link JenkinsRepository}.
    */
   public JenkinsRepository() {
      this( LoggerFactory.getLogger( JenkinsRepository.class ) );
   }// End Constructor
   
   /**
    * Constructs a new {@link JenkinsRepository}.
    * @param logger the {@link Logger} to report to.
    */
   JenkinsRepository( Logger logger ) {
      this.logger = logger;
   }// End Constructor

   /**
    * Getter for the entire {@link List} of {@link PageJob}s.
    * @return the {@link PageJob}s.
    */
   public List< PageJob > getJenkinsJobs() {
      if ( connection == null ) {
         logger.error( CONNECTION_ERROR );
         return new ArrayList<>();
      }
      return connection.getJobs();
   }// End Method
   
   /**
    * Getter for the entire {@link List} of {@link JwsJenkinsUser}s.
    * @return the {@link JwsJenkinsUser}s.
    */
   public List< JwsJenkinsUser > getJenkinsUsers() {
      if ( connection == null ) {
         logger.error( CONNECTION_ERROR );
         return new ArrayList<>();
      }
      return connection.getUsers();
   }// End Method

   /**
    * {@inheritDoc}
    */
   @Override public void setApplicationContext( ApplicationContext applicationContext ) {
      logger.debug( "Setting application context on: " + toString() );
      connection = applicationContext.getBean( JenkinsConnection.class );
   }// End Method

}// End Class
