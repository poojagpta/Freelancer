package com.xyz.Tree;

public class BinarySearchTree {

	public BinaryEntryTree<Integer, String> binarySearchTree;

	
	public static void main(String str[]){
		
		BinarySearchTree tree=new BinarySearchTree();
		//5, 30, 2, 40, 25, 4
		tree.put(5, "Five");
		tree.put(30, "Thirty");
		tree.put(2, "Two");
		tree.put(40, "Fourty");
		tree.put(25, "fwenty Five");
		tree.put(4, "four");
		
		System.out.println(tree.binarySearchTree.getValue());
		
	}
	
	
	public BinarySearchTree() {
		binarySearchTree = null;
	}

	
	/**
	 * Putting data in binary Search Tree
	 * 
	 * @param key
	 * @param value
	 */
	public void put(Integer key, String value) {
		if (binarySearchTree!=null) {
			_put(key,value,binarySearchTree);
		}else{
			binarySearchTree=new BinaryEntryTree(key, value, null,null, null); 
		}

	}

	public void _put(Integer key, String value,
			BinaryEntryTree<Integer, String> currentNode) {
		if (key < currentNode.getKey()) {
			if (currentNode.getLeftNode() != null) {
				this._put(key, value, currentNode.getLeftNode());
			} else {
				currentNode.setLeftNode(new BinaryEntryTree(key, value, null,
						null, currentNode));
			}
		} else {
			if (currentNode.getRightNode() != null) {
				this._put(key, value, currentNode.getRightNode());
			} else {
				currentNode.setRightNode(new BinaryEntryTree(key, value, null,
						null, currentNode));
			}
		}

	}

	
	/*def get(self,key):
	    if self.root:
	        res = self._get(key,self.root)
	        if res:
	               return res.payload
	        else:
	               return None
	    else:
	        return None

	def _get(self,key,currentNode):
	    if not currentNode:
	        return None
	    elif currentNode.key == key:
	        return currentNode
	    elif key < currentNode.key:
	        return self._get(key,currentNode.leftChild)
	    else:
	        return self._get(key,currentNode.rightChild)

	def __getitem__(self,key):
	    return self.get(key)*/
	
	
	
	
}
