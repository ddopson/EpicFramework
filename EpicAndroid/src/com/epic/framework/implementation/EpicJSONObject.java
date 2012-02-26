package com.epic.framework.implementation;

import org.json.JSONException;
import org.json.JSONObject;

import com.epic.framework.common.util.exceptions.EpicJSONException;

public class EpicJSONObject {
	JSONObject jsonObject;

	public EpicJSONObject(String json) {
		try {
			jsonObject = new JSONObject(json);
		} catch (JSONException e) {
			throw new RuntimeException("Creation of JSON object failed!");
		}
	}

	public String getString(String string) throws EpicJSONException {
		try {
			return jsonObject.getString(string);
		} catch (JSONException e) {
			throw new EpicJSONException("EpicJSONException getting " + string, e);
		}
	}

	public int getInt(String string) {
		return 0;
	}
}
