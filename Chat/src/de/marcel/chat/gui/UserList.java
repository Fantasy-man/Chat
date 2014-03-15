package de.marcel.chat.gui;

import java.util.ArrayList;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import de.marcel.chat.Chat;
import de.marcel.chat.ChatUser;
import de.marcel.chat.MainActivity;
import de.marcel.chat.R;

public class UserList {
	
	public static LinearLayout list;
	private static ArrayList<UserView> userViews = new ArrayList<UserView>();
	
	public UserList() {
	}
	
	public static void activate() {
		Log.d("Chat", "Activate UserList");
		MainActivity.mainActivity.setContentView(R.layout.userlist);
		list = (LinearLayout) MainActivity.mainActivity.findViewById(R.id.userList);
		if (list == null) {
			Log.e("Chat", "List == null!!");
		}
		
		for (UserView uv : userViews) {
			//list.addView(uv);
		}
	}
	
	public static void addUserView(UserView uv) {
		Log.d("Chat", "addUser " + uv.getUser().getId());
		userViews.add(uv);
		if (list != null) {
			Log.d("Chat", "Add UserView to VIew");
			list.addView(uv);
		}
	}
	
	public static void addUser(ChatUser user) {
		Log.d("Chat", "Add User " + user.getId() + " " + user.getUserName());
		addUserView(new UserView(new Chat(user)));
	}
	
	public static void requestAll() {
		MainActivity.conn.sendRequestForAllUsers();
	}
	
	public static Chat getChat(int id) {
		for (UserView uv : userViews) {
			if (uv.getUser().getId() == id) {
				return uv.getChat();
			}
		}
		return null;
	}
	
	public static Chat getChat(ChatUser user) {
		for (UserView uv : UserList.userViews) {
			if (uv.getUser() == user) {
				return uv.getChat();
			}
		}
		return null;
	}
	
	public static UserView getUserView(ChatUser user) {
		for (UserView uv : userViews) {
			if (uv.getUser() == user) {
				return uv;
			}
		}
		return null;
	}
	
}
