package ampat.popup.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import metamodel.ClassElement;
import metamodel.FeatureData;
import metamodel.FieldElement;
import metamodel.MetaMethod;
import metamodel.MethodElement;
import metamodel.PATH;
import metamodel.TmpDatabase;
import metamodel.Vertex;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import core.algorithm.AnalysisMethods;
import core.algorithm.Features;
import core.algorithm.InvokeSequence;
import core.analysis.ClassAnalysis;



public class MetricsAnalysisAction implements IObjectActionDelegate {

	private Shell shell;
	private String path;
    private TmpDatabase tdb=TmpDatabase.getdbInstance();
    //private Logger log=Logger.getLogger(MetricsAnalysisAction.class);
	/**
	 * Constructor for Action1.
	 */
	public MetricsAnalysisAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		tdb.getClasslist().clear();
		tdb.getMethodList().clear();
		tdb.getFeatureList().clear();
		tdb.getFieldlist().clear();
		List<File> files=searchFiles(path);
		for(File file:files){
			ClassAnalysis cp=new ClassAnalysis();
			try {
				InputStream is=new FileInputStream(file);
				ClassReader cr=new ClassReader(is);
				cr.accept(cp, false);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		PrintPath();
		//PrintFeatureMetrics();
		RecordData();
		MessageDialog.openInformation(
			shell,
			"AMPAT",
			"Analysis was finished.");
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		IStructuredSelection aSelection=null;
		if (selection instanceof IStructuredSelection)
			 aSelection = (IStructuredSelection) selection;
		IProject project=convertSelection(aSelection);	
		if(project!=null){
			path=project.getLocation().toOSString()+"/bin";
		}
	    
	}
	private IProject convertSelection(IStructuredSelection structuredSelection)
	{
	  IProject project = null;
	  Object element = structuredSelection.getFirstElement();

	  if (element instanceof IResource)
	  {
	    project = ((IResource) element).getProject();
	  }
	  else if (element instanceof IJavaElement)
	  {
	    IJavaElement javaElement = (IJavaElement) element;
	    project = javaElement.getJavaProject().getProject();
	  }
	  else if (element instanceof IAdaptable)
	  {
	    IAdaptable adaptable = (IAdaptable) element;
	    IWorkbenchAdapter adapter = (IWorkbenchAdapter) adaptable.getAdapter(IWorkbenchAdapter.class);
	    if (adapter != null)
	    {
	      Object parent = adapter.getParent(adaptable);
	      if (parent instanceof IJavaProject)
	      {
	        IJavaProject javaProject = (IJavaProject) parent;
	        project = javaProject.getProject();
	      }
	    }
	  }
	 

	  return project;
	}
	private void PrintPath(){
		List<ClassElement> celist= tdb.getClasslist();
		InvokeSequence is=new InvokeSequence();
		if(celist.size()>0){
			for(ClassElement ce: celist){
				String cname="";
				if(ce.getPackageName()!=null){
				    cname=ce.getPackageName()+"/"+ce.getName();
				    
				}
				else{
					cname=ce.getName();
				
				}
				System.out.println("Class: "+cname);	
				
				List<MetaMethod> mmlist=ce.getMethodList();
				List<MethodElement> melist=tdb.getMethodList(mmlist);
				if(melist.size()>0){
					for(MethodElement me: melist){
						List<String> args=me.getM_method().getArgumentList();
						System.out.println("Method: "+me.getM_method().getName());
					
						Iterator<Vertex> it= is.getSequence(me);
						if(it!=null){
							while(it.hasNext()){
								Vertex vt=it.next();
								System.out.println("     "+vt.getInArc()+" Method :"+vt.getMmethod().getOwner()+"."+vt.getMmethod().getName()+" "+vt.getMmethod().getDesc()+" "+vt.getInvoketype());
							}
						}			
					}
				}
			}
		}
	}
	private List<File> searchFiles(String apath){
		List<File> result=new LinkedList<File>();
		if(apath!=""){
			File root=new File(apath);
			FilenameFilter dew=new FilenameFilter(){
				public boolean accept(File funtoosh, String name){
					return name.endsWith(".class");
				}
			};
			File[] filesOrdirs=root.listFiles();
			for(int i=0;i<filesOrdirs.length;i++){				
				if(filesOrdirs[i].isDirectory()){
					result.addAll(searchFiles(filesOrdirs[i].getAbsolutePath()));
				}
			}
			File[] fs= root.listFiles(dew);
			for(File f: fs){
				result.add(f);
			}
			
			
		}
		return result;
	}
	private void RecordData(){
		List<MethodElement> melist= tdb.getMethodList();
		if(melist!=null){
			if(melist.size()>0){
				AnalysisMethods am=new AnalysisMethods();
				tdb.SetTotalTokenList(am.calculateTotalTokens(melist));
				for(MethodElement me:melist){
					if(!me.getM_method().getName().equals("<init>")){			
						if(filtering(me.getM_method().getOwner())){
							Features fe=new Features();
							int fiv=fe.calculateFIV(me.getM_method().getName(),me.getM_method().getOwner());
							if(fiv>=0){
								FeatureData f_data=new FeatureData();
								String cname=me.getM_method().getOwner();
								String mname=me.getM_method().getName();
								int dit= fe.calculateDIT(cname);
								int noc=fe.calculateNOC(cname);
								int cbo=fe.calculateCBO(cname);
								int wmc=fe.calculateWMC(cname);
								int fov=fe.calculateFOV(mname, cname);
								double mic=fe.calculateMIC(mname, cname);
								double mec=fe.calculateMEC(mname, cname);
								double msp=fe.calculateMSP(mname, cname);
								int rv=fe.getRV(mname, cname);
								int ac=fe.calculateAC(mname, cname);
								int rfc=fe.calculateRFC(cname);
								double lcom=fe.calculateLCOM(cname);
								int np=fe.calculateNP(mname, cname);
								double tf=fe.calculateTF(mname, cname);
								int exfiv=fe.calculateExFIV(mname, cname);
								int IF=fiv*fov;
								int Msig=fe.calculateMSig(mname, cname);
								double COM=fe.calculateCOM(mname, cname);
								int C_p=(fiv*fov)*(fiv*fov);
								List<Integer> BVector=fe.calculateBVector(mname, cname);
								List<Integer> sigTokens=fe.calculateSigTokens(mname, cname);
								f_data.setName(cname+"."+mname);
								f_data.setDIT(dit);
								f_data.setNOC(noc);
								f_data.setCBO(cbo);
								f_data.setWMC(wmc);
								f_data.setFIV(fiv);
								f_data.setFOV(fov);
								f_data.setC_p(C_p);
								f_data.setMIC(mic);
								f_data.setMEC(mec);
								f_data.setMSP(msp);
								f_data.setRV(rv);
								f_data.setAC(ac);
								f_data.setRFC(rfc);
								f_data.setLCOM(lcom);
								f_data.setNP(np);
								f_data.setTF(tf);
								f_data.setExFIV(exfiv);
								f_data.setIF(IF);
								f_data.setMSig(Msig);
								f_data.setCOM(COM);
								f_data.setBVector(BVector);
								f_data.setSigTokens(sigTokens);
								tdb.getFeatureList().add(f_data);
							}
						}
					}
				}
			}
		}
	}
	private void PrintFeatureMetrics(){
		//logInit();
		List<MethodElement> melist= tdb.getMethodList();
		if(melist!=null){
			if(melist.size()>0){
				for(MethodElement me:melist){
					if(!me.getM_method().getName().equals("<init>")){			
						if(filtering(me.getM_method().getOwner())){
							Features fe=new Features();
							int fiv=fe.calculateFIV(me.getM_method().getName(),me.getM_method().getOwner());
							if(fiv>=0){
								//info=new StringBuilder();
								FeatureData f_data=new FeatureData();
								String cname=me.getM_method().getOwner();
								String mname=me.getM_method().getName();
								
								System.out.println("Candidate Method: "+cname+"."+mname);
								int dit= fe.calculateDIT(cname);
								int noc=fe.calculateNOC(cname);
								int cbo=fe.calculateCBO(cname);
								int wmc=fe.calculateWMC(cname);
								//int fiv=fe.calculateFIV(mname, cname);
								int fov=fe.calculateFOV(mname, cname);
								double mic=fe.calculateMIC(mname, cname);
								double mec=fe.calculateMEC(mname, cname);
								double msp=fe.calculateMSP(mname, cname);
								int rv=fe.getRV(mname, cname);
								int ac=fe.calculateAC(mname, cname);
								int rfc=fe.calculateRFC(cname);
								double lcom=fe.calculateLCOM(cname);
								int np=fe.calculateNP(mname, cname);
								f_data.setName(cname+"."+mname);
								f_data.setDIT(dit);
								f_data.setNOC(noc);
								f_data.setCBO(cbo);
								f_data.setWMC(wmc);
								f_data.setFIV(fiv);
								f_data.setFOV(fov);
								f_data.setMIC(mic);
								f_data.setMEC(mec);
								f_data.setMSP(msp);
								f_data.setRV(rv);
								f_data.setAC(ac);
								f_data.setRFC(rfc);
								f_data.setLCOM(lcom);
								f_data.setNP(np);
								tdb.getFeatureList().add(f_data);
//								System.out.println("  "+"Class: "+cname+" Depth of Inheritance Tree is "+dit);
//								System.out.println("  "+"Class: "+cname+" Number of Children of a Class is "+noc);
//								System.out.println("  "+"Class: "+cname+" Coupling Between Object Classes is "+cbo);	
//								System.out.println("  "+"Class: "+cname+" Weighted Methods per Class is "+wmc);
//								System.out.println("  "+"Method: "+cname+"."+mname+" Fan-in Value is "+fiv);
//								System.out.println("  "+"Method: "+cname+"."+mname+" Fan-out Value is "+fov);
//								System.out.println("  "+"Method: "+cname+"."+mname+" Method Internal Coupling is "+mic);
//								System.out.println("  "+"Method: "+cname+"."+mname+" Method External Coupling is "+mec);
//								System.out.println("  "+"Method: "+cname+"."+mname+" Method Spread is "+msp);
//								System.out.println("  "+"Method: "+cname+"."+mname+" Return Type is "+rv);
//								System.out.println("  "+"Method: "+cname+"."+mname+" Affected Classes is "+ac);
//								System.out.println("  "+"Class: "+cname+" Repose to a class is "+rfc);
//								System.out.println("  "+"Class: "+cname+" Lack of Cohesion on Methods is "+lcom);
//								System.out.println("  "+"Method: "+cname+"."+mname+" Number of Parameter is "+np);
								//info.append(cname+"."+mname);
								//System.out.println(info.toString());
								//log.info(info.toString());
							}
						}
						
					}
					
				}
			}
		}
		
	}
	private boolean filtering(String owner){
		String regstr="\\bjava/io\\b|\\bjava/lang\\b|\\bjava/util\\b|\\bjava/lang/Object/toString\\b";
		
		Pattern pattern=Pattern.compile(regstr);
		Matcher matcher=pattern.matcher(owner);
		if(matcher.find()){
			return false;
		}
		else{
			return true;
		}
	}
}
