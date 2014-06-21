package com.xyz.collection;

import java.io.Serializable;
import java.util.Collection;

public class NewLinkedListImpl implements Serializable{
	public static class Pointer<Object>{
		Object obj;
		Pointer<Object> next;
		Pointer<Object> previous;
		
		public Pointer(Object obj, Pointer<Object> next,
				Pointer<Object> previous) {
			super();
			this.obj = obj;
			this.next = next;
			this.previous = previous;
		}
		
	}
	
	Pointer<Object> headPointer=new Pointer(null,null,null);
	private int size=0;
	
	 public NewLinkedListImpl() {
		 headPointer.next = headPointer.previous = headPointer;
	    }
	 
	 //Just need to add in future
	public boolean addObj(Object[] e){
		
		for(int i=0;i<e.length;i++){
		
			if(size>0){
				
				Pointer<Object> predecessor=get(size);
				Pointer<Object> newPointer=new Pointer(e[i],null,predecessor);	
				newPointer.next=newPointer;
				predecessor.next=newPointer;
				size++;
			}
			else{
				headPointer.obj=e[i];
				size++;
			}
		}
		
		return false;
	}
	
	private Pointer<Object> get(int index){
		
		Pointer<Object> successor =null;
		successor=headPointer;
		for(int i=0;i<index;i++){			
			successor=successor.next;
		}
		return successor;
	}

  public Object getObject(int index){
		
		Pointer<Object> successor =null;
		successor=headPointer;
		for(int i=0;i<index;i++){			
			successor=successor.next;
		}
		return successor.obj;
	}
}
