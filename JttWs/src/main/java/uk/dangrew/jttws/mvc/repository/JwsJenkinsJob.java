/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.repository;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.StringJoiner;

import uk.dangrew.jtt.model.jobs.BuildResultStatus;
import uk.dangrew.jtt.model.jobs.JenkinsJob;

/**
 * The {@link JwsJenkinsJob} provides a wrapper for the {@link JenkinsJob} for
 * easily accessing properties in appropriate formats.
 */
public class JwsJenkinsJob {

   static final String NO_SUSPECTS = "No Suspects";
   static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern( "HH:mm:ss" ).appendLiteral( "-" ).appendPattern( "dd/MM/yy" ).toFormatter();
   private final JenkinsJob job;
   
   /**
    * Construts a new {@link JwsJenkinsJob}.
    * @param job the {@link JenkinsJob} being wrapped.
    */
   public JwsJenkinsJob( JenkinsJob job ) {
      if ( job == null ) {
         throw new IllegalArgumentException( "Must provide non null Job." );
      }
      this.job = job;
   }//End Constructor
   
   /**
    * Access to the name of the {@link uk.dangrew.jtt.model.jobs.JenkinsJob}.
    * @return the name.
    */
   public String name() {
      return job.nameProperty().get();
   }//End Method

   /**
    * Access to the build timestamp of the {@link uk.dangrew.jtt.model.jobs.JenkinsJob}.
    * @return the timestamp.
    */
   public Long timestampValue(){
      return job.buildTimestampProperty().get();
   }//End Method
   
   /**
    * Access to a formatted build timestamp of the {@link uk.dangrew.jtt.model.jobs.JenkinsJob}.
    * @return the timestamp.
    */
   public String timestamp() {
      return new Timestamp( job.buildTimestampProperty().get() ).toInstant()
               .atZone( ZoneId.of( "Europe/London" ) )
               .toLocalDateTime().format( DATE_TIME_FORMATTER );
   }//End Method
   
   /**
    * Access to the prgress of the {@link uk.dangrew.jtt.model.jobs.JenkinsJob}.
    * @return the progress of the build, 0 - 100.
    */
   public int progress() {
      return ( int )( ( double )job.currentBuildTimeProperty().get() * 100 / ( double )job.expectedBuildTimeProperty().get() );
   }//End Method

   /**
    * Access to the {@link BuildResultStatus} of the {@link uk.dangrew.jtt.model.jobs.JenkinsJob}.
    * @return the {@link BuildResultStatus}.
    */
   public BuildResultStatus status() {
      return job.getBuildStatus();
   }//End Method

   /**
    * Access to the committers of the {@link uk.dangrew.jtt.model.jobs.JenkinsJob}.
    * @return the {@link String} list of committers.
    */
   public String committers() {
      if ( job.culprits().isEmpty() ) {
         return NO_SUSPECTS;
      } else {
         StringJoiner joiner = new StringJoiner( ", " );
         job.culprits().forEach( u -> joiner.add( u.nameProperty().get() ) );
         return joiner.toString();
      }
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public String toString() {
      return name();
   }//End Method
   
}//End Class
