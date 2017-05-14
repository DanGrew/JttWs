/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.common;

import org.junit.Test;

import uk.dangrew.jtt.model.utility.TestCommon;

public class ComparisonTest {

   @Test public void shouldMapNameWithValueOf() {
      TestCommon.assertEnumNameWithValueOf( Comparison.class );
   }//End Method
   
   @Test public void shouldMapToStringWithValueOf() {
      TestCommon.assertEnumToStringWithValueOf( Comparison.class );
   }//End Method

}//End Class
