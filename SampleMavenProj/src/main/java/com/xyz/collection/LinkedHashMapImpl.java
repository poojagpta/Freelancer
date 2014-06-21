package com.xyz.collection;



public class LinkedHashMapImpl extends NewHashMap{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private final boolean accessOrder;
    private transient HashObject header;
	
    public LinkedHashMapImpl(int initialCapacity, float loadFactor) {
        
    	super(initialCapacity, loadFactor);
        accessOrder = false;
    }
    
    
    public LinkedHashMapImpl() {
            super();
            accessOrder = false;
    }
       
      
    
	 private static class HashObject extends NewHashMap.HashObject {
		 
		 // These fields comprise the doubly linked list used for iteration.
		 HashObject before, after;

		 HashObject(Object key, Object value,int hash, NewHashMap.HashObject next) {
	            super(key, value,hash, next);
	        }
		 
		 /**
	         * Removes this entry from the linked list.
	         */
	        private void remove() {
	            before.after = after;
	            after.before = before;
	        }

	        /**
	         * Inserts this entry before the specified existing entry in the list.
	         */
	        private void addBefore(HashObject existingEntry) {
	            after  = existingEntry;
	            before = existingEntry.before;
	            before.after = this;
	            after.before = this;
	        }

	        /**
	         * This method is invoked by the superclass whenever the value
	         * of a pre-existing entry is read by Map.get or modified by Map.set.
	         * If the enclosing Map is access-ordered, it moves the entry
	         * to the end of the list; otherwise, it does nothing.
	         */
	        void recordAccess(NewHashMap m) {
	        	LinkedHashMapImpl lm = (LinkedHashMapImpl)m;
	            if (lm.accessOrder) {
	                lm.modCount++;
	                remove();
	                addBefore(lm.header);
	            }
	        }

	        void recordRemoval(NewHashMap m) {
	            remove();
	        }		 
	 }	 

}
