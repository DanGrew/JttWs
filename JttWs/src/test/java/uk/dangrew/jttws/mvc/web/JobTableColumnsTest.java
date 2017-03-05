/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import uk.dangrew.jtt.utility.TestCommon;

public class JobTableColumnsTest {

   @Test public void shouldValueOfToString() {
      TestCommon.assertEnumToStringWithValueOf( JobTableColumns.class );
   }//End Method
   
   @Test public void shouldValueOfName() {
      TestCommon.assertEnumNameWithValueOf( JobTableColumns.class );
   }//End Method
   
   @Test public void shouldHaveWidth(){
      for( JobTableColumns column : JobTableColumns.values() ) {
         assertThat( column.width(), is( not( 0 ) ) );
      }
   }//End Method

}//End Class
