/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.web;

import uk.dangrew.jttws.core.jobtable.common.Comparison;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.core.jobtable.structure.ColumnType;
import uk.dangrew.jttws.mvc.repository.PageJob;

/**
 * The {@link PageColumn} provides the web ui version of the {@link Column}.
 */
public class PageColumn implements Comparable< PageColumn >{
   
   private final Column column;
   private final boolean isActive;

   /**
    * Constructs a new {@link PageColumn}.
    * @param column the associated {@link Column}.
    * @param isActive whether the {@link Column} is currently active.
    */
   public PageColumn( Column column, boolean isActive ) {
      if ( column == null ) {
         throw new IllegalArgumentException( "Column must not be null." );
      }
      
      this.column = column;
      this.isActive = isActive;
   }//End Constructor

   /**
    * Access to the {@link Column#name()}.
    * @return the name.
    */
   public String name() {
      return column.name();
   }//End Method

   /**
    * Access to the {@link Column#type()}.
    * @return the {@link ColumnType}.
    */
   public ColumnType type() {
      return column.type();
   }//End Method
   
   /**
    * Access to the {@link Column#id()}.
    * @return the id.
    */
   public String id(){
      return column.id();
   }//End Method

   /**
    * Access to whether the {@link Column} is active in the ui.
    * @return true if active.
    */
   public boolean isActive() {
      return isActive;
   }//End Method
   
   /**
    * Method to provide the value to display in the table for the given job.
    * @param job the {@link PageJob}.
    * @return the {@link String} value to display in the table.
    */
   public String valueFor( PageJob job ) {
      return column.valueForJob( job );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ( ( column == null ) ? 0 : column.hashCode() );
      result = prime * result + ( isActive ? 1231 : 1237 );
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
      PageColumn other = ( PageColumn ) obj;
      if ( !column.equals( other.column ) ) {
         return false;
      }
      if ( isActive != other.isActive ) {
         return false;
      }
      return true;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public int compareTo( PageColumn o ) {
      if ( column == o.column && isActive == o.isActive ) {
         return Comparison.Equal.value();
      }
      
      if ( column != o.column ) {
         return column.name().compareToIgnoreCase( o.name() );
      } else if ( isActive ) {
         return Comparison.GreaterThan.value();
      } else {
         return Comparison.LessThan.value();
      }
   }//End Method

}//End Class
