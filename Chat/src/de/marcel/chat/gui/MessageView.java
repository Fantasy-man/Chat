package de.marcel.chat.gui;

import android.widget.LinearLayout;
import android.widget.TextView;
import de.marcel.chat.MainActivity;
import de.marcel.chat.MessageData;

public class MessageView extends LinearLayout {
	
	private MessageData message;
	private ChatView parentChatList;
	
	private TextView messageTextView;
	
	public MessageView(MessageData message, ChatView parentChatList) {
		super(MainActivity.mainActivity);
		this.message = message;
		this.parentChatList = parentChatList;
		
		parentChatList.messageLayout.addView(this);
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
	
	public MessageData getMessage() {
		return message;
	}
	
}
