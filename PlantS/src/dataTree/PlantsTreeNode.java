package dataTree;

import javax.media.opengl.GL3;
import javax.vecmath.Matrix4f;

import matrix.MatrixStack;

public abstract class PlantsTreeNode {
	
	public PlantsTreeNode() {
		
	}

	public abstract void render(GL3 gl, MatrixStack stack, int MVPLocation);
	
	public String toString() {
		return ("TREE");
	}
	
}
