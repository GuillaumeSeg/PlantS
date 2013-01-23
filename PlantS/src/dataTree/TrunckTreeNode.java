package dataTree;

import java.nio.FloatBuffer;

import javax.media.opengl.GL3;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import matrix.GLMatrix;
import matrix.MatrixStack;

import com.jogamp.common.nio.Buffers;

import drawable.Cylinder;

public class TrunckTreeNode extends PlantsTreeNode {

	float length;
	Vector3f axe;
	float radius;
	float alpha;

	int nbVertices;
	Matrix4f MPcp, MPcb;
	
	Cylinder cylinder;
	
	public TrunckTreeNode(float length, Vector3f axe, float rad, Matrix4f PASSAGEchildParent, Matrix4f PASSAGEchildBrother) {
		
		this.length = length;
		this.axe = axe;
		this.radius = rad;
		float normeAxe = axe.length();
		this.alpha = (float) Math.acos(axe.x/normeAxe);
		if(axe.y < 0) {
			this.alpha = -this.alpha;
		}
		MPcp = new Matrix4f(PASSAGEchildParent);
		MPcb = new Matrix4f(PASSAGEchildBrother);
		
		cylinder = new Cylinder(length, radius, 55, 55, alpha, MPcp, MPcb);
		
	}
	
	public TrunckTreeNode() {
		this.length = 0;
		this.axe = new Vector3f(0, 1, 0);
		this.alpha = 1.0f;
		cylinder = new Cylinder(0, 0, 1, 1, 0, new Matrix4f(), new Matrix4f());
	}

	public Matrix4f getRotationMatrix() {
		
		Vector3f v = new Vector3f(axe);
		v.normalize();
		
		Vector3f uPrim = new Vector3f(1.0f, 0.0f, 0.0f);
		if(Math.abs(uPrim.dot(v)) < 0.5) {
			uPrim.x = 0.0f;
			uPrim.y = 0.0f;
			uPrim.z = 1.0f;
		}
		
		Vector3f w = new Vector3f();
		w.cross(uPrim, v);
		w.normalize();
		
		Vector3f u = new Vector3f();
		u.cross(v, w);
		u.normalize();
		
		Matrix4f Mrotate = new Matrix4f(u.x, v.x, w.x, 0.0f, u.y, v.y, w.y, 0.0f, u.z, v.z, w.z, 0.0f , 0.0f, 0.0f, 0.0f, 1f);
		
		return Mrotate;
	}

	public String toString() {
		return ("TRUNCK - L = " + length + " - AXE = " + axe.toString()  + " - angle =" + alpha +"");
	}
	
	public void render(GL3 gl, MatrixStack stack, int MVLocation) {
		
		stack.rotate(getRotationMatrix());
        gl.glUniformMatrix4fv(MVLocation, 1, false, GLMatrix.parseToFloatArray(stack.top()), 0);
        cylinder.draw(gl);
		
	}

	public float getLength() {
		return this.length;
	}
}