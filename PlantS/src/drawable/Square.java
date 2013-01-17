package drawable;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.media.opengl.GL3;

import com.jogamp.common.nio.Buffers;

import utils.CST;

public class Square {

	private ArrayList<VertexShape> m_Vertices; // une liste des points composant la forme
	private static int s_NBvertices = 4; // le nombre de points en tout
	private static int s_DrawingMode = GL3.GL_TRIANGLE_FAN; // le mode de dessin
	private FloatBuffer m_Buffer; // le buffer contenant les points à envoyer à openGL
	
	public Square() {
		m_Vertices = new ArrayList<VertexShape>();
		m_Vertices.add(new VertexShape(-0.5f, 0.5f, 0.0f));
		m_Vertices.add(new VertexShape(0.5f, 0.5f, 0.0f));
		m_Vertices.add(new VertexShape(0.5f, -0.5f, 0.0f));
		m_Vertices.add(new VertexShape(-0.5f, -0.5f, 0.0f));
		m_Buffer = Buffers.newDirectFloatBuffer(getFloatArray());
	}
	
	public void draw(GL3 gl) {
		gl.glEnableVertexAttribArray(CST.SHADER_POSITION_LOCATION);        
        gl.glVertexAttribPointer(CST.SHADER_POSITION_LOCATION, 3, GL3.GL_FLOAT, false, 0, m_Buffer);
        gl.glDrawArrays(s_DrawingMode, 0, s_NBvertices);
	}
	
	// Fonction qui parse la liste de vertex en tableau de floats et qui renvoie ce tableau
	public float[] getFloatArray() {
		float[] vertices = new float[s_NBvertices * VertexShape.s_NB_TOTAL_COMPONENTS];
		int i = 0;
		for(VertexShape v : m_Vertices) {
			vertices[i] = v.position.x;
			++i;
			vertices[i] = v.position.y;
			++i;
			vertices[i] = v.position.z;
			++i;
		}
		
		return vertices;
	}
	
}
