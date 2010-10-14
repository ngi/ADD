package hemas.add.dicom.utils;

import java.util.Iterator;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.util.TagUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * @author Angelo Alfano
 * @since 0.1
 *
 */ 
public class DicomUtils {

	public static void listHeader(Tree parent, DicomObject dcmObj) {
		listHeader((Object) parent, dcmObj);
	}
	
	private static void listHeader(Object parentobj, DicomObject dcmObj) {
		Iterator<DicomElement> iter = dcmObj.datasetIterator();

		while (iter.hasNext()) {
			DicomElement element = iter.next();

			int tag = element.tag();

			String tagName = dcmObj.nameOf(tag);
			String tagAddr = TagUtils.toString(tag);
			String tagVR = dcmObj.vrOf(tag).toString();
			int vm = dcmObj.vm(tag);
			String tagVM = Integer.toString(vm);

			TreeItem item = null;
			if (parentobj instanceof TreeItem) {
				item = new TreeItem((TreeItem) parentobj, SWT.NONE);
			} else if (parentobj instanceof Tree) {
				item = new TreeItem((Tree) parentobj, SWT.NONE);
			}
			
			
			if (tagVR.equals("SQ")) {
				item.setText(new String[] { tagName, tagAddr, tagVR, tagVM, "" });
				if (element.hasItems()) {
					for (int ii = 0; ii < element.countItems(); ii++) {
						TreeItem subitem = new TreeItem(item, SWT.NONE);
						subitem.setText(new String[] { Integer.toString(ii) });
						listHeader(subitem, element.getDicomObject());
					}
				}
			} else {
				String tagValue;
				if (vm > 1) {
					String[] tagValues =  dcmObj.getStrings(tag);
					tagValue = joinString(tagValues, "/");
				} else {
					tagValue = dcmObj.getString(tag);
				}
				item.setText(new String[] { tagName, tagAddr, tagVR, tagVM, tagValue });

			}

		}

	}
	
	public static String joinString(String[] array, String delimiter) {
		if (array.length == 0)
			return "";
		StringBuffer buffer = new StringBuffer();
		buffer.append(array[0]);
		for (int ii = 1; ii < array.length; ii++)
			buffer.append(delimiter + array[ii]);
		return buffer.toString();
	}

}
