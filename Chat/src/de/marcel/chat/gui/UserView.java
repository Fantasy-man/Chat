package de.marcel.chat.gui;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.marcel.chat.Chat;
import de.marcel.chat.ChatUser;
import de.marcel.chat.MainActivity;

public class UserView extends LinearLayout implements TextView.OnClickListener{

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
		refreshText();
		tvName.setTextSize(Format.USER_LIST_NAME.getChatTextSize());
		tvName.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT ,LayoutParams.WRAP_CONTENT));
		tvName.setOnClickListener(this);
		addView(tvName);
	}
	
	public void refreshText() {
		tvName.setText(chat.getUser().getUserName() + " " + chat.getUser().getId());
	}

	@Override
	public void onClick(View v) {
		MainActivity.showChat(chat);
	}
	
}
