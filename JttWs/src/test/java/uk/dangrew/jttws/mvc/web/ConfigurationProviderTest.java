/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import javafx.util.Pair;

public class ConfigurationProviderTest {

   private static final String ATTRIBUTE = "anything";
   
   private List< String > collection;
   @Mock private Predicate< String > includer;
   @Mock private Function< String, String > representer;
   
   @Captor private ArgumentCaptor< List< ConfigurationEntry > > entryCaptor;
   @Mock private Model model;
   private ConfigurationProvider systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      collection = new ArrayList<>();
      collection.add( "one" );
      collection.add( "two" );
      collection.add( "three" );
      collection.add( "four" );
      
      systemUnderTest = new ConfigurationProvider();
   }//End Method

   @Test public void shouldIncludeAndExcludeItemsCorrectly() {
      when( includer.test( collection.get( 0 ) ) ).thenReturn( true );
      when( includer.test( collection.get( 1 ) ) ).thenReturn( true );
      when( includer.test( collection.get( 2 ) ) ).thenReturn( false );
      when( includer.test( collection.get( 3 ) ) ).thenReturn( true );
      
      systemUnderTest.provideConfigurationEntries( ATTRIBUTE, model, collection, includer, representer );
      
      assertThatEntriesHaveBeenProvided( 
               new Pair<>( null, true ),
               new Pair<>( null, true ),
               new Pair<>( null, false ),
               new Pair<>( null, true )
      );
   }//End Method
   
   /**
    * Method to assert that the given {@link Pair}s are found in the {@link ConfigurationEntry}s provided
    * to the {@link Model}.
    * @param expectedEntries the {@link Pair} of representation to inclusion for precisely those expected. 
    */
   @SafeVarargs private final void assertThatEntriesHaveBeenProvided(
            Pair< String, Boolean >... expectedEntries 
   ) {
      verify( model ).addAttribute( Mockito.eq( ATTRIBUTE ), entryCaptor.capture() );
      List< ConfigurationEntry > entries = entryCaptor.getValue();
      assertThat( entries, hasSize( expectedEntries.length ) );
      
      for ( int i = 0; i < expectedEntries.length; i++ ) {
         Pair< String, Boolean > expected = expectedEntries[ i ];
         assertThat( entries.get( i ).name(), is( expected.getKey() ) );
         assertThat( entries.get( i ).isActive(), is( expected.getValue() ) );
      }
   }//End Method
   
   @Test public void shouldRepresentItemsUsingFunctions() {
      when( includer.test( Mockito.anyString() ) ).thenReturn( true );
      
      final String representationSuffix = "-pretty";
      for ( int i = 0; i < collection.size(); i++ ) {
         when( representer.apply( collection.get( i ) ) ).thenReturn( collection.get( i ) + representationSuffix );
      }
      
      systemUnderTest.provideConfigurationEntries( ATTRIBUTE, model, collection, includer, representer );
      
      assertThatEntriesHaveBeenProvided( 
               new Pair<>( collection.get( 0 ) + representationSuffix, true ),
               new Pair<>( collection.get( 1 ) + representationSuffix, true ),
               new Pair<>( collection.get( 2 ) + representationSuffix, true ),
               new Pair<>( collection.get( 3 ) + representationSuffix, true )
      );
   }//End Method
   
}//End Class
