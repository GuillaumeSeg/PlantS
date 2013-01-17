import javax.media.opengl.GL2;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import TreeElements.NodeTree;

public class JOGLRenderer implements GLEventListener {
	
	private JDOMHierarchy m_TreeHierarchy;
	float angle1 = 0;
	float angle2 = 0;
	
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		
		//float angle = (System.currentTimeMillis()/10)%360;
		float angle1 =+ 1;
		float angle2 =+ 0.1f;
		gl.glRotatef(angle2, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(angle1, 0.0f, 1.0f, 0.0f);
		//gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
		
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glPushMatrix();
			JDOMHierarchy.drawJDOM(m_TreeHierarchy.getRoot(), gl); 
		gl.glPopMatrix();
		
		//System.out.println("GL Error(s) : " + gl.glGetError());
		
	}

	public void dispose(GLAutoDrawable drawable) {

	}

	@Override
	public void init(GLAutoDrawable drawable) {
				
		// setup OpenGL
		GL2 gl = drawable.getGL().getGL2();						// get the OpenGL graphics context
		gl.glLoadIdentity();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 						// set background (clear) color
		gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
		gl.glClearDepthf(1.0f);											// set clear depth value to farthest
		gl.glEnable(GL3.GL_DEPTH_TEST);									// enables depth testing
		gl.glDepthFunc(GL3.GL_LEQUAL);									// the type of depth test to do
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL3.GL_NICEST);	// best perspective correction
		
		m_TreeHierarchy = new JDOMHierarchy("xml_tree");
	}

	public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3, int arg4) {

	}
	
}
