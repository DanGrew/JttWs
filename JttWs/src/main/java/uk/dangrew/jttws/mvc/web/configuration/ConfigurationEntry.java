/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web.configuration;

/**
 * {@link ConfigurationEntry} provides a basic item that can be configured on or off.
 */
public class ConfigurationEntry {
   
   private final String name;
   private boolean isActive;
   
   /**
    * Constructs a new {@link ConfigurationEntry} witht he given name of the entry.
    * @param name the name of the entry being turned on or off.
    */
   public ConfigurationEntry( String name ) {
      this.name = name;
      this.isActive = true;
   }//End Constructor
   
   /**
    * Access to the name.
    * @return the name of the entry.
    */
   public String name(){
      return name;
   }//End Method
   
   /**
    * Method to make the entry inactive.
    */
   public void inactive(){
      this.isActive = false;
   }//End Method

   /**
    * Access to the active state.
    * @return true if active.
    */
   public boolean isActive(){
      return isActive;
   }//End Method
   
}//End Class
