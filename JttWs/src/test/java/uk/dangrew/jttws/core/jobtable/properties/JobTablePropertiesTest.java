/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.jobtable.properties;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import uk.dangrew.jttws.core.jobtable.TableData;
import uk.dangrew.jttws.core.jobtable.buildresult.BuildResultColumn;
import uk.dangrew.jttws.core.jobtable.jobname.JobNameColumn;
import uk.dangrew.jttws.core.jobtable.parameters.JobTableParameters;
import uk.dangrew.jttws.mvc.web.configuration.ConfigurationEntry;

public class JobTablePropertiesTest {

   @Mock private Model model;
   @Captor private ArgumentCaptor< List< ConfigurationEntry > > entryCaptor;
   
   private JobTableParameters parameters;
   private TableData data;
   private JobTableProperties systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      parameters = new JobTableParameters();
      data = new TableData();
      systemUnderTest = new JobTableProperties( model, data, parameters );
   }//End Method

   @Test public void shouldPopulateColumnsInParameters() {
      parameters.includeColumns( JobNameColumn.staticName() );
      systemUnderTest.populate();
      
      verify( model ).addAttribute( eq( JobTableProperties.COLUMNS ), entryCaptor.capture() );
      
      List< ConfigurationEntry > entries = entryCaptor.getValue();
      assertThat( entries, hasSize( 2 ) );
      assertThat( entries.get( 0 ).name(), is( JobNameColumn.staticName() ) );
      assertThat( entries.get( 0 ).isActive(), is( true ) );
      assertThat( entries.get( 1 ).name(), is( BuildResultColumn.staticName() ) );
      assertThat( entries.get( 1 ).isActive(), is( false ) );
   }//End Method

}//End Class
