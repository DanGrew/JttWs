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

import uk.dangrew.jtt.model.storage.database.JenkinsDatabase;

/**
 * The {@link PageTranslator} provides a translation from {@link JenkinsDatabase} items to their
 * equivalent page version.
 */
public class PageTranslator {

   private final JenkinsDatabase database;
   
   /**
    * Constructs a new {@link PageTranslator}.
    * @param database the {@link JenkinsDatabase} to translate from.
    */
   public PageTranslator( JenkinsDatabase database ) {
      this.database = database;
   }//End Constructor

   /**
    * Method to translate the current {@link uk.dangrew.jtt.model.jobs.JenkinsJob}s to {@link PageJob}s.
    * @return the {@link PageJob}s.
    */
   public List< PageJob > translateJobs() {
      List< PageJob > translated = new ArrayList<>();
      database.jenkinsJobs().forEach( j -> translated.add( new PageJob( j ) ) );
      return translated;
   }//End Method

   /**
    * Method to translate the current {@link uk.dangrew.jtt.model.users.JenkinsUser}s to {@link PageUser}s.
    * @return the {@link PageUser}s.
    */
   public List< PageUser > translateUsers() {
      List< PageUser > translated = new ArrayList<>();
      database.jenkinsUsers().forEach( j -> translated.add( new PageUser( j ) ) );
      return translated;
   }//End Method

}//End Class
