import java.util.Stack;

import javax.vecmath.Matrix4d;

public class MatrixStack {

	public static void main(String[] args) {
		
		Stack pileMatrix = new Stack();
		Matrix4d matrix = new Matrix4d();
		pileMatrix.push(matrix);
		System.out.println(pileMatrix.peek());

	}

}
