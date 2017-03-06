/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web.jobtable;

import java.util.Comparator;

import uk.dangrew.jtt.model.jobs.BuildResultStatus;

/**
 * The {@link SeverityComparator} provides a {@link Comparator} for the {@link BuildResultStatus}
 * based on severity of issue.
 */
public class SeverityComparator implements Comparator< BuildResultStatus > {

   static final int SECOND_MORE_SEVERE = -1;
   static final int FIRST_MORE_SEVERE = 1;
   
   private static final SeverityComparator INSTANCE = new SeverityComparator();

   /**
    * Singleton pattern.
    * @return the singleton.
    */
   public static SeverityComparator get(){
      return INSTANCE;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public int compare( BuildResultStatus o1, BuildResultStatus o2 ) {
      if ( o1 == o2 ) {
         return 0;
      }
      
      if ( o1 == BuildResultStatus.FAILURE ) {
         return FIRST_MORE_SEVERE;
      } else if ( o2 == BuildResultStatus.FAILURE ) {
         return SECOND_MORE_SEVERE;
      } 
      
      if ( o1 == BuildResultStatus.UNSTABLE ) {
         return FIRST_MORE_SEVERE;
      } else if ( o2 == BuildResultStatus.UNSTABLE ) {
         return SECOND_MORE_SEVERE;
      } 
      
      if ( o1 == BuildResultStatus.ABORTED ) {
         return FIRST_MORE_SEVERE;
      } else if ( o2 == BuildResultStatus.ABORTED ) {
         return SECOND_MORE_SEVERE;
      } 
      
      if ( o1 == BuildResultStatus.NOT_BUILT ) {
         return FIRST_MORE_SEVERE;
      } else if ( o2 == BuildResultStatus.NOT_BUILT ) {
         return SECOND_MORE_SEVERE;
      } 
      
      if ( o1 == BuildResultStatus.UNKNOWN ) {
         return FIRST_MORE_SEVERE;
      } else if ( o2 == BuildResultStatus.UNKNOWN ) {
         return SECOND_MORE_SEVERE;
      } 
      
      if ( o1 == BuildResultStatus.SUCCESS ) {
         return FIRST_MORE_SEVERE;
      } else if ( o2 == BuildResultStatus.SUCCESS ) {
         return SECOND_MORE_SEVERE;
      } 
      
      return 0;
   }//End Method

}//End Class
