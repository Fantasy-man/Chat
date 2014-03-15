package de.marcel.chat.network.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

import de.marcel.chat.MessageData;

public class SendMessageThread extends Thread {
	
	MessageData m;
	URL url;
	
	public SendMessageThread(URL url, MessageData m) {
		this.m = m;
		this.url = url;
		start();
	}
	
	public void run() {
		try {
			String msg = m.getMessageText();
			msg = msg.replace(" ", "%20");
			url = new URL("http://www.chatalternative.webuda.com/?user='test'&msg=" + msg + "");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return;
		}
		HttpURLConnection httpConn;
		try {
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("GET");
			BufferedReader b = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			String line;
			while ((line = b.readLine()) != null) {
				Log.i("Senden", "back: " + line);
			}
			Log.i("Senden", "Gesendet!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
