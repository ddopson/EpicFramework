package com.example.HelloResources;

import com.epic.framework.common.EpicInflatableClass;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.Ui2.EpicAction;
import com.epic.framework.common.Ui2.EpicRestRequest;
import com.epic.framework.common.Ui2.Registry;
import com.epic.framework.common.Ui2.EpicRestRequest.RequestInstance;
import com.epic.framework.common.Ui2.NativeWidgets.EpicNativeTextFieldWidget;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.StringHelper;
import com.epic.resources.EpicConfig;

@EpicInflatableClass
public class FinishedApiAction extends EpicRestRequest.RestResponseAction {
	@Override
	public void run(RequestInstance request) {
		EpicLog.i("Request Finished. " + StringHelper.namedArgList("code", request.responseCode, "msg", request.responseMessage, "body", request.responseBody));
		EpicNativeTextFieldWidget text = (EpicNativeTextFieldWidget)Registry.get("named/thethingy");
		EpicLog.i("Text = " + text.getText());
		EpicPlatform.changeScreen(EpicConfig.screens.Login.get());
	}
}
