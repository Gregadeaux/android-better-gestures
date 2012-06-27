package com.deaux.gestures;

import android.view.View;

public interface PanGestureListener {

	public abstract void onPan(View v, float deltax, float deltaY);
	
	public abstract void onLift(View v, float velocityX, float velocityY);
}
