package openGL3;
import java.util.Stack;

import javax.vecmath.Matrix4f;

public class testMatrix {

	public static void main(String[] args){
		Stack<Matrix4f> stack = new Stack<Matrix4f>();
		stack.push(new Matrix4f());
		System.out.println(stack.peek().toString());
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		stack.push(m);
		System.out.println(stack.peek().toString());
		System.out.println(stack.peek().toString());
	}
	
}
