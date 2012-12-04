package drawable;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class Vertex {
	
	private Vector3f m_Position;
	private Vector3f m_Normal;
	private Vector2f m_TexCoord;
	
	public Vertex() {
		m_Position = new Vector3f(0f, 0f, 0f);
	}
	
	public Vertex(float x, float y, float z) {
		m_Position = new Vector3f(x, y, z);
	}
	
	public Vector3f getPosition() {
		return m_Position;
	}
	
}
