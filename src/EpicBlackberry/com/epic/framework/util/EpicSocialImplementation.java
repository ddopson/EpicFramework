package com.epic.framework.util;

import java.util.Vector;
import com.epic.framework.Ui.EpicClickListener;
import com.epic.framework.Ui.EpicDialogBuilder;
import com.epic.framework.Ui.EpicNativeListWidget;
import com.epic.framework.Ui.EpicNotification;
import com.epic.framework.Ui.EpicPlatform;
import com.epic.framework.Ui.EpicTextEntryWidget;
import com.epic.framework.util.EpicSocial.EpicSocialSignInCompletionHandler;
import com.epic.resources.EpicImages;
import com.realcasualgames.words.OnlineChallenge;
import com.realcasualgames.words.PlayerState;
import com.realcasualgames.words.ScreenBuyTokens;
import com.realcasualgames.words.ScreenMainMenu;
import com.realcasualgames.words.ScreenNursery;
import com.realcasualgames.words.ScreenOnlineChallengeDetails;
import com.realcasualgames.words.WordsHttp;

import net.rim.blackberry.api.mail.Folder;
import net.rim.blackberry.api.mail.NoSuchServiceException;
import net.rim.blackberry.api.mail.ServiceConfiguration;
import net.rim.blackberry.api.mail.Session;
import net.rim.blackberry.api.mail.Store;
import net.rim.device.api.servicebook.ServiceBook;
import net.rim.device.api.servicebook.ServiceRecord;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class EpicSocialImplementation {

	public static String getUniqueUserId() {
		return (Integer.toString(DeviceInfo.getDeviceId(),16)).toUpperCase();
	}
		
	public static String getPlatformId() {
		return "b";
	}
	
	public static String getDisplayNameFromEmail(String email) {
		return email;
	}
	
	public static String getEmailList() {
		return "";
	}
	
	public static String[] getDisplayNamesFromEmails(String[] names_to_lookup) {
		String[] results = new String[names_to_lookup.length];
		for(int i = 0; i < names_to_lookup.length; ++i) {
			results[i] = EpicSocial.getDisplayNameFromEmail(names_to_lookup[i]);
		}
		
		return results;
	}

	private static String[] recurseEmailFolders() 
	{
		ServiceBook sb = ServiceBook.getSB();
		ServiceRecord[] srs = sb.getRecords();
		Vector v = new Vector();
		for(int cnt = srs.length - 1; cnt >= 0; --cnt) 
		{
		      //identify the service record associated with a mail message service via a CID of 'CMIME'
		      if( srs[cnt].getCid().equals("CMIME")) 
		      {
		          ServiceConfiguration sc = new ServiceConfiguration(srs[cnt]);
		          Store store = Session.getInstance(sc).getStore();
		          //then search recursively for INBOX folders using either method (1) or (2) above
		          Folder[] folders = store.list();
					
					for( int foldercnt = folders.length - 1; foldercnt >= 0; --foldercnt) 
					{
					   Folder f = folders[foldercnt];
					   recurse(f, v);
					}
		       }
		}
		
		if(v.size() == 0) {
			return new String[] { "anonymous" + EpicRandom.nextInt(99999) + "@wordfarmgame.com" };
		} else {
			String[] e = new String[v.size()];
			for(int i = 0; i < v.size(); ++i) {
				e[i] = (String) v.elementAt(i);
			}
			
			return e;
		}
	}
	
	public static void recurse(Folder f, Vector v)
	{
	   if ( f.getType() == Folder.INBOX)
	   {
		   // add email
		   v.addElement(f.getName());
	   }
	   
	   Folder[] farray = f.list();
	   for (int fcnt = farray.length - 1; fcnt >= 0; --fcnt)
	   {
	       recurse(farray[fcnt], v);
	   }
	}
	
	
	public static String chooseContact() {
//		String[] emails = recurseEmailFolders();
//		EpicLog.w("Found " + emails.length + " emails on device.");
//		for(int i = 0; i < emails.length; ++i) {
//			EpicLog.i("Found email: " + emails[i]);
//		}
//		
//		if(emails.length > 1) {
//			int result = -1;
//			synchronized (UiApplication.getEventLock()) {
//				result = Dialog.ask("Please select an email account to use with Word Farm:", emails, 0);	
//			}
//			
//			if(result >= 0) {
//				return emails[result];
//			} else {
//				return emails[0];
//			}
//		} else {
//			return emails[0];
//		}
		
		return Integer.toHexString(DeviceInfo.getDeviceId()).toUpperCase();
	}
	
	public static void selectFromEmailList(String[] strings) {
		
	}

	public static void signOut() {
		// TODO Auto-generated method stub
		
	}

	public static boolean supportsFacebookPost() {
		return false;
	}

	public static void postToFacebook(String title, String url, String caption, String imageUrl) {
		
	}

	public static void beginLogin(EpicSocialSignInCompletionHandler epicSocialSignInCompletionHandler) {
		epicSocialSignInCompletionHandler.onSignedIn(chooseContact());
	}
	
	
	/*
	 * 
	 * CHALLENGE RELATED FUNCTIONS
	 * 
	 */

	public static void viewChallenges() {
		viewChallenges(OnlineChallenge.DEFAULT_LIST_LENGTH);
	}
	
	public static void viewChallenges(String cached_list) {
		processResponse(cached_list);
	}
	
	public static void viewChallenges(int amount) {
		//final ProgressDialog dialog = ProgressDialog.show(EpicAndroidActivity.getCurrentAndroidActivity(), "", "Loading...", true);
		WordsHttp.getChallenges(amount, new EpicHttpResponseHandler() {
			public void handleResponse(EpicHttpResponse response) {
				// dialog.dismiss();
				processResponse(response.body);
			}
			
			public void handleFailure(Exception e) {
				// dialog.dismiss();
				EpicLog.e(e.toString());
				EpicPlatform.doToastNotification(new EpicNotification("Problem retreiving your friends list.", new String[] { "Please try again or contact support."}));
			}
		});
	}
	
	public static void processResponse(final String response) {
		final OnlineChallenge[] challenges = OnlineChallenge.parseList(response);
		
		final EpicDialogBuilder alert = new EpicDialogBuilder();
		
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
			
			alert.setList(sorted_titles, -1, new EpicDialogBuilder.EpicRowSelectListener() {
				public void onSelectRow(int row) {
					EpicPlatform.changeScreen(new ScreenOnlineChallengeDetails(challenges[sorted_ids[row]].challenge_id, response));
					// dialog.dismiss();
				}
			});
		}
		
		alert.setTitle(titles == null ? "No Challenges" : "Last " + titles.length + " Challenges:");
		
		alert.setNegativeButton("Start a Challenge", new EpicClickListener() {
			public void onClick() {
				EpicSocialImplementation.issueChallenge();
			}
		});
		
		alert.setNeutralButton("Top Players", new EpicClickListener() {
			public void onClick() {
				EpicSocialImplementation.displayOnlineLeaderboard(response);
			}
		});
		
		if(titles != null && titles.length == OnlineChallenge.DEFAULT_LIST_LENGTH) {
			alert.setPositiveButton("More...", new EpicClickListener() {
				public void onClick() {
					alert.dismiss();
					viewChallenges(OnlineChallenge.MORE_LIST_LENGTH);
				}
			});
		}

		alert.show();
	}
	

	protected static void displayOnlineLeaderboard(final String cached_list) {
		// final ProgressDialog dialog = ProgressDialog.show(EpicAndroidActivity.getCurrentAndroidActivity(), "", "Loading...", true);
		WordsHttp.displayOnlineLeaderboard(new EpicHttpResponseHandler() {
			public void handleResponse(final EpicHttpResponse response) {
				// dialog.dismiss();
				String[] players = StringHelper.split(response.body, ";");
				EpicDialogBuilder alert = new EpicDialogBuilder();
				alert.setTitle("Top Global Players");
				
				alert.setList(players, -1, new EpicDialogBuilder.EpicRowSelectListener() {
					public void onSelectRow(int row) {
					}
				});
				
				alert.setNeutralButton("Back", new EpicClickListener() {
					public void onClick() {
						// dialog.dismiss();
						viewChallenges(cached_list);
					}
				});
				
				alert.show();
			}
			
			public void handleFailure(Exception e) {
				// dialog.dismiss();
				EpicPlatform.doToastNotification(new EpicNotification("Problem connecting to our servers", new String[] { "There was a problem connecting to our servers.", "Please try again later or contact support."}));
			}
		});		
	}

	public static void issueChallenge() {
		// final ProgressDialog dialog = ProgressDialog.show(EpicAndroidActivity.getCurrentAndroidActivity(), "", "Loading...", true);
		WordsHttp.getFriends(new EpicHttpResponseHandler() {
			
			public void handleResponse(EpicHttpResponse response) {
				// dialog.dismiss();
				EpicDialogBuilder alert = new EpicDialogBuilder();
				alert.setTitle("Which friend would you like to invite?");
				String[] emails = null;
				String[] customer_ids = null;
				if(response.body.length() > 0) {
					final String[] parts = StringHelper.split(response.body, ";");
					emails = new String[parts.length+1];
					customer_ids = new String[parts.length];
					
					for(int i = 0; i < parts.length; ++i) {
						String[] ip = StringHelper.split(parts[i], ":");
						if(ip.length > 1) {
							if(StringHelper.contains(ip[1], "@")) {
								emails[i+1] = StringHelper.split(ip[1], "@")[0];
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
				
				alert.setList(emails, -1, new EpicDialogBuilder.EpicRowSelectListener() {
					
					@Override
					public void onSelectRow(int row) {
						// dialog.dismiss();
						selectWagerAndSendChallengeTo(row == 0 ? null : cid[row-1]);
					}
				});
				
				alert.setNeutralButton("Add a Friend", new EpicClickListener() {
					public void onClick() {
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
				// dialog.dismiss();
				EpicLog.e(e.toString());
				EpicPlatform.doToastNotification(new EpicNotification("Problem retreiving your friends list.", new String[] { "Please try again or contact support."}));
			}
		});
	}
	
	public static void selectWagerAndSendChallengeTo(final String opponent_id) {
		EpicDialogBuilder alert_tokens = new EpicDialogBuilder();
		alert_tokens.setTitle("How many tokens would you like to wager?");
		final int[] options = opponent_id == null ? new int[] { 100, 1000 } : new int[] { 100, 500, 1000, 5000, 10000 };
		String[] string_options = opponent_id == null ? new String[] { "100", "1,000" } : new String[] { "100", "500", "1,000", "5,000", "10,000" };
	
		alert_tokens.setNeutralButton("Get More Tokens", new EpicClickListener() {
			public void onClick() {
				// dialog.dismiss();
				EpicPlatform.changeScreen(new ScreenBuyTokens(new ScreenMainMenu()));
			}
		});
		
		alert_tokens.setList(string_options, -1, new EpicDialogBuilder.EpicRowSelectListener() {
			public void onSelectRow(int row) {
				final int which_c = row;
				
				if(PlayerState.getTokens() < options[row]) {
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
					
					// dialog.dismiss();
					return;
				}
				
				if(opponent_id == null) {
					// rematch
					if(PlayerState.getTokens() < options[row]) {
						EpicSocialImplementation.promptForTokens();
						return;
					}
					
					// final ProgressDialog pgd = ProgressDialog.show(EpicAndroidActivity.getCurrentAndroidActivity(), "", "Loading...", true);
					WordsHttp.sendRandomChallenge(options[row], new EpicHttpResponseHandler() {
						public void handleResponse(EpicHttpResponse response) {
							// pgd.dismiss();
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
							// pgd.dismiss();
							EpicLog.e(e.toString());
							EpicPlatform.doToastNotification(new EpicNotification("Problem sending challenge request", new String[] { "Please try again later or contact support." }));
						}
					});
					
					PlayerState.onChallengeComplete(65);
					
				} else {
					// rematch
					if(PlayerState.getTokens() < options[row]) {
						EpicSocialImplementation.promptForTokens();
						return;
					}
					// final ProgressDialog pgd = ProgressDialog.show(EpicAndroidActivity.getCurrentAndroidActivity(), "", "Loading...", true);
					WordsHttp.sendChallenge(opponent_id, options[row], new EpicHttpResponseHandler() {
						public void handleResponse(EpicHttpResponse response) {
							// pgd.dismiss();
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
							// pgd.dismiss();
							EpicLog.e(e.toString());
							EpicPlatform.doToastNotification(new EpicNotification("Problem sending challenge request", new String[] { "Please try again later or contact support." }));
						}
					});
				}
						
				// dialog.dismiss();
			}
		});
		
		alert_tokens.show();
		
	}
	
	public static void onProcessContactChosen(String email) {
		// final ProgressDialog dialog = ProgressDialog.show(EpicAndroidActivity.getCurrentAndroidActivity(), "", "Loading...", true);
		WordsHttp.inviteFriend(email, new EpicHttpResponseHandler() {
			public void handleResponse(EpicHttpResponse response) {
				//dialog.dismiss();
				new EpicDialogBuilder()
				.setMessage("Your friend has been invited! Once they play their first game, both of you will get a 500 token bonus! Invite more friends now for even more bonuses.	")
				.setPositiveButton("OK", EpicClickListener.NoOp)
				.show();
				PlayerState.onChallengeComplete(31);
			}

			public void handleFailure(Exception e) {
				//dialog.dismiss();
				new EpicDialogBuilder()
				.setMessage("There was a problem connecting to our servers. Please ensure you have internet connectivity and try again later.")
				.setPositiveButton("OK", EpicClickListener.NoOp)
				.show();
			}
		});
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

}
