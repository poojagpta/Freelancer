package com.xyz.collection;

import java.io.Serializable;

	/**
	 * threshold value=initial Capacity*load factor
	HashMap
	-------
	hash is calculated using hashCode&hashMapSize().
	Now irrespective of hashcode of object, hashMap length and (& operator) will return same hashCode,
	then how will you increase the hashMap size and ensure 1 value per hashCode.

	Size>threshold  values and null!=hashMap[bucketIndex]-->increase to 2 times
	 */

public class NewHashMap implements Serializable{

	public static class HashObject{
		Object key;
		Object value;
		int hash;
		HashObject next;

		public HashObject(Object key, Object value, int hash,HashObject n) {
			super();
			this.key = key;
			this.value = value;
			this.hash = hash;
			this.next=n;
		}


	}

	private static final long serialVersionUID = 1L;
	static final float DEFAULT_LOAD_FACTOR = 0.75f;
	static final int DEFAULT_INITIAL_CAPACITY = 16;



	HashObject[] table;
	int size;
	//Update in every insert/update of map object and while iteration if found that modCount not match then throw ConcurrentModificationError
	int modCount;


	public NewHashMap() {
		this(DEFAULT_INITIAL_CAPACITY,DEFAULT_LOAD_FACTOR);
	}


	public NewHashMap(int initialCapacity,float loadfactor){
		//super();
		float initalFactor=initialCapacity*loadfactor;
		table=new HashObject[(int)initalFactor];
		size=0;
		modCount=0;
	}


	 static int hash(int h) {
	        // This function ensures that hashCodes that differ only by
	        // constant multiples at each bit position have a bounded
	        // number of collisions (approximately 8 at default load factor).
	        h ^= (h >>> 20) ^ (h >>> 12);
	        return h ^ (h >>> 7) ^ (h >>> 4);
	    }

	   /**
	     * Returns index for hash code h.
	     */
	    static int indexFor(int h, int length) {
	        return h & (length-1);
	    }

	public Object put(Object key,Object value){

		if(key==null){
			putFornull(value);
		}
		modCount++;
		int hash = hash(key.hashCode());
		int i = indexFor(hash, table.length);

		//If hash code is same and object also equal then return old value
		  for (HashObject e = table[i]; e != null; e = e.next) {
	            Object k;
	            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
	                Object oldValue = e.value;
	                e.value = value;
	                return oldValue;
	            }
	        }

		  //If hashcode exist but key not equals then
		  addHashObject(hash, key, value, i);

      return null;
	}


	void addHashObject(int hash, Object key, Object value, int bucketIndex) {
		HashObject e = table[bucketIndex];
	    table[bucketIndex] = new HashObject(key, value,hash, e);
	    //Not check for resizing
	    }

	void putFornull(Object value){
		addHashObject(0,null,value,0);
	}

	public Object get(Object key){

		int hash = hash(key.hashCode());
		int index = indexFor(hash, table.length);
		HashObject hashobj=table[index];
		for(;hashobj!=null; hashobj=hashobj.next){
			 Object k;
	            if (hashobj.hash == hash && ((k = hashobj.key) == key || key.equals(k)))
	                return hashobj.value;
		}
		return null;
	}
}
