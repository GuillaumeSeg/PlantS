package TreeElements;

import javax.media.opengl.GL2;

public class Leaf extends TreeElement{
	
	private float m_ScaleX;
	private float m_ScaleY;
	
	public Leaf(float size){
		m_ScaleX = size;
		m_ScaleY = size;
	}
	
	public void draw(GL2 gl) {
		/*gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glPushMatrix();
			gl.glScalef(m_ScaleX, m_ScaleY, 1);
			drawPrimitiveLeaf(gl);
		gl.glPopMatrix();*/System.out.println("leaf");
	}
	
	private void drawPrimitiveLeaf(GL2 gl){
		gl.glColor3f(0.0f, 255.0f, 0.0f);
		gl.glBegin(GL2.GL_QUADS);
			gl.glVertex3f(0.0f, 0.5f, 0.0f);
			gl.glVertex3f(0.5f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, -0.5f, 0.0f);
			gl.glVertex3f(-0.5f, 0.0f, 0.0f);
		gl.glEnd();
	}


	
	
}
