package core.analysis;

import java.util.LinkedList;
import java.util.List;



import metamodel.ClassElement;
import metamodel.ElementFactory;
import metamodel.FieldElement;
import metamodel.MetaMethod;
import metamodel.MethodElement;
import metamodel.TmpDatabase;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;


public class ClassAnalysis implements ClassVisitor{
	private TmpDatabase tdb=TmpDatabase.getdbInstance();
	private ClassElement ce;
	private List<MetaMethod> melist;
	private ElementFactory factory=ElementFactory.getFactory();
	
	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		// TODO Auto-generated method stub
		ce=new ClassElement();
		String reg="\\/";
		String[] sts=name.split(reg);
		if(sts.length>1){
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<sts.length-1;i++){
				sb.append(sts[i]+"/");
			}
			sb.deleteCharAt(sb.length()-1);
			ce.setPackageName(sb.toString());
		}		
		ce.setName(sts[sts.length-1]);		
		melist=new LinkedList<MetaMethod>();	
		tdb.getGeneralizationmps().put(name, superName);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void visitAttribute(Attribute arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitEnd() {
		// TODO Auto-generated method stub
		 ce.setMethodList(melist);
	     tdb.getClasslist().add(ce);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		// TODO Auto-generated method stub
		FieldElement fe=new FieldElement();
		fe.setOwner(ce.getPackageName()+"/"+ce.getName());
		fe.setName(name);
		fe.setDesc(desc);
		ce.getFieldlist().add(fe);
		tdb.getFieldlist().add(fe);
		System.out.println("  Filed: "+name+" "+desc+" "+signature+" "+access+" Value: "+value);
		return null;
	}

	@Override
	public void visitInnerClass(String arg0, String arg1, String arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		// TODO Auto-generated method stub
		MetaMethod mm=new MetaMethod();
		mm.setDesc(desc);
		mm.setName(name);
		if(ce.getPackageName()==null){
			mm.setOwner(ce.getName());
		}
		else{
		    mm.setOwner(ce.getPackageName()+"/"+ce.getName());	
		}
		MethodElement me=factory.createMethod(mm);
		MethodAnalysis mp=new MethodAnalysis(me);
		melist.add(mm);		
		return mp;
	}

	@Override
	public void visitOuterClass(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitSource(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

}
