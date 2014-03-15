package de.marcel.chat.gui;

import android.widget.LinearLayout;
import android.widget.TextView;
import de.marcel.chat.Chat;
import de.marcel.chat.ChatUser;
import de.marcel.chat.MainActivity;

public class UserView extends LinearLayout{

	private Chat chat;
	
	private TextView tvName;
	
	public UserView(Chat chat) {
		super(MainActivity.mainActivity);
		this.chat = chat;
		repaint();
	}

	public Chat getChat() {
		return chat;
	}
	
	public void setChat(Chat chat) {
		this.chat = chat;
	}
	
	public ChatUser getUser() {
		return chat.getUser();
	}
	
	public void repaint() {
		removeAllViews();
		
		tvName = new TextView(getContext());
		tvName.setText("Name: " + chat.getUser().getUserName());
		tvName.setTextSize(Format.USER_LIST_NAME.getChatTextSize());
		tvName.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT ,LayoutParams.WRAP_CONTENT));
		addView(tvName);
	}
	
	public void refreshText() {
		tvName.setText(chat.getUser().getUserName());
	}
	
}
