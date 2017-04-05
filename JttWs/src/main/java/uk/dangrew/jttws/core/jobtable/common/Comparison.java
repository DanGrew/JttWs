/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.common;

/**
 * {@link Comparison} provides the return values associated with a {@link java.util.Comparator}.
 */
public enum Comparison {

   Equal( 0 ),
   LessThan( -1 ),
   GreaterThan( 1 );
   
   private final int value;
   
   /**
    * Constructs a new {@link Comparison}.
    * @param value the value asociated.
    */
   private Comparison( int value ) {
      this.value = value;
   }//End Constructor
   
   /**
    * Access to the return value.
    * @return the value.
    */
   public int value(){
      return value;
   }//End Method
}//End Enum
