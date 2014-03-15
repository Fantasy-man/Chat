package de.marcel.chat.network.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Intent;
import android.util.Log;
import de.marcel.chat.Chat;
import de.marcel.chat.ChatUser;
import de.marcel.chat.MainActivity;
import de.marcel.chat.MessageData;
import de.marcel.chat.gui.ChatView;
import de.marcel.chat.gui.UserList;
import de.marcel.chat.gui.UserView;
import de.marcel.chat.network.ChatLoader;

public class Connection extends Thread implements ChatLoader{

	public static final String LOGIN = "Login";
	public static final String LOGIN_FAIL = "FailedLogin";
	public static final String GREET = "Hello";
	public static final String PARA = ",*,";
	public static final String USER_INFO_REQUEST = "UserInfoRequest";
	public static final String USER_INFO = "UserData";
	public static final String USER_REQUEST_ALL = "UserRequestAll";

	public static final String MESSAGE_TO = "MessageTo";
	public static final String MESSAGE_FROM = "MessageFrom";
	public static final String MESSAGE_NOT_SENT = "MessageNotSent";
	public static final String ALL_MESSAGES = "GetAllMessages";
	
	private Socket socket;
	private BufferedReader reader;
	private ConnectionListener listener;
	private PrintWriter writer;
	private String ip;
	private int port;
	private boolean authorized = false;
	
	public Connection(String ip, int port) {
		this.ip = ip;
		this.port = port;
		start();
	}
	
	public void run() {
		try {
			socket = new Socket(ip, port);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (UnknownHostException e) {
			Log.e("Chat", "Server " + ip + " konnte nicht erreicht werden! " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("Chat", "Verbindung zu " + ip + ":" + port + " konnte nicht aufgebaut werden! " + e.getMessage());
			e.printStackTrace();
		}
		Log.d("Chat", "Verbindung aufgebaut");
		
		login("Marcel", "123");
	}
	
	public void handleInputString(String s) {
		Log.d("Chat", "Empfangen: " + s);
		if (s.startsWith(GREET)) {
			try {
				int id = Integer.parseInt(s.substring(GREET.length()));
				MainActivity.thisUser.setId(id);
			} catch (NumberFormatException e) {
				return;
			}

			authorize();
			
			// Liste erstellen
			UserList.activate();
			UserList.requestAll();
			
			return;
		} else if (s.startsWith(LOGIN_FAIL)) {
			Log.w("chat", "Login failed!");
			return;
		} else if (s.startsWith(MESSAGE_FROM)) {
			if (!s.contains(PARA)) {
				return;
			}
			try {
				Integer id = Integer.parseInt(s.substring(MESSAGE_FROM.length(), s.indexOf(PARA)));
				s = s.substring(s.indexOf(PARA) + PARA.length());
				if (!s.contains(PARA)) {
					return;
				}
				
				Integer from = Integer.parseInt(s.substring(0, s.indexOf(PARA)));
				s = s.substring(s.indexOf(PARA) + PARA.length());
				if (!s.contains(PARA)) {
					return;
				}
				
				Integer to = Integer.parseInt(s.substring(0, s.indexOf(PARA)));
				String msg = s.substring(s.indexOf(PARA) + PARA.length());
				
				ChatUser userFrom = MainActivity.getChat(from).getUser();
				if (userFrom == null) {
					// User erstellen
					userFrom = new ChatUser();
					UserList.addUser(userFrom);
					userFrom.setId(from);
					userFrom.setUserName("Loading...");
				}
				
				if (to != MainActivity.thisUser.getId()) {
					Log.e("Chat", "Nachricht nicht für mich!");
					return;
				}
				
				MessageData md = new MessageData(id, msg, userFrom, MainActivity.thisUser);
				
				userFrom.getChat().addMessage(md);
				
			} catch (NumberFormatException e) {
				return;
			}
		} else if (s.startsWith(USER_INFO)) {
			Log.d("Chat", "USER_INFO");
			if (!s.contains(PARA)) {
				return;
			}
			try {
				Log.d("Chat", "adding...");
				Integer id = Integer.parseInt(s.substring(USER_INFO.length(), s.indexOf(PARA)));
				String name = s.substring(s.indexOf(PARA) + PARA.length());
				
				Chat chat = MainActivity.getChat(id);
				if (chat == null) {
					chat = new Chat(new ChatUser());
					UserList.addUser(chat.getUser());
				}
				chat.getUser().setId(id);
				chat.getUser().setUserName(name);
				Log.d("Chat", "added!");
			} catch (NumberFormatException e) {
				Log.d("Chat", "Return " + e.getMessage());
				return;
			}
		}
	}
	
	public void sendRaw(String s) {
		Log.d("Chat", "sendRaw:" + s);
		writer.println(s);
		writer.flush();
	}
	
	@Override
	public void startRequest(ChatView list) {
		Log.e("Chat", "startRequest() zu früh aufgerufen. reader == null !!!!");
		listener = new ConnectionListener(this);
	}
	
	public void authorize() {
		authorized = true;
		Log.d("Chat", "User " + MainActivity.thisUser.getId() + " - Authorized");
	}

	@Override
	public void sendMessage(MessageData m) {
		sendRaw(MESSAGE_TO + m.getReceiver().getId() + PARA + m.getMessageText());
	}
	
	public void login(String user, String pass) {
		sendRaw(LOGIN + user + PARA + pass);
		MainActivity.thisUser.setUserName(user);
	}
	
	public void requestUser(int id) {
		sendRaw(USER_INFO_REQUEST + id);
	}
	
	public void sendRequestForAllMessages(Chat chat) {
		sendRaw(ALL_MESSAGES + chat.getUser().getId());
	}
	
	public void sendRequestForAllUsers() {
		sendRaw(USER_REQUEST_ALL);
	}
	
	public BufferedReader getReader() {
		return reader;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
	
}
