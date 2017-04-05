/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web.configuration;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jttws.core.jobtable.common.Comparison;

public class ConfigurationEntryTest {

   private ConfigurationEntry systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new ConfigurationEntry( "anything" );
   }//End Method

   @Test public void shouldProvideName() {
      assertThat( systemUnderTest.name(), is( "anything" ) );
   }//End Method
   
   @Test public void shouldBeActiveInitially() {
      assertThat( systemUnderTest.isActive(), is( true ) );
   }//End Method
   
   @Test public void shouldBeDeactivated() {
      systemUnderTest.inactive();
      assertThat( systemUnderTest.isActive(), is( false ) );
   }//End Method
   
   @Test public void shouldBeEqual(){
      assertThat( systemUnderTest, is( systemUnderTest ) );
      assertThat( systemUnderTest, is( new ConfigurationEntry( "anything" ) ) );
      assertThat( new ConfigurationEntry( "anything" ), is( systemUnderTest ) );
      assertThat( new ConfigurationEntry( "anything" ), is( new ConfigurationEntry( "anything" ) ) );
      assertThat( new ConfigurationEntry( "AAA" ), is( new ConfigurationEntry( "aaa" ) ) );
      
      assertThat( systemUnderTest.hashCode(), is( systemUnderTest.hashCode() ) );
      assertThat( systemUnderTest.hashCode(), is( new ConfigurationEntry( "anything" ).hashCode() ) );
      assertThat( new ConfigurationEntry( "anything" ).hashCode(), is( systemUnderTest.hashCode() ) );
      assertThat( new ConfigurationEntry( "anything" ).hashCode(), is( new ConfigurationEntry( "anything" ).hashCode() ) );
      assertThat( new ConfigurationEntry( "AAA" ).hashCode(), is( new ConfigurationEntry( "aaa" ).hashCode() ) );
      
      assertThat( systemUnderTest.compareTo( systemUnderTest ), is( 0 ) );
      assertThat( systemUnderTest.compareTo( new ConfigurationEntry( "anything" ) ), is( 0 ) );
      assertThat( new ConfigurationEntry( "anything" ).compareTo( systemUnderTest ), is( 0 ) );
      assertThat( new ConfigurationEntry( "anything" ).compareTo( new ConfigurationEntry( "anything" ) ), is( 0 ) );
      assertThat( new ConfigurationEntry( "AAA" ).compareTo( new ConfigurationEntry( "aaa" ) ), is( greaterThanOrEqualTo( Comparison.Equal.value() ) ) );
   }//End Method
   
   @Test public void shouldNotBeEqual(){
      assertThat( systemUnderTest, is( not( new Object() ) ) );
      assertThat( systemUnderTest.hashCode(), is( not( new Object().hashCode() ) ) );
      assertThat( systemUnderTest.compareTo( new ConfigurationEntry( "nothing" ) ), is( not( 0 ) ) );
      
      systemUnderTest.inactive();
      assertThat( systemUnderTest, is( not( new ConfigurationEntry( "anything" ) ) ) );
      assertThat( systemUnderTest.hashCode(), is( not( new ConfigurationEntry( "anything" ).hashCode() ) ) );
      assertThat( systemUnderTest.compareTo( new ConfigurationEntry( "anything" ) ), is( not( 0 ) ) );
   }//End Method
   
   @Test public void shouldCompare(){
      assertThat( systemUnderTest.compareTo( new ConfigurationEntry( "aaaa" ) ), is( greaterThanOrEqualTo( Comparison.GreaterThan.value() ) ) );
      assertThat( systemUnderTest.compareTo( new ConfigurationEntry( "zzz" ) ), is( lessThanOrEqualTo( Comparison.LessThan.value() ) ) );
      assertThat( new ConfigurationEntry( "zzz" ).compareTo( new ConfigurationEntry( "AAA" ) ), is( greaterThanOrEqualTo( Comparison.GreaterThan.value() ) ) );
      
      systemUnderTest.inactive();
      assertThat( systemUnderTest.compareTo( new ConfigurationEntry( "anything" ) ), is( Comparison.LessThan.value() ) );
   }//End Method

}//End Class
