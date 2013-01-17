package drawable;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.media.opengl.GL3;
import javax.vecmath.Vector3f;

import utils.CST;

import com.jogamp.common.nio.Buffers;

public class Repere {
	
	private ArrayList<VertexShape> m_Vertices; // une liste des points composant la forme
	private int m_NBvertices;
	private static int s_DrawingMode = GL3.GL_LINES; // le mode de dessin
	private FloatBuffer m_BufferPosition; // le buffer contenant les points à envoyer à openGL
	private FloatBuffer m_BufferColor; // le buffer contenant les points à envoyer à openGL
	
	public Repere() {
        
        ArrayList<VertexShape> allVertices = new ArrayList<VertexShape>();
        
        // Construit l'ensemble des vertex
        VertexShape v000 = new VertexShape(0f, 0f, 0f);
        VertexShape v100 = new VertexShape(0.2f, 0f, 0f);
        VertexShape v010 = new VertexShape(0f, 0.4f, 0f);
        VertexShape v001 = new VertexShape(0f, 0f, 0.6f);
        
        allVertices.add(v000);
        allVertices.add(v100);
        allVertices.add(v000);
        allVertices.add(v010);
        allVertices.add(v000);
        allVertices.add(v001);

        m_Vertices = new ArrayList<VertexShape>();
        m_NBvertices = 6;      
        
    	for(int i = 0; i < allVertices.size(); ++i) {
    		m_Vertices.add(allVertices.get(i));
        }
        
        m_BufferPosition = Buffers.newDirectFloatBuffer(getFloatArray());
        m_BufferColor = Buffers.newDirectFloatBuffer(getColorFloatArray());
	}
	
	public void draw(GL3 gl) {
		gl.glEnableVertexAttribArray(CST.SHADER_POSITION_LOCATION);        
        gl.glVertexAttribPointer(CST.SHADER_POSITION_LOCATION, 3, GL3.GL_FLOAT, false, 0, m_BufferPosition);
        
        gl.glEnableVertexAttribArray(CST.SHADER_COLOR_LOCATION);        
        gl.glVertexAttribPointer(CST.SHADER_COLOR_LOCATION, 3, GL3.GL_FLOAT, false, 0, m_BufferColor);
        
        gl.glDrawArrays(s_DrawingMode, 0, m_NBvertices);
        
        gl.glDisableVertexAttribArray(CST.SHADER_POSITION_LOCATION); // Allow release of vertex position memory
        gl.glDisableVertexAttribArray(CST.SHADER_COLOR_LOCATION); // Allow release of vertex color memory
	}
	
	public float[] getFloatArray() {
		
		float[] vertices = new float[m_NBvertices * VertexShape.s_NB_TOTAL_COMPONENTS];
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

	public float[] getColorFloatArray() {
		
		float[] vertices = new float[m_NBvertices * VertexShape.s_NB_TOTAL_COMPONENTS];
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
