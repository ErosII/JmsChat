package windows;

import java.util.Random;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import application.JmsChat;
import application.MainCallback;
import chat.jms.core.ChatMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MainPageController {
	
    private String usernameString;
    private UUID userId;
    
    private JmsChat jmsChat;
    
    @FXML
    private TextField id_text_message;
    
    @FXML
    private Button id_button_send;
    
    @FXML
    private VBox id_chat_box;
	
    @FXML
    private void initialize() 
    {
    	userId = UUID.randomUUID();
    	
    	try {
			jmsChat = new JmsChat(MainPageController.this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
	@FXML
	void publishMessage(ActionEvent event) {
		
		if (id_text_message.getText().length() > 0) {
			try {
				jmsChat.publish(userId, usernameString, id_text_message.getText());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			id_text_message.clear();
		}
	}
	
	@FXML
	void close(ActionEvent event) {
		try {
			jmsChat.disconnect();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	
	public void consumeMessage (ChatMessage chatMessage) {
		Platform.runLater(() -> {
			TextField messageArea = new TextField();
			messageArea.setText(chatMessage.getMsg());
			messageArea.setPrefHeight(150);
			messageArea.setAlignment(Pos.TOP_LEFT);
			messageArea.setEditable(false);
			TitledPane titledPane = new TitledPane(chatMessage.getUser(), messageArea);
			titledPane.setMaxWidth(300);
			titledPane.setMinWidth(300);
			titledPane.setPrefHeight(150);
			
			titledPane.setCollapsible(false);
			HBox hBox = new HBox();
			
			if (chatMessage.getUserId().equals(userId)) {
				hBox.setAlignment(Pos.TOP_RIGHT);
				titledPane.setPadding(new Insets(0,20,0,0));
			}

			else
				hBox.setAlignment(Pos.TOP_LEFT);
			
			hBox.getChildren().add(titledPane);
						
			id_chat_box.getChildren().add(hBox);

		});


	}
	
 	// Using the controller to get the logged user
    public MainPageController(String loggedUser)
    {
    	this.usernameString = loggedUser;
    }
}
