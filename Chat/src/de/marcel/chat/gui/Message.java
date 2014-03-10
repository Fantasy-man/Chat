package de.marcel.chat.gui;

import de.marcel.chat.ChatUser;
import de.marcel.chat.MainActivity;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessageView extends LinearLayout {
	
	private String messageText;
	private ChatUser sender;
	private ChatList parentChatList;
	
	private TextView messageTextView;
	
	public MessageView(String messageText, ChatUser sender, ChatList parentChatList) {
		super(parentChatList.getContext());
		this.messageText = messageText;
		this.sender = sender;
		this.parentChatList = parentChatList;
		
		parentChatList.addView(this);
	}
	
	public void repaint() {
		removeAllViews();
		
		messageTextView = new TextView(getContext());
		messageTextView.setText(messageText);
		messageTextView.setTextSize(Format.NORMAL.getChatTextSize());
	}
	
}
