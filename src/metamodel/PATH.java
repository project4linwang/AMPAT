package metamodel;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/***
 * The invoke path, this path has only one direction.
 * The path is contained by a number of vertices with order.
 * @author linwang
 *
 */
public class PATH {

	//private HashMap<MetaMethod,Vertex> hashmap=new HashMap<MetaMethod,Vertex>();
	private Queue<Vertex> qe=new LinkedList<Vertex>();
	
	private int index=0;	
	/***
	 * Add a vertex into the path, a vertex represents a method.
	 * @param e is the vertex
	 * @return
	 */
	public boolean add(Vertex e) {
		// TODO Auto-generated method stub
		
		e.setInArc(index);
		qe.add(e);
		//hashmap.put(e.getMmethod(), e);
		index++;
		return true;
	}
	

	public void clear() {
		// TODO Auto-generated method stub
		//hashmap.clear();
		qe.clear();
	}
    public Vertex getVertex(MetaMethod mm){
    	
    	 Iterator<Vertex> iter= qe.iterator();
    	 while(iter.hasNext()){
    		 Vertex v=iter.next();
    		 if(v.getMmethod().equals(mm)){
    			 return v;
    		 }
    	 }
    	 return null;
    }
	

	

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		
		return qe.isEmpty();
	}

	public Iterator<Vertex> iterator() {
		// TODO Auto-generated method stub		
		return qe.iterator();
	}
					
	public int size() {
		// TODO Auto-generated method stub
		return qe.size();
	}
	
	
}
