package de.marcel.chat.network;

import de.marcel.chat.Message;
import de.marcel.chat.gui.ChatList;

public interface ChatLoader {
	
	public void startRequest(ChatList list);
	
	public void sendMessage(Message m);
	
}
