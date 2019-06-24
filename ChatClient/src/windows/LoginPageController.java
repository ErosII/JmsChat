package windows;

import java.sql.SQLException;

import application.MainCallback;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LoginPageController {
	
	@FXML
	private TextField id_text_username;
		
	@FXML
	private Button id_button_login;
	
	@FXML
	void logIn(ActionEvent event) {
				
		if (id_text_username.getText()!= null && id_text_username.getText().length() >= 1)
		{
			interfaceMain.switchScene(MainCallback.Pages.MainPage, id_text_username.getText());
		}
		else 
		{
			showAlertNoUser();	
		} 
	}
	

	
	// Create an error Alert for no username
	private void showAlertNoUser() 
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Messaggio di errore");
		alert.setHeaderText("Inserire un nome utente");
		alert.setContentText("Riprovare");
		alert.showAndWait();
	}
	
	 // Interface to callback the main class 
 	private MainCallback interfaceMain;	
 	
    public LoginPageController(MainCallback interfaceMain)
    {
    	this.interfaceMain = interfaceMain;
    }

}
