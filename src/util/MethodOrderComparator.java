package util;

import java.util.Comparator;

import metamodel.Vertex;

public class MethodOrderComparator implements Comparator<Vertex> {

	@Override
	public int compare(Vertex v1, Vertex v2) {
		// TODO Auto-generated method stub
		if(v1!=null && v2!=null){
			if(v1.getInArc()<v2.getInArc()){
				return -1;
			}
			if(v1.getInArc()>v2.getInArc()){
				return 1;
			}
		}
		
        return 0;

	}

}
