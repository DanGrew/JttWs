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
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Component;

import uk.dangrew.jttws.core.jobtable.TableData;
import uk.dangrew.jttws.core.jobtable.jobname.JobNameAlphabetical;
import uk.dangrew.jttws.core.jobtable.jobname.JobNameColumn;
import uk.dangrew.jttws.core.jobtable.structure.Column;
import uk.dangrew.jttws.mvc.web.configuration.CookieManager;

public class ParametersPopulatorTest {

   private static final String ANYTHING = "anything";
   private static final String ANOTHER_ANYTHING = "another anything";
   
   @Mock private HttpServletRequest request;
   @Mock private HttpServletResponse response;
   @Mock private CookieManager cookies;
   private TableData tableData;
   private ParametersPopulator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      tableData = new TableData();
      systemUnderTest = new ParametersPopulator( cookies );
   }//End Method
   
   @Test public void shouldBeSpringComponent(){
      assertThat( ParametersPopulator.class.getAnnotation( Component.class ), is( notNullValue() ) );
   }//End Method

   @Test public void shouldApplyFilterWhenProvided() {
      when( cookies.retrieveCookie( ParametersPopulator.ID, request, response ) ).thenReturn( JobNameColumn.staticId() );
      when( cookies.retrieveCookie( ParametersPopulator.VALUE, request, response ) ).thenReturn( ANYTHING );
      JobTableParameters parameters = systemUnderTest.construct( tableData, request, response );
      
      assertThat( parameters.filterValueFor( JobNameColumn.staticName() ), is( ANYTHING ) );
   }//End Method
   
   @Test public void shouldApplyFilterForEachColumnWhenProvided() {
      for ( Column column : tableData.columns() ) {
         when( cookies.retrieveCookie( column.id(), request, response ) ).thenReturn( column.name() );
      }
      JobTableParameters parameters = systemUnderTest.construct( tableData, request, response );
      
      for ( Column column : tableData.columns() ) {
         assertThat( parameters.filterValueFor( column.name() ), is( column.name() ) );
      }
   }//End Method
   
   @Test public void shouldNotApplyFilterWhenNotProvided() {
      JobTableParameters parameters = systemUnderTest.construct( tableData, request, response );
      
      for ( Column column : tableData.columns() ) {
         assertThat( parameters.filterValueFor( column.name() ), is( nullValue() ) );
      }
   }//End Method
   
   @Test public void shouldIgnoreInvalidId(){
      when( cookies.retrieveCookie( ParametersPopulator.VALUE, request, response ) ).thenReturn( ANYTHING );
      JobTableParameters parameters = systemUnderTest.construct( tableData, request, response );
      
      assertThat( parameters.filterValueFor( JobNameColumn.staticName() ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldIgnoreMissingFilterValue(){
      systemUnderTest.construct( tableData, request, response );
      verify( cookies, never() ).saveCookie( Mockito.anyString(), Mockito.anyString(), Mockito.any() );
   }//End Method
   
   @Test public void shouldOverrideFilterWhenCookieExists() {
      when( cookies.retrieveCookie( ParametersPopulator.ID, request, response ) ).thenReturn( JobNameColumn.staticId() );
      when( cookies.retrieveCookie( ParametersPopulator.VALUE, request, response ) ).thenReturn( ANOTHER_ANYTHING );
      when( cookies.retrieveCookie( JobNameColumn.staticId(), request, response ) ).thenReturn( ANYTHING );
      JobTableParameters parameters = systemUnderTest.construct( tableData, request, response );
      
      assertThat( parameters.filterValueFor( JobNameColumn.staticName() ), is( ANOTHER_ANYTHING ) );
   }//End Method
   
   @Test public void shouldSaveFilterWhenProvided() {
      when( cookies.retrieveCookie( ParametersPopulator.ID, request, response ) ).thenReturn( JobNameColumn.staticId() );
      when( cookies.retrieveCookie( ParametersPopulator.VALUE, request, response ) ).thenReturn( ANOTHER_ANYTHING );
      systemUnderTest.construct( tableData, request, response );
      verify( cookies ).saveCookie( JobNameColumn.staticId(), ANOTHER_ANYTHING, response );
   }//End Method
   
   @Test public void shouldPopulateSortingConfiguration(){
      when( cookies.retrieveCookie( ParametersPopulator.SORT, request, response ) ).thenReturn( ANOTHER_ANYTHING );
      JobTableParameters parameters = systemUnderTest.construct( tableData, request, response );
      assertThat( parameters.sorting(), is( ANOTHER_ANYTHING ) );
   }//End Method
   
   @Test public void shouldIgnoreMissingSortingConfiguration(){
      JobTableParameters parameters = systemUnderTest.construct( tableData, request, response );
      assertThat( parameters.sorting(), is( JobNameAlphabetical.staticName() ) );
   }//End Method

}//End Class
