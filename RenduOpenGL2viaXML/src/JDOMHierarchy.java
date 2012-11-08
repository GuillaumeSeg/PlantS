import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.media.opengl.GL2;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class JDOMHierarchy {

	private Element m_Root;
	private org.jdom2.Document m_Document;
	
	public JDOMHierarchy(String XMLFileName){
		SAXBuilder sxb = new SAXBuilder();
		try{
			m_Document = sxb.build(new File("src/xml/" + XMLFileName + ".xml"));
			
		}
		catch(Exception e){
			System.out.println("error when building Document !");
		}
		m_Root = m_Document.getRootElement();
	}
	
	public void printXML(){
		try{
			XMLOutputter exit = new XMLOutputter(Format.getPrettyFormat());
			exit.output(m_Document, System.out);
		}catch(java.io.IOException e){}
	}
	
	public static void drawJDOM(Element element, GL2 gl){
		
		draw(element, gl);
		if(element.getName() == "trunck"){
			gl.glPushMatrix();
			
				gl.glRotatef(Float.parseFloat(element.getAttributeValue("angle")), 0, 0, 1);
				gl.glTranslatef(0.0f, Float.parseFloat(element.getAttributeValue("height")), 0.0f);
				
				if(!element.getChildren().isEmpty()){
					List childrenList = element.getChildren();
					Iterator itChildren = childrenList.iterator();
					while(itChildren.hasNext()){
						Element child = (Element)itChildren.next();
						drawJDOM(child, gl);
					}
				
				}
			gl.glPopMatrix();
		}else{
			if(!element.getChildren().isEmpty()){
				List childrenList = element.getChildren();
				Iterator itChildren = childrenList.iterator();
				while(itChildren.hasNext()){
					Element child = (Element)itChildren.next();
					drawJDOM(child, gl);
				}
			}
		}
		
	}
	
	public static void draw(Element element, GL2 gl){

		if(element.getName() == "trunck"){
			
			float height = Float.parseFloat(element.getAttributeValue("height"));
			float angle = Float.parseFloat(element.getAttributeValue("angle"));
			
			gl.glPushMatrix();
				gl.glRotatef(angle, 0, 0, 1);
				gl.glScalef(1, height, 1);
				drawPrimitiveTrunk(gl);
			gl.glPopMatrix();
		}
		
		if(element.getName() == "leaf"){
			float size = Float.parseFloat(element.getAttributeValue("size"));
			gl.glPushMatrix();
				gl.glTranslatef(0, size/2, 0);
				gl.glRotatef(45, 0, 0, 1);				
				gl.glScalef(size, size, 1);
				drawPrimitiveLeaf(gl);
			gl.glPopMatrix();
		}
		
	}
	
	private static void drawPrimitiveTrunk(GL2 gl){
		gl.glColor3f(255.0f, 255.0f, 255.0f);
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glEnd();
	}
	
	private static void drawPrimitiveLeaf(GL2 gl){
		gl.glColor3f(0.0f, 255.0f, 0.0f);
		gl.glBegin(GL2.GL_QUADS);
			gl.glVertex3f(-1f, 1f, 0.0f);
			gl.glVertex3f(1f, 1f, 0.0f);
			gl.glVertex3f(1f, -1f, 0.0f);
			gl.glVertex3f(-1f, -1f, 0.0f);
		gl.glEnd();
	}

	public Element getRoot(){
		return m_Root;
	}
}
