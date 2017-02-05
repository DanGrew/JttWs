/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.login;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import uk.dangrew.jtt.graphics.JavaFxInitializer;

public class JenkinsLoginPromptTest {

   private JenkinsLoginPrompt systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      JavaFxInitializer.startPlatform();
      PlatformImpl.runAndWait( () -> systemUnderTest = new JenkinsLoginPrompt() );
   }//End Method

   @Ignore
   @Test public void manual() {
      JavaFxInitializer.startPlatform();
      
      PlatformImpl.runAndWait( () -> {
         new JenkinsLoginPrompt().showAndWait();
      } );
   }//End Method
   
   @Test public void shouldHaveTitleAndHeader() {
      assertThat( systemUnderTest.getTitle(), is( JenkinsLoginPrompt.TITLE ) );
      assertThat( systemUnderTest.getHeaderText(), is( JenkinsLoginPrompt.HEADER_TEXT ) );
   }//End Method
   
   @Test public void shouldProvideLoginCancelButtonTypes() {
      assertThat( systemUnderTest.getDialogPane().getButtonTypes(), hasSize( 2 ) );
      assertThat( systemUnderTest.getDialogPane().getButtonTypes().get( 0 ).getText(), is( JenkinsLoginPrompt.TITLE ) );
      assertThat( systemUnderTest.getDialogPane().getButtonTypes().get( 0 ).getButtonData(), is( ButtonData.OK_DONE ) );
      assertThat( systemUnderTest.getDialogPane().getButtonTypes().get( 1 ), is( ButtonType.CANCEL ) );
   }//End Method
   
   @Test public void shouldHaveContent() {
      assertThat( systemUnderTest.getDialogPane().getContent(), is( systemUnderTest.content() ) );
   }//End Method
   
   @Test public void shouldHavePadding() {
      assertThat( systemUnderTest.content().getHgap(), is( JenkinsLoginPrompt.PADDING ) );
      assertThat( systemUnderTest.content().getVgap(), is( JenkinsLoginPrompt.PADDING ) );
      assertThat( systemUnderTest.content().getPadding().getBottom(), is( JenkinsLoginPrompt.PADDING ) );
      assertThat( systemUnderTest.content().getPadding().getTop(), is( JenkinsLoginPrompt.PADDING ) );
      assertThat( systemUnderTest.content().getPadding().getLeft(), is( JenkinsLoginPrompt.PADDING ) );
      assertThat( systemUnderTest.content().getPadding().getRight(), is( JenkinsLoginPrompt.PADDING ) );
   }//End Method
   
   @Test public void shouldHaveLoginFieldsAndLabels() {
      assertThat( systemUnderTest.content().getChildren().contains( systemUnderTest.locationLabel() ), is( true ) );
      assertThat( systemUnderTest.content().getChildren().contains( systemUnderTest.usernameLabel() ), is( true ) );
      assertThat( systemUnderTest.content().getChildren().contains( systemUnderTest.passwordLabel() ), is( true ) );
      assertThat( systemUnderTest.content().getChildren().contains( systemUnderTest.locationTextField() ), is( true ) );
      assertThat( systemUnderTest.content().getChildren().contains( systemUnderTest.usernameTextField() ), is( true ) );
      assertThat( systemUnderTest.content().getChildren().contains( systemUnderTest.passwordTextField() ), is( true ) );
   }//End Method
   
   @Test public void shouldConfigureLoginFieldsAndLabels() {
      assertThat( systemUnderTest.locationLabel().getText(), is( JenkinsLoginPrompt.LOCATION_LABEL ) );
      assertThat( systemUnderTest.usernameLabel().getText(), is( JenkinsLoginPrompt.USERNAME_LABEL ) );
      assertThat( systemUnderTest.passwordLabel().getText(), is( JenkinsLoginPrompt.PASSWORD_LABEL ) );
      
      assertThat( systemUnderTest.locationTextField().getPromptText(), is( JenkinsLoginPrompt.LOCATION_LABEL ) );
      assertThat( systemUnderTest.usernameTextField().getPromptText(), is( JenkinsLoginPrompt.USERNAME_LABEL ) );
      assertThat( systemUnderTest.passwordTextField().getPromptText(), is( JenkinsLoginPrompt.PASSWORD_LABEL ) );
   }//End Method
   
   @Test public void shouldConvertTextIntoCredentials() {
      systemUnderTest.locationTextField().setText( "anyLocation" );
      systemUnderTest.usernameTextField().setText( "anyUsername" );
      systemUnderTest.passwordTextField().setText( "anyPassword" );
      
      JenkinsCredentials credentials = systemUnderTest.getResultConverter().call( systemUnderTest.getDialogPane().getButtonTypes().get( 0 ) );
      assertThat( credentials.getLocation(), is( "anyLocation" ) );
      assertThat( credentials.getUsername(), is( "anyUsername" ) );
      assertThat( credentials.getPassword(), is( "anyPassword" ) );
   }//End Method
   
   @Test public void shouldNotConvertTextIntoCredentialsWhenCancelled() {
      assertThat( systemUnderTest.getResultConverter().call( ButtonType.CANCEL ), is( nullValue() ) );
   }//End Method

}//End Class
