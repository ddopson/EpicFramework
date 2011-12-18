package com.epic.framework.Ui;

import java.io.InputStream;
import com.epic.framework.util.EpicBufferedReader;
import com.epic.framework.util.EpicFileImplementation;

public class EpicFile {
	private final String filename;
	public EpicFile(String filename) {
		this.filename = filename;
	}
	
	public InputStream openAsInputStream() {
		return EpicFileImplementation.openInputStream(filename);
	}
	
	public EpicBufferedReader openAsBufferedReader() {
		return new EpicBufferedReader( openAsInputStream() );
	}

	public static void executeFile(String apkPath) {
		EpicFileImplementation.executeFile(apkPath);
	}
	
	public String getFilename() {
		return filename;
	}
}
