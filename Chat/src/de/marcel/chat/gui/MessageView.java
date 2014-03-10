package de.marcel.chat.gui;

import android.widget.LinearLayout;
import android.widget.TextView;
import de.marcel.chat.Message;

public class MessageView extends LinearLayout {
	
	private Message message;
	private ChatList parentChatList;
	
	private TextView messageTextView;
	
	public MessageView(Message message, ChatList parentChatList) {
		super(parentChatList.getContext());
		this.message = message;
		this.parentChatList = parentChatList;
		
		parentChatList.addView(this);
		repaint();
	}
	
	public void repaint() {
		removeAllViews();
		
		messageTextView = new TextView(getContext());
		messageTextView.setText("> " + message.getMessageText());
		messageTextView.setTextSize(Format.NORMAL.getChatTextSize());
		messageTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT ,LayoutParams.WRAP_CONTENT));
		addView(messageTextView);
	}
	
	public Message getMessage() {
		return message;
	}
	
}
