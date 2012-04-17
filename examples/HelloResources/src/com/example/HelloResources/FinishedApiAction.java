package com.example.HelloResources;

import com.epic.framework.common.EpicInflatableClass;
import com.epic.framework.common.Ui2.EpicAction;
import com.epic.framework.common.Ui2.EpicRestRequest;
import com.epic.framework.common.Ui2.EpicRestRequest.RequestInstance;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.StringHelper;

@EpicInflatableClass
public class FinishedApiAction extends EpicRestRequest.RestResponseAction {
	@Override
	public void run(RequestInstance request) {
		EpicLog.i("Request Finished. " + StringHelper.namedArgList("code", request.responseCode, "msg", request.responseMessage, "body", request.responseBody));
	}
}
