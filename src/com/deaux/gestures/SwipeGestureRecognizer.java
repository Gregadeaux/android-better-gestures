/*
 * Copyright 2012 Greg Billetdeaux
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.deaux.gestures;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;

public class SwipeGestureRecognizer implements OnTouchListener {

	private View view;
	private float pressY;
	private float pressX;
	private float lastY;
	private float lastX;
	private VelocityTracker tracker;
	private SwipeGestureListener listener;
	
	public SwipeGestureRecognizer(View v, SwipeGestureListener listener) {
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
			lastY = event.getRawY();
			lastX = event.getRawX();
			
			if(Math.abs(pressX - lastX) > 100 && Math.abs(pressX - lastX) > Math.abs(pressY - lastY)) {
				tracker.computeCurrentVelocity(1000);
				listener.onSwipe(v, tracker.getXVelocity());
			}
			
			tracker.recycle();
			break;
		case MotionEvent.ACTION_MOVE:
			tracker.addMovement(event);
			break;
		}
		return false;
	}

}
