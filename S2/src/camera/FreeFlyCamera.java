package camera;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

public class FreeFlyCamera {
	
	Vector3f m_Position;
	float m_fPhi;
	float m_fTheta;
	Vector3f m_FrontVector;
	Vector3f m_LeftVector;
	Vector3f m_UpVector;
	Matrix4f m_MVP;

	public FreeFlyCamera() {
		
		m_Position = new Vector3f(0, 0, 0);
		m_fPhi = (float) Math.PI;
		m_fTheta = 0;
		
		m_MVP = new Matrix4f();
		m_FrontVector = new Vector3f();
		m_UpVector = new Vector3f();
		m_LeftVector = new Vector3f();
		
		computeDirectionVectors();
	}
	
	private void computeDirectionVectors() {
		
		float cosTheta = (float)Math.cos(m_fTheta);
		
		m_FrontVector.x = cosTheta*(float)Math.sin(m_fPhi);
		m_FrontVector.y = (float)Math.sin(m_fTheta);
		m_FrontVector.z = cosTheta*(float)Math.cos(m_fPhi);
	
		m_LeftVector.x = (float)Math.sin(m_fPhi + Math.PI/2);
		m_LeftVector.y = 0;
		m_LeftVector.z = (float)Math.cos(m_fPhi + Math.PI/2);
		
		m_UpVector.cross(m_FrontVector, m_LeftVector);
		
	}
	
	public void moveLeft(float t) {
		m_Position.x += t*m_LeftVector.x;
		m_Position.y += t*m_LeftVector.y;
		m_Position.z += t*m_LeftVector.z;
	}
	
	public void moveFront(float t) {
		m_Position.x += t*m_FrontVector.x;
		m_Position.y += t*m_FrontVector.y;
		m_Position.z += t*m_FrontVector.z;
	}
	
	public void rotateLeft(float degrees) {
		m_fPhi += degrees/360 * 2 * Math.PI;
		computeDirectionVectors();
	}
	
	public void rotateUp(float degrees) {
		m_fTheta += degrees/360 * 2 * Math.PI;
		computeDirectionVectors();
	}
	
	public Matrix4f getViewMatrix() {
		
		Matrix4f T = new Matrix4f(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, -m_Position.x, -m_Position.y, -m_Position.z, 1f);
		Vector3f v = new Vector3f(m_FrontVector.x - m_Position.x, m_FrontVector.y - m_Position.y, m_FrontVector.z - m_Position.z);
		v.normalize();
		Vector3f w = new Vector3f();
		m_UpVector.normalize();
		w.cross(v, m_UpVector);
		Vector3f u2 = new Vector3f();
		u2.cross(w, v);
		
		Matrix4f Mref = new Matrix4f(w.x, w.y, w.z, 0f, u2.x, u2.y, u2.z, 0f, -v.x, -v.y, -v.z, 0f, 0f, 0f, 0f, 1f);
		T.mul(Mref);
		return T;
	}
	
}