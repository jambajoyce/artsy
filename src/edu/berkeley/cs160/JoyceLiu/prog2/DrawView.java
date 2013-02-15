package edu.berkeley.cs160.JoyceLiu.prog2;

import java.util.HashMap;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class DrawView extends View {
	
	private static final float TOUCH_TOLERANCE = 10;
	
	private Bitmap bitmap;
	private Canvas bmCanvas;
	private Paint paintScreen;
	private Paint paintLine;
	private HashMap<Integer, Path> pathMap;
	private HashMap<Integer, Point> previousPointMap;
	
	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		//CHECK THIS
		paintScreen = new Paint();
		
		paintLine = new Paint();
		paintLine.setAntiAlias(true);
		paintLine.setColor(Color.GREEN);
		paintLine.setStyle(Paint.Style.STROKE);
		paintLine.setStrokeWidth(5);
		paintLine.setStrokeCap(Paint.Cap.ROUND);
		pathMap = new HashMap<Integer, Path>();
		previousPointMap = new HashMap<Integer, Point>();
		
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldW, int oldH) {
		bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		bmCanvas = new Canvas(bitmap);
		bitmap.eraseColor(Color.WHITE);
	}
	
	public void clear() {
		pathMap.clear();
		previousPointMap.clear();
		bitmap.eraseColor(Color.WHITE);
		invalidate();
	}
	
	public void setDrawingColor(int color) {
		paintLine.setColor(color);
	}
	
	public int getDrawingColor() {
		return paintLine.getColor();
	}
	
	public void setLineWidth(int w) {
		paintLine.setStrokeWidth(w);
	}
	
	public int getLW() {
		return (int) paintLine.getStrokeWidth();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(bitmap, 0, 0, paintScreen);
		
		for (Integer key : pathMap.keySet()) {
			canvas.drawPath(pathMap.get(key), paintLine);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		int action = e.getActionMasked();
		int actionIndex = e.getActionIndex();
		
		if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
			touchStarted(e.getX(actionIndex), e.getY(actionIndex), e.getPointerId(actionIndex));
			
		} else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
			touchEnded(e.getPointerId(actionIndex));
		} else {
			touchMoved(e);
		}
		
		invalidate();
		return true;
	}
	
	private void touchStarted(float x, float y, int lineID) {
		Path path;
		Point point;
		
		if (pathMap.containsKey(lineID)) {
			path = pathMap.get(lineID);
			path.reset();
			point = previousPointMap.get(lineID);
		} else {
			path = new Path();
			pathMap.put(lineID, path);
			point = new Point();
			previousPointMap.put(lineID, point);
			
			path.moveTo(x, y);
			point.x = (int) x;
			point.y = (int) y;
			
		}
	}
	
	private void touchMoved(MotionEvent e) {
		for (int i = 0; i < e.getPointerCount(); i++) {
			
			int pointerID = e.getPointerId(i);
			int pointerIndex = e.findPointerIndex(pointerID);
			
			if (pathMap.containsKey(pointerID)) {
				float newX = e.getX(pointerIndex);
				float newY = e.getY(pointerIndex);
				
				Path path = pathMap.get(pointerID);
				Point point = previousPointMap.get(pointerID);
				
				float dX = Math.abs(newX - point.x);
				float dY = Math.abs(newY - point.y);
				
				if (dX >= TOUCH_TOLERANCE || dY >= TOUCH_TOLERANCE) {
					path.quadTo(point.x, point.y, ((newX + point.x) / 2), ((newY + point.y) / 2));
					
					point.x = (int) newX;
					point.y = (int) newY;
				}
			}
		}
	}
	
	private void touchEnded(int lineID) {
		Path path = pathMap.get(lineID);
		bmCanvas.drawPath(path, paintLine);
		path.reset();
	}
}