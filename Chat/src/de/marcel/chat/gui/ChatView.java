package de.marcel.chat.gui;

import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import de.marcel.chat.Chat;
import de.marcel.chat.ChatUser;
import de.marcel.chat.MainActivity;
import de.marcel.chat.MessageData;
import de.marcel.chat.MessageListener;
import de.marcel.chat.R;

public class ChatView implements MessageListener, OnClickListener {

	Chat chat;
	ScrollView mainLayout;
	public LinearLayout messageLayout;
	Button btnSenden;
	EditText textInput;
	
	ArrayList<MessageView> messageViews = new ArrayList<MessageView>();
	
	public ChatView(Chat chat) {
		this.chat = chat;
		MainActivity.currentChatView = this;
		MainActivity.mainActivity.setContentView(R.layout.activity_main);
		messageLayout = new LinearLayout(MainActivity.mainActivity);
		LinearLayout.LayoutParams pa = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		messageLayout.setLayoutParams(pa);
		messageLayout.setOrientation(LinearLayout.VERTICAL);
		
		btnSenden = (Button)MainActivity.mainActivity.findViewById(R.id.button1);
		btnSenden.setOnClickListener(this);
		textInput = (EditText) MainActivity.mainActivity.findViewById(R.id.editText1);
		mainLayout = (ScrollView) MainActivity.mainActivity.findViewById(R.id.scrollView1);
		mainLayout.addView(messageLayout);
	}
	
	public void loadChat(Chat chat) {
		refresh();
	}

	@Override
	public void messageIncome() {
		refresh();
	}
	
	public void refresh() {
		chat.refresh();
		for (MessageData m : chat.getMessages()) {
			if (!containsMessage(m)) {
				addMessage(m);
			}
		}
	}
	
	public void addMessage(MessageData md) {
		if (!containsMessage(md)) {
			Log.d("Chat", "Add Message " + md.getId());
			MessageView mv = new MessageView(md, this);
			
			messageViews.add(mv);
		}
	}
	
	public boolean containsMessage(MessageData md) {
		for (MessageView mv : messageViews) {
			if (mv.getMessage().getId() == md.getId()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		if (v == btnSenden) {
			Log.i("onClick - Sende", "Senden: " + textInput.getText().toString());
			if (!textInput.getText().toString().equals("")) {
				MessageData m = new MessageData(0, textInput.getText().toString(), new ChatUser(), MainActivity.thisUser);
				MainActivity.conn.sendMessage(m);
				textInput.setText("");
			}
		}
	}
	
	public void setChat(Chat chat) {
		this.chat = chat;
	}
	
	/*public Location getLocationForNextMessage() {
		Location l = new Location(0, 0);
		
		if (messages.size() > 0) {
			Message lastMsg = messages.get(messages.size() - 1);
			l.setY((int) lastMsg.getY());
		}
		
		return l;
	}*/

}
