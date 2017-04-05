/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.parameters;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import javafx.util.Pair;
import uk.dangrew.jttws.core.jobtable.jobname.JobNameAlphabetical;
import uk.dangrew.jttws.core.jobtable.jobname.JobNameColumn;

public class JobTableParametersTest {

   private static final String ANYTHING = "anything";
   
   private JobTableParameters systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new JobTableParameters();
   }//End Method

   @Test public void shouldHoldColumnToSortBy() {
      assertThat( systemUnderTest.sorting(), is( new Pair<>( JobNameColumn.staticName(), JobNameAlphabetical.staticName() ) ) );
      systemUnderTest.sortBy( ANYTHING, "something" );
      assertThat( systemUnderTest.sorting(), is( new Pair<>( ANYTHING, "something" ) ) );
   }//End Method
   
   @Test public void shouldHoldFiltersByColumn(){
      assertThat( systemUnderTest.filterValueFor( ANYTHING ), is( nullValue() ) );
      systemUnderTest.filterBy( ANYTHING, "something" );
      assertThat( systemUnderTest.filterValueFor( ANYTHING ), is( "something" ) );
   }//End Method
   
   @Test public void shouldHoldIncludedColumns(){
      assertThat( systemUnderTest.includedColumns(), is( nullValue() ) );
      systemUnderTest.includeColumns( JobNameColumn.staticName() );
      assertThat( systemUnderTest.includedColumns(), is( JobNameColumn.staticName() ) );
   }//End Method

}//End Class
