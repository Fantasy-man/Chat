package de.marcel.chat.gui;

import java.util.ArrayList;

import android.content.Context;
import android.widget.LinearLayout;
import de.marcel.chat.Message;

public class ChatList extends LinearLayout {

	private ArrayList<MessageView> messages = new ArrayList<MessageView>();
	
	public ChatList(Context context) {
		super(context);
		LinearLayout.LayoutParams pa = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		setLayoutParams(pa);
		setOrientation(VERTICAL);
	}
	
	public void addMessage(Message message) {
		if (!containsMessage(message.getId())) {
			messages.add(new MessageView(message, this));
		}
	}
	
	public boolean containsMessage(Integer id) {
		for (MessageView m : messages) {
			if (m.getMessage().getId() == id) {
				return true;
			}
		}
		return false;
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
