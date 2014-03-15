package de.marcel.chat;

public class MessageData {
	
	private Integer id;
	private String messageText;
	private ChatUser sender;
	private ChatUser receiver;
	
	public MessageData(Integer id, String messageText, ChatUser sender, ChatUser receiver) {
		this.id = id;
		this.messageText = messageText;
		this.sender = sender;
		this.receiver = receiver;
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

	public ChatUser getReceiver() {
		return receiver;
	}

	public void setReceiver(ChatUser receiver) {
		this.receiver = receiver;
	}
	
}
