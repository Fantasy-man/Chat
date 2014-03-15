package de.marcel.chat;

import de.marcel.chat.gui.UserList;

public class ChatUser {
	
	private String userName;
	private int id;
	
	public Chat getChat() {
		return UserList.getChat(this);
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
		if (UserList.getUserView(this) != null)
			UserList.getUserView(this).refreshText();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		if (UserList.getUserView(this) != null)
			UserList.getUserView(this).refreshText();
	}
	
}
