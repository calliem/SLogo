package model;

import java.util.ArrayList;
import java.util.List;

import model.instructions.Instruction;

/// TODO: make this abstract and create a commandnode and datanode
public class Node{
		
		private List<Node> myChildren;
		private String myValue;
		
		public Node(String me){
			myValue = me;
			myChildren = new ArrayList<Node>();
		}
		public String getValue(){
			return myValue;
		}
	public Node addChild(Node n){
		myChildren.add(n);
		return n;
	}
	public String[] childrenToStringArray(){
		return (String[]) myChildren.toArray();
	}
	public List<Node> getChildren(){
		return myChildren;
	}
}
