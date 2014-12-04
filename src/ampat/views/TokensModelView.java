package ampat.views;

import java.util.List;

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

public class TokensModelView extends ViewPart{
	public static final String ID = "ampat.views.TokensModelView";
	private TableViewer viewer;
	private Features fe=new Features();
	Table tb;
	private Action action1;
	private Action action2;
	private Action action3;
	private Action action5;
	private Action doubleClickAction;
	private TmpDatabase tdb=TmpDatabase.getdbInstance();
	private Logger log=Logger.getLogger(ModelView.class);
	
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
				}
				if(index>=1){
					return Integer.toString(f.getSigTokens().get(index-1));
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
	public TokensModelView(){
		
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
	private void setTBColumn(){
		if(tb.getColumnCount()<=1){
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
				TokensModelView.this.fillContextMenu(manager);
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
		logInit(fdlist.get(0).getSigTokens().size());
		if(fdlist!=null){
			
			for(FeatureData fd:fdlist){
				StringBuilder sb=new StringBuilder();
																
				List<Integer> sigTokens=fd.getSigTokens();
				
				if(sigTokens!=null){
					for(Integer st:sigTokens){
						sb.append(st+" ");
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
		
		log.info(number+1);
		StringBuilder sb=new StringBuilder();
		String title="#n";
		sb.append(title);
		for(int i=0;i<number;i++){
			sb.append(" "+"O"+i);
		}
		log.info(sb.toString());
	}
}
