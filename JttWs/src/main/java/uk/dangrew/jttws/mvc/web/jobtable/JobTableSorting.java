/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web.jobtable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

/**
 * {@link JobTableSorting} provides the sorting options for the jobs list table.
 */
public enum JobTableSorting {
   
   NameIncreasing( ( a, b ) -> a.name().compareTo( b.name() ) ),
   NameDecreasing( ( a, b ) -> NameIncreasing.comparator.compare( b, a ) ),
   ProgressIncreasing( ( a, b ) -> Integer.compare( a.progress(), b.progress() ) ),
   ProgressDecreasing( ( a, b ) -> ProgressIncreasing.comparator.compare( b, a ) ), 
   StatusIncreasing( ( a, b ) -> SeverityComparator.get().compare( a.status(), b.status() ) ),
   StatusDecreasing( ( a, b ) -> StatusIncreasing.comparator.compare( b, a ) ),
   TimestampIncreasing( ( a, b ) -> a.timestampValue().compareTo( b.timestampValue() ) ),
   TimestampDecreasing( ( a, b ) -> TimestampIncreasing.comparator.compare( b, a ) ),
   CommittersIncreasing( ( a, b ) -> a.committers().compareTo( b.committers() ) ),
   CommittersDecreasing( ( a, b ) -> CommittersIncreasing.comparator.compare( b, a ) );
   
   private final Comparator< JwsJenkinsJob > comparator;
   
   /**
    * Construct a new {@link JobTableSorting}.
    * @param comparator the {@link Comparator} for sorting. 
    */
   private JobTableSorting( Comparator< JwsJenkinsJob > comparator ) {
      this.comparator = comparator;
   }//End Constructor
   
   /**
    * Method to sort the given {@link JwsJenkinsJob}s.
    * @param jobs the {@link List} of {@link JwsJenkinsJob} to sort.
    */
   public void sort( List< JwsJenkinsJob > jobs ) {
      Collections.sort( jobs, comparator );
   }//End Method
   
}//End Enum
