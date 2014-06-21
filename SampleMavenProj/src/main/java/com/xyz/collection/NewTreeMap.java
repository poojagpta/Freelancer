package com.xyz.collection;

public class NewTreeMap {

	
	    
	public static class MapEntry{
		Object key;
        Object value;
        MapEntry left = null;
        /**
		 * @return the key
		 */
		public Object getKey() {
			return key;
		}

		/**
		 * @param key the key to set
		 */
		public void setKey(Object key) {
			this.key = key;
		}

		/**
		 * @return the value
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * @param value the value to set
		 */
		public Object setValue(Object value) {
			Object oldValue=this.value;
			this.value = value;
			return oldValue;
		}

		MapEntry right = null;
        MapEntry parent;
    
		
        public MapEntry(Object key, Object value, MapEntry parent) {
			super();
			this.key = key;
			this.value = value;
			this.parent = parent;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "MapEntry [key=" + key + ", value=" + value + "]";
		}
	}
	
	  
	   private transient MapEntry root = null;
	   private int size=0;
	   


	public Object put(Object key,Object value){
		   
		   MapEntry t=root;
		   
		   if(t==null){
			   
			   root=new  MapEntry(key,value,null);
			   size=1;
			   return null;
		   }
		   
		   
		   MapEntry parent =null;
		   int cmp=0;
           if(key==null) throw new NullPointerException();		   
           Comparable k = (Comparable) key;
           do {
               parent = t;
               cmp = k.compareTo(t.key);
               if (cmp < 0)
                   t = t.left;
               else if (cmp > 0)
                   t = t.right;
               else
                   return t.setValue(value); //If value already exists
           } while (t != null);
		   
           MapEntry e = new MapEntry(key, value, parent);
           if (cmp < 0)
               parent.left = e;
           else
               parent.right = e;
		   
           size++;
		   return null;
	   }
	  
	public MapEntry getEntry(Object key) {
	        
		// Offload comparator-based version for sake of performance
	        if (key == null)
	            throw new NullPointerException();
		Comparable k = (Comparable) key;
		    MapEntry p = root;
	        while (p != null) {
	            int cmp = k.compareTo(p.key);
	            if (cmp < 0)
	                p = p.left;
	            else if (cmp > 0)
	                p = p.right;
	            else
	                return p;
	        }
	        return null;
	    }
	
}
