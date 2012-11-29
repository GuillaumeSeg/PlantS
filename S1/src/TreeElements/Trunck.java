package TreeElements;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class Trunck extends TreeElement{
	
	private float m_ScaleX;
	private float m_ScaleY;
	private float[] m_Axe;
	private float m_Degrees;
	
	public Trunck(float height, float[] axe, float degrees){
		m_ScaleX = height;
		m_ScaleY = height;
		m_Degrees = degrees;
		m_Axe[0] = axe[0];
		m_Axe[1] = axe[1];
		m_Axe[2] = axe[2];
	}

	public void draw(GL2 gl){
		/*gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);		
		gl.glPushMatrix();
			gl.glRotatef(m_Degrees, m_Axe[0], m_Axe[1], m_Axe[2]);
			gl.glScalef(m_ScaleX, m_ScaleY, 1);
			drawPrimitiveTrunk(gl);
		gl.glPopMatrix();*/System.out.println("trunck");
	}
	
	private void drawPrimitiveTrunk(GL2 gl){
		gl.glColor3f(255.0f, 255.0f, 255.0f);
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glEnd();
	}	
}
