/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.login;

/**
 * The {@link JenkinsCredentials} provides a storage object for entered
 * login credentials.
 */
public class JenkinsCredentials {

   private String location;
   private String username;
   private String password;
   
   /**
    * Setter for the location of Jenkins.
    * @param location the location.
    */
   public void setLocation( String location ) {
      this.location = location;
   }//End Method

   /**
    * Getter for the location of Jenkins to log in to.
    * @return the location.
    */
   public String getLocation() {
      return location;
   }//End Method

   /**
    * Setter for the username to login in to Jenkins with.
    * @param username the username.
    */
   public void setUsername( String username ) {
      this.username = username;
   }//End Method

   /**
    * Getter for the username to log in to Jenkins with.
    * @return the username.
    */
   public String getUsername() {
      return username;
   }//End Method

   /**
    * Setter for the password to login in to Jenkins with.
    * @param password the password.
    */
   public void setPassword( String password ) {
      this.password = password;
   }//End Method

   /**
    * Getter for the password to log in to Jenkins with.
    * @return the password.
    */
   public String getPassword() {
      return password;
   }//End Method

}//End Constructor
