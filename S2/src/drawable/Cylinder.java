package drawable;

import javax.media.opengl.GL3;

import utils.CST;

//Représente un cylindre ouvert discrétisé dont la base est centrée en (0, 0, 0) (dans son repère local)
//Son axe vertical est (0, 1, 0) et ses axes transversaux sont (1, 0, 0) et (0, 0, 1)
public class Cylinder {

	static int POSITION_NUM_COMPONENTS = 3;
    static int NORMAL_NUM_COMPONENTS = 0;
    static int TEXCOORDS_NUM_COMPONENTS = 0;
    
    static int POSITION_OFFSET = 0;
    static int NORMAL_OFFSET = POSITION_NUM_COMPONENTS * CST.SIZEOF_FLOAT;
    static int TEX_COORDS_OFFSET = (POSITION_NUM_COMPONENTS + NORMAL_NUM_COMPONENTS) * CST.SIZEOF_FLOAT;
    
    static int VERTEX_BYTE_SIZE = (POSITION_NUM_COMPONENTS + NORMAL_NUM_COMPONENTS + TEXCOORDS_NUM_COMPONENTS) * CST.SIZEOF_FLOAT;
    
    // Alloue et construit les données
    void build(float height, float radius, int discLat, int discHeight) {
    	// Equation paramétrique en (r, phi, h) du cylindre
        // avec r >= 0, -PI / 2 <= theta <= PI / 2, 0 <= h <= height
        //
        // x(r, phi, h) = r sin(phi)
        // y(r, phi, h) = h
        // z(r, phi, h) = r cos(phi)
        //
        // Discrétisation:
        // dPhi = 2PI / discLat, dH = height / discHeight
        //
        // x(r, i, j) = r * sin(i * dPhi)
        // y(r, i, j) = j * dH
        // z(r, i, j) = r * cos(i * dPhi)
    	
    	float rcpLat = 1.f / discLat, rcpH = 1.f / discHeight;
        float dPhi = 2 * (float)Math.PI * rcpLat, dH = height * rcpH;
    }
	
}
