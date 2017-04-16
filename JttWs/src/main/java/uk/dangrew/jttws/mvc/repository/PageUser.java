/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.repository;

import uk.dangrew.jtt.model.users.JenkinsUser;

/**
 * The {@link PageUser} provides a wrapper for the {@link JenkinsUser} for communicating
 * with web pages.
 */
public class PageUser {

   private final JenkinsUser user;
   
   /**
    * Constructs a new {@link PageUser}.
    * @param user the {@link JenkinsUser} associated.
    */
   public PageUser( JenkinsUser user ) {
      this.user = user;
   }//End Constructor

   /**
    * Access to the associated {@link JenkinsUser}.
    * @return the {@link JenkinsUser}.
    */
   public JenkinsUser association(){
      return user;
   }//End Method
   
   /**
    * Access to the name of the {@link JenkinsUser}.
    * @return the name.
    */
   public String name() {
      return user.nameProperty().get();
   }//End Method

}//End Class
