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
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
			VBox messageBox = new VBox();
			Label userLabel = new Label();
			userLabel.setText(chatMessage.getUser());
			TextArea messageArea = new TextArea();
			messageArea.setText(chatMessage.getMsg());
			messageBox.getChildren().add(userLabel);
			messageBox.getChildren().add(messageArea);
			id_chat_box.getChildren().add(messageBox);

		});


	}
	
 	// Using the controller to get the logged user
    public MainPageController(String loggedUser)
    {
    	this.usernameString = loggedUser;
    }
}
