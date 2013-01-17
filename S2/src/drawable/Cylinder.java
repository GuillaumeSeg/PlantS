package drawable;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.media.opengl.GL3;

import com.jogamp.common.nio.Buffers;

import utils.CST;

//Représente un cylindre ouvert discrétisé dont la base est centrée en (0, 0, 0) (dans son repère local)
//Son axe vertical est (0, 1, 0) et ses axes transversaux sont (1, 0, 0) et (0, 0, 1)
public class Cylinder {

	private ArrayList<VertexShape> m_Vertices; // une liste des points composant la forme
	private int m_NBvertices;
	private static int s_DrawingMode = GL3.GL_POINTS; // le mode de dessin
	private FloatBuffer m_BufferPosition; // le buffer contenant les points à envoyer à openGL
	private FloatBuffer m_BufferColor; // le buffer contenant les points à envoyer à openGL

	public Cylinder(float height, float radius, int discLat, int discHeight) {
		
		// Equation paramétrique en (r, phi, h) du cylindre
        // avec r >= 0, -PI / 2 <= theta <= PI / 2, 0 <= h <= height
        //
        // x(r, phi, h) = r sin(phi)
        // y(r, phi, h) = h
        // z(r, phi, h) = r cos(phi)
        //
        // Discrétisation:
        // dPhi = 2PI / discLat, dH = height / discHeight
        //
        // x(r, i, j) = r * sin(i * dPhi)
        // y(r, i, j) = j * dH
        // z(r, i, j) = r * cos(i * dPhi)
		
		float rcpLat = 1.f / discLat, rcpH = 1.f / discHeight;
        float dPhi = 2 * (float)Math.PI * rcpLat, dH = height * rcpH;
        
        ArrayList<VertexShape> allVertices = new ArrayList<VertexShape>();
        
        // Construit l'ensemble des vertex
        for(int j = 0; j <= discHeight; ++j) {
        	for(int i = 0; i < discLat; ++i) {
        		
                VertexShape v = new VertexShape();
                
                v.texCoord.x = i * rcpLat;
                v.texCoord.y = j * rcpH;
                
                v.normal.x = (float)Math.sin(i * dPhi);
                v.normal.y = 0;
                v.normal.z = (float)Math.cos(i * dPhi);
                
                
                v.position.x = radius * (float)Math.sin(i*dPhi);
                v.position.y = j * dH;
                v.position.z = radius * (float)Math.cos(i * dPhi);
                
                allVertices.add(v);
            }
        }
        
        m_Vertices = new ArrayList<VertexShape>();
        m_NBvertices = discLat * discHeight * 6; // *6 car 2 triangles
        
        // Construit les vertex finaux en regroupant les données en triangles:
        // Pour une longitude donnée, les deux triangles formant une face sont de la forme:
        // (i, i + 1, i + discLat + 1), (i, i + discLat + 1, i + discLat)        
        
        for(int j = 0; j < discHeight; ++j) {
        	int offset = j * discLat;
        	for(int i = 0; i < discLat; ++i) {
        		m_Vertices.add(new VertexShape(allVertices.get(offset + i)));
        		m_Vertices.add(new VertexShape(allVertices.get(offset + (i + 1)%discLat)));
        		m_Vertices.add(new VertexShape(allVertices.get(offset + discLat + (i + 1)%discLat)));
        		m_Vertices.add(new VertexShape(allVertices.get(offset + i)));
        		m_Vertices.add(new VertexShape(allVertices.get(offset + discLat + (i + 1)%discLat)));
        		m_Vertices.add(new VertexShape(allVertices.get(offset + i + discLat)));
            }
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
			vertices[i] = 1.0f;
			++i;
			vertices[i] = 1.0f;
			++i;
			vertices[i] = 1.0f;
			++i;
		}
		
		return vertices;
	}


}