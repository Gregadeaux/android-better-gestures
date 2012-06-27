package com.deaux.gestures;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class PinchGestureRecognizer implements OnTouchListener {

	private View view;
	private PinchGestureListener listener;
	
	private float pressX1;
	private float pressY1;
	private float pressX2;
	private float pressY2;
	private float refDistance;
	private float lastDistance;
	
	public PinchGestureRecognizer(View v, PinchGestureListener listener) {
		this.view = v;
		this.listener = listener;
		this.view.setOnTouchListener(this);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getPointerCount() > 1) {
			int action = event.getAction() & MotionEvent.ACTION_MASK;
			switch(action) {
			case MotionEvent.ACTION_DOWN:
				pressX1 = event.getX(0);
				pressY1 = event.getY(0);
				
				pressX2 = event.getX(1);
				pressY2 = event.getY(1);
				
				refDistance = (float) Math.sqrt(Math.pow(pressX2 - pressX1, 2) + Math.pow(pressY2 - pressY1, 2));
				break;
			case MotionEvent.ACTION_MOVE:
				pressX1 = event.getX(0);
				pressY1 = event.getY(0);
				
				pressX2 = event.getX(1);
				pressY2 = event.getY(1);
				
				lastDistance = (float) Math.sqrt(Math.pow(pressX2 - pressX1, 2) + Math.pow(pressY2 - pressY1, 2));
				listener.onPinch(v, lastDistance/refDistance);
			}
		}
		return false;
	}

}
