package dataTree;

import java.nio.FloatBuffer;

import javax.media.opengl.GL3;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import matrix.MatrixStack;

import com.jogamp.common.nio.Buffers;

import drawable.Cylinder;

import utils.CST;

public class TrunckTreeNode extends PlantsTreeNode {

	float length;
	float[] axe;
	float ldiamb, sdiamb, ldiamt, sdiamt; 
	float[] texCoord;
	
	FloatBuffer verticesPosition;
	FloatBuffer verticesColor;
	int nbVertices;
	
	public TrunckTreeNode(float length, float[] axe) {
		
		this.length = length;
		this.axe = new float[3];
		this.axe[0] = axe[0];
		this.axe[1] = axe[1];
		this.axe[2] = axe[2];
		
		float[] array_verticesPosition = new float[] {
				0.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f
		};
		
		verticesPosition = Buffers.newDirectFloatBuffer(array_verticesPosition);
		nbVertices = array_verticesPosition.length/3;
		
		float[] array_verticesColor = new float[] {
				0.4f, 0.2f, 0.0f,
				0.4f, 0.2f, 0.0f
		};
		
		verticesColor = Buffers.newDirectFloatBuffer(array_verticesColor);
		
	}
	
	public TrunckTreeNode() {
		this.length = 0;
		this.axe = new float[3];
		this.axe[0] = 0;
		this.axe[1] = 1;
		this.axe[2] = 0;
	}

	public Matrix4f getRotationMatrix() {
		
		// changement de référentiel
		Vector3f v = new Vector3f(axe[0], axe[1], axe[2]);
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
		return ("TRUNCK - L = " + length + " - AXE = (" + axe[0] + ", " + axe[1] + ", " + axe[2] + ")");
	}
	
	public void render(GL3 gl, MatrixStack stack, int MVPLocation) {
		
		stack.rotate(getRotationMatrix());
		
		Cylinder cylinder = new Cylinder(length, length*0.1f, 50, 50);
        gl.glUniformMatrix4fv(MVPLocation, 1, false, stack.parseTopToFloatArray(), 0);
        cylinder.draw(gl);
		
	}

	public float getLength() {
		return this.length;
	}
}
