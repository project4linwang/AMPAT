package core.analysis;



import java.util.LinkedList;
import java.util.List;

import metamodel.ElementFactory;
import metamodel.FieldElement;
import metamodel.MetaMethod;
import metamodel.MethodElement;
import metamodel.Vertex;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class MethodAnalysis implements MethodVisitor{

	private MethodElement methodelement;
	private ElementFactory factory=ElementFactory.getFactory();
	public MethodAnalysis(MethodElement me){
		methodelement=me;
	}
	@Override
	public AnnotationVisitor visitAnnotation(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnnotationVisitor visitAnnotationDefault() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void visitAttribute(Attribute arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitCode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		// TODO Auto-generated method stub
		FieldElement fe=factory.createField(owner, name);
		methodelement.addAccessField(fe);
		//System.out.println(methodelement.getM_method().getOwner()+"."+methodelement.getM_method().getName()+" Field: "+owner+" " +name+" "+desc+" "+opcode);
	}

	@Override
	public void visitIincInsn(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitInsn(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitIntInsn(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitJumpInsn(int arg0, Label arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitLabel(Label arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitLdcInsn(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		// TODO Auto-generated method stub
		//System.out.println("Line: "+line+" Instruction: "+start.toString());
	}

	@Override
	public void visitLocalVariable(String arg0, String arg1, String arg2,
			Label arg3, Label arg4, int arg5) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitLookupSwitchInsn(Label arg0, int[] arg1, Label[] arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitMaxs(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		// TODO Auto-generated method stub
		MetaMethod mm=new MetaMethod();
		mm.setName(name);
		mm.setOwner(owner);
		mm.setDesc(desc);
		
		Vertex vt=new Vertex();
		vt.setMmethod(mm);
		vt.setInvoketype(opcode);
		methodelement.addInvokesMethod(mm);
		MethodElement invokedMethod= factory.createMethod(mm);		
		invokedMethod.addInvokedMethod(methodelement.getM_method());
		methodelement.getOrderPath().add(vt);
		//System.out.println("Name: "+name+" Desc: "+desc);
	}

	@Override
	public void visitMultiANewArrayInsn(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AnnotationVisitor visitParameterAnnotation(int arg0, String arg1,
			boolean arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void visitTableSwitchInsn(int arg0, int arg1, Label arg2,
			Label[] arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitTryCatchBlock(Label arg0, Label arg1, Label arg2,
			String arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitTypeInsn(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitVarInsn(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
    
	
}
