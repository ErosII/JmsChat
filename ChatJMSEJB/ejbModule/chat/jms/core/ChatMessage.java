package chat.jms.core;

import java.io.Serializable;
import java.util.UUID;

public class ChatMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	UUID userId;
	String user;
	String msg;
	
	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ChatMessage (UUID userId, String user, String msg) {
		this.userId = userId;
		this.user = user;
		this.msg = msg;
	}

}
