/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.web;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jttws.core.jobtable.buildresult.BuildResultColumn;
import uk.dangrew.jttws.core.jobtable.common.Comparison;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.core.jobtable.structure.ColumnType;

public class PageColumnTest {

   private static final String ANYTHING = "anything";
   
   @Mock private Column column;
   @Mock private Column column2;
   private PageColumn alternate;   
   private PageColumn systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      when( column.name() ).thenReturn( ANYTHING );
      when( column2.name() ).thenReturn( "something" );
      alternate = new PageColumn( column2, true );
      systemUnderTest = new PageColumn( column, true );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullColumn(){
      new PageColumn( null, false );
   }//End Method

   @Test public void shouldProvideName() {
      assertThat( systemUnderTest.name(), is( ANYTHING ) );
   }//End Method
   
   @Test public void shouldProvideType() {
      when( column.type() ).thenReturn( ColumnType.ProgressBar );
      assertThat( systemUnderTest.type(), is( ColumnType.ProgressBar ) );
   }//End Method
   
   @Test public void shouldBeActive(){
      assertThat( systemUnderTest.isActive(), is( true ) );
   }//End Method
   
   @Test public void shouldBeInactive(){
      systemUnderTest = new PageColumn( column, false );
      assertThat( systemUnderTest.isActive(), is( false ) );
   }//End Method
   
   @Test public void shouldBeEqual(){
      assertThat( systemUnderTest, is( systemUnderTest ) );
      assertThat( systemUnderTest, is( new PageColumn( column, true ) ) );
      assertThat( new PageColumn( column, true ), is( systemUnderTest ) );
      assertThat( new PageColumn( column, true ), is( new PageColumn( column, true ) ) );
      
      assertThat( systemUnderTest.hashCode(), is( systemUnderTest.hashCode() ) );
      assertThat( systemUnderTest.hashCode(), is( new PageColumn( column, true ).hashCode() ) );
      assertThat( new PageColumn( column, true ).hashCode(), is( systemUnderTest.hashCode() ) );
      assertThat( new PageColumn( column, true ).hashCode(), is( new PageColumn( column, true ).hashCode() ) );
      
      assertThat( systemUnderTest.compareTo( systemUnderTest ), is( 0 ) );
      assertThat( systemUnderTest.compareTo( new PageColumn( column, true ) ), is( 0 ) );
      assertThat( new PageColumn( column, true ).compareTo( systemUnderTest ), is( 0 ) );
      assertThat( new PageColumn( column, true ).compareTo( new PageColumn( column, true ) ), is( 0 ) );
   }//End Method
   
   @Test public void shouldNotBeEqual(){
      assertThat( systemUnderTest, is( not( new Object() ) ) );
      assertThat( systemUnderTest.equals( null ), is( false ) );
      assertThat( systemUnderTest.hashCode(), is( not( new Object().hashCode() ) ) );
      assertThat( systemUnderTest.compareTo( new PageColumn( new BuildResultColumn(), true ) ), is( not( 0 ) ) );
      
      assertThat( systemUnderTest, is( not( new PageColumn( column, false ) ) ) );
      assertThat( systemUnderTest.hashCode(), is( not( new PageColumn( column, false ).hashCode() ) ) );
      assertThat( systemUnderTest.compareTo( new PageColumn( column, false ) ), is( not( 0 ) ) );
      
      assertThat( systemUnderTest, is( not( alternate ) ) );
      assertThat( systemUnderTest.hashCode(), is( not( alternate.hashCode() ) ) );
      assertThat( systemUnderTest.compareTo( alternate ), is( not( 0 ) ) );
   }//End Method
   
   @Test public void shouldCompare(){
      when( column2.name() ).thenReturn( "aaaa" );
      assertThat( systemUnderTest.compareTo( alternate ), is( greaterThanOrEqualTo( Comparison.GreaterThan.value() ) ) );
      when( column2.name() ).thenReturn( "zzz" );
      assertThat( systemUnderTest.compareTo( alternate ), is( lessThanOrEqualTo( Comparison.LessThan.value() ) ) );
      
      when( column.name() ).thenReturn( "zzz" );
      when( column2.name() ).thenReturn( "AAA" );
      assertThat( systemUnderTest.compareTo( alternate ), is( greaterThanOrEqualTo( Comparison.GreaterThan.value() ) ) );
      
      assertThat( new PageColumn( column, false ).compareTo( systemUnderTest ), is( Comparison.LessThan.value() ) );
   }//End Method

}//End Class
