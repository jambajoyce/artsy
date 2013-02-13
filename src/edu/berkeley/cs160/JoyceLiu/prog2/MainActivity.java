package edu.berkeley.cs160.JoyceLiu.prog2;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.app.Dialog;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;


public class MainActivity extends Activity {
	
	private DrawView drawView;
	private Dialog currentDialog;
	
	private static final int COLOR_MENU_ID = Menu.FIRST;
	private static final int WIDTH_MENU_ID = Menu.FIRST + 1;
	private static final int ERASE_MENU_ID = Menu.FIRST + 2;
	private static final int CLEAR_MENU_ID = Menu.FIRST + 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		drawView = (DrawView) findViewById(R.id.DrawView);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		
		menu.add(Menu.NONE, COLOR_MENU_ID, Menu.NONE, R.string.menu_color);
		menu.add(Menu.NONE, WIDTH_MENU_ID, Menu.NONE, R.string.menu_lw);
		menu.add(Menu.NONE, ERASE_MENU_ID, Menu.NONE, R.string.menu_erase);
		menu.add(Menu.NONE, CLEAR_MENU_ID, Menu.NONE, R.string.menu_clear);
		
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()) {
			
		case COLOR_MENU_ID:
			showColorChooser();
			return true;
		case WIDTH_MENU_ID:
			showLWChooser();
			return true;
		case ERASE_MENU_ID:
			drawView.setDrawingColor(Color.WHITE);
			return true;
		case CLEAR_MENU_ID:
			drawView.clear();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showColorChoose() {
		
		currentDialog = new Dialog(this);
		currentDialog.setContentView(R.layout.color);
		currentDialog.setTitle(R.string.title_color);
		currentDialog.setCancelable(true);
	}

}
