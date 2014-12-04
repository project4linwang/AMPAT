package metamodel;

import java.util.LinkedList;
import java.util.List;
/***
 * 
 * Represent the class element object in the project
 * @author linwang
 *
 */
public class ClassElement {

	
	private String name;
	private String packageName;
	private List<FieldElement> fieldlist=new LinkedList<FieldElement>();
	/***
	 * The list of methods which are defined in this class
	 */
	private List<MetaMethod> methodList=new LinkedList<MetaMethod>();
	/***
	 * The list of classes which are invoked by this class
	 */
	private List<String> invokedClasses=new LinkedList<String>();
	/***
	 * 
	 * @param name : the name of the class
	 */
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setMethodList(List<MetaMethod> methodList) {
		this.methodList = methodList;
	}
	public List<MetaMethod> getMethodList() {
		return methodList;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setInvokedClasses(List<String> invokedClasses) {
		this.invokedClasses = invokedClasses;
	}
	public List<String> getInvokedClasses() {
		return invokedClasses;
	}
	public List<FieldElement> getFieldlist() {
		return fieldlist;
	}
	public void setFieldlist(List<FieldElement> fieldlist) {
		this.fieldlist = fieldlist;
	}
	
	
}
