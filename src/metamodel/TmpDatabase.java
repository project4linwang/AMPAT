package metamodel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/***
 * A temporary database object, using singleton design pattern
 * @author linwang
 *
 */
public class TmpDatabase {

	private static TmpDatabase tdb;
	private List<MethodElement> methodlist=new LinkedList<MethodElement>();
	private List<ClassElement> classlist=new LinkedList<ClassElement>();
	private Map<String,String> generalizationMaps=new HashMap<String,String>();
	private List<FieldElement> fieldlist=new LinkedList<FieldElement>();
	private List<FeatureData> flist=new LinkedList<FeatureData>();
	private List<FeatureData> exportflist=new LinkedList<FeatureData>();
	private List<String> totalTokenList=new LinkedList<String>();
	private TmpDatabase(){
		
	}
	public static TmpDatabase getdbInstance(){
		if(tdb==null){
			tdb=new TmpDatabase();
			return tdb;
		}
		else{
			return tdb;
		}
	}
	public void SetTotalTokenList(List<String> tokens){
		totalTokenList=tokens;
	}
	public List<String> getTotalTokenList(){
		return totalTokenList;
	}
	public List<FeatureData> getExportFList(){
		return exportflist;
	}
	public List<FeatureData> getFeatureList(){
		return flist;
	}
	public List<FieldElement> getFieldlist(){
		return fieldlist;
	}
	/***
	 * 
	 * @return the list of class elements
	 */
	public List<ClassElement> getClasslist() {
		return classlist;
	}
	/***
	 * 
	 * @return the list of method elements
	 */
	public List<MethodElement> getMethodList(){
		return methodlist;
	}
	/***
	 * 
	 * @param mmlist is the list of meta method elements
	 * @return the list of method elements, which are related to the meta method object
	 */
	public List<MethodElement> getMethodList(List<MetaMethod> mmlist){
		List<MethodElement> results=new LinkedList<MethodElement>();
		if(mmlist!=null){
			for(MetaMethod mm:mmlist){
				for(MethodElement me:methodlist){
					if(me.getM_method().equals(mm)){
						results.add(me);
					}
				}
			}
		}
		return results;
	}
	/***
	 * 
	 * @param mm is a meta method object
	 * @return the method element object
	 */
	public MethodElement getMethod(MetaMethod mm){
	   if(mm!=null){
		   for(MethodElement me:methodlist){
				if(me.getM_method().equals(mm)){
					return me;
				}
			}
	   }
	   return null;
	}
	public Map<String,String> getGeneralizationmps() {
		// TODO Auto-generated method stub
		return generalizationMaps;
	}
	
	
}
