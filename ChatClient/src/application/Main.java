package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;

import windows.LoginPageController;
import windows.MainPageController;
import windows.SplashScreenController;

public class Main extends Application implements MainCallback {
	
	// Using "Progetto Franco" approach to switch the pages of the application
	@Override
	public void start(Stage primaryStage) {
		
		try {
		this.primaryStage = primaryStage;

		showScene(createSplashScreen());
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// 
	public void switchScene(MainCallback.Pages requiredPage, String user) 
	{
		Platform.runLater(() -> 
		{	
			try
			{
				switch (requiredPage)
				{	
				case LoginPage:
					showScene(createLoginLayout());
					break;

				case MainPage:
					showScene(createMainPageLayout(user));
					break;				
				}
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});	
	}
	
	// Create the Splash Screen
	private Scene createSplashScreen() throws IOException 
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../windows/SplashScreen.fxml"));
		SplashScreenController fxmlController = new SplashScreenController(this);	
		fxmlLoader.setController(fxmlController);
		BorderPane pane = fxmlLoader.load();
		Scene scene = new Scene(pane, 700, 700);
		
		return scene;
	}
	
	// Create the LoginPage
	private Scene createLoginLayout() throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../windows/LoginPage.fxml"));
		LoginPageController fxmlController = new LoginPageController(this);	
		fxmlLoader.setController(fxmlController);
		GridPane pane = fxmlLoader.load();
		Scene scene = new Scene(pane, 700, 700);
		
		return scene;
	}
	
	// Create the LoginPage
	private Scene createMainPageLayout(String user) throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../windows/MainPage.fxml"));
		MainPageController fxmlController = new MainPageController(user);	
		fxmlLoader.setController(fxmlController);
		GridPane pane = fxmlLoader.load();
		Scene scene = new Scene(pane, 700, 700);
			
		return scene;
	}
	
	// Load a scene into the stage
	private void showScene(Scene scene)
	{
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	// Application stage
	private Stage primaryStage;
	
	@Override
	public Stage getStage() {
		return primaryStage;
	}
	

}
