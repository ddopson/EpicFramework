/**
 * @author Derek Johnson
 * 
 * EpicFacebookUser represents a Facebook user and a set of basic
 * properties returned from a Facebook Graph API call to '/me'.
 *
 */

package com.epic.framework.common.util;

import com.epic.framework.common.Ui2.JSON.JSONObject;

public class EpicFacebookUser {
	/**
	 * Facebook display name--e.g. a users real name.
	 */
	public String displayName;
	
	/**
	 * Either the users raw Facebook ID ('546633'), or their chosen handle ('myfacebookname')
	 * if the user chose to set one on Facebook.
	 */
	public String facebookId;
	
	/**
	 * Raw JSON object representing a Facebook user. This can be used by advanced users to
	 * object more information about the user not provided by EpicSocial utility methods.
	 */
	public JSONObject rawJsonObject;
	
	/**
	 * Create an EpicFacebookUser from the response from the Graph API call to '/me'.
	 * 
	 * @param facebookObject The raw object returned from the Facebook Graph API call 
	 * to '/me'
	 * 
	 */
	protected EpicFacebookUser(JSONObject facebookObject) {}
	
	public EpicFacebookUser() {}

}
