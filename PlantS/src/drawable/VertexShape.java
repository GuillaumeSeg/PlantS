package drawable;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class VertexShape {
	
	public static int s_NB_COMPONENTS_POSITION = 4;
	public static int s_NB_TOTAL_COMPONENTS = s_NB_COMPONENTS_POSITION;
	
	public Vector4f position;
	public Vector4f positionRP;
	public Vector4f positionRB;
	public Vector3f normal;
	public Vector2f texCoord;
	
	public VertexShape() {
		position = new Vector4f(0f, 0f, 0f, 1f);
		normal = new Vector3f(0f, 0f, 0f);
		texCoord = new Vector2f(0f, 0f);
		positionRP = new Vector4f(0f, 0f, 0f, 1f);
		positionRB = new Vector4f(0f, 0f, 0f, 1f);
	}
	
	public VertexShape(VertexShape v) {
		position = v.position;
		normal = v.normal;
		texCoord = v.texCoord;
		positionRP = new Vector4f(0f, 0f, 0f, 1f);
		positionRB = new Vector4f(0f, 0f, 0f, 1f);
	}
	
	public VertexShape(float x, float y, float z) {
		position = new Vector4f(x, y, z, 1f);
		normal = new Vector3f(0f, 0f, 0f);
		texCoord = new Vector2f(0f, 0f);
		positionRP = new Vector4f(0f, 0f, 0f, 1f);
		positionRB = new Vector4f(0f, 0f, 0f, 1f);
	}
	
	public VertexShape(float x, float y, float z, float n1, float n2, float n3) {
		position = new Vector4f(x, y, z, 1f);
		normal = new Vector3f(n1, n2, n3);
		texCoord = new Vector2f(0f, 0f);
		positionRP = new Vector4f(0f, 0f, 0f, 1f);
		positionRB = new Vector4f(0f, 0f, 0f, 1f);
	}
	
	public VertexShape(float x, float y, float z, float n1, float n2, float n3, float tc1, float tc2) {
		position = new Vector4f(x, y, z, 1f);
		normal = new Vector3f(n1, n2, n3);
		texCoord = new Vector2f(tc1, tc2);
		positionRP = new Vector4f(0f, 0f, 0f, 1f);
		positionRB = new Vector4f(0f, 0f, 0f, 1f);
	}
	
}
