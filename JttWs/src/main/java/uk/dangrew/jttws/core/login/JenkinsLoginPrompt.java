/*
 * ----------------------------------------
 *    Jenkins Test Tracker Web Services
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.jttws.core.login;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * The {@link JenkinsLoginPrompt} provides a {@link Dialog} for entering login details.
 */
public class JenkinsLoginPrompt extends Dialog< JenkinsCredentials >{

   static final String TITLE = "Login";
   static final String HEADER_TEXT = "Please enter your Jenkins credentials.";
   static final String PASSWORD_LABEL = "Password";
   static final String USERNAME_LABEL = "Username";
   static final String LOCATION_LABEL = "Location";
   static final double PADDING = 10;
   
   private final GridPane content;
   private final Label locationLabel;
   private final Label usernameLabel;
   private final Label passwordLabel;
   private final TextField locationTextField;
   private final TextField usernameTextField;
   private final PasswordField passwordTextField;

   /**
    * Constructs a new {@link JenkinsLoginPrompt}.
    */
   public JenkinsLoginPrompt() {
      setTitle( TITLE );
      setHeaderText( HEADER_TEXT );

      ButtonType loginButtonType = new ButtonType( TITLE, ButtonData.OK_DONE );
      getDialogPane().getButtonTypes().addAll( loginButtonType, ButtonType.CANCEL );

      content = new GridPane();
      content.setHgap( PADDING );
      content.setVgap( PADDING );
      content.setPadding( new Insets( PADDING ) );

      locationTextField = new TextField();
      locationTextField.setPromptText( LOCATION_LABEL );
      usernameTextField = new TextField();
      usernameTextField.setPromptText( USERNAME_LABEL );
      passwordTextField = new PasswordField();
      passwordTextField.setPromptText( PASSWORD_LABEL );

      content.add( locationLabel = new Label( LOCATION_LABEL ), 0, 0 );
      content.add( locationTextField, 1, 0 );
      content.add( usernameLabel = new Label( USERNAME_LABEL ), 0, 1 );
      content.add( usernameTextField, 1, 1 );
      content.add( passwordLabel = new Label( PASSWORD_LABEL ), 0, 2 );
      content.add( passwordTextField, 1, 2 );

      getDialogPane().setContent( content );

      setResultConverter( dialogButton -> {
         if ( dialogButton == loginButtonType ) {
            JenkinsCredentials credentials = new JenkinsCredentials();
            credentials.setLocation( locationTextField.getText() );
            credentials.setUsername( usernameTextField.getText() );
            credentials.setPassword( passwordTextField.getText() );
            return credentials;
         }
         return null;
      } );
   }// End Constructor
   
   GridPane content(){
      return content;
   }//End Method
   
   Label locationLabel(){
      return locationLabel;
   }//End Method
   
   Label usernameLabel(){
      return usernameLabel;
   }//End Method
   
   Label passwordLabel(){
      return passwordLabel;
   }//End Method
   
   TextField locationTextField(){
      return locationTextField;
   }//End Method
   
   TextField usernameTextField(){
      return usernameTextField;
   }//End Method
   
   PasswordField passwordTextField(){
      return passwordTextField;
   }//End Method

}// End Class
