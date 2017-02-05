/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.repository;

import uk.dangrew.jtt.model.jobs.BuildResultStatus;

/**
 * The {@link JenkinsJobDto} represents a data transfer version of the {@link uk.dangrew.jtt.model.jobs.JenkinsJob}.
 */
public class JenkinsJobDto {
   
   private final String name;
   private int progress;
   private Long timestamp;
   private BuildResultStatus status;
   private String committers;

   /**
    * Constructs a new {@link JenkinsJobDto}.
    * @param name the unique name of the {@link uk.dangrew.jtt.model.jobs.JenkinsJob}.
    */
   public JenkinsJobDto( String name ) {
      this.name = name;
   }//End Constructor
   
   /**
    * Access to the name of the {@link uk.dangrew.jtt.model.jobs.JenkinsJob}.
    * @return the name.
    */
   public String name() {
      return name;
   }//End Method

   /**
    * Setter for the build timestamp.
    * @param timestamp the timestamp.
    */
   public void setBuildTimestamp( Long timestamp ) {
      this.timestamp = timestamp;
   }//End Method
   
   /**
    * Access to the build timestamp of the {@link uk.dangrew.jtt.model.jobs.JenkinsJob}.
    * @return the timestamp.
    */
   public Long timestamp() {
      return timestamp;
   }//End Method
   
   /**
    * Setter for the progress of the build.
    * @param progress, 0 - 100.
    */
   public void setProgress( int progress ) {
      this.progress = progress;
   }//End Method
   
   /**
    * Access to the prgress of the {@link uk.dangrew.jtt.model.jobs.JenkinsJob}.
    * @return the progress of the build, 0 - 100.
    */
   public int progress() {
      return progress;
   }//End Method

   /**
    * Setter for the {@link BuildResultStatus}.
    * @param status the {@link BuildResultStatus}.
    */
   public void setStatus( BuildResultStatus status ) {
      this.status = status;
   }//End Method
   
   /**
    * Access to the {@link BuildResultStatus} of the {@link uk.dangrew.jtt.model.jobs.JenkinsJob}.
    * @return the {@link BuildResultStatus}.
    */
   public BuildResultStatus status() {
      return status;
   }//End Method

   /**
    * Setter for the list of committers as a {@link String}.
    * @param committers the committers list.
    */
   public void setCommitters( String committers ) {
      this.committers = committers;
   }//End Method
   
   /**
    * Access to the committers of the {@link uk.dangrew.jtt.model.jobs.JenkinsJob}.
    * @return the {@link String} list of committers.
    */
   public String committers() {
      return committers;
   }//End Method

}//End Class
