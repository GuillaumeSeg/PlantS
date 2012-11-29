package openGL3;

import java.io.File;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES2;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import utils.CST;

import com.jogamp.common.nio.Buffers;
import java.lang.Math;

public class JOGLRenderer implements GLEventListener {
	
	ShaderProgram m_ShaderProgram;
	JDOMHierarchy m_TreeHierarchy;
	
	int m_NbVertices;
	FloatBuffer m_VerticesPosition;
	FloatBuffer m_VerticesColor;
	FloatBuffer m_VerticesRepere;
	
	MatrixStack m_MatrixStack;
	int m_MVPLocation;
	
	float angle;
	
	public void display(GLAutoDrawable drawable) {
		
		GL3 gl = drawable.getGL().getGL3();
		
		gl.glClearColor(0.f, 0.f, 0.f, 1.0f);
        gl.glClear(GL3.GL_STENCIL_BUFFER_BIT | GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT );
        
        gl.glUseProgram(m_ShaderProgram.getProgram());
        
        // draw repere
        
        m_MatrixStack.push();
	    	m_MatrixStack.rotate(new Vector3f(0.5f, 0.5f, 0f), angle);
        	drawRepere(gl);
        m_MatrixStack.pop();
        
        angle++;
        
        /*
        System.out.println(m_MatrixStack.top().toString());
        gl.glUniformMatrix4fv(m_MVPLocation, 1, false, m_MatrixStack.parseTopToFloatArray(), 0);
    	gl.glDrawArrays(GL3.GL_TRIANGLE_FAN, 0, 4);
    	
    	m_MatrixStack.push();
        	m_MatrixStack.rotate(new Vector3f(0.5f, 0.4f, 1f), scale);
        	System.out.println(m_MatrixStack.top().toString());
        	gl.glUniformMatrix4fv(m_MVPLocation, 1, false, m_MatrixStack.parseTopToFloatArray(), 0);
        	gl.glDrawArrays(GL3.GL_TRIANGLE_FAN, 0, 4);
        m_MatrixStack.pop();   

        scale = scale +1;        
        if(scale > 90 || scale < -90) {
        	sign = -sign;
        }
        
        if(scale>2){
        System.exit(0);
        }*/
	}

	public void dispose(GLAutoDrawable drawable) {

	}

	public void init(GLAutoDrawable drawable) {
		
		GL3 gl = drawable.getGL().getGL3();
		m_ShaderProgram = new ShaderProgram(drawable, new File("src/shaders/color.vs.glsl"), new File("src/shaders/color.fs.glsl"));
		
		//m_TreeHierarchy = new JDOMHierarchy(new File("src/xml/xml_tree.xml"));
		//m_TreeHierarchy.printXML();
		
		m_NbVertices = 4;
		
		float[] verticesPosition = new float[] { 
        		-0.5f, 0.5f, -0.0f,
        		0.5f, 0.5f, -0.0f,
        		0.5f, -0.5f, -0.0f,
        		-0.5f, -0.5f, -0.0f
         };		
		m_VerticesPosition = Buffers.newDirectFloatBuffer(verticesPosition);
		
		float[] verticesRepere = new float[] { 
        		0f, 0f, 0f,
        		1f, 0f, 0f,
        		0f, 0f, 0f,
        		0f, 1f, 0f,
        		0f, 0f, 0f,
        		0f, 0f, 1f
         };		
		m_VerticesRepere = Buffers.newDirectFloatBuffer(verticesRepere);
		
		float[] verticesColor = new float[] {
				0.2f, 0.2f, 0.2f,
				0.7f, 0.7f, 0.7f,
				0.5f, 0.5f, 0.5f,
				0.2f, 0.2f, 0.2f
		};
		m_VerticesColor = Buffers.newDirectFloatBuffer(verticesColor);
		
		m_MatrixStack = new MatrixStack();
		
		m_MVPLocation = gl.glGetUniformLocation(m_ShaderProgram.getProgram(), "uniform_MVP");
		
		angle = 0;
		
	}

	public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3, int arg4) {

	}

	public void drawRepere(GL3 gl){
		
		gl.glEnableVertexAttribArray(CST.SHADER_POSITION_LOCATION);        
        gl.glVertexAttribPointer(CST.SHADER_POSITION_LOCATION, 3, GL3.GL_FLOAT, false, 0, m_VerticesRepere);
        
        gl.glEnableVertexAttribArray(CST.SHADER_COLOR_LOCATION);        
        gl.glVertexAttribPointer(CST.SHADER_COLOR_LOCATION, 3, GL3.GL_FLOAT, false, 0, m_VerticesRepere);
        
        gl.glUniformMatrix4fv(m_MVPLocation, 1, false, m_MatrixStack.parseTopToFloatArray(), 0);
        gl.glDrawArrays(GL3.GL_LINES, 0, 6);
	}
	
}
