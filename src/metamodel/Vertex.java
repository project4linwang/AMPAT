package metamodel;

/***
 * 
 * @author linwang
 *
 */
public class Vertex {
	/***
	 * the meta method object
	 */
   private MetaMethod mmethod;
   /***
    * the invoke type:
    * Invoke dynamic : 186
    * Invoke Interface : 185
    * Invoke Special: 183
    * Invoke Static : 184
    * Invoke Virtual: 182
    */
   private int invoketype;
   /***
    * The arc of the vertex, it represents the invoke order.
    */
   private int inArc;

public void setInArc(int inArc) {
	this.inArc = inArc;
}
public int getInArc() {
	return inArc;
}
public void setInvoketype(int invoketype) {
	this.invoketype = invoketype;
}
public int getInvoketype() {
	return invoketype;
}
public void setMmethod(MetaMethod mmethod) {
	this.mmethod = mmethod;
}
public MetaMethod getMmethod() {
	return mmethod;
}
   
   
}
