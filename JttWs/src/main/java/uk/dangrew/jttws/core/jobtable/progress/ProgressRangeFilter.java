/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.progress;

/**
 * {@link ProgressRangeFilter} provides a specific range that a {@link uk.dangrew.jttws.mvc.repository.PageJob}s
 * progress can fit in.
 */
public class ProgressRangeFilter {
   
   private final int start;
   private final int end;
   private final boolean includeStart;
   private final boolean includeEnd;
   
   /**
    * Constructs a new {@link ProgressRangeFilter}.
    * @param start the start of the range.
    * @param includeStart whether to match on the start.
    * @param end the end of the range.
    * @param includeEnd whether to match on the end.
    */
   public ProgressRangeFilter( 
            int start, 
            boolean includeStart, 
            int end, 
            boolean includeEnd 
   ) {
      this.start = start;
      this.end = end;
      this.includeStart = includeStart;
      this.includeEnd= includeEnd;
   }//End Constructor
   
   /**
    * Method to provide a friendly name for the filtered range.
    * @return a displayable name.
    */
   public String name() {
      StringBuilder string = new StringBuilder();
      string.append( start ).append( " <" );
      if ( includeStart ) {
         string.append( "=" );
      }
      string.append( " x <" );
      if ( includeEnd ) {
         string.append( "=" );
      }
      string.append( " " ).append( end );
      return string.toString();
   }//End Method

   /**
    * Method to determine whether the given progress matches the range.
    * @param progress the progress in question.
    * @return true if within the range given the inclusions.
    */
   public boolean matches( int progress ) {
      if ( progress < start ) {
         return false;
      } else if ( progress > end ) {
         return false;
      } else if ( progress == start ) {
         return includeStart;
      } else if ( progress == end ) {
         return includeEnd;
      } else {
         return true;
      }
   }//End Method

}//End Class
