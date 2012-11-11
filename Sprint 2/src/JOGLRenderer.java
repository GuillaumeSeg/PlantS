import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL2;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import utils.CST;

public class JOGLRenderer implements GLEventListener {
	
	private ShaderProgram m_ShaderProgram;
	private int[] m_Vbo;
	private int[] m_Vao;
	
	public void init(GLAutoDrawable drawable) {
		
		// Setup opengl
        GL3 gl = drawable.getGL().getGL3();       
        gl.glClearColor(0.0f, 0.2f, 0.2f, 0.0f);        
        gl.glClearDepth(1.0f);          
        gl.glEnable(GL3.GL_DEPTH_TEST);         
        gl.glDepthFunc(GL3.GL_LEQUAL);           
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL3.GL_NICEST);
        
        // Create vertex data         
        float[] vertices = new float[]{
              0.5f, 0.5f,
              -0.5f, -0.5f
        };
        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length*CST.SIZEOF_FLOAT);            
        vertexByteBuffer.order(ByteOrder.nativeOrder());      
        FloatBuffer vertexBuffer = vertexByteBuffer.asFloatBuffer();          
        vertexBuffer.put(vertices);
        
        // Create vbo        
        m_Vbo = new int[1];         
        gl.glGenBuffers(1, m_Vbo, 0);        
        	gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, m_Vbo[0]);      
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, vertices.length*CST.SIZEOF_FLOAT, null, GL2.GL_DYNAMIC_DRAW); 
        // Load vertex data into vertex buffer         
        gl.glBufferSubData(GL2.GL_ARRAY_BUFFER, 0, vertexByteBuffer.capacity(), vertexByteBuffer);
        
        // Create vao
        m_Vao = new int[1];
        gl.glGenVertexArrays(1, m_Vao, 0);
        gl.glBindVertexArray(m_Vbo[0]);
        	gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, m_Vbo[0]);
        	gl.glEnableVertexAttribArray(CST.SHADER_POSITION_LOCATION);
        	
        	gl.glVertexAttribPointer(
        			CST.SHADER_POSITION_LOCATION, // attribute location into shader
        			2, // number of element for the attribute
        			GL2.GL_FLOAT, // data type
        			false, // normalize ?
        			2*CST.SIZEOF_FLOAT, // 1 vertex total size
        			0 // attribute offset      			
        	);
        	
        	gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
        gl.glBindVertexArray(0);
        
        
        m_ShaderProgram = new ShaderProgram(drawable, new File("src/shaders/color.vs.glsl"), new File("src/shaders/color.fs.glsl"));
	}
	
	public void display(GLAutoDrawable drawable) {
		
		GL3 gl = drawable.getGL().getGL3();   
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
		System.out.println("GL Error(s) : " + gl.glGetError());
		
		// shader code
		gl.glUseProgram(m_ShaderProgram.p);
			//gl.glBindVertexArray(m_Vao[0]);
			gl.glDrawArrays(GL2.GL_POINTS, 0, 2);
			//gl.glBindVertexArray(0);
		gl.glUseProgram(0);
		
	}

	public void dispose(GLAutoDrawable drawable) {
	
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL3 gl = drawable.getGL().getGL3();     
        GLU glu = new GLU(); 
        gl.glViewport(0, 0, width, height);   
        glu.gluPerspective(45.0f, (float) width / (float) height, 0.1f, 100.0f);
	}
	
	
}
