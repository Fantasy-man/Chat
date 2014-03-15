package de.marcel.chat;

import java.util.ArrayList;

import android.util.Log;

public class Chat{

	private ChatUser user;
	private ArrayList<MessageData> messages = new ArrayList<MessageData>();
	
	MessageListener messageListener = null;
	
	public Chat(ChatUser user) {
		this.user = user;
	}
	
	public void addMessage(MessageData message) {
		/*if (containsMessage(message.getId())) {
			return;
		}*/
		
		Log.d("Chat", "User " + user.getId() + " addMessage " + message.getMessageText());
		
		messages.add(message);
		
		if (messageListener != null) {
			messageListener.messageIncome();
		}
		
	}
	
	public boolean containsMessage(Integer id) {
		for (MessageData m : messages) {
			if (m.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public void refresh() {
		MainActivity.conn.sendRequestForAllMessages(this);
	}
	
	public ArrayList<MessageData> getMessages() {
		return messages;
	}

	public ChatUser getUser() {
		return user;
	}

	public void setUser(ChatUser user) {
		this.user = user;
	}
	
}
