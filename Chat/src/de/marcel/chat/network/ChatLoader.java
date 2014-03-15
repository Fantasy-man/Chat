package de.marcel.chat.network;

import de.marcel.chat.MessageData;
import de.marcel.chat.gui.ChatView;

public interface ChatLoader {
	
	public void startRequest(ChatView list);
	
	public void sendMessage(MessageData m);
	
}
