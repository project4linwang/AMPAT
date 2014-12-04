package ampat.views;


import java.util.List;

import javax.swing.JOptionPane;

import metamodel.FeatureData;
import metamodel.TmpDatabase;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

import core.algorithm.Features;

import ampat.forms.FilterView;
import ampat.popup.actions.MetricsAnalysisAction;



/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class FeaturesView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "ampat.views.FeaturesView";

	private TableViewer viewer;
	private Features fe=new Features();
	Table tb;
	private Action action1;
	private Action action2;
	private Action action3;
	private Action action4;
	private Action action5;
	private Action doubleClickAction;
	private TmpDatabase tdb=TmpDatabase.getdbInstance();
	private Logger log=Logger.getLogger(FeaturesView.class);
	
	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
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
					return f.getName();
				case 1:
					return Double.toString(f.getMSP());
				case 2:
					return Integer.toString(f.getExFIV());
				case 3:
					return Integer.toString(f.getCBO());
				case 4:
					return Integer.toString(f.getWMC());
				case 5:
					return Integer.toString(f.getFIV());
				case 6:
					return Integer.toString(f.getFOV());
				case 7:
					return Double.toString(f.getMIC());
				case 8:
					return Double.toString(f.getMEC());
				case 9:
					return Integer.toString(f.getDIT());
				case 10:
					return Integer.toString(f.getRV());
				case 11:
					return Integer.toString(f.getAC());
				case 12:
					return Integer.toString(f.getRFC());
				case 13:
					return Double.toString(f.getLCOM());
				case 14:
					return Integer.toString(f.getNP());
				case 15:
					return Integer.toString(f.getNOC());
				case 16:
					return Double.toString(f.getTF());
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

	/**
	 * The constructor.
	 */
	public FeaturesView() {
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
        TableColumn col2= new TableColumn(tb,SWT.CENTER);
        TableColumn col3= new TableColumn(tb,SWT.CENTER);
        TableColumn col4= new TableColumn(tb,SWT.CENTER);
        TableColumn col5= new TableColumn(tb,SWT.CENTER);
        TableColumn col6= new TableColumn(tb,SWT.CENTER);
        TableColumn col7= new TableColumn(tb,SWT.CENTER);
        TableColumn col8= new TableColumn(tb,SWT.CENTER);
        TableColumn col9= new TableColumn(tb,SWT.CENTER);
        TableColumn col10= new TableColumn(tb,SWT.CENTER);
        TableColumn col11= new TableColumn(tb,SWT.CENTER);
        TableColumn col12= new TableColumn(tb,SWT.CENTER);
        TableColumn col13= new TableColumn(tb,SWT.CENTER);
        TableColumn col14= new TableColumn(tb,SWT.CENTER);
        TableColumn col15= new TableColumn(tb,SWT.CENTER);
        TableColumn col16=new TableColumn(tb,SWT.CENTER);
        TableColumn col17=new TableColumn(tb,SWT.CENTER);
        col1.setText("Name");
        
        col2.setText("MSP");
        
        col3.setText("ExFIV");
        col4.setText("CBO");
        col5.setText("WMC");
        col6.setText("FIV");
        col7.setText("FOV");
        col8.setText("MIC");
        col9.setText("MEC");
        col10.setText("DIT");
        col11.setText("RV");
        col12.setText("AC");
        col13.setText("RFC");
        col14.setText("LCOM");
        col15.setText("NP");
        col16.setText("NOC");
        col17.setText("TF");
        
        
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
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private List<FeatureData> getElements(){
		return tdb.getFeatureList();
	}
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				FeaturesView.this.fillContextMenu(manager);
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
		manager.add(action4);
		manager.add(new Separator());
		manager.add(action5);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(action3);
		manager.add(action4);
		manager.add(action5);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(action3);
		manager.add(action4);
		manager.add(action5);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				tdb.getExportFList().clear();
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
	
				Object[] possibleValues = { 1, 2,3,4,5,6,7,8,9,10};      
				int selectedValue = (Integer)JOptionPane.showInputDialog(null,  "Choose the min number of Fan-in value", "Filter by FIV",       
				    JOptionPane.INFORMATION_MESSAGE, null,    
				    possibleValues, possibleValues[0]);
				if (selectedValue != 0) {
					
					fe.filterByFIV(selectedValue);
					viewer.setInput(tdb.getExportFList());
					for(int i=0,n=tb.getColumnCount();i<n;i++){
			        	tb.getColumn(i).pack();
			        }
				}
				else{
					showMessage("Action 2 executed");
				}
				
				//
			}
		};
		action3.setText("Filter By FIV");
		action3.setToolTipText("Filter Features By Fan-In value");
		action3.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
		
		action4 = new Action() {
			public void run() {
	
				Object[] possibleValues = { 1, 2,3,4,5,6,7,8,9,10};      
				int selectedValue = (Integer)JOptionPane.showInputDialog(null,  "Choose the min number of Affected classes", "Filter by AC",       
				    JOptionPane.INFORMATION_MESSAGE, null,    
				    possibleValues, possibleValues[0]);
				if(selectedValue!=0){
					fe.filterByAC(selectedValue);
					viewer.setInput(tdb.getExportFList());
					for(int i=0,n=tb.getColumnCount();i<n;i++){
			        	tb.getColumn(i).pack();
			        }
				}
				
				//showMessage("Action 2 executed");
			}
		};
		action4.setText("Filter By AC");
		action4.setToolTipText("Filter Features By Affected Class");
		action4.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
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
		//
//		doubleClickAction = new Action() {
//			public void run() {
//				ISelection selection = viewer.getSelection();
//				Object obj = ((IStructuredSelection)selection).getFirstElement();
//				showMessage("Double-click detected on "+obj.toString());
//			}
//		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Features View",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	private void SaveFeatures(){
		logInit();
		/**
		 * Filter the elements which can not be crosscutting concerns.
		 */
		fe.filterConcerns();
		List<FeatureData> fdlist=tdb.getExportFList();
		if(fdlist!=null){
			
			for(FeatureData fd:fdlist){
				StringBuilder sb=new StringBuilder();
				
				sb.append(fd.getMSP()+" ");
				sb.append(fd.getExFIV()+" ");
				
				sb.append(fd.getCBO()+" ");
				sb.append(fd.getWMC()+" ");
				sb.append(fd.getFIV()+" ");
				sb.append(fd.getFOV()+" ");
				sb.append(fd.getMIC()+" ");
				sb.append(fd.getMEC()+" ");
				sb.append(fd.getDIT()+" ");				
				sb.append(fd.getRV()+" ");
				sb.append(fd.getAC()+" ");
				sb.append(fd.getRFC()+" ");
				sb.append(fd.getLCOM()+" ");
				sb.append(fd.getNP()+" ");
				sb.append(fd.getNOC()+" ");
				sb.append(fd.getTF()+" ");
				sb.append(fd.getName());
				log.info(sb.toString()); 
			}
		}
	}

	private void logInit() {
		// TODO Auto-generated method stub
		log.removeAllAppenders();
		log.info(16);
		log.info("#n"+" "+"MSP"+" "+"ExFIV"+" "+"CBO"+" "+"WMC"+" "+"FIV"+" "+"FOV"+" "+"MIC"+" "+"MEC"+" "+"DIT"+" "+"RV"+" "+"AC"+" "+"RFC"+" "+"LCOM"+" "+"NP"+" "+"NOC"+" "+"TF");
	}
	 
}