package drawable;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.media.opengl.GL3;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector4f;

import matrix.GLMatrixTransform;

import com.jogamp.common.nio.Buffers;

import utils.CST;

//Représente un cylindre ouvert discrétisé dont la base est centrée en (0, 0, 0) (dans son repère local)
//Son axe vertical est (0, 1, 0) et ses axes transversaux sont (1, 0, 0) et (0, 0, 1)
public class Cylinder {

	private ArrayList<VertexShape> m_Vertices; // une liste des points composant la forme
	private int m_NBvertices;
	private static int s_DrawingMode = GL3.GL_POINTS; // le mode de dessin
	private FloatBuffer m_BufferPositionC;
	private FloatBuffer m_BufferPositionP;
	private FloatBuffer m_BufferPositionB;
	
	
	public Cylinder(float height, float radius, int discLat, int discHeight, float iangle, Matrix4f MPcp, Matrix4f MPcb) {
		
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
                v.position.w = 1 - 4*(float)j/discHeight;
                
                allVertices.add(v);
                
            }
        }
        
        float dy = height/discHeight;
        
        for(int i = 0; i < discHeight/4; ++i) {
        	
        	float weight = (1-(float)4*i/discHeight);
        	float angle = (float)Math.PI/4;
        	float ponderateAngle = weight*angle;
        	
    		for(int j = 0; j < discLat/2; ++j) {
        		float tmpx = allVertices.get(j + discLat*i).position.x;
        		float tmpy = allVertices.get(j + discLat*i).position.y;
        		
        		tmpy = tmpy - i*dy;
        		
        		float newx = (tmpx * (float)Math.cos(ponderateAngle) - tmpy * (float)Math.sin(ponderateAngle));
        		float newy = (tmpx * (float)Math.sin(ponderateAngle) + tmpy * (float)Math.cos(ponderateAngle));
        		float newz = allVertices.get(j  + discLat*i).position.z;
        		
        		newy = newy + i*dy;
        		
        		allVertices.set(j  + discLat*i, new VertexShape(newx, newy, newz));
    		}
    		
    		ponderateAngle =  (float) (weight*(angle - (float)Math.PI/2));
    		
    		for(int j = discLat/2; j < discLat; ++j) {

    			float tmpx = allVertices.get(j + discLat*i).position.x;
        		float tmpy = allVertices.get(j + discLat*i).position.y;
        		
        		tmpy = tmpy - i*dy;
        		
        		float newx = (tmpx * (float)Math.cos(ponderateAngle) - tmpy * (float)Math.sin(ponderateAngle));
        		float newy = (tmpx * (float)Math.sin(ponderateAngle) + tmpy * (float)Math.cos(ponderateAngle));
        		float newz = allVertices.get(j  + discLat*i).position.z;
        		
        		newy = newy + i*dy;
        		
        		allVertices.set(j  + discLat*i, new VertexShape(newx, newy, newz));
        	}
        }
        
        int k = 0;
        
        for(VertexShape v : allVertices) {
        	
        	int j = k/discHeight;
        	
        	// Calcul des positions dans les repères parents et frères
            Vector4f coordsRP = new Vector4f(GLMatrixTransform.multMat4Vec4(MPcp, new Vector4f(v.position.x, v.position.y, v.position.z, 1)));
            v.positionRP.x = coordsRP.x/coordsRP.w;
            v.positionRP.y = coordsRP.y/coordsRP.w;
            v.positionRP.z = coordsRP.z/coordsRP.w;
            v.positionRP.w = 1;
            
            Vector4f coordsRB = new Vector4f(GLMatrixTransform.multMat4Vec4(MPcb, new Vector4f(v.position.x, v.position.y, v.position.z, 1)));
            v.positionRB.x = coordsRB.x/coordsRB.w;
            v.positionRB.y = coordsRB.y/coordsRB.w;
            v.positionRB.z = coordsRB.z/coordsRB.w;
            v.positionRB.w = 1;
            
            k++;
            
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
        
        m_BufferPositionC = Buffers.newDirectFloatBuffer(getPositionCFloatArray());
        m_BufferPositionP = Buffers.newDirectFloatBuffer(getPositionPFloatArray());
        m_BufferPositionB = Buffers.newDirectFloatBuffer(getPositionBFloatArray());
        
	}
	
	public void draw(GL3 gl) {
		
		gl.glEnableVertexAttribArray(CST.SHADER_POSITIONC_LOCATION);        
        gl.glVertexAttribPointer(CST.SHADER_POSITIONC_LOCATION, VertexShape.s_NB_COMPONENTS_POSITION, GL3.GL_FLOAT, false, 0, m_BufferPositionC);
        
        gl.glEnableVertexAttribArray(CST.SHADER_POSITIONP_LOCATION);        
        gl.glVertexAttribPointer(CST.SHADER_POSITIONP_LOCATION, VertexShape.s_NB_COMPONENTS_POSITION, GL3.GL_FLOAT, false, 0, m_BufferPositionP);
        
        gl.glEnableVertexAttribArray(CST.SHADER_POSITIONB_LOCATION);        
        gl.glVertexAttribPointer(CST.SHADER_POSITIONB_LOCATION, VertexShape.s_NB_COMPONENTS_POSITION, GL3.GL_FLOAT, false, 0, m_BufferPositionB);
        
        gl.glDrawArrays(s_DrawingMode, 0, m_NBvertices);
        
        gl.glDisableVertexAttribArray(CST.SHADER_POSITIONC_LOCATION);
        gl.glDisableVertexAttribArray(CST.SHADER_POSITIONP_LOCATION);
        gl.glDisableVertexAttribArray(CST.SHADER_POSITIONB_LOCATION);
	}
	
	public float[] getPositionCFloatArray() {
		
		float[] vertices = new float[m_NBvertices * VertexShape.s_NB_COMPONENTS_POSITION];
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
	
	public float[] getPositionPFloatArray() {
		
		float[] vertices = new float[m_NBvertices * VertexShape.s_NB_COMPONENTS_POSITION];
		int i = 0;
		for(VertexShape v : m_Vertices) {
			vertices[i] = v.positionRP.x;
			++i;
			vertices[i] = v.positionRP.y;
			++i;
			vertices[i] = v.positionRP.z;
			++i;
		}
		
		return vertices;
	}
	
	public float[] getPositionBFloatArray() {
		
		float[] vertices = new float[m_NBvertices * VertexShape.s_NB_COMPONENTS_POSITION];
		int i = 0;
		for(VertexShape v : m_Vertices) {
			vertices[i] = v.positionRB.x;
			++i;
			vertices[i] = v.positionRB.y;
			++i;
			vertices[i] = v.positionRB.z;
			++i;
		}
		
		return vertices;
	}

}
