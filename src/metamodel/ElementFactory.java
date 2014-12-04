package metamodel;

import java.util.List;

public class ElementFactory {
	private static ElementFactory instance;
	private TmpDatabase tdb=TmpDatabase.getdbInstance(); 
	private ElementFactory(){
		
	}
   public static  ElementFactory getFactory(){
	   if(instance==null){
		   instance=new ElementFactory();
		   
	   }
	   return instance;
   }
   /***
    * Create method object
    * @param mmethod is the meta method object
    * @return if the method object has exist then return it, else create a new method object.
    */
   public MethodElement createMethod(MetaMethod mmethod){
	  List<MethodElement> mlist= tdb.getMethodList();
	  if(mlist!=null){
		  for(MethodElement me:mlist){
			  
			  if(me.getM_method().equals(mmethod)){
				  return me;
			  }
		  }
	  }
	  MethodElement method=new MethodElement();
	  method.setM_method(mmethod);
	  addMethod(method);
	  return method;
   }
   public FieldElement createField(String owner,String name){
	   List<FieldElement> felist=tdb.getFieldlist();
	   if(felist!=null){
		   for(FieldElement fe:felist){
			   if(fe.getOwner().equals(owner) && fe.getName().equals(name)){
				   return fe;
			   }
		   }
	   }
	   FieldElement fe=new FieldElement();
	   fe.setOwner(owner);
	   fe.setName(name);
	   addField(fe);
	   return fe;
	   
   }
   private void addField(FieldElement fe){
	   if(!containsField(fe)){
		   tdb.getFieldlist().add(fe);
	   }
   }
   private boolean containsField(FieldElement fe){
	   List<FieldElement> felist=tdb.getFieldlist();
	   if(felist!=null){
		   for(FieldElement file:felist){
			   if(file.equals(fe)){
				   return true;
			   }
		   }
	   }
	   return false;
   }
   /***
    * Add a method object to the method list
    * @param me is the method element object
    */
   private void addMethod(MethodElement me){
	   if(!containsMethod(me)){
		   tdb.getMethodList().add(me);
	   }
   }
   /***
    * Check whether the method elements has exist.
    * @param me is a method element object
    * @return
    */
   private boolean containsMethod(MethodElement me){
	   List<MethodElement> melist=tdb.getMethodList();
	   if(melist!=null){
		   for(MethodElement method: melist){
			   if(method.getM_method().equals(me.getM_method())){
				  return true; 
			   }
		   }
	   }
	   return false;
   }
   
}
