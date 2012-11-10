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
	
	public void display(GLAutoDrawable drawable) {
		
		GL3 gl = drawable.getGL().getGL3();   
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
		System.out.println("GL Error(s) : " + gl.glGetError());
		
		gl.glVertexAttribPointer(
				CST.SHADER_POSITION_LOCATION, // index of attribute into shader
				3, // number of float for 1 vertex
				gl.GL_FLOAT, // datatype
				false, // normalize ?
				3*CST.SIZEOF_FLOAT, // One vertex total size
				0 // attribute's offset
		);
		
		// shader code
		gl.glUseProgram(m_ShaderProgram.p);
			//start render at 0 vertex go to 3 for a count of 4
			gl.glDrawRangeElements(GL2.GL_TRIANGLE_STRIP, 0, 3, 4, GL2.GL_UNSIGNED_INT, 0);		
		gl.glUseProgram(0);
		
	}

	public void dispose(GLAutoDrawable drawable) {
	
	}

	public void init(GLAutoDrawable drawable) {
		
		//setup opengl
        GL3 gl = drawable.getGL().getGL3();       
        gl.glClearColor(0.0f, 0.2f, 0.2f, 0.0f);        
        gl.glClearDepth(1.0f);          
        gl.glEnable(GL3.GL_DEPTH_TEST);         
        gl.glDepthFunc(GL3.GL_LEQUAL);           
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL3.GL_NICEST);
        
        // Create vertex data         
        float[] vertices = new float[]{
              -1f, 1f, -2.0f, //0
              1f, 1f, -2.0f, //1
              -1f, -1f, -2.0f, //2
              1f, -1f, -2.0f, //3
        };
        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(3 * 4 * 4);            
        vertexByteBuffer.order(ByteOrder.nativeOrder());      
        FloatBuffer vertexBuffer = vertexByteBuffer.asFloatBuffer();          
        vertexBuffer.put(vertices);
        
        // Create vertex buffer              
        int[] vbo = new int[1];         
        gl.glGenBuffers(1, vbo, 0);        
        	gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, vbo[0]);      
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, vertices.length*CST.SIZEOF_FLOAT, null, GL2.GL_DYNAMIC_DRAW); 
        // Load vertex data into vertex buffer         
        gl.glBufferSubData(GL2.GL_ARRAY_BUFFER, 0, vertexByteBuffer.capacity(), vertexByteBuffer);
        
        // Create index data           
        int[] indexes = new int[]{0, 1, 2, 3};

        ByteBuffer indexByteBuffer = ByteBuffer.allocateDirect(4 * 4);     
        indexByteBuffer.order(ByteOrder.nativeOrder());
        IntBuffer indexBuffer = indexByteBuffer.asIntBuffer();       
        indexBuffer.put(indexes);

        // Create index buffer     
        int[] vao = new int[1];      
        gl.glGenBuffers(1, vao, 0);
        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, vao[0]);          
        gl.glBufferData(GL2.GL_ELEMENT_ARRAY_BUFFER, indexes.length*CST.SIZEOF_FLOAT, null, GL2.GL_DYNAMIC_DRAW);       
        // Load index data into index buffer          
        gl.glBufferSubData(GL2.GL_ELEMENT_ARRAY_BUFFER, 0, indexByteBuffer.capacity(), indexByteBuffer);
        
        m_ShaderProgram = new ShaderProgram(drawable, new File("src/shaders/color.vs.glsl"), new File("src/shaders/color.fs.glsl"));
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL3 gl = drawable.getGL().getGL3();     
        GLU glu = new GLU(); 
        gl.glViewport(0, 0, width, height);   
        glu.gluPerspective(45.0f, (float) width / (float) height, 0.1f, 100.0f);
	}
	
	
}
