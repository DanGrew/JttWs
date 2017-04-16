/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.buildresult;

import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.mvc.repository.PageJob;
import uk.dangrew.jttws.mvc.web.jobtable.SeverityComparator;

/**
 * {@link BuildResultSeverity} is a {@link SortingFunction} for the {@link uk.dangrew.jtt.model.jobs.BuildResultStatus}, ordering 
 * jobs by their status according to the severity.
 */
public class BuildResultSeverity implements SortingFunction {

   static final String NAME = "Result Severity";
   private final SeverityComparator comparator;
   
   /**
    * Constructs a new {@link BuildResultSeverity}.
    */
   public BuildResultSeverity() {
      this( new SeverityComparator() );
   }//End Constructor
   
   /**
    * Constructs a new {@link BuildResultSeverity}.
    * @param comparator the {@link SeverityComparator}.
    */
   BuildResultSeverity( SeverityComparator comparator ) {
      this.comparator = comparator;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public String name() {
      return staticName();
   }//End Method
   
   /**
    * Access to the static name of the {@link SortingFunction}.
    * @return the name.
    */
   public static String staticName(){
      return NAME;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public int compare( PageJob o1, PageJob o2 ) {
      return comparator.compare( o1.association().getBuildStatus(), o2.association().getBuildStatus() );
   }//End Method

}//End Class
