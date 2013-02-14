package edu.berkeley.cs160.JoyceLiu.prog2;

import java.io.IOException;
import java.io.OutputStream;
import java.io.HashMap;
import android.view.View;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;


public class DrawView extends View {
	
	private static final float TOUCH_TOLERANCE = 10;
	
	private Bitmap bitmap;
	private Canvas bmCanvas;
	private Paint paintScreen;
	private Paint paintLine;
	private HashMap<Integer, Path> pathMap;
	private HashMap<Integer, Point> previousPointMap;
	
}