package de.marcel.chat.network.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;
import de.marcel.chat.ChatUser;
import de.marcel.chat.MainActivity;
import de.marcel.chat.MessageData;
import de.marcel.chat.gui.ChatView;

public class RequestMessagesThread extends Thread {
	
	private URL url;
	private ChatView list;
	public boolean check = true;
	
	public RequestMessagesThread(URL url, ChatView list) {
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
				StringBuffer sb = new StringBuffer();
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				
				String splitString = "<SplitHere>";
				
				for (String line1 : sb.toString().split(splitString)) {
					line1 = splitString + line1;
					Log.d("Connection.getMessages", "Message eingelesen: " + line1);
					
					if (line1.contains("<Messages>")) {
						nochMessages = true;
						line1 = line1.substring(line1.indexOf("<Messages>") + 10);
					} else if (line1.contains("</Messages>")) {
						nochMessages = false;
					}
					
					if (nochMessages) {
						// Im Messages-Bereich
						if (line1.contains("<Message>")) {
							id = -1;
							text = "";
							nochMessage = true;
							line1 = line1.substring(line1.indexOf("<Message>") + 9);
						} else if (line1.contains("</Message>")) {
							nochMessage = false;
							nochText = false;
						}
						
						if (nochMessage) {
							// Im Message-Bereich
							if (line1.contains("<Id>")) {
								if (line1.contains("</Id>")) {
									String sID = line1.substring(line1.indexOf("<Id>") + 4, line1.indexOf("</Id>"));
									Log.d("Encode", "sID = '" + sID + "'");
									try {
										id = Integer.parseInt(sID);
									} catch (NumberFormatException e) {
										Log.e("Encode", "sID '" + sID + "' kann nicht in eine Zahl umgewandelt werden!");
									}
								}
							}
							
							if (line1.contains("<Text>")) {
								if (line1.contains("</Text>")) {
									text = line1.substring(line1.indexOf("<Text>") + 6, line1.indexOf("</Text>"));
									Log.d("Encode", "text = '" + text + "'");
								} else {
									text += line1.substring(line1.indexOf("<Text>") + 6);
									nochText = true;
									Log.d("Encode", "Teil text = '" + text + "'");
								}
							}
							if (!line1.contains("<Text>") && line1.contains("</Text>")) {
								text += line1.substring(0, line1.indexOf("</Text>"));
								nochText = false;
								Log.d("Encode", "Teil text = '" + text + "'");
							}
							
							if (nochText) {
								text += line1;
							}
							
							// Message hinzufügen 
							if (!nochText && !text.equals("") && id > -1) {
								final MessageData m = null;//new MessageData(id, text, new ChatUser());
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
