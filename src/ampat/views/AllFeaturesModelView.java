package ampat.views;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import metamodel.FeatureData;
import metamodel.TmpDatabase;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import ampat.views.ModelView.NameSorter;
import ampat.views.ModelView.ViewLabelProvider;
import core.algorithm.Features;

public class AllFeaturesModelView extends ViewPart{
	public static final String ID = "ampat.views.AllFeaturesModelView";

	private TableViewer viewer;
	private Features fe=new Features();
	Table tb;
	private Action action1;
	private Action action2;
	private Action action3;
	private Action action5;
	//private Action doubleClickAction;
	private TmpDatabase tdb=TmpDatabase.getdbInstance();
	private Logger log=Logger.getLogger(ModelView.class);
	private boolean fivshow;
	private boolean mspshow;
	private boolean fovshow;
	private boolean cpshow;
	private boolean micshow;
	private boolean mecshow;
	private boolean acshow;
	private boolean rvshow;
	private boolean npmshow;
	private boolean comshow;
	private boolean msigshow;
	private boolean bvectorshow;
	private boolean sigtokenshow;
	private StringBuilder titleBuilder=new StringBuilder();
	private int featureNumber=0;
	private List<String> features=new LinkedList<String>();
	private int bvnumber=0;
	private int sigtokennumber=0;
	private boolean bvstate=false;
	private boolean tokenstate=false;
	private int featurenumber=0;
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			return new String[] {""};
			//return null;
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			//return getText(obj);
			
			if(obj!=null && obj!=""){
				
				FeatureData f=(FeatureData)obj;
				switch(index){
				case 0:
					featurenumber=0;
					bvstate=false;
					tokenstate=false;
					bvnumber=0;
					sigtokennumber=0;
					return f.getName();
				
				}
				if(index>=1 ){
					if(!bvstate && !tokenstate){
						String sf="";
						if(bvnumber!=0){
							sf=features.get(index-1-(bvnumber-1));
						}
						else if(sigtokennumber!=0){
							sf=features.get(index-1-(sigtokennumber-1));
						}
						else{
							sf=features.get(index-1);
						}
							
							
						
						if(sf.equals("MSP")){
							featurenumber++;
							return Double.toString(f.getMSP()); 
						}
						else if(sf.equals("FIV")){
							featurenumber++;
							return Integer.toString(f.getFIV());
						}
						else if(sf.equals("FOV")){
							featurenumber++;
							return Integer.toString(f.getFOV());
						}
						else if(sf.equals("CP")){
							featurenumber++;
							return Double.toString(f.getC_p());
						}
						else if(sf.equals("MIC")){
							featurenumber++;
							return Double.toString(f.getMIC());
						}
						else if(sf.equals("MEC")){
							featurenumber++;
							return Double.toString(f.getMEC());
						}
						else if(sf.equals("AC")){
							featurenumber++;
							return Integer.toString(f.getAC());
						}
						else if(sf.equals("RV")){
							featurenumber++;
							return Integer.toString(f.getRV());
						}
						else if(sf.equals("NPM")){
							featurenumber++;
							return Integer.toString(f.getNP());
						}
						else if(sf.equals("COM")){
							featurenumber++;
							return Double.toString(f.getCOM());
						}
						else if(sf.equals("MSig")){
							featurenumber++;
							return Integer.toString(f.getMSig());
						}
						else if(sf.equals("BVector")){
							featurenumber++;
							bvstate=true;
							tokenstate=false;
							bvnumber=f.getBVector().size();
							return Integer.toString(f.getBVector().get(index-featurenumber-sigtokennumber));
							
						}
						else if(sf.equals("SigTokens")){
							featurenumber++;
							bvstate=false;
							tokenstate=true;
							sigtokennumber=f.getSigTokens().size();
							return Integer.toString(f.getSigTokens().get(0));
						}
					}
					else if(bvstate){
						bvnumber=f.getBVector().size();
						int bindex=0;
						if(sigtokennumber!=0){
							bindex=index-(featurenumber-1)-sigtokennumber;
						}
						else{
							bindex=index-featurenumber;
						}
						
						if(bindex==f.getBVector().size()-1){
							bvstate=false;
						}
						return Integer.toString(f.getBVector().get(bindex));
						
					}
					else if(tokenstate){
						sigtokennumber=f.getSigTokens().size();
						int sindex=0;
						if(bvnumber!=0){
							 sindex=index-(featurenumber-1)-bvnumber;
						}
						else{
							 sindex=index-featurenumber;
						}
						
						if(sindex==f.getSigTokens().size()-1){
							tokenstate=false;
						}
						return Integer.toString(f.getSigTokens().get(sindex));
					}
					
					
				}
			}
			
			return "";
		}
		public Image getColumnImage(Object obj, int index) {
			//return getImage(obj);
			return null;
		}
		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().
					getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
	class NameSorter extends ViewerSorter {
	}
	public AllFeaturesModelView(){
		
	}
	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL); 
		//viewer.setContentProvider(new ViewContentProvider());
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		//viewer.setInput(getViewSite());
	    tb=viewer.getTable();
        tb.setLayoutData(new GridData(GridData.FILL_BOTH)); 
        
        TableColumn col1= new TableColumn(tb,SWT.CENTER);              
        //TableColumn col2= new TableColumn(tb,SWT.CENTER);
       
        col1.setText("Methods");
        //col2.setText("FIV");            
       
        
//        TableColumnLayout layout=new TableColumnLayout();
//        parent.setLayout(layout);
//        layout.setColumnData(col1, new ColumnWeightData(200));
        for(int i=0,n=tb.getColumnCount();i<n;i++){
        	tb.getColumn(i).pack();
        }
        tb.setHeaderVisible(true);
        tb.setLinesVisible(true);
        viewer.setInput(getElements());
        //getSite().setSelectionProvider(viewer);
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "AMPAT.viewer");
		makeActions();
		hookContextMenu();
		//hookDoubleClickAction();
		contributeToActionBars();
	}
	private void setTBColumn(){
		if(tb.getColumnCount()<=1){
			titleBuilder.append("#n");
			if(mspshow){
				TableColumn col= new TableColumn(tb,SWT.CENTER);
				col.setText("MSP");
				titleBuilder.append(" "+"MSP");
				featureNumber++;
				features.add("MSP");
			}
			if(fivshow){
				TableColumn col= new TableColumn(tb,SWT.CENTER);
				col.setText("FIV");
				titleBuilder.append(" "+"FIV");
				featureNumber++;
				features.add("FIV");
			}
			if(fovshow){
				TableColumn col= new TableColumn(tb,SWT.CENTER);
				col.setText("FOV");
				titleBuilder.append(" "+"FOV");
				featureNumber++;
				features.add("FOV");
			}
		    if(cpshow){
		    	TableColumn col= new TableColumn(tb,SWT.CENTER);
				col.setText("CP");
				titleBuilder.append(" "+"CP");	
				featureNumber++;
				features.add("CP");
		    }
		    if(micshow){
		    	TableColumn col= new TableColumn(tb,SWT.CENTER);
				col.setText("MIC");
				titleBuilder.append(" "+"MIC");
				featureNumber++;
				features.add("MIC");
		    }
		    if(mecshow){
		    	TableColumn col= new TableColumn(tb,SWT.CENTER);
				col.setText("MEC");
				titleBuilder.append(" "+"MEC");
				featureNumber++;
				features.add("MEC");
		    }
		    if(acshow){
		    	TableColumn col= new TableColumn(tb,SWT.CENTER);
				col.setText("AC");
				titleBuilder.append(" "+"AC");
				featureNumber++;
				features.add("AC");
		    }
		    if(rvshow){
		    	TableColumn col= new TableColumn(tb,SWT.CENTER);
				col.setText("RV");
				titleBuilder.append(" "+"RV");
				featureNumber++;
				features.add("RV");
		    }
		    if(npmshow){
		    	TableColumn col= new TableColumn(tb,SWT.CENTER);
				col.setText("NPM");
				titleBuilder.append(" "+"NPM");
				featureNumber++;
				features.add("NPM");
		    }
		    if(comshow){
		    	TableColumn col= new TableColumn(tb,SWT.CENTER);
				col.setText("COM");
				titleBuilder.append(" "+"COM");
				featureNumber++;
				features.add("COM");
		    }
		    if(msigshow){
		    	TableColumn col= new TableColumn(tb,SWT.CENTER);
				col.setText("MSig");
				titleBuilder.append(" "+"MSig");
				featureNumber++;
				features.add("MSig");
		    }
		    if(bvectorshow){
		    	List<FeatureData> fdlist=tdb.getFeatureList();
				int colNumber=0;
				if(fdlist!=null){
					for(FeatureData fd: fdlist){
						colNumber=fd.getBVector().size();
						break;
					}		
				}
				for(int i=0;i<colNumber;i++){
					TableColumn col= new TableColumn(tb,SWT.CENTER);
					col.setText("B"+i);
				}
				features.add("BVector");
				featureNumber=featureNumber+ fdlist.get(0).getBVector().size();
		    }
		    if(sigtokenshow){
		    	List<FeatureData> fdlist=tdb.getFeatureList();
				int colNumber=0;
				if(fdlist!=null){
					for(FeatureData fd: fdlist){
						colNumber=fd.getSigTokens().size();
						break;
					}		
				}
				for(int i=0;i<colNumber;i++){
					TableColumn col= new TableColumn(tb,SWT.CENTER);
					col.setText("O"+i);
				}
				features.add("SigTokens");
				featureNumber=featureNumber+ fdlist.get(0).getSigTokens().size();
		    }
			
		}
		
			
	}
	private List<FeatureData> getElements(){
		return tdb.getFeatureList();
	}
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				AllFeaturesModelView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
		manager.add(new Separator());
		manager.add(action3);
		manager.add(new Separator());
		
		manager.add(action5);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(action3);
	
		manager.add(action5);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(action3);
		manager.add(action5);
	}
	private void makeActions() {
		action1 = new Action() {
			public void run() {
				tdb.getExportFList().clear();
				setTBColumn();
				List<FeatureData> fdlist=tdb.getFeatureList();
				if(fdlist!=null)
					for(FeatureData fd:fdlist){
						
						tdb.getExportFList().add(fd);
					}
				viewer.setInput(getElements());
				 for(int i=0,n=tb.getColumnCount();i<n;i++){
			        	tb.getColumn(i).pack();
			        }
				//showMessage("Action 1 executed");
				
			}
		};
		action1.setText("Refresh");
		action1.setToolTipText("Refresh Feature view");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_TOOL_REDO ));
		
		action2 = new Action() {
			public void run() {
				tdb.getExportFList().clear();
				viewer.setInput(null);
				for(int i=0,n=tb.getColumnCount();i<n;i++){
		        	tb.getColumn(i).pack();
		        }
				//showMessage("Action 2 executed");
			}
		};
		action2.setText("Clear");
		action2.setToolTipText("Clear Feature View");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_ETOOL_CLEAR));
		//
		action3 = new Action() {
			public void run() {
				
				//Object[] possibleValues = { 1, 2,3,4,5,6,7,8,9,10};      
				//int selectedValue = (Integer)JOptionPane.showInputDialog(null,  "Choose the min number of Fan-in value", "Filter by FIV",       
				    //JOptionPane.INFORMATION_MESSAGE, null,    
				    //possibleValues, possibleValues[0]);
				//if (selectedValue != 0) {
					
					//fe.filterByFIV(selectedValue);
					//viewer.setInput(tdb.getExportFList());
					//for(int i=0,n=tb.getColumnCount();i<n;i++){
			        	//tb.getColumn(i).pack();
			        //}
				//}
				//else{
					//showMessage("Action 2 executed");
				//}
				//"MSP"+" "+"FIV"+" "+"FOV"+" "+"MIC"+" "+"MEC"+" "+"AC"+" "+"CP"+"RV"+"NPM"+"MSig"+"COM"
			    JCheckBox checkbox1 = new JCheckBox("MSP");
			    JCheckBox checkbox2 = new JCheckBox("FIV");
			    JCheckBox checkbox3 = new JCheckBox("FOV");
			    JCheckBox checkbox4 = new JCheckBox("CP");
			    JCheckBox checkbox5 = new JCheckBox("MIC");
			    JCheckBox checkbox6 = new JCheckBox("MEC");
			    JCheckBox checkbox7 = new JCheckBox("AC");
			    JCheckBox checkbox8 = new JCheckBox("RV");
			    JCheckBox checkbox9 = new JCheckBox("NPM");
			    JCheckBox checkbox10 = new JCheckBox("MSig");
			    JCheckBox checkbox11 = new JCheckBox("Com");
			    JCheckBox checkbox12 = new JCheckBox("BVector");
			    JCheckBox checkbox13 = new JCheckBox("SigToken");
			    String message = "Select the subset of features";  
			    Object[] params = {message, checkbox1,checkbox2,checkbox3,checkbox4,checkbox5,checkbox6,checkbox7,checkbox8,checkbox9,checkbox10,checkbox11,checkbox12,checkbox13};  
			    int n = JOptionPane.showConfirmDialog(null, params, "Disconnect Products", JOptionPane.YES_NO_OPTION);  
			    if(n!=1){
			    	fivshow = checkbox2.isSelected();
				    mspshow=checkbox1.isSelected();
				    fovshow=checkbox3.isSelected();
				    cpshow=checkbox4.isSelected();
				    micshow=checkbox5.isSelected();
				    mecshow=checkbox6.isSelected();
				    acshow=checkbox7.isSelected();
				    rvshow=checkbox8.isSelected();
				    npmshow=checkbox9.isSelected();
				    msigshow=checkbox10.isSelected();
				    comshow=checkbox11.isSelected();
				    bvectorshow=checkbox12.isSelected();
				    sigtokenshow=checkbox13.isSelected();
			    }			    
				//
			}
		};
		action3.setText("Select Features");
		action3.setToolTipText("Select the subset of features");
		action3.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
		

		
		action5 = new Action() {
			public void run() {
	
				SaveFeatures();
				showMessage("Save Successed");
			}
		};
		action5.setText("Save");
		action5.setToolTipText("Save to file");
		action5.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_ETOOL_SAVE_EDIT));

	}
//	private void hookDoubleClickAction() {
//		viewer.addDoubleClickListener(new IDoubleClickListener() {
//			public void doubleClick(DoubleClickEvent event) {
//				doubleClickAction.run();
//			}
//		});
//	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Features View",
			message);
	}
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		viewer.getControl().setFocus();
	}
	private void SaveFeatures(){
		
		/**
		 * Filter the elements which can not be crosscutting concerns.
		 */
		fe.filterConcerns();
		List<FeatureData> fdlist=tdb.getExportFList();
		int fs=features.size();
		if(bvectorshow){
			fs=fs-1;
		}
		if(sigtokenshow){
			fs=fs-1;
		}
		int n=fs+fdlist.get(0).getBVector().size()+fdlist.get(0).getSigTokens().size();
		logInit(n);
		
		if(fdlist!=null){
			
			for(FeatureData fd:fdlist){
				StringBuilder sb=new StringBuilder();
				if(mspshow){
					sb.append(fd.getMSP()+" ");
				}
				if(fivshow){
					sb.append(fd.getFIV()+" ");
				}
				if(fovshow){
				  sb.append(fd.getFOV()+" ");
				}
			    if(cpshow){
			    	sb.append(fd.getC_p()+" ");
			    }
			    if(micshow){
			    	sb.append(fd.getMIC()+" ");
			    }
			    if(mecshow){
			    	sb.append(fd.getMEC()+" ");
			    }
			    if(acshow){
			    	sb.append(fd.getAC()+" ");
			    }
			    if(rvshow){
			    	sb.append(fd.getRV()+" ");
			    }
			    if(npmshow){
			    	sb.append(fd.getNP()+" ");
			    }
			    if(comshow){
			    	sb.append(fd.getCOM()+" ");
			    }
			    if(msigshow){
			    	sb.append(fd.getMSig()+" ");
			    }
			    if(bvectorshow){
			    	List<Integer> bvectors=fd.getBVector();
					if(bvectors!=null){
						for(Integer bv:bvectors){
							sb.append(bv+" ");
						}
					}
					
			    }
			    if(sigtokenshow){
			    	List<Integer> sigTokens=fd.getSigTokens();
					
					if(sigTokens!=null){
						for(Integer st:sigTokens){
							sb.append(st+" ");
						}
					}
					
			    }												
			    sb.append(fd.getName());
				
				
				log.info(sb.toString()); 
			}
		}
	}
	private void logInit(int number) {
		// TODO Auto-generated method stub
		log.removeAllAppenders();
		List<FeatureData> fdlist=tdb.getExportFList();
		log.info(number);
		if(bvectorshow){
			int bsize=fdlist.get(0).getBVector().size();
			for(int i=0;i<bsize;i++){
				titleBuilder.append(" "+"B"+i);
			}
		}
		if(sigtokenshow){
			int stsize=fdlist.get(0).getSigTokens().size();
			for(int i=0;i<stsize;i++){
				titleBuilder.append(" "+"O"+i);
			}
		}
		
		log.info(titleBuilder.toString());
		
	}
}
