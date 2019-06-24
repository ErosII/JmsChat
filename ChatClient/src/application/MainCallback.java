package application;

import javafx.stage.Stage;

// Services exposed from Main class
public interface MainCallback 
{
	// Identifiers for all application pages
	public enum Pages
	{
		MainPage,			// Main Application
		LoginPage,			// LogInPage
	}
	
	// Adding the user to the switch enables "moving" the logged user from one page to another
	public void switchScene(Pages requiredPage, String user);
	
	public Stage getStage();
	
}
