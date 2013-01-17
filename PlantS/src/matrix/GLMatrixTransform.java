package matrix;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class GLMatrixTransform {

	public static Matrix4f LookAt(Vector3f eye, Vector3f center, Vector3f up) {
		
		Vector3f w = new Vector3f(eye);
		w.sub(center);
		w.normalize();
		
		Vector3f u = new Vector3f();
		u.cross(up, w);
		u.normalize();
		
		Vector3f v = new Vector3f();
		v.cross(w, u);
		
		Matrix4f rot = new Matrix4f(
			u.x, v.x, w.x, 0.0f,
			u.y, v.y, w.y, 0.0f,
			u.z, v.z, w.z, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		);
		rot.transpose();
		
		Matrix4f t = new Matrix4f(
			1.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f,
			-eye.x, -eye.y, -eye.z, 1.0f
		);
		t.transpose();
		
		rot.mul(t);
		return rot;
	}
	
	public static Matrix4f Perspective(float l, float r, float b, float t, float n, float f) {
		Matrix4f P = new Matrix4f(
				2*n/(r-l),		0.0f,			0.0f,			0.0f,
				0,				2*n/(t-b),		0.0f,			0.0f,
				(r+l)/(r-l),	(t+b)/(t-b),	-(f+n)/(f-n),	-1.0f,
				0.0f,			0.0f,			-2*f*n/(f-n),	0.0f
		);
		P.transpose();
		return P;
	}
	
	public static Matrix4f Perspective(float fovy, float aspect, float near, float far) {
		
		float range = (float)Math.tan((fovy/2) * 2 * Math.PI / 360) * near;
		float l = -range * aspect;
		float r = range * aspect;
		float b = -range;
		float t = range;
		
		Matrix4f P = new Matrix4f(
				2*near/(r-l),		0.0f,			0.0f,			0.0f,
				0,				2*near/(t-b),		0.0f,			0.0f,
				(r+l)/(r-l),	(t+b)/(t-b),	-(far+near)/(far-near),	-1.0f,
				0.0f,			0.0f,			-2*far*near/(far-near),	0.0f
		);
		P.transpose();
		return P;
	}
}
