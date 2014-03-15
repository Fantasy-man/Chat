package de.marcel.chat.network.tcp;

import java.io.IOException;

import android.util.Log;
import de.marcel.chat.MainActivity;

public class ConnectionListener extends Thread{
	
	private Connection connection;
	public boolean check = true;
	
	public ConnectionListener(Connection connection) {
		this.connection = connection;
		start();
	}
	
	public void run() {
		String line;
		try {
			Log.d("Chat", "Warte auf Nachrichten...");
			while(check && (line = connection.getReader().readLine()) != null) {
				final String fLine = line;
				
				MainActivity.mainActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//list.addMessage(new MessageData(0, fLine, null));
						connection.handleInputString(fLine);
					}
				});
			}
		} catch (IOException e) {
			Log.e("Chat", "Verbindung zum Server fehlgeschlagen! " + e.getMessage());
			e.printStackTrace();
		} catch (NullPointerException e) {
			Log.d("Chat", "Null!");
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
				return;
			}
			run();
		}
	}
	
}
