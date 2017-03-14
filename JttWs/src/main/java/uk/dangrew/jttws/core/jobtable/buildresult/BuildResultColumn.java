/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.buildresult;

import java.util.List;

import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

/**
 * The {@link BuildResultColumn} represents the column of data holding the {@link uk.dangrew.jtt.model.jobs.BuildResultStatus}
 * associated with the {@link JwsJenkinsJob}.
 */
public class BuildResultColumn implements Column {

   /**
    * {@inheritDoc}
    */
   @Override public String name() {
      return null;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void sort( List< JwsJenkinsJob > jobs, JobTableParameters parameters ) {
   }//End Method

}//End Class