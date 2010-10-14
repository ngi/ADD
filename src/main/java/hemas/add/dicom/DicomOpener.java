package hemas.add.dicom;

import java.io.File;
import java.io.IOException;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;

/**
 * @author Angelo Alfano
 * @since 0.1
 *
 */ 
public class DicomOpener {

	private DicomObject dcmObj = null;

	public DicomOpener(String filepath) {

		File file = new File(filepath);
		if (file.isFile() && file.canRead()) {
			DicomInputStream dis = null;
			try {
				dis = new DicomInputStream(file);
				dcmObj = dis.readDicomObject();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				dis.close();
			} catch (IOException ignore) {

			}

		}
	}

	public DicomObject getDcmObj() {
		return dcmObj;
	}

}
