package dataTree;

import java.nio.FloatBuffer;

import javax.media.opengl.GL3;

import matrix.MatrixStack;

import com.jogamp.common.nio.Buffers;

import utils.CST;

public class LeafTreeNode extends PlantsTreeNode {

	FloatBuffer verticesPosition;
	FloatBuffer verticesColor;
	int nbVertices;
	
	public LeafTreeNode() {
		
		float[] array_verticesPosition = new float[] {
				-0.5f, 0.5f, 0.0f,
				0.5f, 0.5f, 0.0f,
				0.5f, -0.5f, 0.0f,
				-0.5f, -0.5f, 0.0f
		};
		
		verticesPosition = Buffers.newDirectFloatBuffer(array_verticesPosition);
		nbVertices = array_verticesPosition.length/3;
		
		float[] array_verticesColor = new float[] {
				0.1f, 0.7f, 0.0f,
				1.5f, 0.8f, 0.1f,
				0.05f, 0.7f, 0.0f,
				0.1f, 0.8f, 0.1f
		};
		
		verticesColor = Buffers.newDirectFloatBuffer(array_verticesColor);
		
	}
	
	@Override
	public void render(GL3 gl, MatrixStack stack, int MVPLocation) {
		
		gl.glEnableVertexAttribArray(CST.SHADER_POSITION_LOCATION);        
        gl.glVertexAttribPointer(CST.SHADER_POSITION_LOCATION, 3, GL3.GL_FLOAT, false, 0, verticesPosition);
        
        gl.glEnableVertexAttribArray(CST.SHADER_COLOR_LOCATION);        
        gl.glVertexAttribPointer(CST.SHADER_COLOR_LOCATION, 3, GL3.GL_FLOAT, false, 0, verticesColor);
        
        gl.glUniformMatrix4fv(MVPLocation, 1, false, stack.parseTopToFloatArray(), 0);
        gl.glDrawArrays(GL3.GL_TRIANGLE_FAN, 0, nbVertices);
        
        gl.glDisableVertexAttribArray(CST.SHADER_POSITION_LOCATION); // Allow release of vertex position memory
        gl.glDisableVertexAttribArray(CST.SHADER_COLOR_LOCATION); // Allow release of vertex color memory
		System.out.print("rend leaf");
		
	}

	public String toString() {
		return ("LEAF");
	}
	
}
