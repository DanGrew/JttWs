/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.ui.Model;

/**
 * The {@link ConfigurationProvider} manufactures {@link ConfigurationEntry}s to represent something that
 * can be displayed and turned on/off in the web ui.
 */
public class ConfigurationProvider {

   /**
    * Method to provide {@link ConfigurationEntry}s for the given items, representing them using their
    * representation {@link Function}, and including them using the include {@link Predicate}.
    * @param <ItemT> the type of item {@link ConfigurationEntry}s are for.
    * @param attribute the attribute being added to the {@link Model}.
    * @param model the {@link Model} to add the {@link ConfigurationEntry}s to.
    * @param items the {@link Collection} of items to make {@link ConfigurationEntry}s for.
    * @param includer the {@link Predicate} for determining which to include.
    * @param representer the {@link Function} determining how to represent the item.
    */
   public < ItemT > void provideConfigurationEntries( 
            String attribute, 
            Model model,
            Collection< ItemT > items, 
            Predicate< ItemT > includer,
            Function< ItemT, String > representer
   ){
      List< ConfigurationEntry > entries = new ArrayList<>();
      for ( ItemT item : items ) {
         ConfigurationEntry entry = new ConfigurationEntry( representer.apply( item ) );
         entries.add( entry );
         
         if ( !includer.test( item ) ) {
            entry.inactive();
         }
      }
      model.addAttribute( attribute, entries );
   }//End Method
   
}//End Class
