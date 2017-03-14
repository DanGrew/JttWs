/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.common;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jttws.core.jobtable.structure.SortingFunction;
import uk.dangrew.jttws.mvc.repository.JwsJenkinsJob;

public class ReverseSortingTest {

   private static final int UNQIUE_ANSWER = 384756;
   
   @Mock private JwsJenkinsJob job1;
   @Mock private JwsJenkinsJob job2;
   @Mock private SortingFunction sort;
   private SortingFunction systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ReverseSorting( sort );
   }//End Method
   
   @Test public void shouldProvideReverseName(){
      when( sort.name() ).thenReturn( "anything" );
      assertThat( systemUnderTest.name(), is( "anything (Reverse)" ) );
   }//End Method

   @Test public void shouldReverseArguments() {
      when( sort.compare( job2, job1 ) ).thenReturn( UNQIUE_ANSWER );
      assertThat( systemUnderTest.compare( job1, job2 ), is( UNQIUE_ANSWER ) );
   }//End Method
   
}//End Class
