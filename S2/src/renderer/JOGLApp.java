package renderer;

public class JOGLApp {
	
	JOGLWindow m_Window;
	
	JOGLApp(String name){
		m_Window = new JOGLWindow(name);
	}
	
	public static void main(String[] args){
		JOGLApp application = new JOGLApp("Complex tree with cylinders");
	}
	
}
