package de.marcel.chat;

public class Message {
	
	private Integer id;
	private String messageText;
	private ChatUser sender;
	
	public Message(Integer id, String messageText, ChatUser sender) {
		this.id = id;
		this.messageText = messageText;
		this.sender = sender;
	}
	
	public Integer getId() {
		return id;
	}

	public String getMessageText() {
		return messageText;
	}

	public ChatUser getSender() {
		return sender;
	}
	
}
