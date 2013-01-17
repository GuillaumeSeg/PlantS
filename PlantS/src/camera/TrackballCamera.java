package camera;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import matrix.GLMatrixTransform;

public class TrackballCamera {
	
	private float m_fDistance;
	private float m_fAngleX;
	private float m_fAngleY;
	private Vector3f m_fTarget;
	
	public TrackballCamera() {
		m_fDistance = 0.0f;
		m_fAngleX = 0.0f;
		m_fAngleY = 0.0f;
		m_fTarget = new Vector3f(0.0f, 0.0f, 0.0f);
	}
	
	public void setTarget(float x, float y) {
		m_fTarget.x += x;
		m_fTarget.y += y;
	}
	
	public void moveFront(float distance) {
		m_fDistance += -distance;
	}
	
	public void rotateLeft(float degrees) {
		m_fAngleX += degrees;
	}
	
	public void rotateUp(float degrees) {
		m_fAngleY += degrees;
	}

	public float getDistance() {
		return m_fDistance;
	}
	
	public Matrix4f getViewMatrix() {
		
		Matrix4f V = new Matrix4f();
		V = GLMatrixTransform.LookAt(new Vector3f(0.0f, 0.0f, m_fDistance), m_fTarget, new Vector3f(0.0f, 1.0f, 0.0f));
		//System.err.println(m_fTarget.toString());
		//V.rotX(m_fAngleX);
		//V.rotY(m_fAngleY);
		
		return V;
	}

}
