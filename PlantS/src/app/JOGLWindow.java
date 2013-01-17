package app;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import javax.vecmath.Vector2f;

import renderer.JOGLRenderer;

import camera.CameraController;

import com.jogamp.newt.event.InputEvent;
import com.jogamp.opengl.util.FPSAnimator;


public class JOGLWindow {
	
	private JFrame m_Frame;
	private GLCanvas m_Canvas;
	private GLCapabilities m_Capabilities;
	private GLProfile m_Profile;	
	private FPSAnimator m_Animator;
	private JOGLRenderer m_Renderer;
	
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
		
		m_Canvas.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() == -1) {
					if(m_Renderer.getTCamera().getDistance() > 0.1) {
						m_Renderer.getTCamera().moveFront(0.1f);
					}					
				} else {
					m_Renderer.getTCamera().moveFront(-0.1f);
				}
			}
		});
		
		m_Canvas.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("released");			
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getButton() == 2) {
						System.out.println("pressed");			
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("exited");			
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("entered");			
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("clicked");			
			}
		});

		m_Canvas.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("moved");
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e.getButton());
				if(e.getButton() == 0) {
					float newXpositionCamera = (float)0.1*e.getX() / 640;
					float newYpositionCamera = (float)0.1*e.getY() / 640;
					System.err.println(newXpositionCamera + ", " + newYpositionCamera);
					m_Renderer.getTCamera().setTarget(newXpositionCamera, newYpositionCamera);
					System.out.println("dragged : " + e.getX() + ", " + e.getY());
				}
			}
		});
		
		m_Renderer = new JOGLRenderer();
		m_Canvas.addGLEventListener(m_Renderer);
		m_Frame.add(m_Canvas);
		
		m_Animator = new FPSAnimator(m_Canvas, 60);
		m_Animator.add(m_Canvas);
		m_Animator.start();
			
		m_Frame.setVisible(true);	
	}
	
}
