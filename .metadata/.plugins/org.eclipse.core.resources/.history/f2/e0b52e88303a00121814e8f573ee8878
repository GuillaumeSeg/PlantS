package openGL3;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.media.opengl.GL2ES2;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;


public class ShaderProgram {
     
    int m_iVertexShader;
    int m_iFragmentShader;
    int m_iProgram;
    
	public ShaderProgram(GL2ES2 gl, File vertexShaderFile, File fragmentShaderFile) {
		
		/*********************************************/
		/*** FILL AND COMPILE SHADERS INTO PROGRAM ***/
		/*********************************************/
		
		m_iVertexShader = gl.glCreateShader(GL2ES2.GL_VERTEX_SHADER);
		m_iFragmentShader = gl.glCreateShader(GL2ES2.GL_FRAGMENT_SHADER);
		
		/* >>>>>>>>>>>>> FILL VERTEX SHADER */
		String[] vertexShaderSource = GetShaderSource(vertexShaderFile);
		int nbStringInVertexShaderSource = vertexShaderSource.length;
		int[] lengtOfEachStringInVertexShaderSource = new int[vertexShaderSource.length];
		for(int i = 0; i < vertexShaderSource.length; ++i) {
			lengtOfEachStringInVertexShaderSource[i] = vertexShaderSource[i].length();
		}
		
        gl.glShaderSource(m_iVertexShader, nbStringInVertexShaderSource, vertexShaderSource, lengtOfEachStringInVertexShaderSource, 0);
        gl.glCompileShader(m_iVertexShader);
        
        /* CHECK COMPILE */
        int[] compiled = new int[1];
        gl.glGetShaderiv(m_iVertexShader, GL2ES2.GL_COMPILE_STATUS, compiled, 0);
        if(compiled[0] != 0) {
        	System.out.println("Horray! vertex shader compiled");
        } else {
            int[] logLength = new int[1];
            gl.glGetShaderiv(m_iVertexShader, GL2ES2.GL_INFO_LOG_LENGTH, logLength, 0);
            byte[] log = new byte[logLength[0]];
            gl.glGetShaderInfoLog(m_iVertexShader, logLength[0], (int[])null, 0, log, 0);
            System.err.println("Error compiling the vertex shader: " + new String(log));
            System.exit(1);
        }
        
        /* >>>>>>>>>>>>> FILL FRAGMENT SHADER */
		String[] fragmentShaderSource = GetShaderSource(fragmentShaderFile);
		int nbStringInFragmentShaderSource = fragmentShaderSource.length;
		int[] lengtOfEachStringInFragmentShaderSource = new int[fragmentShaderSource.length];
		for(int i = 0; i < fragmentShaderSource.length; ++i) {
			lengtOfEachStringInFragmentShaderSource[i] = fragmentShaderSource[i].length();
		}
		
        gl.glShaderSource(m_iFragmentShader, nbStringInFragmentShaderSource, fragmentShaderSource, lengtOfEachStringInFragmentShaderSource, 0);
        gl.glCompileShader(m_iFragmentShader);
        
        /* CHECK COMPILE */
        gl.glGetShaderiv(m_iFragmentShader, GL2ES2.GL_COMPILE_STATUS, compiled, 0);
        if(compiled[0] != 0) {
        	System.out.println("Horray! fragment shader compiled");
        } else {
            int[] logLength = new int[1];
            gl.glGetShaderiv(m_iFragmentShader, GL2ES2.GL_INFO_LOG_LENGTH, logLength, 0);
            byte[] log = new byte[logLength[0]];
            gl.glGetShaderInfoLog(m_iFragmentShader, logLength[0], (int[])null, 0, log, 0);
            System.err.println("Error compiling the fragment shader: " + new String(log));
            System.exit(1);
        }
        
        /* >>>>>>>>>>>>> FILL & COMPILE PROGRAM */
        m_iProgram = gl.glCreateProgram();
        gl.glAttachShader(m_iProgram, m_iVertexShader);
        gl.glAttachShader(m_iProgram, m_iFragmentShader);

        //Associate attribute ids with the attribute names inside the vertex shader
        gl.glBindAttribLocation(m_iProgram, 0, "attribute_Position");
        gl.glBindAttribLocation(m_iProgram, 1, "attribute_Color");

        gl.glLinkProgram(m_iProgram);
	}
	
	static String[] GetShaderSource(File shaderFile) {
		
	      ArrayList<String> shaderSource = new ArrayList<String>();
	      Scanner scanner_shader = null;
	      try {
	    	  scanner_shader = new Scanner(shaderFile);
	      } catch (FileNotFoundException e) {
	    	  System.out.println("Shader " + shaderFile.toString() + " file was not found");
	      }

	      scanner_shader.useDelimiter("\n");	      
	      while(scanner_shader.hasNext()){
	    	  String temp = scanner_shader.next() + "\n";
	    	  shaderSource.add(temp);
	      }
	      
	      String[] shaderSourceArray = new String[shaderSource.size()];
	      shaderSourceArray = shaderSource.toArray(shaderSourceArray);
	      int[] lengths= new int[shaderSourceArray.length];
	      for(int i = 0; i < shaderSourceArray.length; i++){
	    	  lengths[i] = shaderSourceArray[i].length();
	      }
	      
	      return shaderSourceArray;
	}

	
	public int getVertexShader() {
		return m_iVertexShader;
	}
	

	public int getFragmentShader() {
		return m_iFragmentShader;
	}
	

	public int getProgram() {
		return m_iProgram;
	}



}
