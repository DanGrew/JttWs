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
 * The {@link JwsJenkinsUser} provides a wrapper for the {@link JenkinsUser} for communicating
 * with web pages.
 */
public class JwsJenkinsUser {

   private final JenkinsUser user;
   
   /**
    * Constructs a new {@link JwsJenkinsUser}.
    * @param user the {@link JenkinsUser} associated.
    */
   public JwsJenkinsUser( JenkinsUser user ) {
      this.user = user;
   }//End Constructor

   /**
    * Access to the name of the {@link JenkinsUser}.
    * @return the name.
    */
   public String name() {
      return user.nameProperty().get();
   }//End Method

}//End Class
