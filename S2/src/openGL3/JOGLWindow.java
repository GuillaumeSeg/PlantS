package openGL3_test2;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;


public class JOGLWindow {
	
	private JFrame m_Frame;
	private GLCanvas m_Canvas;
	private GLCapabilities m_Capabilities;
	private GLProfile m_Profile;	
	private FPSAnimator m_Animator;
	
	public JOGLWindow(String name){
		
		m_Frame = new JFrame(name);
		m_Frame.setSize(640, 640);
		m_Frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				m_Frame.dispose();
				System.exit(0);
			}
		});
		
		m_Profile = GLProfile.getDefault();
		m_Capabilities = new GLCapabilities(m_Profile);
		m_Canvas = new GLCanvas(m_Capabilities);
		m_Canvas.addGLEventListener(new JOGLRenderer());
		m_Frame.add(m_Canvas);
		
		m_Animator = new FPSAnimator(m_Canvas, 60);
		m_Animator.add(m_Canvas);
		m_Animator.start();
			
		m_Frame.setVisible(true);	
	}

}
