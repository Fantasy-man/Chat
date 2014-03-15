package de.marcel.chat.network.http;

import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

import de.marcel.chat.MessageData;
import de.marcel.chat.gui.ChatView;
import de.marcel.chat.network.ChatLoader;

public class Connection implements ChatLoader {
	
	private URL url;
	public RequestMessagesThread requestThread;
	
	public Connection() {
		try {
			url = new URL("http://www.chatalternative.webuda.com/?user=test&msg=");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return;
		}
		Log.i("Verbindungsaufbau", "URL wurde erstellt");
	}
	
	@Override
	public void startRequest(ChatView list) {
		requestThread = new RequestMessagesThread(url, list);
	}
	
	public void stopRequest() {
		requestThread.check = false;
	}

	@Override
	public void sendMessage(MessageData m) {
		new SendMessageThread(url, m);
	}
	
}
