package com.epic.framework.implementation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.Toast;

import com.epic.framework.common.Ui.EpicClickListener;
import com.epic.framework.common.Ui.EpicDialogBuilder;
import com.epic.framework.common.Ui.EpicNotification;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.util.EpicHttpResponse;
import com.epic.framework.common.util.EpicHttpResponseHandler;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.EpicSocial;
import com.epic.framework.common.util.EpicSocial.EpicSocialSignInCompletionHandler;
import com.epic.framework.common.util.StringHelper;
import com.epic.framework.common.util.exceptions.EpicUnexpectedDataException;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class EpicSocialImplementation {
	public static void beginLogin(final EpicSocialSignInCompletionHandler doAfter) {
		final Account[] accounts = AccountManager.get(EpicAndroidActivity.getCurrentAndroidActivity()).getAccounts();
		HashSet<String> emails = new HashSet<String>();
		for(int i = 0; i < accounts.length; ++i) {
			if(EMAIL_ADDRESS_PATTERN.matcher(accounts[i].name).matches()) {
				emails.add(accounts[i].name);
			}
		}
		
		final String[] valid_emails = emails.toArray(new String[emails.size()]);

		if(valid_emails.length == 0) {
			EpicLog.w("UHOH - No accounts on device");
			new EpicDialogBuilder()
			.setMessage("This is odd. You don't seem to have any email accounts configured on your device. Please add one and try again.")
			.setPositiveButton("OK", EpicClickListener.NoOp)
			.show();
		} else if(valid_emails.length == 1) {
			String identity = valid_emails[0];
			doAfter.onSignedIn(identity);
		} else {
			AlertDialog.Builder alert = new AlertDialog.Builder(EpicAndroidActivity.getCurrentAndroidActivity());
			alert.setTitle("Please select an account to use with Word Farm:");
			//alert.setMessage("Your account is used to find friends, submit scores, and store your progress. Your privacy is important to us, and your account will never be sold or shared with third parties:");
			alert.setCancelable(false);
			alert.setSingleChoiceItems(valid_emails,-1, new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					String identity = valid_emails[which];
					dialog.dismiss();
//					PushPreferences prefs = PushManager.shared().getPreferences();
//			        prefs.setSoundEnabled(false);
//			        prefs.setVibrateEnabled(false);
//			        
//			        PlayerState.getState().setPushId(prefs.getPushId());
					doAfter.onSignedIn(identity);
				}
			});
			alert.setNeutralButton("Why?", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					AlertDialog.Builder privacyWarning = new AlertDialog.Builder(EpicAndroidActivity.getCurrentAndroidActivity());
					privacyWarning.setTitle("Why do you need my email address?");
					privacyWarning.setMessage("Your account is used to find friends, submit scores, and store your progress. Your privacy is important to us, and your account will never be sold or shared with third parties.");
					privacyWarning.setNeutralButton("Back", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							beginLogin(doAfter);
						}
					});
					privacyWarning.show();
				}
			});
			alert.show();
		}	 
	}

	public static String getPlatformId() {
		return "a";
	}

	public static String getDisplayNameFromEmail(String email) {
		Uri uri = Uri.withAppendedPath(Email.CONTENT_LOOKUP_URI, Uri.encode(email));
		Cursor c = EpicAndroidActivity.getCurrentAndroidActivity().getContentResolver().query(uri, new String[]{Phone.DISPLAY_NAME}, null, null, null);

		try {
			if(c.moveToFirst()) {
				String name = c.getString(0);
				if(name == null) {
					EpicLog.e("Name was null for " + email);
					return email;
				}

				EpicLog.i("Got " + name + " for " + email);
				return name;
			} else {
				return email;
			}
		} catch(Exception e) {
			EpicLog.e(e.toString());
			return email;
		} finally {
			c.close();
		}
	}

	public static String getEmailList() {
		int limit = 100;
		int found = 0;
		StringBuilder sb = new StringBuilder();
		Cursor cursor =	EpicAndroidActivity.getCurrentAndroidActivity().getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		while (cursor.moveToNext()) {
			String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

			Cursor emails = EpicAndroidActivity.getCurrentAndroidActivity().getContentResolver().query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,
					null,
					null);

			while (emails.moveToNext()) {
				String emailAddress = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				if(found++ < limit) {
					sb.append(emailAddress+",");
				} else {
					emails.close();
					cursor.close();
					sb.deleteCharAt(sb.length()-1);
					return sb.toString();
				}
			}

			emails.close();

		}

		cursor.close();
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();		
	}

	public static String[] getDisplayNamesFromEmails(String[] names_to_lookup) {
		String[] results = new String[names_to_lookup.length];
		for(int i = 0; i < names_to_lookup.length; ++i) {
			results[i] = EpicSocial.getDisplayNameFromEmail(names_to_lookup[i]);
		}

		return results;
	}

	public static String chooseContact() {
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		EpicAndroidActivity.getCurrentAndroidActivity().startActivityForResult(intent, EpicAndroidActivity.CONTACT_PICK);  
		return null;
	}

	public static void selectFromEmailList(String[] strings) {
		final String[] strings_copy = strings;

		if(strings == null || strings.length == 0) {
			new EpicDialogBuilder()
			.setMessage("This contact does not have any email addresses. Please enter one for them and try again.")
			.setPositiveButton("OK", EpicClickListener.NoOp)
			.show();
		} else {
			AlertDialog.Builder alert = new AlertDialog.Builder(EpicAndroidActivity.getCurrentAndroidActivity());
			alert.setTitle("Which email address would you like to use for the invite?");
			alert.setSingleChoiceItems(strings,-1, new DialogInterface.OnClickListener()
			{

				public void onClick(DialogInterface dialog, int which) 
				{
					try {
						((ScreenScores) EpicPlatform.currentScreen).onProcessContactChosen(strings_copy[which]);	
					} catch(ClassCastException cce) {
						EpicLog.e("Class cast during contact selection: " + cce.toString());
					} finally {
						dialog.dismiss();
					}
					
				}
			});

			alert.show();
		}	    
	}

	private final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
			"[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
			"\\@" +
			"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
			"(" +
			"\\." +
			"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
			")+"
	);

	public static void getFacebookFriendList() {
		Facebook f = EpicAndroidActivity.getCurrentAndroidActivity().facebook;
		
		if(f.isSessionValid()) {
			// get list
			try {
				displayFacebookFriendList();
			} catch (Exception e) {
				EpicLog.e(e.toString());
			}
		} else {
			// authorize then post on success
			f.authorize(EpicAndroidActivity.getCurrentAndroidActivity(),
				new String[] { "publish_stream" },
				6969, 
				new DialogListener() {
					public void onFacebookError(FacebookError e) {
						EpicLog.e(e.toString());
						EpicPlatform.doToastNotification(new EpicNotification("Problem connecting to Facebook", new String[] { "We were unable to connect to Facebook.", "Please try again or contact support." }, EpicImages.icon, 6));
					}
					
					public void onError(DialogError e) {
						EpicLog.e(e.toString());
						EpicPlatform.doToastNotification(new EpicNotification("Problem connecting to Facebook", new String[] { "We were unable to connect to Facebook.", "Please try again or contact support." }, EpicImages.icon, 6));
					}
					
					public void onComplete(Bundle values) {
						// get list
						displayFacebookFriendList();
					}
					
					public void onCancel() {
						EpicLog.w("Cancelled FB connect");
					}
				});
		}
	}
	
	private static void displayFacebookFriendList() {
		Facebook f = EpicAndroidActivity.getCurrentAndroidActivity().facebook;
		
		try {
			if(PlayerState.getState().fbid == null) {
				String jsonUser = f.request("me");
				JSONObject obj = new JSONObject(jsonUser);
				PlayerState.setFBID(obj.optString("id"));
			}
			
			String response = f.request("me/friends");

			EpicLog.i("Facebook response received.");
			//Parse JSON Data
            JSONObject json = new JSONObject(response);
            
            //Get the JSONArry from our response JSONObject
            JSONArray friendArray = json.getJSONArray("data");
            
            StringBuffer fids = new StringBuffer(10000);
            //Loop through our JSONArray
            int friendCount = 0;
            String fId, fNm;
            JSONObject friend;
            for (int i = 0;i<friendArray.length();i++){
                //Get a JSONObject from the JSONArray
                friend = friendArray.getJSONObject(i);
                //Extract the strings from the JSONObject
                fId = friend.getString("id");
                fNm = friend.getString("name");
                //Set the values to our arrays
                fids.append(fNm);
                fids.append(';');
                fids.append(fId);
                fids.append(':');
                friendCount ++;
            }
            
            final ProgressDialog d = new ProgressDialog(EpicAndroidActivity.getCurrentAndroidActivity());
            d.setMessage("Searching " + friendCount + " friends for Word Farmers...");
            d.show();
            
            WordsHttp.findFriends(fids.toString(), new EpicHttpResponseHandler() {
				public void handleResponse(EpicHttpResponse response) {
					d.dismiss();
					if(response.body.length() < 2) {
//						EpicNotification n = new EpicNotification("No Friends Playing Word Farm", new String[] {"Post your scores to get friends playing!" });
//						EpicPlatform.doToastNotification(n);
						AlertDialog.Builder dialognf = new AlertDialog.Builder(EpicAndroidActivity.getCurrentAndroidActivity());
						dialognf.setTitle("No Facebook Friends Playing");
						dialognf.setMessage("None of your friends on Facebook are playing Word Farm--yet! Tell them about Word Farm on Facebook and get referral bonuses for every friend that starts playing!");
			            dialognf.setPositiveButton("Not Now", new OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
			            
						dialognf.setNegativeButton("Tell Your Friends", new OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								EpicSocial.postToFacebook("Play Word Farm with me!", "http://wordfarmgame.com?from=" + PlayerState.getIdentity(), "Can you beat me in Word Farm? " + (PlayerState.getState().longestWord.length() > 0 ? "The longest word I have found is " + PlayerState.getState().longestWordString() : ""), "http://epicbeta.com/wf/images/logo.png", null);
							}
						});
			            
						dialognf.show();
						return;
					}

					String[] parts = response.body.split(";");
					
					final String[] names = new String[parts.length];
					final String[] ids = new String[parts.length];
					
					for(int i = 0; i < parts.length; ++i) {
						String[] pieces = parts[i].split(":");
						names[i] = pieces[0];
						ids[i] = pieces[1];
					}
		            
		            AlertDialog.Builder dialog = new AlertDialog.Builder(EpicAndroidActivity.getCurrentAndroidActivity());
		            dialog.setTitle(names.length + (names.length == 1 ? " friend" : " friends") + " playing. Click to challenge:");
		            dialog.setItems(names, new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
//							d.show();
//							final int which_copy = which;
//							WordsHttp.addFriendById(ids[which], new EpicHttpResponseHandler() {
//								public void handleResponse(EpicHttpResponse response) {
//									if(response.responseCode != 200) {
//										handleFailure(new EpicRuntimeException("Status was: " + response.responseCode));
//										return;
//									}
//									
//									d.dismiss();
//									EpicPlatform.doToastNotification(new EpicNotification(names[which_copy] + " added as a friend!", new String[] { "You can now challenge them in Head to Head matches.", "You will also see their scores in your scoreboard." }));
//								}
//								
//								public void handleFailure(Exception e) {
//									d.dismiss();
//									EpicPlatform.doToastNotification(new EpicNotification("Problem Getting Friend List", new String[] { "We had a problem processing your friend list.", "Please try again later or contact support." }));
//									EpicLog.e(e.toString());								}
//							});
							
							selectWagerAndSendChallengeTo(ids[which]);
						}
					});
		            dialog.setPositiveButton("OK", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
		            
		            dialog.setNegativeButton("Tell Your Friends", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							EpicSocial.postToFacebook("Play Word Farm with me!", "http://wordfarmgame.com?from=" + PlayerState.getIdentity(), "Can you beat me in Word Farm? The longest word I have found is " + PlayerState.getState().longestWordString(), "http://epicbeta.com/wf/images/logo.png", null);
						}
					});
		            
		            dialog.show();
				}
				
				public void handleFailure(Exception e) {
					d.dismiss();
					EpicPlatform.doToastNotification(new EpicNotification("Problem Getting Friend List", new String[] { "We had a problem processing your friend list.", "Please try again later or contact support." }));
					EpicLog.e(e.toString());
				}
			});

            
        } catch (Exception e) {
			EpicLog.e(e.toString());
			EpicPlatform.doToastNotification(new EpicNotification("Problem connecting to Facebook", new String[] { "We were unable to connect to Facebook.", "Please try again or contact support." }, EpicImages.icon, 6));
        }
	}

	public static void postToFacebook(String name, String url, String caption, String imageUrl, final EpicClickListener callback) {
		Bundle params = new Bundle();
		params.putString("link", url);
		params.putString("name", name);
		params.putString("caption", caption);
		params.putString("description", "Word Farm is available on Android and BlackBerry devices. Get it now and be rewarded with FREE tokens!");
		params.putString("picture", imageUrl);
		
		final Bundle params_copy = params;
		Facebook f = EpicAndroidActivity.getCurrentAndroidActivity().facebook; 
		if(f.isSessionValid()) {
			// post message
			sendPostRequestToFacebook(params, callback);
		} else {
			// authorize then post on success
			f.authorize(EpicAndroidActivity.getCurrentAndroidActivity(),
				new String[] { "publish_stream" },
				6969, 
				new DialogListener() {
					public void onFacebookError(FacebookError e) {
						EpicLog.e(e.toString());
						EpicPlatform.doToastNotification(new EpicNotification("Problem posting to Facebook", new String[] { "We were unable to connect to Facebook.", "Please try again or contact support." }, EpicImages.icon, 6));
					}
					
					public void onError(DialogError e) {
						EpicLog.e(e.toString());
						EpicPlatform.doToastNotification(new EpicNotification("Problem posting to Facebook", new String[] { "We were unable to connect to Facebook.", "Please try again or contact support." }, EpicImages.icon, 6));
					}
					
					public void onComplete(Bundle values) {
						sendPostRequestToFacebook(params_copy, callback);
					}
					
					public void onCancel() {
						EpicLog.w("Cancelled FB post");
					}
				});
		}
	}

	private static void sendPostRequestToFacebook(Bundle params, final EpicClickListener callback) {
		EpicAndroidActivity.getCurrentAndroidActivity().facebook.dialog(EpicAndroidActivity.getCurrentAndroidActivity(), "feed", params,
				new DialogListener() {
	           public void onComplete(Bundle values) {
	        	   
	        	   if(values.containsKey("post_id")) {
		        	   // give award
		        	   PlayerState.onChallengeComplete(30);
		        	   PlayerState.updateLocalTokens(500);
		        	   EpicNotification n = new EpicNotification("You have shared your score!", new String[] { "Thanks for sharing your score!", "You have earned 500 tokens."}, EpicImages.icon, 5);
		        	   EpicPlatform.doToastNotification(n);
		        	   if(callback != null) {
		        		   callback.onClick();
		        	   }
	        	   } else {
	        		   EpicLog.w("onComplete didnt have post_id: ");
	        	   }
	           }

	           public void onFacebookError(FacebookError error) {
	        	   EpicLog.e(error.toString());
					EpicPlatform.doToastNotification(new EpicNotification("Problem posting to Facebook", new String[] { "We were unable to connect to Facebook.", "Please try again or contact support." }, EpicImages.icon, 6));
	           }

	           public void onError(DialogError e) {
	        	   EpicLog.e(e.toString());
					EpicPlatform.doToastNotification(new EpicNotification("Problem posting to Facebook", new String[] { "We were unable to connect to Facebook.", "Please try again or contact support." }, EpicImages.icon, 6));
	           }

	           public void onCancel() {
	        	   EpicLog.w("Posting to FB cancelled");
	           }
	      });
	}

	public static boolean supportsFacebookPost() {
		return true;
	}
	
	public static void selectWagerAndSendChallengeTo(final String opponent_id) {
		AlertDialog.Builder alert_tokens = new AlertDialog.Builder(EpicAndroidActivity.getCurrentAndroidActivity());
		alert_tokens.setTitle("How many tokens would you like to wager?");
		final int[] options = opponent_id == null ? new int[] { 100, 1000 } : new int[] { 100, 500, 1000, 5000, 10000 };
		String[] string_options = opponent_id == null ? new String[] { "100", "1,000" } : new String[] { "100", "500", "1,000", "5,000", "10,000" };
	
		alert_tokens.setNeutralButton("Get More Tokens", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				EpicPlatform.changeScreen(new ScreenBuyTokens(new ScreenMainMenu()));
			}
		});
		
		alert_tokens.setSingleChoiceItems(string_options, -1, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				final int which_c = which;
				
				if(PlayerState.getTokens() < options[which]) {
					// not enough tokens
					EpicDialogBuilder b = new EpicDialogBuilder();
					b.setMessage("You do not have enough tokens for this wager. Please select another option or get more tokens.").
					setPositiveButton("Get More Tokens", new EpicClickListener() {
						public void onClick() {
							EpicPlatform.changeScreen(new ScreenBuyTokens(new ScreenMainMenu()));
						}
					}).
					setNegativeButton("OK", new EpicClickListener() {
						public void onClick() {
							selectWagerAndSendChallengeTo(opponent_id);
						}
					}).show();
					
					dialog.dismiss();
					return;
				}
				
				if(opponent_id == null) {
					// rematch
					if(PlayerState.getTokens() < options[which]) {
						EpicSocialImplementation.promptForTokens();
						return;
					}
					
					if(PlayerState.getState().currentChallenge != null) {
						EpicNotification n = new EpicNotification("You are already in a challenge!", new String[] { "Click here to play your challenge."}, EpicImages.challenge_icon, 6);
						n.clickCallback = new EpicClickListener() {
							public void onClick() {
								EpicPlatform.changeScreen(new ScreenNursery());
							}
						};
						dialog.dismiss();
						
						EpicPlatform.doToastNotification(n);
						return;
					}
					
					final ProgressDialog pgd = ProgressDialog.show(EpicAndroidActivity.getCurrentAndroidActivity(), "", "Loading...", true);
					WordsHttp.sendRandomChallenge(options[which], new EpicHttpResponseHandler() {
						public void handleResponse(EpicHttpResponse response) {
							pgd.dismiss();
							if(response.responseCode == 200) {
								PlayerState.getState().openChallenges++;
								if(PlayerState.getState().setCurrentChallengeId(response.body, options[which_c])) {
									EpicPlatform.doToastNotification(new EpicNotification("Challenge Begun!", new String[] { "Your next game will be your entry in this challenge."}, EpicImages.challenge_icon, 5));
									EpicPlatform.changeScreen(new ScreenNursery());
								}
							} else {
								EpicLog.i("Response (status " + response.responseCode + ") was: " + response.body);
							}
						}
						
						public void handleFailure(Exception e) {
							pgd.dismiss();
							EpicLog.e(e.toString());
							EpicPlatform.doToastNotification(new EpicNotification("Problem sending challenge request", new String[] { "Please try again later or contact support." }));
						}
					});
					
					PlayerState.onChallengeComplete(65);
					
				} else {
					// rematch
					if(PlayerState.getTokens() < options[which]) {
						EpicSocialImplementation.promptForTokens();
						return;
					}
					
					if(PlayerState.getState().currentChallenge != null) {
						EpicNotification n = new EpicNotification("You are already in a challenge!", new String[] { "Click here to play your challenge."}, EpicImages.challenge_icon, 6);
						n.clickCallback = new EpicClickListener() {
							public void onClick() {
								EpicPlatform.changeScreen(new ScreenNursery());
							}
						};
						dialog.dismiss();
						EpicPlatform.doToastNotification(n);
						return;
					}
					
					final ProgressDialog pgd = ProgressDialog.show(EpicAndroidActivity.getCurrentAndroidActivity(), "", "Loading...", true);
					WordsHttp.sendChallenge(opponent_id, options[which], new EpicHttpResponseHandler() {
						public void handleResponse(EpicHttpResponse response) {
							pgd.dismiss();
							if(response.responseCode == 200) {
								PlayerState.getState().openChallenges++;
								if(PlayerState.getState().setCurrentChallengeId(response.body, options[which_c])) {
									EpicPlatform.doToastNotification(new EpicNotification("Challenge Begun!", new String[] { "Your next game will be your entry in this challenge."}, EpicImages.challenge_icon, 5));
									EpicPlatform.changeScreen(new ScreenNursery());
								}
							} else if(response.responseCode == 403) {
								EpicNotification n = new EpicNotification("Your challenge was declined.", new String[] { "Your opponent is not accepting challenges at this time." }, EpicImages.challenge_icon, 4);
								EpicPlatform.doToastNotification(n);
							} else {
								EpicLog.i("Response (status " + response.responseCode + ") was: " + response.body);
								handleFailure(new EpicUnexpectedDataException("Got response: " + response.body));
							}
						}
						
						public void handleFailure(Exception e) {
							pgd.dismiss();
							EpicLog.e(e.toString());
							EpicPlatform.doToastNotification(new EpicNotification("Problem sending challenge request", new String[] { "Please try again later or contact support." }));
						}
					});
				}
						
				dialog.dismiss();
			}
		});
		
		alert_tokens.show();
		
	}
	
	public static void issueChallenge() {
		final ProgressDialog dialog = ProgressDialog.show(EpicAndroidActivity.getCurrentAndroidActivity(), "", "Loading...", true);
		WordsHttp.getFriends(new EpicHttpResponseHandler() {
			
			public void handleResponse(EpicHttpResponse response) {
				dialog.dismiss();
				AlertDialog.Builder alert = new AlertDialog.Builder(EpicAndroidActivity.getCurrentAndroidActivity());
				alert.setTitle("Which friend would you like to invite?");
				String[] emails = null;
				String[] customer_ids = null;
				if(response.body.length() > 0) {
					final String[] parts = response.body.split(";");
					emails = new String[parts.length+1];
					customer_ids = new String[parts.length];
					
					for(int i = 0; i < parts.length; ++i) {
						String[] ip = parts[i].split(":");
						if(ip.length > 1) {
							if(ip[1].contains("@")) {
								emails[i+1] = ip[1].split("@")[0];
							} else {
								emails[i+1] = ip[1];
							}
						} else {
							emails[i+1] = "Anonymous";
						}
						
						customer_ids[i] = ip[0];
					}
					
					emails[0] = "<<Random Opponent>>";
				} else {
					emails = new String[] { "<<Random Opponent>>" };
				}
				
				final String[] cid = customer_ids;
				
				alert.setSingleChoiceItems(emails,-1, new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int which) 
					{
						dialog.dismiss();
						selectWagerAndSendChallengeTo(which == 0 ? null : cid[which-1]);
					}
				});
				
				alert.setPositiveButton("Find Facebook Friends", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						EpicSocialImplementation.getFacebookFriendList();
					}
				});
				
				alert.setNeutralButton("Add a Friend", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						final EpicTextEntryWidget textEntry = new EpicTextEntryWidget();
						final EpicDialogBuilder builder = new EpicDialogBuilder();
						
						builder.
							setMessage("To invite your friend to Word Farm, enter their email address below.").
							setTitle("Add a Friend").
							setTextInput(textEntry).
							setNegativeButton("Cancel", new EpicClickListener() {
								public void onClick() {
									EpicSocialImplementation.issueChallenge();
								}
							}).
							setPositiveButton("Send Invite", new EpicClickListener() {
								public void onClick() {
									String email = textEntry.getText();
									if(StringHelper.validateEmail(email)) {
										onProcessContactChosen(email);
									} else {
										// tell them to enter real email
										new EpicDialogBuilder().
											setMessage("The email (" + email + ") you entered is invalid. Please try again.").
											setPositiveButton("OK", new EpicClickListener() {
												public void onClick() {
												}
											}).
											show();
									}
								}
							}).
							show();
					}
				});

				alert.show();
			}
			
			public void handleFailure(Exception e) {
				dialog.dismiss();
				EpicLog.e(e.toString());
				EpicPlatform.doToastNotification(new EpicNotification("Problem retreiving your friends list.", new String[] { "Please try again or contact support."}));
			}
		});
	}
	
	public static void onProcessContactChosen(String email) {
		final ProgressDialog dialog = ProgressDialog.show(EpicAndroidActivity.getCurrentAndroidActivity(), "", "Loading...", true);
		WordsHttp.inviteFriend(email, new EpicHttpResponseHandler() {
			public void handleResponse(EpicHttpResponse response) {
				dialog.dismiss();
				new EpicDialogBuilder()
				.setMessage("Your friend has been invited! Once they play their first game, both of you will get a 500 token bonus! Invite more friends now for even more bonuses.	")
				.setPositiveButton("OK", EpicClickListener.NoOp)
				.show();
				PlayerState.onChallengeComplete(31);
			}

			public void handleFailure(Exception e) {
				dialog.dismiss();
				new EpicDialogBuilder()
				.setMessage("There was a problem connecting to our servers. Please ensure you have internet connectivity and try again later.")
				.setPositiveButton("OK", EpicClickListener.NoOp)
				.show();
			}
		});
	}

	public static void processResponse(final String response) {
		final OnlineChallenge[] challenges = OnlineChallenge.parseList(response);

		AlertDialog.Builder alert = new AlertDialog.Builder(EpicAndroidActivity.getCurrentAndroidActivity());
		
		
		String[] titles = null;
		if(challenges != null && challenges.length > 0) {
			titles = new String[challenges.length];
			for(int i = 0; i < titles.length; ++i) {
				titles[i] = challenges[i].toString();
			}
			
			String[] sorted_titles = new String[titles.length];
			final int[] sorted_ids = new int[titles.length];
			
			int pos = 0;
			String[] options = new String[] { "(*NEW*)", "(WAITING)", "(WON)", "(TIED)", "(LOST)", "(ERROR)" };
			for(int outer = 0; outer < 3; ++outer) {
				for(int i = 0; i < titles.length; ++i) {
					if(outer < 2 && titles[i].startsWith(options[outer])) {
						sorted_titles[pos] = titles[i];
						sorted_ids[pos] = i;
						pos++;
					} else if(outer >= 2 && (titles[i].startsWith(options[2]) || titles[i].startsWith(options[3]) || titles[i].startsWith(options[4]) || titles[i].startsWith(options[5]))) {
						sorted_titles[pos] = titles[i];
						sorted_ids[pos] = i;
						pos++;
					}
				}
			}
			
			alert.setSingleChoiceItems(sorted_titles,-1, new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					EpicPlatform.changeScreen(new ScreenOnlineChallengeDetails(challenges[sorted_ids[which]].challenge_id, response));
					dialog.dismiss();
				}
			});
		}
		
		alert.setTitle(titles == null ? "No Challenges" : "Last " + titles.length + " Challenges:");
		
		alert.setNegativeButton("Start a Challenge", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				EpicSocialImplementation.issueChallenge();
			}
		});
		
		alert.setNeutralButton("Top Players", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				EpicSocialImplementation.displayOnlineLeaderboard(response);
			}
		});
		
		if(titles != null && titles.length == OnlineChallenge.DEFAULT_LIST_LENGTH) {
			alert.setPositiveButton("More...", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					viewChallenges(OnlineChallenge.MORE_LIST_LENGTH);
				}
			});
		}

		alert.show();
	}
	
	protected static void displayOnlineLeaderboard(final String cached_list) {
		final ProgressDialog dialog = ProgressDialog.show(EpicAndroidActivity.getCurrentAndroidActivity(), "", "Loading...", true);
		WordsHttp.displayOnlineLeaderboard(new EpicHttpResponseHandler() {
			public void handleResponse(EpicHttpResponse response) {
				dialog.dismiss();
				final String[] players = response.body.split(";");
				String[] toDisplay = new String[players.length];
				for(int i = 0; i < players.length; ++i) {
					String[] parts = players[i].split(":");
					String email = "";
					if(parts[0].contains("@")) {
						email = parts[0].split("@")[0];
					} else {
						email = parts[0];
					}
					
					toDisplay[i] = email + " (" + parts[1] + ")"; 
				}
				
				AlertDialog.Builder alert = new AlertDialog.Builder(EpicAndroidActivity.getCurrentAndroidActivity());
				alert.setTitle("Top Global Players (click to challenge)");
				
				alert.setItems(toDisplay, new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						selectWagerAndSendChallengeTo(players[which].split(":")[2]);
					}
				});
				
				alert.setNeutralButton("Back", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						viewChallenges(cached_list);
					}
				});
				
				alert.show();
			}
			
			public void handleFailure(Exception e) {
				dialog.dismiss();
				EpicPlatform.doToastNotification(new EpicNotification("Problem connecting to our servers", new String[] { "There was a problem connecting to our servers.", "Please try again later or contact support."}));
			}
		});
	}

	public static void viewChallenges(int amount) {
		final ProgressDialog dialog = ProgressDialog.show(EpicAndroidActivity.getCurrentAndroidActivity(), "", "Loading...", true);
		WordsHttp.getChallenges(amount, new EpicHttpResponseHandler() {
			public void handleResponse(EpicHttpResponse response) {
				dialog.dismiss();
				processResponse(response.body);
			}
			
			public void handleFailure(Exception e) {
				dialog.dismiss();
				EpicLog.e(e.toString());
				EpicPlatform.doToastNotification(new EpicNotification("Problem retreiving your friends list.", new String[] { "Please try again or contact support."}));
			}
		});
	}

	public static void viewChallenges(String response) {
		processResponse(response);
	}

	public static void promptForTokens() {
		EpicDialogBuilder b = new EpicDialogBuilder();
		b.setMessage("You do not have enough tokens yet to accept this wager. Please get more tokens and come back.").
		setPositiveButton("Get More Tokens", new EpicClickListener() {
			public void onClick() {
				EpicPlatform.changeScreen(new ScreenBuyTokens(new ScreenMainMenu()));
			}
		}).
		setNegativeButton("OK", EpicClickListener.NoOp).show();
	}

	public static void togglePush(boolean pushEnabled) {
//		if(pushEnabled) {
//	        PushManager.enablePush();
//	        PushManager.shared().setIntentReceiver(EpicIntentReceiver.class);
//
//	        PushPreferences prefs = PushManager.shared().getPreferences();
//	        prefs.setSoundEnabled(false);
//	        prefs.setVibrateEnabled(false);
//	        
//	        PlayerState.getState().setPushId(prefs.getPushId());
//	        EpicLog.w("My Application onCreate - App APID: " + prefs.getPushId());
//		} else {
//			PushManager.disablePush();
//		}
	}

	public static void promptForFbConnect() {
		EpicDialogBuilder b = new EpicDialogBuilder();
		b.setTitle("Would you like to play with friends on Facebook?");
		b.setMessage("Connect with Facebook to see which of your friends are playing, and challenge them to head-to-head Word Farm games!");
		b.setPositiveButton("Find My Friends", new EpicClickListener() {
			public void onClick() {
				EpicSocialImplementation.getFacebookFriendList();
			}
		});

		b.setNeutralButton("Don't Ask Again", new EpicClickListener() {
			public void onClick() {
				PlayerState.getState().ignoreFb();
				EpicDialogBuilder db = new EpicDialogBuilder();
				db.setTitle("Perhaps another time");
				db.setMessage("You can connect with Facebook any time by pressing Add Friend on the High Score screen.");
				db.setPositiveButton("OK", new EpicClickListener() {
					public void onClick() {
						EpicSocialImplementation.viewChallenges(OnlineChallenge.DEFAULT_LIST_LENGTH);
					}
				});
				db.show();
			}
		});
		b.show();
	}
	
	public static void promptForFbConnectScores() {
		EpicDialogBuilder b = new EpicDialogBuilder();
		b.setTitle("Would you like to play with friends on Facebook?");
		b.setMessage("Connect with Facebook to see which of your friends are playing, and challenge them to head-to-head Word Farm games!");
		b.setPositiveButton("Find My Friends", new EpicClickListener() {
			public void onClick() {
				EpicSocialImplementation.getFacebookFriendList();
			}
		});

		b.setNeutralButton("Not Now", new EpicClickListener() {
			public void onClick() {
				PlayerState.getState().ignoreFb();
				EpicDialogBuilder db = new EpicDialogBuilder();
				db.setTitle("Perhaps another time");
				db.setMessage("You can connect with Facebook any time by pressing Add Friend on the High Score screen.");
				db.setPositiveButton("OK", new EpicClickListener() {
					public void onClick() {
						ScreenScores.promptForEmail();
					}
				});
				db.show();
			}
		});
		b.show();
	}
}
