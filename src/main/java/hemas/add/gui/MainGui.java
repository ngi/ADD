package hemas.add.gui;

import hemas.add.dicom.DicomOpener;
import hemas.add.dicom.utils.DicomUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

/**
 * @author Angelo Alfano
 * @since 0.2
 * 
 */

public class MainGui {
	
	Display display; // = new Display();
	final Shell shell; // = new Shell(display);
	FilterTree tree;
	Text txtFile;
	
	public MainGui() {
		
		display = new Display();
		shell = new Shell(display);
		
		GridLayout layout = new GridLayout(5, true);
		GridData gridData;
		shell.setLayout(layout);
		
		Button btnOpen = new Button(shell, SWT.PUSH);
		btnOpen.setText("Open");
		btnOpen.addSelectionListener(new FileOpen());
		gridData = new GridData();
		gridData.widthHint = 100;
		btnOpen.setLayoutData(gridData);
		
		txtFile = new Text(shell, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.horizontalSpan = 4;
		gridData.grabExcessHorizontalSpace = true;
		txtFile.setLayoutData(gridData);
		
		tree = new Tree(shell, SWT.PUSH | SWT.H_SCROLL | SWT.V_SCROLL);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.horizontalSpan = 5;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		
		tree.setLayoutData(gridData);
		tree.setHeaderVisible(true);
		
		// tagAddr +" ["+ tagVR +"] "+ tagName +" ["+ tagValue+"]"
		TreeColumn clmTagName = new TreeColumn(tree, SWT.CENTER);
		clmTagName.setText("DICOM Tag");
		clmTagName.setWidth(200);
		
		TreeColumn clmTagAddr = new TreeColumn(tree, SWT.CENTER);
		clmTagAddr.setText("DICOM Addr");
		clmTagAddr.setWidth(100);
		
		TreeColumn clmVR = new TreeColumn(tree, SWT.CENTER);
		clmVR.setText("VR");
		clmVR.setWidth(50);
		
		TreeColumn clmVM = new TreeColumn(tree, SWT.CENTER);
		clmVM.setText("VM");
		clmVM.setWidth(50);

		TreeColumn clmValue = new TreeColumn(tree, SWT.LEFT);
		clmValue.setText("DICOM Value");
		clmValue.setWidth(450);
		
	    shell.pack();
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		display.dispose();
	}
	
	class FileOpen implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent arg0) {}

		public void widgetSelected(SelectionEvent arg0) {
			FileDialog fd = new FileDialog(shell, SWT.OPEN);
			fd.setText("Open");
			fd.setFilterPath("/Users/ngi/Documents/workspace/add");
			String[] filterExt = { "*.dcm", "*.dicom", "*.*" };
			fd.setFilterExtensions(filterExt);
			String selected = fd.open();

			if (selected != null) {
	        	try {
		        	DicomOpener dcmOpener = new DicomOpener(selected);
		      		tree.removeAll();
		        	DicomUtils.listHeader(tree, dcmOpener.getDcmObj());
		        	txtFile.setText(selected);
	        	} catch (Exception e) {
	        		// TODO msg di errore
	        	}
	        }
		}
	}
	
	public static void main(String[] args) {
		new MainGui();	
	}
}
