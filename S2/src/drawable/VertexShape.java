package drawable;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class VertexShape {
	
	public static int s_NB_COMPONENTS_POSITION = 3;
	public static int s_NB_TOTAL_COMPONENTS = s_NB_COMPONENTS_POSITION;
	
	public Vector3f position;
	public Vector3f normal;
	public Vector2f texCoord;
	
	public VertexShape() {
		position = new Vector3f(0f, 0f, 0f);
		normal = new Vector3f(0f, 0f, 0f);
		texCoord = new Vector2f(0f, 0f);
	}
	
	public VertexShape(VertexShape v) {
		position = v.position;
		normal = v.normal;
		texCoord = v.texCoord;
	}
	
	public VertexShape(float x, float y, float z) {
		position = new Vector3f(x, y, z);
		normal = new Vector3f(0f, 0f, 0f);
		texCoord = new Vector2f(0f, 0f);
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
}
