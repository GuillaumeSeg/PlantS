package matrix;

import javax.vecmath.Matrix4f;

public class GLMatrix {
	
	private GLMatrix(){}
	
	public static float[] parseToFloatArray(Matrix4f mat) {
		int k = 0;
		float[] matrix = new float[16];
		for(int col=0; col<4; ++col) {
			for(int row=0; row<4; ++row) {
				matrix[k] = mat.getElement(row, col);
				++k;
			}
		}
		return matrix;
	}
}
