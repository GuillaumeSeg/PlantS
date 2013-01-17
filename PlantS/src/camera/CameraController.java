package camera;

import javax.vecmath.Vector2f;

public class CameraController {
	Vector2f currentPos;
	Vector2f nextPos;
	
	public CameraController() {
		currentPos = new Vector2f();
		nextPos = new Vector2f();
	}
	
	public boolean isMoovingLeft() {
		if(nextPos.x - currentPos.x > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isMoovingTop() {
		if(nextPos.y - currentPos.y < 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setCurrent(float x, float y) {
		currentPos.x = x;
		currentPos.y = y;
	}
	
	public void setCurrentX(float x) {
		currentPos.x = x;
	}
	
	public void setCurrentY(float y) {
		currentPos.y = y;
	}
	
	public void setNext(float x, float y) {
		nextPos.x = x;
		nextPos.y = y;
	}
	
	public void setNextX(float x) {
		nextPos.x = x;
	}
	
	public void setNextY(float y) {
		nextPos.y = y;
	}
	
	public Vector2f getCurrent() {
		return currentPos;
	}
	
	public Vector2f getNext() {
		return nextPos;
	}
	
}
