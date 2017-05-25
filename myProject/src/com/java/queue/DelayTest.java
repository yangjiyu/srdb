package com.java.queue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DelayTest<T> {
    private  List<T> list;
    
    public List<T> getList(){
    	return list;
    }
    
    public void setList(List<T> list){
    	this.list=list;
    }
    
    
    public static void main(String[] args) {
    	System.out.println(System.nanoTime());
    	List<String> list=new ArrayList<String>();
    	Method[]  m=list.getClass().getDeclaredMethods();
    	for(Method me:m){
    	System.out.println(me.getName()+","+me.getReturnType());
    	}
	}
}
