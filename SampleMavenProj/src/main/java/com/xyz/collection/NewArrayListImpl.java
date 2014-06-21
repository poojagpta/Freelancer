package com.xyz.collection;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

public class NewArrayListImpl implements Externalizable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Object[] obj=null;
    public int size;
    public int offset;
	
	public NewArrayListImpl() {
		this(10);
	}
	
	public NewArrayListImpl(int capacity) {
		super();
		obj=new Object[capacity];
		size=capacity;
		offset=0;
	}
	
	public void add(Object a){
		int newLength=size+10;
		if(offset==size-1){
			obj=Arrays.copyOf(obj,newLength);
		}
		obj[offset]=a;
		offset++;
	}
	
	public Object get(int i){
		return obj[i];
	}
	
	 /**
     * Save the state of the <tt>ArrayList</tt> instance to a stream (that
     * is, serialize it).
     *
     * @serialData The length of the array backing the <tt>ArrayList</tt>
     *             instance is emitted (int), followed by all of its elements
     *             (each an <tt>Object</tt>) in the proper order.
     */
    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException{
	s.defaultWriteObject();

        // Write out array length
        s.writeInt(size);

	// Write out all elements in the proper order.
	   for (int i=0; i<size; i++)
            s.writeObject(obj[i]);
 }

    /**
     * Reconstitute the <tt>ArrayList</tt> instance from a stream (that is,
     * deserialize it).
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
	// Read in size, and any hidden stuff
	s.defaultReadObject();

        // Read in array length and allocate array
        int arrayLength = s.readInt();
        Object[] a = obj = new Object[arrayLength];

	// Read in all elements in the proper order.
	for (int i=0; i<size; i++)
            a[i] = s.readObject();
    }

	@Override
	public void writeExternal(ObjectOutput s) throws IOException {
		// TODO Auto-generated method stub
		//s.defaultWriteObject();

        // Write out array length
        s.writeInt(size);

	// Write out all elements in the proper order.
	   for (int i=0; i<size; i++)
            s.writeObject(obj[i]);
	   
	}

	@Override
	public void readExternal(ObjectInput s) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		
		int arrayLength = s.readInt();
        Object[] a = obj = new Object[arrayLength];

	// Read in all elements in the proper order.
	for (int i=0; i<size; i++)
            a[i] = s.readObject();
		
	}
	
	
}
