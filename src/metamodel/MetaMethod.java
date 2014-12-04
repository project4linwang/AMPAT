package metamodel;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.objectweb.asm.Type;

/***
 * The Meta Method Element, only has method property, do not contain the relationship of the method.
 *
 * @author linwang
 *
 */
public class MetaMethod {

	/***
	 * The full name of the class, which defines this method.
	 */
	private String owner;
	/***
	 * The name of the method
	 */
	private String name;
	/***
	 * The argument and return type
	 */
	private String desc;
	
    private List<String> argumentlist=new LinkedList<String>();
    private int signature=0;
    
    public int getSignature(){
    	if(signature==0){
    		setSignature();
    	}
    	
    	return signature;
    }
    public List<String> getArgumentList(){
    	return argumentlist;
    }
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getOwner() {
		return owner;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setDesc(String desc) {		
		this.desc = desc;
		analysisdesc(desc);
		
	}
	public String getDesc() {
		return desc;
	}
	/***
	 * 
	 * @param mm is the MetaMethod object
	 * @return if the two methods are same return true, else return false
	 */
	public boolean equals(MetaMethod mm){
		if(this.owner.equals(mm.getOwner()) && this.name.equals(mm.getName()) && this.desc.equals(mm.getDesc())){
			return true;
		}
		else{
			return false;
		}
	}
	
	private void analysisdesc(String desc){
		
		Type[] types= Type.getArgumentTypes(desc);
		for(int i=0; i<types.length;i++){			
			String arg=types[i].getClassName().replace('.', '/');
			argumentlist.add(arg);			
		}   	 
   }
   private void setSignature(){
	   String sig=name+desc;
	   Charset cset=Charset.forName("US-ASCII");
	   byte[] bytes=sig.getBytes(cset);
	   System.out.print(name+": ");
	   int sigvalue=0;
	   for(byte bt:bytes){
		   sigvalue+=(int)bt;
		   //System.out.print(bt+",");
	   }
	   this.signature=sigvalue;
	   System.out.println(signature);
			   
   }
	

}
