package com.xyz.Tree;

import java.util.Stack;

public class QueueBinaryTree {


	private Stack<BinaryEntryTree> binaryQueue;
	
	public QueueBinaryTree(){
		binaryQueue=new Stack<BinaryEntryTree>();
	}
	
	public static void main(String str[]){
		QueueBinaryTree tree=new QueueBinaryTree();
		BinaryEntryTree binaryTree = tree.buildParseTree("( 3 + ( 4 * 5 ) )");
				
	}
	
	
	public BinaryEntryTree buildParseTree(String expression){
		
		String[] expList=expression.split(" ");
		BinaryEntryTree root=new BinaryEntryTree();
		binaryQueue.add(root);
		BinaryEntryTree currentTree=root;
		for(String exp:expList){			
			if(exp.equals("(")){
				currentTree.setLeftNode(new BinaryEntryTree());
				binaryQueue.add(currentTree);
	            currentTree = currentTree.getLeftNode();	            
			}else if(!exp.contains("+") && !exp.contains("*") && !exp.contains(")")){
				currentTree.setKey(exp);
				BinaryEntryTree parent = binaryQueue.pop();
	            currentTree = parent;
			}else if(exp.contains("+")|| exp.contains("*")){
				currentTree.setKey(exp);
	            currentTree.setRightNode(new BinaryEntryTree());
	            binaryQueue.add(currentTree);
	            currentTree = currentTree.getRightNode();
			}else if (exp.equals(")")){
				currentTree = binaryQueue.pop();
			}    	
		}
		return 	root;
	}
	
	public int evaluate(BinaryEntryTree tree){
		
		BinaryEntryTree lefttree=tree.getLeftNode();
		BinaryEntryTree righttree=tree.getRightNode();
		
		if(lefttree!=null){
			evaluate(lefttree);
		}
		if(righttree!=null){
			evaluate(righttree);
		}
		
		return 0;
	}
	
}
