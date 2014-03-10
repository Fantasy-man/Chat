package de.marcel.chat.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;
import de.marcel.chat.ChatUser;
import de.marcel.chat.MainActivity;
import de.marcel.chat.Message;
import de.marcel.chat.gui.ChatList;

public class RequestMessagesThread extends Thread {
	
	private URL url;
	private ChatList list;
	public boolean check = true;
	
	public RequestMessagesThread(URL url, ChatList list) {
		this.url = url;
		this.list = list;
		start();
	}
	
	public void run() {
		while (check) {
			try {
				HttpURLConnection httpConn;
				BufferedReader reader;
				httpConn = (HttpURLConnection) url.openConnection();
				httpConn.setRequestMethod("GET");
				reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
				String line;
				boolean nochMessages = false;
				boolean nochMessage = false;
				boolean nochText = false;
				Integer id = 0;
				String text = "";
				while ((line = reader.readLine()) != null) {
					Log.d("Connection.getMessages", "Message eingelesen: " + line);
					
					if (line.contains("<Messages>")) {
						nochMessages = true;
						line = line.substring(line.indexOf("<Messages>") + 10);
					} else if (line.contains("</Messages>")) {
						nochMessages = false;
					}
					
					if (nochMessages) {
						// Im Messages-Bereich
						if (line.contains("<Message>")) {
							id = -1;
							text = "";
							nochMessage = true;
							line = line.substring(line.indexOf("<Message>") + 9);
						} else if (line.contains("</Message>")) {
							nochMessage = false;
							nochText = false;
						}
						
						if (nochMessage) {
							// Im Message-Bereich
							if (line.contains("<Id>")) {
								if (line.contains("</Id>")) {
									String sID = line.substring(line.indexOf("<Id>") + 4, line.indexOf("</Id>"));
									Log.d("Encode", "sID = '" + sID + "'");
									try {
										id = Integer.parseInt(sID);
									} catch (NumberFormatException e) {
										Log.e("Encode", "sID '" + sID + "' kann nicht in eine Zahl umgewandelt werden!");
									}
								}
							}
							
							if (line.contains("<Text>")) {
								if (line.contains("</Text>")) {
									text = line.substring(line.indexOf("<Text>") + 6, line.indexOf("</Text>"));
									Log.d("Encode", "text = '" + text + "'");
								} else {
									text += line.substring(line.indexOf("<Text>") + 6);
									nochText = true;
									Log.d("Encode", "Teil text = '" + text + "'");
								}
							}
							if (!line.contains("<Text>") && line.contains("</Text>")) {
								text += line.substring(0, line.indexOf("</Text>"));
								nochText = false;
								Log.d("Encode", "Teil text = '" + text + "'");
							}
							
							if (nochText) {
								text += line;
							}
							
							// Message hinzufügen
							if (!nochText && !text.equals("") && id > -1) {
								final Message m = new Message(id, text, new ChatUser());
								Log.d("Encode", "Hinzugefügt: id = " + id + " text = '" + text + "'");
								MainActivity.mainActivity.runOnUiThread(new Runnable() {
								     @Override
								     public void run() {
										list.addMessage(m);
								    }
								});
							}
						}
					}
				}
				Log.i("Connection.getMessages", "Abfrage erfolgreich");
			} catch (IOException e) {
				Log.e("Connection.getMessages", "IOException - Verbindung konnte nicht hergestellt werden!");
				e.printStackTrace();
			}
			try {
				sleep(1000);
			} catch (InterruptedException e1) {
				Log.e("Encode", "Sleep fehlgeschlaten!");
				e1.printStackTrace();
				return;
			}
		}
	}
}
