package openGL3;

import java.io.File;
import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.List;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.vecmath.Vector3f;

import org.jdom2.Element;

import utils.CST;

import com.jogamp.common.nio.Buffers;

public class JOGLRenderer implements GLEventListener {
	
	ShaderProgram m_ShaderProgram;
	JDOMHierarchy m_TreeHierarchy;
	
	FloatBuffer m_VerticesRepere;
	int m_NbRepereVertices;
	
	FloatBuffer m_VerticesTrunkPosition;
	FloatBuffer m_VerticesTrunkColor;
	int m_NbTrunkVertices;

	FloatBuffer m_VerticesLeafPosition;
	FloatBuffer m_VerticesLeafColor;
	int m_NbLeafVertices;
	
	MatrixStack m_MatrixStack;
	int m_MVPLocation;
	
	float angle;
	
	public void display(GLAutoDrawable drawable) {
		
		GL3 gl = drawable.getGL().getGL3();
		gl.glClearColor(0.f, 0.f, 0.f, 1.0f);
        gl.glClear(GL3.GL_STENCIL_BUFFER_BIT | GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT );
        gl.glUseProgram(m_ShaderProgram.getProgram());
        
        m_MatrixStack.push();
        	m_MatrixStack.rotate(new Vector3f(0f, 1f, 0f), angle);
        	drawJDOM(m_TreeHierarchy.getRoot(), gl);
        m_MatrixStack.pop();
        
        angle++;
        
	}

	public void dispose(GLAutoDrawable drawable) {

	}

	public void init(GLAutoDrawable drawable) {
		
		angle = 0;
		
		GL3 gl = drawable.getGL().getGL3();
		m_ShaderProgram = new ShaderProgram(drawable, new File("src/shaders/color.vs.glsl"), new File("src/shaders/color.fs.glsl"));
		
		m_TreeHierarchy = new JDOMHierarchy(new File("src/xml/xml_tree.xml"));
		
		float[] verticesRepere = new float[] { 
        		0f, 0f, 0f,
        		1f, 0f, 0f,
        		0f, 0f, 0f,
        		0f, 1f, 0f,
        		0f, 0f, 0f,
        		0f, 0f, 1f
         };		
		m_VerticesRepere = Buffers.newDirectFloatBuffer(verticesRepere);
		m_NbRepereVertices = verticesRepere.length/3;
		
		float[] verticesTrunkPosition = new float[] {
				0.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f
		};
		m_VerticesTrunkPosition = Buffers.newDirectFloatBuffer(verticesTrunkPosition);
		m_NbTrunkVertices = verticesTrunkPosition.length/3;
		
		float[] verticesTrunkColor = new float[] {
				0.4f, 0.2f, 0.0f,
				0.4f, 0.2f, 0.0f
		};
		m_VerticesTrunkColor = Buffers.newDirectFloatBuffer(verticesTrunkColor);
		
		float[] verticesLeafPosition = new float[] {
				-0.5f, 0.5f, 0.0f,
				0.5f, 0.5f, 0.0f,
				0.5f, -0.5f, 0.0f,
				-0.5f, -0.5f, 0.0f
		};
		m_VerticesLeafPosition = Buffers.newDirectFloatBuffer(verticesLeafPosition);
		m_NbLeafVertices = verticesLeafPosition.length/3;
		
		float[] verticesLeafColor = new float[] {
				0.1f, 0.7f, 0.0f,
				1.5f, 0.8f, 0.1f,
				0.05f, 0.7f, 0.0f,
				0.1f, 0.8f, 0.1f
		};
		m_VerticesLeafColor = Buffers.newDirectFloatBuffer(verticesLeafColor);
		
		m_MatrixStack = new MatrixStack();
		m_MVPLocation = gl.glGetUniformLocation(m_ShaderProgram.getProgram(), "uniform_MVP");
	}

	public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3, int arg4) {

	}

	public void drawRepere(GL3 gl){
		
		gl.glEnableVertexAttribArray(CST.SHADER_POSITION_LOCATION);        
        gl.glVertexAttribPointer(CST.SHADER_POSITION_LOCATION, 3, GL3.GL_FLOAT, false, 0, m_VerticesRepere);
        
        gl.glEnableVertexAttribArray(CST.SHADER_COLOR_LOCATION);        
        gl.glVertexAttribPointer(CST.SHADER_COLOR_LOCATION, 3, GL3.GL_FLOAT, false, 0, m_VerticesRepere);
        
        gl.glUniformMatrix4fv(m_MVPLocation, 1, false, m_MatrixStack.parseTopToFloatArray(), 0);
        gl.glDrawArrays(GL3.GL_LINES, 0, m_NbRepereVertices);
	}
	
	public void drawJDOM(Element element, GL3 gl){
		
		if(element.getName() == "trunck"){
			
			m_MatrixStack.push();
				
				float height = Float.parseFloat(element.getAttributeValue("height"));
				float angle = Float.parseFloat(element.getAttributeValue("angle"));
				String axeXYZ = element.getAttributeValue("axe");
				String vect[] = axeXYZ.split(" ");
				float x = Float.parseFloat(vect[0]);
				float y = Float.parseFloat(vect[1]);
				float z = Float.parseFloat(vect[2]);
				
				m_MatrixStack.rotate(new Vector3f(x, y, z), angle);
				
				draw(element, gl);
				
				m_MatrixStack.translate(new Vector3f(0f, height, 0f));
					
				if(!element.getChildren().isEmpty()){
					List<Element> childrenList = element.getChildren();
					Iterator itChildren = childrenList.iterator();
					while(itChildren.hasNext()){
						Element child = (Element)itChildren.next();
						drawJDOM(child, gl);
					}
					
				}
			m_MatrixStack.pop();
			
		}else{
			
			draw(element, gl);
			
			if(!element.getChildren().isEmpty()){
				List childrenList = element.getChildren();
				Iterator itChildren = childrenList.iterator();
				while(itChildren.hasNext()){
					Element child = (Element)itChildren.next();
					drawJDOM(child, gl);
				}
			}
		}
		
	}

	public void draw(Element element, GL3 gl){

		if(element.getName() == "trunck"){
			
			// read element attributes
			float height = Float.parseFloat(element.getAttributeValue("height"));
			
			m_MatrixStack.push();
				m_MatrixStack.scale(new Vector3f(1f, height, 1f));
				drawPrimitiveTrunk(gl);
			m_MatrixStack.pop();
		}
		
		if(element.getName() == "leaf"){
			
			// read element attributes
			float size = Float.parseFloat(element.getAttributeValue("size"));
			
			m_MatrixStack.push();
				m_MatrixStack.translate(new Vector3f(0, size/2, 0));
				m_MatrixStack.rotate(new Vector3f(0f, 0f, 1f), 45);
				m_MatrixStack.scale(new Vector3f(size, size, 1));
				drawPrimitiveLeaf(gl);
			m_MatrixStack.pop();
		}
	}
	
	
	private void drawPrimitiveTrunk(GL3 gl){
		
		gl.glEnableVertexAttribArray(CST.SHADER_POSITION_LOCATION);        
        gl.glVertexAttribPointer(CST.SHADER_POSITION_LOCATION, 3, GL3.GL_FLOAT, false, 0, m_VerticesTrunkPosition);
        
        gl.glEnableVertexAttribArray(CST.SHADER_COLOR_LOCATION);        
        gl.glVertexAttribPointer(CST.SHADER_COLOR_LOCATION, 3, GL3.GL_FLOAT, false, 0, m_VerticesTrunkColor);
        
        gl.glUniformMatrix4fv(m_MVPLocation, 1, false, m_MatrixStack.parseTopToFloatArray(), 0);
        
        gl.glDrawArrays(GL3.GL_LINES, 0, m_NbTrunkVertices);
	}
	
	private void drawPrimitiveLeaf(GL3 gl){
		
		gl.glEnableVertexAttribArray(CST.SHADER_POSITION_LOCATION);        
        gl.glVertexAttribPointer(CST.SHADER_POSITION_LOCATION, 3, GL3.GL_FLOAT, false, 0, m_VerticesLeafPosition);
        
        gl.glEnableVertexAttribArray(CST.SHADER_COLOR_LOCATION);        
        gl.glVertexAttribPointer(CST.SHADER_COLOR_LOCATION, 3, GL3.GL_FLOAT, false, 0, m_VerticesLeafColor);
        
        gl.glUniformMatrix4fv(m_MVPLocation, 1, false, m_MatrixStack.parseTopToFloatArray(), 0);
        
        gl.glDrawArrays(GL3.GL_TRIANGLE_FAN, 0, m_NbLeafVertices);
	}
	
	
	
}
