package com.epic.framework.implementation;

import java.util.ArrayList;

import org.xmlvm.iphone.NSIndexPath;
import org.xmlvm.iphone.UIAlertView;
import org.xmlvm.iphone.UIImage;
import org.xmlvm.iphone.UITabBarController;
import org.xmlvm.iphone.UITableView;
import org.xmlvm.iphone.UITableViewCell;
import org.xmlvm.iphone.UITableViewController;
import org.xmlvm.iphone.UITableViewDataSource;
import org.xmlvm.iphone.UITableViewDelegate;
import org.xmlvm.iphone.UITableViewStyle;
import org.xmlvm.iphone.UIViewController;

import com.epic.framework.common.Ui.EpicClickListener;
import com.epic.framework.common.Ui.EpicDialogBuilder;
import com.epic.framework.common.Ui.EpicNotification;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.Ui.EpicTimer;
import com.epic.framework.common.util.EpicHttpResponse;
import com.epic.framework.common.util.EpicHttpResponseHandler;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.exceptions.EpicFrameworkException;
import com.epic.framework.common.util.exceptions.EpicRuntimeException;
import com.epic.framework.common.util.exceptions.EpicInvalidArgumentException;
import com.epic.resources.EpicImages;
import com.realcasualgames.words.OnlineChallenge;
import com.realcasualgames.words.PlayerState;
import com.realcasualgames.words.ScreenBuyTokens;
import com.realcasualgames.words.ScreenDailySpecials;
import com.realcasualgames.words.ScreenGameLoading;
import com.realcasualgames.words.ScreenMainMenu;
import com.realcasualgames.words.ScreenNursery;
import com.realcasualgames.words.ScreenOnlineChallengeDetails;
import com.realcasualgames.words.WordsHttp;


public class EpicSocialTabbedView extends UITabBarController {
	
	public static boolean displayed = false;

	protected static final String START_GAME_TEXT = "+ Start a Challenge ";
	private OnlineChallenge[] completedGames;
	private OnlineChallenge[] waitingGames;
	private OnlineChallenge[] pendingGames;
	private String[] players;
	private String[] customer_ids;
	private int[] options;
	protected String[] toDisplay;
	protected String[] emails;
	public ListDataSource[] sources = new ListDataSource[3];
	ArrayList<UIViewController> list = new ArrayList<UIViewController>();
	private UITableView table;
	private UITableViewController top;
	private UITableViewController start;
	private String cachedString;

	private UITableViewController controller;
	
	int iconSize = 30;

	private String[] opponentIds;
	
	public EpicSocialTabbedView(String cachedResponse) {
		displayed = true;		
		cachedString = cachedResponse;
		
		EpicPlatform.setAppBadge(0);
        
        controller = new UITableViewController(UITableViewStyle.Grouped);
        table = controller.getTableView();
        
        table.setDataSource(new UITableViewDataSource() {
			public int numberOfRowsInSection(UITableView table, int section) {
				return 0;
			}
			
			public UITableViewCell cellForRowAtIndexPath(UITableView table,
					NSIndexPath idx) {
				return null;
			}
			
			@Override
			public int numberOfSectionsInTableView(UITableView table) {
				return 1;
			}
			
			public String titleForHeaderInSection(UITableView table, int section) {
				return "Loading...";
			}
		});
        
        table.setDelegate(
        		new UITableViewDelegate() {
        			public void didSelectRowAtIndexPath(UITableView tableview, NSIndexPath indexPath) {
        				ListDataSource src = (ListDataSource) tableview.getDataSource();
        				EpicLog.i("Selected: " + indexPath.getSection() + ", " + indexPath.getRow() + ", adjusted to section " + src.getAdjustedSection(indexPath.getSection()));
        				OnlineChallenge c = null;
        				switch(src.getAdjustedSection(indexPath.getSection())) {
        				case 0:
        					if(pendingGames.length == 0) {
        						EpicLog.i("Showing start game controller..");
        						EpicSocialTabbedView.this.setSelectedIndex(2);
        						return;
        					} else {
        						c = pendingGames[indexPath.getRow()];
        					}
    						break;
        				case 1:
    						c = waitingGames[indexPath.getRow()];
    						break;
        				case 2:
    						c = completedGames[indexPath.getRow()];
    						break;
        				}
        				
	        			Main.navc.popToRootViewControllerAnimated(true);
	        			EpicPlatform.changeScreen(new ScreenOnlineChallengeDetails(c.challenge_id, cachedString));
        			}
        		});
//        
        
        top = new UITableViewController(UITableViewStyle.Grouped) {
        	public boolean shouldAutorotateToInterfaceOrientation(int uiInterfaceOrientation) {
       			return false;
        	}
        };
        
        top.getTableView().setDataSource(new UITableViewDataSource() {
			public int numberOfRowsInSection(UITableView table, int section) {
				return 0;
			}
			
			public UITableViewCell cellForRowAtIndexPath(UITableView table,
					NSIndexPath idx) {
				return null;
			}
			
			@Override
			public int numberOfSectionsInTableView(UITableView table) {
				return 1;
			}
			
			public String titleForHeaderInSection(UITableView table, int section) {
				return "Loading...";
			}
		});


        top.getTableView().setDelegate(
        		new UITableViewDelegate() {
        			public void didSelectRowAtIndexPath(UITableView tableview, NSIndexPath indexPath) {
        				ListDataSource src = (ListDataSource) tableview.getDataSource();
        				EpicLog.i("Selected: " + indexPath.getSection() + ", " + indexPath.getRow());
        				// Should go to issue challenge screen
        				selectWagerAndSendChallengeTo(opponentIds[indexPath.getRow()], toDisplay[indexPath.getRow()]);
        			}
        		});
        
        
        start = new UITableViewController(UITableViewStyle.Grouped) {
        	public boolean shouldAutorotateToInterfaceOrientation(int uiInterfaceOrientation) {
       			return false;
        	}
        };
        
        start.getTableView().setDataSource(new UITableViewDataSource() {
			public int numberOfRowsInSection(UITableView table, int section) {
				return 0;
			}
			
			public UITableViewCell cellForRowAtIndexPath(UITableView table,
					NSIndexPath idx) {
				return null;
			}
			
			@Override
			public int numberOfSectionsInTableView(UITableView table) {
				return 1;
			}
			
			public String titleForHeaderInSection(UITableView table, int section) {
				return "Loading...";
			}
		});

		start.getTableView().setDelegate(
        		new UITableViewDelegate() {
        			public void didSelectRowAtIndexPath(UITableView tableview, NSIndexPath indexPath) {
        				ListDataSource src = (ListDataSource) tableview.getDataSource();
        				EpicLog.i("Selected: " + indexPath.getSection() + ", " + indexPath.getRow());
        				// Should go to issue challenge screen
        				if(indexPath.getRow() == 0) {
        					selectWagerAndSendChallengeTo(null, "Random Opponent");
        				} else {
        					selectWagerAndSendChallengeTo(customer_ids[indexPath.getRow()-1], emails[indexPath.getRow()-1]);
        				}
        			}
        		});
		
        if(sources[0] == null || sources[1] == null || sources[2] == null) {
        	getDataAndPopulate(cachedResponse != null);
        } else {
			table.setDataSource(sources[0]);
			table.setNeedsDisplay();
			table.reloadData();
			top.getTableView().setDataSource(sources[1]);
        	top.getTableView().setNeedsDisplay();
        	top.getTableView().reloadData();
        	start.getTableView().setDataSource(sources[2]);
			start.getTableView().setNeedsDisplay();
			start.getTableView().reloadData();
        }

        controller.setTitle("Challenges");
        controller.getTabBarItem().setImage((UIImage) EpicImages.game_tomato_gray.getPlatformObject(iconSize, iconSize));
        top.setTitle("Top Players");
        top.getTabBarItem().setImage((UIImage) EpicImages.nursery_pricebox_unlocked_star.getPlatformObject(iconSize, iconSize));
        start.setTitle("Start Challenge");
        start.getTabBarItem().setImage((UIImage) EpicImages.game_extend_icon.getPlatformObject(iconSize, iconSize));
        
        list.add(controller);
        list.add(top);
        list.add(start);
        this.setViewControllers(list);
	}
	
	private void exitAndToast() {
		EpicLog.i("Exiting and toasting connection failure.");
		Main.navc.popToRootViewControllerAnimated(true);
		EpicPlatform.doToastNotification(new EpicNotification("Unable to Connect", new String[] { "There was a problem connecting to our servers.", "Please ensure you have internet connectivity and try again later." }, EpicImages.icon, 4));
	}
	
	private void processRepsonses(String[] pieces) {
		if(pieces.length != 3) {
			EpicLog.e("Was expecting 3 parts, got " + pieces.length);
			exitAndToast();
			return;
		}
		
		processChallenges(pieces[0]);
		processLeaderboard(pieces[1]);
		processFriendList(pieces[2]);
	}

	private void getDataAndPopulate(final boolean fromCache) {
		
		if(fromCache) {
			String[] pieces = cachedString.split("~");
			processRepsonses(pieces);
		} else {
			if(EpicPlatformImplementationNative.isNetworkAvailable() == 0) {
				EpicLog.w("No network, exiting head to head screen.");
				// TODO: this is probably painfully disgusting, but the only way i could figure out to do it
				EpicPlatform.runInBackground(new Runnable() {
					public void run() {
						try {
							Thread.sleep(1000);
						} catch(Exception e) {
							EpicLog.e("Couldn't even sleep: " + e.toString());
						}
						
						exitAndToast();	
					}
				});
				return;
			}
			
			WordsHttp.getIosChallengeData(25, new EpicHttpResponseHandler() {
				public void handleResponse(EpicHttpResponse response) {
					if(!displayed) {
						EpicLog.i("No longer displaying challenge screen, so discarding network response.");
						return;
					}
					
					cachedString = response.body;
					
					// break into 3 parts on ||| and send to each process method
					String[] pieces = response.body.split("~");
					
					processRepsonses(pieces);
				}
				
				public void handleFailure(Exception e) {
					EpicLog.e(e.toString());
					exitAndToast();
				}
			});
		}
	}
	
	private void processFriendList(String responseString) {
		EpicLog.i("Response: " + responseString);
		if(responseString.length() > 0) {
			final String[] parts = responseString.split(";");
			EpicLog.i("Found " + parts.length + " friends");
//			// TODO: hack since split returns 1 extra usually
			emails = new String[parts.length];
			customer_ids = new String[parts.length];
//			
			for(int i = 0; i < parts.length - 1; ++i) {
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
//			
			emails[0] = "< Random Opponent >";
		} else {
			emails = new String[] { "< Random Opponent >" };
		}
//		
		EpicLog.v("Refreshing Friends List...");
        ListDataSource src = new ListDataSource(new String[][] { emails }, new String[] { "Select an Opponent" });
        sources[2] = src;
        start.getTableView().setDataSource(src);
        start.getTableView().reloadData();
	}
	
	private void processLeaderboard(String responseString) {
		EpicLog.i("Response: " + responseString);
		players = responseString.split(";");
		// TODO: HACK -- -1 is for split() returning an extra piece of shit for some reason
		toDisplay = new String[players.length-1];
		opponentIds = new String[players.length-1];
		for(int i = 0; i < players.length; ++i) {
			String[] parts = players[i].split(":");
			if(parts.length < 3) continue;
			EpicLog.i("Parsing " + i + " to " + parts.length + " parts");
			String email = "";
			if(parts[0].contains("@")) {
				email = parts[0].split("@")[0];
			} else {
				email = parts[0];
			}

			opponentIds[i] = parts[2];
			toDisplay[i] = email + " (" + parts[1] + ")"; 
			EpicLog.i("Displaying " + toDisplay[i]);
		}
//		
		EpicLog.v("Refreshing leaderboard...");
        ListDataSource src = new ListDataSource(new String[][] { toDisplay }, new String[] { "Top Players (click to challenge)" });
        sources[1] = src;
        top.getTableView().setDataSource(src);
        top.getTableView().reloadData();
	}
	
	
	private void processChallenges(String responseString) {
		OnlineChallenge[] challenges = OnlineChallenge.parseList(responseString);
		if(challenges != null) {
			int completed = 0, pending = 0, waiting = 0;
			
			for(int i = 0; i < challenges.length; ++i) {
				int status = challenges[i].getStatus();
				
				if(status == OnlineChallenge.STATUS_WAITING_YOU) {
					pending++;
				} else if(status == OnlineChallenge.STATUS_GAME_OVER_YOU_WIN || status == OnlineChallenge.STATUS_GAME_OVER_YOU_LOSE || status == OnlineChallenge.STATUS_GAME_OVER_YOU_TIE) {
					completed++;
				} else if(status == OnlineChallenge.STATUS_MATCHING || status == OnlineChallenge.STATUS_WAITING_OPPONENT) {
					waiting++;
				}
			}
			
			completedGames = new OnlineChallenge[completed];
			waitingGames = new OnlineChallenge[waiting];
			pendingGames = new OnlineChallenge[pending];
			
//			if(pending > 0) controller.getTabBarItem().setBadgeValue(pending + "");
			
			completed = 0;
			pending = 0;
			waiting = 0;
			
			for(int i = 0; i < challenges.length; ++i) {
				int status = challenges[i].getStatus();
				
				if(status == OnlineChallenge.STATUS_WAITING_YOU) {
					pendingGames[pending++] = challenges[i];
				} else if(status == OnlineChallenge.STATUS_GAME_OVER_YOU_WIN || status == OnlineChallenge.STATUS_GAME_OVER_YOU_LOSE || status == OnlineChallenge.STATUS_GAME_OVER_YOU_TIE) {
					completedGames[completed++] = challenges[i];
				} else if(status == OnlineChallenge.STATUS_MATCHING || status == OnlineChallenge.STATUS_WAITING_OPPONENT) {
					waitingGames[waiting++] = challenges[i];
				}
			}
			
			String[] completedTitles = new String[completedGames.length];
			String[] waitingTitles = new String[waitingGames.length];
			String[] pendingTitles = new String[pendingGames.length];
			
			for(int i = 0; i < completedTitles.length; ++i) {
				completedTitles[i] = completedGames[i].toString();
			}
			
			for(int i = 0; i < waitingTitles.length; ++i) {
				waitingTitles[i] = waitingGames[i].toString();
			}
			
			for(int i = 0; i < pendingTitles.length; ++i) {
				pendingTitles[i] = pendingGames[i].toString();
			}
			
			if(pendingGames.length == 0) {
				pendingTitles = new String[] { EpicSocialTabbedView.START_GAME_TEXT };
			}
			
	        ListDataSource src = new ListDataSource(new String[][] { pendingTitles, waitingTitles, completedTitles }, new String[] { "Your Turn", "Their Turn", "Completed"});
	        if(table != null) {
	        	EpicLog.v("Refreshing challenge list...");
	        	sources[0] = src;
		        table.setDataSource(src);
		        table.setNeedsDisplay();
		        table.reloadData();
	        }
		} else {
			ListDataSource src = new ListDataSource(new String[][] { new String[] { EpicSocialTabbedView.START_GAME_TEXT }}, new String[] { "Your Turn"});
			if(table != null) {
				EpicLog.v("Refreshing challenge list...");
				pendingGames = new OnlineChallenge[0];
				sources[0] = src;
				table.setDataSource(src);
				table.setNeedsDisplay();
				table.reloadData();
	        }
		}
	}
	
	public void selectWagerAndSendChallengeTo(final String opponent_id, final String opponent_name) {
		options = opponent_id == null ? new int[] { 100, 1000 } : new int[] { 100, 500, 1000, 5000, 10000 };
		String[] string_options = opponent_id == null ? new String[] { "100 tokens", "1,000 tokens" } : new String[] { "100 tokens", "500 tokens", "1,000 tokens", "5,000 tokens", "10,000 tokens" };
	
		UITableViewController wagerList = new UITableViewController(UITableViewStyle.Grouped) {
			public boolean shouldAutorotateToInterfaceOrientation(int uiInterfaceOrientation) {
       			return false;
        	}
		};
		
		wagerList.setTitle("Select a Wager for " + opponent_name);
		
		ListDataSource src = new ListDataSource(new String[][] { string_options }, new String[] { "Select a Token Wager" } );
		wagerList.getTableView().setDataSource(src);
		Main.navc.pushViewController(wagerList, true);
		
		wagerList.getTableView().setDelegate(new UITableViewDelegate() {
			public void didSelectRowAtIndexPath(UITableView tableview, NSIndexPath indexPath) {
				final int wager = options[indexPath.getRow()];
				
				if(PlayerState.getTokens() < wager) {
					// prompt for tokens
					EpicDialogBuilder b = new EpicDialogBuilder();
					b.setMessage("You do not have enough tokens for this wager. Please select another option or get more tokens.").
					setPositiveButton("Get More Tokens", new EpicClickListener() {
						public void onClick() {
							Main.navc.popToRootViewControllerAnimated(true);
							EpicPlatform.changeScreen(new ScreenBuyTokens(new ScreenMainMenu()));
						}
					}).
					setNegativeButton("OK", new EpicClickListener() {
						public void onClick() {
						}
					}).show();
					return;
				} else {

					if(opponent_id == null) {
						
						if(PlayerState.getState().currentChallenge != null) {
							EpicNotification n = new EpicNotification("You are already in a challenge!", new String[] { "Click here to play your challenge."}, EpicImages.challenge_icon, 6);
							Main.navc.popToRootViewControllerAnimated(true);
							EpicPlatform.changeScreen(new ScreenNursery());
							EpicPlatform.doToastNotification(n);
							return;
						}
	
						WordsHttp.sendRandomChallenge(wager, new EpicHttpResponseHandler() {
							public void handleResponse(EpicHttpResponse response) {
								if(response.responseCode == 200) {
									PlayerState.getState().setOpenChallengeCount(PlayerState.getState().getOpenChallengeCount()+1);									if(PlayerState.getState().setCurrentChallengeId(response.body, wager)) {
										EpicPlatform.doToastNotification(new EpicNotification("Challenge Begun!", new String[] { "Your next game will be your entry in this challenge."}, EpicImages.challenge_icon, 5));
										Main.navc.popToRootViewControllerAnimated(true);
										EpicPlatform.changeScreen(new ScreenNursery());
									}
								} else {
									EpicLog.i("Response (status " + response.responseCode + ") was: " + response.body);
								}
							}
							
							public void handleFailure(Exception e) {
								EpicLog.e(e.toString());
								Main.navc.popToRootViewControllerAnimated(true);
								EpicPlatform.doToastNotification(new EpicNotification("Problem sending challenge request", new String[] { "Please try again later or contact support." }));
							}
						});
						
						PlayerState.onChallengeComplete(65);
						
					} else {
						if(PlayerState.getState().currentChallenge != null) {
							EpicNotification n = new EpicNotification("You are already in a challenge!", new String[] { "Click here to play your challenge."}, EpicImages.challenge_icon, 6);
							Main.navc.popToRootViewControllerAnimated(true);
							EpicPlatform.changeScreen(EpicPlatform.isIos() ? new ScreenDailySpecials() : new ScreenNursery());
							EpicPlatform.doToastNotification(n);
							return;
						}
	
						WordsHttp.sendChallenge(opponent_id, wager, new EpicHttpResponseHandler() {
							public void handleResponse(EpicHttpResponse response) {
								if(response.responseCode == 200) {
									PlayerState.getState().setOpenChallengeCount(PlayerState.getState().getOpenChallengeCount()+1);
									if(PlayerState.getState().setCurrentChallengeId(response.body, wager)) {
										EpicPlatform.doToastNotification(new EpicNotification("Challenge Begun!", new String[] { "Your next game will be your entry in this challenge."}, EpicImages.challenge_icon, 5));
										Main.navc.popToRootViewControllerAnimated(true);
										EpicPlatform.changeScreen(EpicPlatform.isIos() ? new ScreenDailySpecials() : new ScreenNursery());
									}
								} else if(response.responseCode == 403) {
									EpicNotification n = new EpicNotification("Your challenge was declined.", new String[] { "Your opponent is not accepting challenges at this time." }, EpicImages.challenge_icon, 4);
									Main.navc.popToRootViewControllerAnimated(true);
									EpicPlatform.doToastNotification(n);
								} else {
									EpicLog.i("Response (status " + response.responseCode + ") was: " + response.body);
									handleFailure(new EpicFrameworkException("Got response: " + response.body));
								}
							}
							
							public void handleFailure(Exception e) {
								EpicLog.e(e.toString());
								Main.navc.popToRootViewControllerAnimated(true);
								EpicPlatform.doToastNotification(new EpicNotification("Problem sending challenge request", new String[] { "Please try again later or contact support." }));
							}
						});
					}
				}
			}
		});		
	}
	
	@Override
	public String getTitle() {
		return "Online Challenges";
	}
	
	@Override
	public boolean shouldAutorotateToInterfaceOrientation(int uiInterfaceOrientation) {
		return false;
	}
	
}
