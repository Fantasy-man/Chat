package de.marcel.chat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import de.marcel.chat.gui.ChatView;
import de.marcel.chat.gui.UserList;
import de.marcel.chat.network.tcp.Connection;

public class MainActivity extends Activity{
	
	public static Connection conn;
	public static ChatUser thisUser;
	public static ChatView currentChatView;
	public static ChatView list;
	public static UserList userList;
	
	public static MainActivity mainActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mainActivity = this;
		
		userList = new UserList();
		
		thisUser = new ChatUser();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		currentChatView = null;
		
		// Verbindung zum Server aufbauen
		conn = new Connection("192.168.0.150", 4562);
		
		conn.startRequest(list);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public static Chat getChat(int id) {
		return UserList.getChat(id);
	}

}
