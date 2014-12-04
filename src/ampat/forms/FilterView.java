package ampat.forms;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.part.ViewPart;

public class FilterView extends ViewPart {

	private Form form;
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
        form=new Form(parent,1);
        form.setText("Filter Features");
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
        form.setFocus();
	}
	public void dispose(){
		super.dispose();
	}

}
