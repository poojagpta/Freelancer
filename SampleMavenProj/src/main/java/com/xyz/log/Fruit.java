package com.xyz.log;

public class Fruit implements Comparable<Fruit> {

	private String fruitName;
	private String fruitDesc;
	private int quantity;

	public Fruit(String fruitName, String fruitDesc, int quantity) {
		super();
		this.fruitName = fruitName;
		this.fruitDesc = fruitDesc;
		this.quantity = quantity;
	}

	/**
	 * @return the fruitName
	 */
	public String getFruitName() {
		return fruitName;
	}

	/**
	 * @param fruitName
	 *            the fruitName to set
	 */
	public void setFruitName(String fruitName) {
		this.fruitName = fruitName;
	}

	/**
	 * @return the fruitDesc
	 */
	public String getFruitDesc() {
		return fruitDesc;
	}

	/**
	 * @param fruitDesc
	 *            the fruitDesc to set
	 */
	public void setFruitDesc(String fruitDesc) {
		this.fruitDesc = fruitDesc;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int compareTo(Fruit o) {
		if (o instanceof Fruit) {
			return -this.getFruitName().compareTo(
					(String) ((Fruit) o).getFruitName());
		}

		return 0;
	}
	
	@Override
	public String toString() {
		
		return this.getFruitName()+"-"+this.getFruitDesc()+"-"+this.getQuantity();
	}

}
