import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;


public class ShaderProgram {
	
	int p;
    boolean valid = false;
     
    File vertf;
    File fragf;
	
	public ShaderProgram(GLAutoDrawable drawable, File vertf, File fragf) {
		
	      this.vertf = vertf;
	      this.fragf = fragf;
	      GL3 gl = drawable.getGL().getGL3();
	        
	      int p = gl.glCreateProgram();
	      	        
	      //VERTEX STUFF
	      int v = gl.glCreateShader(gl.GL_VERTEX_SHADER);
	        
	      ArrayList <String> vSourceA = new ArrayList<String>();
	      Scanner vert = null;
	      try {
	    	  vert = new Scanner(vertf);
	      } catch (FileNotFoundException e) {
	    	  System.out.println("Vertex shader file was not found");
	      }

	      vert.useDelimiter("\n");
	      
	      while(vert.hasNext()){
	    	  String temp = vert.next() + "\n";
	    	  vSourceA.add(temp);
	    	  System.out.print(temp);
	      }
	        
	      String[] vSource = new String[vSourceA.size()];
	      vSource = vSourceA.toArray(vSource);
	      int [] lengths= new int[vSource.length];
	      for(int i = 0; i < vSource.length; i++){
	    	  lengths[i] = vSource[i].length();
	      }
	        
	      gl.glShaderSource(v, lengths.length, vSource, lengths, 0);        
	      gl.glCompileShader(v);
	      gl.glAttachShader(p, v);
	        
	      //FRAGMENT SHADER CODE
	      int f = gl.glCreateShader(gl.GL_FRAGMENT_SHADER);
	        
	      ArrayList <String> fSourceA = new ArrayList<String>();
	        
	      Scanner frag = null;
	      try {
	    	  frag = new Scanner(fragf);
	      } catch (FileNotFoundException e) {
	    	  System.out.println("Fragment shader file was not found");
	      }
	      
	      frag.useDelimiter("\n");
	      while(frag.hasNext()){
	    	  String temp = frag.next() + "\n";
	    	  fSourceA.add(temp);
	    	  System.out.print(temp);
	      }
	        
	      String[] fSource = new String[fSourceA.size()];
	      fSource = fSourceA.toArray(fSource);
	      lengths= new int[fSource.length];
	      for(int i = 0; i < fSource.length; i++){
	    	  lengths[i] = fSource[i].length();
	      }
	        
	      gl.glShaderSource(f, lengths.length, fSource, lengths, 0);        
	      gl.glCompileShader(f);
	      gl.glAttachShader(p, f);
	         
	      //PROGRAM CODE
	      gl.glLinkProgram(p);
	      gl.glValidateProgram(p);
	      System.out.println(gl.glGetError());
	      	valid = true;
	      }
}
