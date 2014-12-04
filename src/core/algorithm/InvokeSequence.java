package core.algorithm;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import metamodel.MethodElement;
import metamodel.PATH;
import metamodel.Vertex;

public class InvokeSequence {
	
	public Iterator<Vertex> getSequence(MethodElement me){		
		Iterator<Vertex> iter=null;
		if(me!=null){
			PATH mypath=me.getOrderPath();			
			if(mypath.size()>0){
				 iter= mypath.iterator();
				return iter;
			
			}			
		}
		return iter;	
		
	}	
	
}
