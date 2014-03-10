package de.marcel.chat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import de.marcel.chat.gui.ChatList;
import de.marcel.chat.network.ChatLoader;
import de.marcel.chat.network.Connection;

public class MainActivity extends Activity implements OnClickListener{
	
	public static ChatLoader cl = new Connection();
	
	ChatList list;
	ScrollView mainLayout;
	Button btnSenden;
	EditText textInput;
	
	public static MainActivity mainActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mainActivity = this;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnSenden = (Button)findViewById(R.id.button1);
		btnSenden.setOnClickListener(this);
		textInput = (EditText) findViewById(R.id.editText1);
		mainLayout = (ScrollView) findViewById(R.id.scrollView1);
		
		// Liste erstellen
		list = new ChatList(this);
		mainLayout.addView(list);
		
		cl.startRequest(list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v == btnSenden) {
			Log.i("onClick - Sende", "Senden: " + textInput.getText().toString());
			if (!textInput.getText().toString().equals("")) {
				Message m = new Message(0, textInput.getText().toString(), new ChatUser());
				cl.sendMessage(m);
				textInput.setText("");
			}
		}
	}

}
