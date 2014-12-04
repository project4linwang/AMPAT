package metamodel;

import java.util.LinkedList;
import java.util.List;
/***
 * The method Element, contains the relationship info of the method
 * @author linwang
 *
 */
public class MethodElement {

	/***
	 * The meta method element
	 */
	private MetaMethod m_method;	
	/***
	 * The invoke path of the method
	 */
	private PATH orderPath=new PATH();
	/***
	 * The list of the methods, which invoke this method
	 */
	private List<MetaMethod> invokedMethods=new LinkedList<MetaMethod>();
	/***
	 * The list of the methods, which are invoked by this method
	 */
	private List<MetaMethod> invokesMethods=new LinkedList<MetaMethod>();
	
    private List<FieldElement> accessFields=new LinkedList<FieldElement>();
    
    
    public List<FieldElement> getAccessFields(){
    	return accessFields;
    }
	public void setOrderPath(PATH orderPath) {
		this.orderPath = orderPath;
	}
	public PATH getOrderPath() {
		return orderPath;
	}
	
	public void setM_method(MetaMethod m_method) {
		this.m_method = m_method;
	}
	public MetaMethod getM_method() {
		return m_method;
	}
	
	public List<MetaMethod> getInvokedMethods() {
		return invokedMethods;
	}
	
	public List<MetaMethod> getInvokesMethods() {
		return invokesMethods;
	}
	public boolean addAccessField(FieldElement fe){
		if(!containsField(fe)){
			accessFields.add(fe);
			return true;
		}
		else{
			return false;
		}
	}
	private boolean containsField(FieldElement fe) {
		// TODO Auto-generated method stub
		if(accessFields!=null){
			for(FieldElement field:accessFields){
				if(field.equals(fe)){
					return true;
				}
			}
		}
		return false;
	}
	/***
	 * Add an invoked method
	 * @param mm is the method which invoke this method
	 * @return
	 */
	public boolean addInvokedMethod(MetaMethod mm){
		if(!containsMethod(invokedMethods,mm)){
			invokedMethods.add(mm);
			return true;
		}
		else{
			return false;
		}
	}
	/***
	 * 
	 * @param methodlist A list of meta methods
	 * @param mm a meta method
	 * @return If the meta method object is in the list, then return true, else return false
	 */
	private boolean containsMethod(List<MetaMethod> methodlist,MetaMethod mm){
		   
		   if(methodlist!=null){
			   for(MetaMethod method: methodlist){
				   if(method.equals(mm)){
					  return true; 
				   }
			   }
		   }
		   return false;
	}
	/***
	 * Add an invoke method
	 * @param mm is the method which is invoked by this method
	 * @return
	 */
	public boolean addInvokesMethod(MetaMethod mm){
		if(!containsMethod(invokesMethods,mm)){
	        invokesMethods.add(mm);
	        return true;
		}
		else{
			return false;
		}
	}
	
	
}
