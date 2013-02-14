package edu.berkeley.cs160.JoyceLiu.prog2;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.app.Dialog;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.content.Context;
import android.content.DialogInterface;
import java.util.concurrent.atomic.AtomicBoolean;



public class MainActivity extends Activity {
	
	private DrawView drawView;
	private Dialog currentDialog;
	private AtomicBoolean dialogIsVisible = new AtomicBoolean();
	
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
	
	private void showColorChooser() {
		
		currentDialog = new Dialog(this);
		currentDialog.setContentView(R.layout.color);
		currentDialog.setCancelable(true);
		
		final SeekBar alphaBar = (SeekBar) currentDialog.findViewById(R.id.alphaSeekBar);
		final SeekBar redBar = (SeekBar) currentDialog.findViewById(R.id.redSeekBar);
		final SeekBar greenBar = (SeekBar) currentDialog.findViewById(R.id.greenSeekBar);
		final SeekBar blueBar = (SeekBar) currentDialog.findViewById(R.id.blueSeekBar);
		
		alphaBar.setOnSeekBarChangeListener(colorSeekBarChanged);
		redBar.setOnSeekBarChangeListener(colorSeekBarChanged);
		greenBar.setOnSeekBarChangeListener(colorSeekBarChanged);
		blueBar.setOnSeekBarChangeListener(colorSeekBarChanged);
		
	}
	
	private OnSeekBarChangeListener colorSeekBarChanged = new OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			
			SeekBar alphaBar = (SeekBar) currentDialog.findViewById(R.id.alphaSeekBar);
			SeekBar redBar = (SeekBar) currentDialog.findViewById(R.id.redSeekBar);
			SeekBar greenBar = (SeekBar) currentDialog.findViewById(R.id.greenSeekBar);
			SeekBar blueBar = (SeekBar) currentDialog.findViewById(R.id.blueSeekBar);
			
			View colorView = (View) currentDialog.findViewById(R.id.colorView);
			
			colorView.setBackgroundColor(Color.argb(alphaBar.getProgress(), 
					redBar.getProgress(), greenBar.getProgress(), blueBar.getProgress()));
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
		
	};
	
	private OnClickListener setColorButtonList = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			
			SeekBar alphaBar = (SeekBar) currentDialog.findViewById(R.id.alphaSeekBar);
			SeekBar redBar = (SeekBar) currentDialog.findViewById(R.id.redSeekBar);
			SeekBar greenBar = (SeekBar) currentDialog.findViewById(R.id.greenSeekBar);
			SeekBar blueBar = (SeekBar) currentDialog.findViewById(R.id.blueSeekBar);
			
			drawView.setDrawingColor(Color.argb(alphaBar.getProgress(), 
					redBar.getProgress(), greenBar.getProgress(), blueBar.getProgress()));
			dialogIsVisible.set(false);
			currentDialog.dismiss();
			currentDialog = null;
		}
	};
	
	private void showLWChooser() {
		
		currentDialog = new Dialog(this);
		currentDialog.setContentView(R.layout.width);
		currentDialog.setCancelable(true);
		
		SeekBar widthBar = (SeekBar) currentDialog.findViewById(R.id.widthSeekBar);
		widthBar.setOnSeekBarChangeListener(widthSeekBarChanged);
		widthBar.setProgress(drawView.getLW());
		
		Button setLWButton = (Button) currentDialog.findViewById(R.id.setWidthButton);
		setLWButton.setOnClickListener(setLineWidthButtonListener);
		
		dialogIsVisible.set(true);
		currentDialog.show();
	}
	
	private OnSeekBarChangeListener widthSeekBarChanged = new OnSeekBarChangeListener()
	{
		Bitmap bitmap = Bitmap.createBitmap(400, 100, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			ImageView widthImageView = (ImageView) currentDialog.findViewById(R.id.widthImageView);
			
			Paint p = new Paint();
			p.setColor(drawView.getDrawingColor());
			p.setStrokeCap(Paint.Cap.ROUND);
			p.setStrokeWidth(progress);
			
			bitmap.eraseColor(Color.WHITE);
			canvas.drawLine(30, 50, 370, 50, p);
			
			widthImageView.setImageBitmap(bitmap);
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
		
	};
	
	private OnClickListener setLineWidthButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			SeekBar widthSeekBar = (SeekBar) currentDialog.findViewById(R.id.widthSeekBar);
			
			drawView.setLineWidth(widthSeekBar.getProgress());
			dialogIsVisible.set(false);
			currentDialog.dismiss();
			currentDialog = null;
			}
		};
}