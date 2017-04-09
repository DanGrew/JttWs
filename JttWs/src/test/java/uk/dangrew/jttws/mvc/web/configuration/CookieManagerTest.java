/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.mvc.web.configuration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jttws.mvc.web.configuration.CookieManager;

public class CookieManagerTest {

   private static final String PARAMETER = "parameter";
   private static final String PARAMETER_VALUE = "parameterValue";
   
   @Mock private HttpServletRequest request;
   @Mock private HttpServletResponse response;
   
   @Captor private ArgumentCaptor< Cookie > cookieCaptor;
   private CookieManager systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new CookieManager();
   }//End Method

   @Test public void shouldReturnParameterValueWhenProvided() {
      when( request.getParameter( PARAMETER ) ).thenReturn( PARAMETER_VALUE );
      assertThat( systemUnderTest.retrieveCookie( PARAMETER, request, response ), is( PARAMETER_VALUE ) );
   }//End Method
   
   @Test public void shouldPrioritiseParameterValue() {
      when( request.getParameter( PARAMETER ) ).thenReturn( PARAMETER_VALUE );
      Cookie[] cookies = new Cookie[]{ new Cookie( PARAMETER, "anything" ) };
      when( request.getCookies() ).thenReturn( cookies );
      assertThat( systemUnderTest.retrieveCookie( PARAMETER, request, response ), is( PARAMETER_VALUE ) );
   }//End Method
   
   @Test public void shouldRetrieveValueFromCookieIfNoParameter() {
      Cookie[] cookies = new Cookie[]{ new Cookie( PARAMETER, PARAMETER_VALUE ) };
      when( request.getCookies() ).thenReturn( cookies );
      assertThat( systemUnderTest.retrieveCookie( PARAMETER, request, response ), is( PARAMETER_VALUE ) );
   }//End Method
   
   @Test public void shouldReturnNoCookieValueIfNonPresent() {
      assertThat( systemUnderTest.retrieveCookie( PARAMETER, request, response ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldAddCookieIfParameterValueIsProvidedInRequest(){
      when( request.getParameter( PARAMETER ) ).thenReturn( PARAMETER_VALUE );
      systemUnderTest.retrieveCookie( PARAMETER, request, response );
      
      verify( response ).addCookie( cookieCaptor.capture() );
      Cookie cookie = cookieCaptor.getValue();
      assertThat( cookie.getName(), is( PARAMETER ) );
      assertThat( cookie.getValue(), is( PARAMETER_VALUE ) );
   }//End Method
   
   @Test public void shouldSaveCookie(){
      systemUnderTest.saveCookie( PARAMETER, PARAMETER_VALUE, response );
      verify( response ).addCookie( cookieCaptor.capture() );
      
      Cookie cookie = cookieCaptor.getValue();
      assertThat( cookie.getName(), is( PARAMETER ) );
      assertThat( cookie.getValue(), is( PARAMETER_VALUE ) );
   }//End Method
}//End Class
