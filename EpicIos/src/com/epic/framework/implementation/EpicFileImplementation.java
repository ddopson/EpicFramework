package com.epic.framework.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.xmlvm.iphone.NSBundle;

import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.exceptions.EpicInvalidArgumentException;

public class EpicFileImplementation {

	public static void executeFile(String apkPath) {
		
	}
	
    public static InputStream openInputStream(String filename) {
        // Split image name into parts
        int lastdot = filename.lastIndexOf('.');
        int lastpath = filename.lastIndexOf('/');
        String directory = "", resource = "", type = "";
        if (lastdot < 0) {
        	throw EpicFail.invalid_argument("File name should be in the form PATH/FILENAME.EXT");
        }
        if (lastpath >= 0) {
            directory = filename.substring(0, lastpath);
            resource = filename.substring(lastpath + 1, lastdot);
        } else {
            resource = filename.substring(0, lastdot);
        }
        type = filename.substring(lastdot + 1);

        String path = NSBundle.mainBundle().pathForResource(resource, type, directory);
        if (path == null) {
            // Not found
            EpicLog.e("Unable to locate file with name " + filename);
            return null;
        }

        File f = new File(path);
        InputStream s = null;
		try {
			s = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO: this should be an exception
			EpicLog.e("FileNotFound: " + e.toString() + " for " + path);
			return null;
          }
        return s;
    }

}
