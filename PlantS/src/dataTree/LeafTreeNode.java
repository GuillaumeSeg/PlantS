package dataTree;

import java.nio.FloatBuffer;

import javax.media.opengl.GL3;

import matrix.GLMatrix;
import matrix.MatrixStack;

import com.jogamp.common.nio.Buffers;

import utils.CST;

public class LeafTreeNode extends PlantsTreeNode {

	int nbVertices;
	
	public LeafTreeNode() {
		
	}
	
	@Override
	public void render(GL3 gl, MatrixStack stack, int MVLocation) {
		System.out.print("rend leaf");
	}

	public String toString() {
		return ("LEAF");
	}
	
}
