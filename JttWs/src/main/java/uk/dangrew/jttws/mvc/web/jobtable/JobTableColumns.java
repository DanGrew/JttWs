/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web.jobtable;

/**
 * {@link JobTableColumns} provides the columns available in the 
 * jobs list table.
 */
public enum JobTableColumns {
   
   Name( 15 ), 
   Progress( 30 ), 
   Status( 15 ),
   Timestamp( 20 ),
   Committers( 20 );
   
   private final int width;
   
   /**
    * Constructs a new {@link JobTableColumns}.
    * @param width the width of the column in percent.
    */
   private JobTableColumns( int width ) {
      this.width = width;
   }//End Constructor
   
   /**
    * Access to the percentage width of the column.
    * @return the width.
    */
   public int width(){
      return width;
   }//End Method

}//End Enum
