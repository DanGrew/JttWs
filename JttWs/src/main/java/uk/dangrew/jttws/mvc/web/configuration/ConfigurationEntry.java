/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web.configuration;

import uk.dangrew.jttws.core.jobtable.common.Comparison;

/**
 * {@link ConfigurationEntry} provides a basic item that can be configured on or off.
 */
public class ConfigurationEntry implements Comparable< ConfigurationEntry >{
   
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

   /**
    * {@inheritDoc}
    */
   @Override public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ( isActive ? 1231 : 1237 );
      result = prime * result + ( ( name == null ) ? 0 : name.toLowerCase().hashCode() );
      return result;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public boolean equals( Object obj ) {
      if ( this == obj ) {
         return true;
      }
      if ( obj == null ) {
         return false;
      }
      if ( getClass() != obj.getClass() ) {
         return false;
      }
      ConfigurationEntry other = ( ConfigurationEntry ) obj;
      if ( isActive != other.isActive ) {
         return false;
      }
      if ( name == null ) {
         if ( other.name != null ) {
            return false;
         }
      } else if ( !name.equalsIgnoreCase( other.name ) ) {
         return false;
      }
      return true;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public int compareTo( ConfigurationEntry o ) {
      if ( name == o.name && isActive == o.isActive ) {
         return Comparison.Equal.value();
      }
      
      if ( name != o.name ) {
         return name.compareToIgnoreCase( o.name );
      } else if ( isActive ) {
         return Comparison.GreaterThan.value();
      } else {
         return Comparison.LessThan.value();
      }
   }//End Method
   
}//End Class
