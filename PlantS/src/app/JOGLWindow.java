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
	
	int currentKeyPressed;
	private float TpreviousPositionX = 0;
	private float TpreviousPositionY = 0;
	private float TpositionX = 0;
	private float TpositionY = 0;
	
	private float RpreviousPositionX = 0;
	private float RpreviousPositionY = 0;
	private float RpositionX = 0;
	private float RpositionY = 0;
	
	private boolean mouseIsDown = false;
	
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
		
		currentKeyPressed = 0;
		
		m_Canvas.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				currentKeyPressed = 0;
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				currentKeyPressed = e.getButton();
				RpreviousPositionX = e.getX();
				RpreviousPositionY = e.getY();
				TpreviousPositionX = e.getX();
				TpreviousPositionY = e.getY();
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});

		m_Canvas.addMouseMotionListener(new MouseMotionListener() {
			
			
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {

				
				switch(currentKeyPressed) {
					
					case 2 :
					
					TpositionX = e.getX();
					TpositionY = e.getY();
					float tx = 0.005f*(TpositionX - TpreviousPositionX);
					System.out.println(tx);
					float ty = -0.005f*(TpositionY - TpreviousPositionY);
					System.out.println(ty);
					m_Renderer.getTCamera().setTarget(tx, ty);
					TpreviousPositionX = TpositionX;
					TpreviousPositionY = TpositionY;
					
					break;
					
					case 3 :

					RpositionX = e.getX();
					RpositionY = e.getY();
					float rLeft = -0.01f *(RpositionX - RpreviousPositionX);
					float rUp = -0.01f * (RpositionY - RpreviousPositionY);
					m_Renderer.getTCamera().rotateLeft(rUp);
					m_Renderer.getTCamera().rotateUp(rLeft);
					RpreviousPositionX = RpositionX;
					RpreviousPositionY = RpositionY;
					
					break;
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
