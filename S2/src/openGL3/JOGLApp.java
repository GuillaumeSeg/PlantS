package openGL3;
public class JOGLApp {
	
	JOGLWindow m_Window;
	JOGLRenderer m_Renderer;
	
	JOGLApp(String name){
		m_Window = new JOGLWindow(name);
		m_Renderer = new JOGLRenderer();
	}
	
	public static void main(String[] args){
		JOGLApp application = new JOGLApp("testOpenGL3");
	}
	
}
