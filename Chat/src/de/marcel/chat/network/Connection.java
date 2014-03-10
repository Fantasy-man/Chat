package de.marcel.chat.network;

import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

import de.marcel.chat.Message;
import de.marcel.chat.gui.ChatList;

public class Connection implements ChatLoader {
	
	private URL url;
	public RequestMessagesThread requestThread;
	
	public Connection() {
		try {
			url = new URL("http://192.168.0.109/?user=test&msg=");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return;
		}
		Log.i("Verbindungsaufbau", "URL wurde erstellt");
	}
	
	@Override
	public void startRequest(ChatList list) {
		requestThread = new RequestMessagesThread(url, list);
	}
	
	public void stopRequest() {
		requestThread.check = false;
	}

	@Override
	public void sendMessage(Message m) {
		new SendMessageThread(url, m);
	}
	
}
