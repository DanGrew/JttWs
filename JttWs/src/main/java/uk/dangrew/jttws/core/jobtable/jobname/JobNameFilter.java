/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.jobname;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import uk.dangrew.jttws.core.jobtable.common.WebUiParameterParsing;
import uk.dangrew.jttws.core.jobtable.structure.Filter;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

/**
 * The {@link JobNameFilter} is a {@link Filter} for filtering {@link JwsJenkinsJob}s by name.
 */
public class JobNameFilter implements Filter {
   
   private final WebUiParameterParsing webParsing;
   
   /**
    * Constructs a new {@link JobNameFilter}.
    */
   public JobNameFilter() {
      this.webParsing = new WebUiParameterParsing();
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public List< JwsJenkinsJob > identifyExclusions( List< JwsJenkinsJob > jobs, String value ) {
      List< JwsJenkinsJob > excludedJobs = new ArrayList<>();
      if ( value == null ) {
         return excludedJobs;
      }
      
      Set< String > includedJobs = new LinkedHashSet<>( webParsing.parseStringList( value ) );
      for ( JwsJenkinsJob job : jobs ) {
         if ( !includedJobs.contains( job.name() ) ) {
            excludedJobs.add( job );
         }
      }
      
      return excludedJobs;
   }//End Method

}//End Class
