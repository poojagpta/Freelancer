package com.xyz.Tree;

public class BinaryEntryTree<K, V> {

	private K key;
	private V value;

	private BinaryEntryTree<K, V> leftNode;
	private BinaryEntryTree<K, V> rightNode;
	private BinaryEntryTree<K, V> parent;

	public BinaryEntryTree(){
		
	}
	
	
	
	public BinaryEntryTree(K key, V value, BinaryEntryTree<K, V> leftNode,
			BinaryEntryTree<K, V> rightNode, BinaryEntryTree<K, V> parent) {
		super();
		this.key = key;
		this.value = value;
		this.leftNode = leftNode;
		this.rightNode = rightNode;
		this.parent = parent;
	}

	public K getKey() {
		return key;
	}

	public BinaryEntryTree<K, V> getParent() {
		return parent;
	}

	public void setParent(BinaryEntryTree<K, V> parent) {
		this.parent = parent;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public BinaryEntryTree<K, V> getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(BinaryEntryTree<K, V> leftNode) {
		this.leftNode = leftNode;
	}

	public BinaryEntryTree<K, V> getRightNode() {
		return rightNode;
	}

	public void setRightNode(BinaryEntryTree<K, V> rightNode) {
		this.rightNode = rightNode;
	}

}
