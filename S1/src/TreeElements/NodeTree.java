package TreeElements;

import java.util.List;

import org.jdom2.Element;

public class NodeTree {
	
	private NodeTree m_LeftChild;
	private NodeTree m_RightChild;
	private String m_Name;
	private TreeElement m_Element;
	
	public NodeTree(NodeTree left, NodeTree right, String name, TreeElement element){
		m_Name = name;
		m_LeftChild = left;
		m_RightChild = right;
		m_Element = element;
	}
	
	public void printTree(){

		System.out.println(m_Name);
		
		if(m_LeftChild != null){
			System.out.print("  -");
			m_LeftChild.printTree();
		}
		if(m_RightChild != null){
			System.out.print("  -");
			m_RightChild.printTree();
		}
	}

	public void fillTree(Element element){
		m_Name = element.getName();
		
		if(element.getName()=="trunck"){
			//m_Element = new Trunck(0.5f,new float[]{0f, 0f, 1f}, 30f);
		}
		
		if(!element.getChildren().isEmpty()){
			List childrenList = element.getChildren();
			if(childrenList.size() > 2){
				System.err.println("Too muche children");
				System.exit(0);
			}
			m_LeftChild = new NodeTree(null, null, null, null);
			m_LeftChild.fillTree((Element)childrenList.get(0));	
			if(childrenList.size() == 2){
				m_RightChild = new NodeTree(null, null, null, null);
				m_RightChild.fillTree((Element)childrenList.get(1));
			}
		}
	}
		
	public NodeTree getM_LeftChild() {
		return m_LeftChild;
	}
	
	public void setM_LeftChild(NodeTree m_LeftChild) {
		this.m_LeftChild = m_LeftChild;
	}
	
	public NodeTree getM_RightChild() {
		return m_RightChild;
	}
	
	public void setM_RightChild(NodeTree m_RightChild) {
		this.m_RightChild = m_RightChild;
	}
	
	public String getM_Name() {
		return m_Name;
	}
	
	public void setM_TreeElement(String m_Name) {
		this.m_Name = m_Name;
	}
	
}
