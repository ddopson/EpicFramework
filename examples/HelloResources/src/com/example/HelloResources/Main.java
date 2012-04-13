package com.example.HelloResources;


import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import com.epic.framework.vendor.org.json.simple.*;
import com.epic.framework.common.Ui.EpicFile;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.Ui2.Registry;
import com.epic.resources.EpicFiles;
import com.epic.resources.EpicImages;

public class Main {

	/**
	 * @param args
	 * @throws IOException
	 * @throws JSONException
	 * @throws InterruptedException
	 * @throws org.json.JSONException 
	 */
	public static void main(String[] args) throws IOException, JSONException, InterruptedException {
		EpicImages.register();
		Registry.processConfig(EpicFiles.config);
		EpicPlatform.Main(args);
	}
}
