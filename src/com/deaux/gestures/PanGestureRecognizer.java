package com.deaux.gestures;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;

public class PanGestureRecognizer implements OnTouchListener {

	private View view;
	private PanGestureListener listener;
	private VelocityTracker tracker;
	private float pressY;
	private float pressX;
	private float lastY;
	private float lastX;
	
	public PanGestureRecognizer(View v, PanGestureListener listener) {
		this.view = v;
		this.listener = listener;
		this.view.setOnTouchListener(this);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		switch(action) {
		case MotionEvent.ACTION_DOWN:
			tracker = VelocityTracker.obtain();
			tracker.addMovement(event);
			pressY = event.getRawY();
			pressX = event.getRawX();
			break;
		case MotionEvent.ACTION_UP:
			tracker.computeCurrentVelocity(1000);
			listener.onLift(v, tracker.getXVelocity(), tracker.getYVelocity());
			tracker.recycle();
			break;
		case MotionEvent.ACTION_MOVE:
			tracker.addMovement(event);
			lastY = event.getRawY();
			lastX = event.getRawX();

			listener.onPan(v, lastY-pressY, lastX-pressX);
			break;
		}
		return false;
	}
	
	public void resetReference() {
		pressY = lastY;
		pressX = lastX;
	}

}
