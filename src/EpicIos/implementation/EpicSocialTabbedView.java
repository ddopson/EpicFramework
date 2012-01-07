package com.epic.framework.implementation;

import java.util.ArrayList;

import org.xmlvm.iphone.NSIndexPath;
import org.xmlvm.iphone.UIAlertView;
import org.xmlvm.iphone.UIImage;
import org.xmlvm.iphone.UITabBarController;
import org.xmlvm.iphone.UITableView;
import org.xmlvm.iphone.UITableViewController;
import org.xmlvm.iphone.UITableViewDelegate;
import org.xmlvm.iphone.UITableViewStyle;
import org.xmlvm.iphone.UIViewController;

import com.epic.framework.common.Ui.EpicNotification;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.util.EpicHttpResponse;
import com.epic.framework.common.util.EpicHttpResponseHandler;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.exceptions.EpicFrameworkException;
import com.epic.framework.common.util.exceptions.EpicRuntimeException;
import com.epic.resources.EpicImages;
import com.realcasualgames.words.OnlineChallenge;
import com.realcasualgames.words.PlayerState;
import com.realcasualgames.words.ScreenNursery;
import com.realcasualgames.words.WordsHttp;


public class EpicSocialTabbedView extends UITabBarController {

	private OnlineChallenge[] completedGames;
	private OnlineChallenge[] waitingGames;
	private OnlineChallenge[] pendingGames;
	private String[] players;
	private String[] customer_ids;
	private int[] options;
	protected String[] toDisplay;
	protected String[] emails;
	
	public EpicSocialTabbedView() {
		ArrayList<UIViewController> list = new ArrayList<UIViewController>();
        
        UITableViewController controller = new UITableViewController(UITableViewStyle.Grouped);
        final UITableView table = controller.getTableView();
        
        WordsHttp.getChallenges(25, new EpicHttpResponseHandler() {
			
			public void handleResponse(EpicHttpResponse response) {
				OnlineChallenge[] challenges = OnlineChallenge.parseList(response.body);
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
					
			        ListDataSource src = new ListDataSource(new String[][] { pendingTitles, waitingTitles, completedTitles }, new String[] { "Your Turn", "Their Turn", "Completed"});
			        if(table != null) {
			        	EpicLog.v("Refreshing challenge list...");
				        table.setDataSource(src);
				        table.setNeedsDisplay();
				        table.reloadData();
			        }
				} else {
					ListDataSource src = new ListDataSource(new String[][] { new String[] { "No Games Found" }}, new String[] { "Online Challenges"});
					if(table != null) {
						EpicLog.v("Refreshing challenge list...");
						table.setDataSource(src);
						table.setNeedsDisplay();
						table.reloadData();
			        }
				}
			}
			
			public void handleFailure(Exception e) {
				EpicLog.e(e.toString());
			}
		});
        
        table.setDelegate(
        		new UITableViewDelegate() {
        			public void didSelectRowAtIndexPath(UITableView tableview, NSIndexPath indexPath) {
        				ListDataSource src = (ListDataSource) tableview.getDataSource();
        				EpicLog.i("Selected: " + indexPath.getSection() + ", " + indexPath.getRow());
        				// Should go to Challenge Details screen, inside native game
        				OnlineChallenge c = null;
        				switch(indexPath.getSection()) {
        				case 0:
    						c = pendingGames[indexPath.getRow()];
    						break;
        				case 1:
    						c = waitingGames[indexPath.getRow()];
    						break;
        				case 2:
    						c = completedGames[indexPath.getRow()];
    						break;
        				}
        				
//        				UIAlertView alert = new UIAlertView("Online Challenge Details", "Challenge ID: " + c.challenge_id, null, "OK");
//        				alert.show();
        				
        				EpicSocialImplementation.showChallengeDetails(c.challenge_id);
        			}
        		});
//        
        
        final UITableViewController top = new UITableViewController(UITableViewStyle.Grouped) {
        	public boolean shouldAutorotateToInterfaceOrientation(int uiInterfaceOrientation) {
       			return false;
        	}
        };
        
		WordsHttp.displayOnlineLeaderboard(new EpicHttpResponseHandler() {
			public void handleResponse(EpicHttpResponse response) {
				EpicLog.i("Response: " + response.body);
				players = response.body.split(";");
				// TODO: HACK -- -1 is for split() returning an extra piece of shit for some reason
				toDisplay = new String[players.length-1];
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
					
					toDisplay[i] = email + " (" + parts[1] + ")"; 
					EpicLog.i("Displaying " + toDisplay[i]);
				}
//				
				EpicLog.v("Refreshing leaderboard...");
		        ListDataSource src = new ListDataSource(new String[][] { toDisplay }, new String[] { "Top Players" });
		        top.getTableView().setDataSource(src);
		        top.getTableView().reloadData();
			}
			
			public void handleFailure(Exception e) {
				EpicLog.e(e.toString());
			}
		});		


        top.getTableView().setDelegate(
        		new UITableViewDelegate() {
        			public void didSelectRowAtIndexPath(UITableView tableview, NSIndexPath indexPath) {
        				ListDataSource src = (ListDataSource) tableview.getDataSource();
        				EpicLog.i("Selected: " + indexPath.getSection() + ", " + indexPath.getRow());
        				// Should go to issue challenge screen
        				selectWagerAndSendChallengeTo(players[indexPath.getRow()], toDisplay[indexPath.getRow()]);
        			}
        		});
        
        
        final UITableViewController start = new UITableViewController(UITableViewStyle.Grouped) {
        	public boolean shouldAutorotateToInterfaceOrientation(int uiInterfaceOrientation) {
       			return false;
        	}
        };
        
		WordsHttp.getFriends(new EpicHttpResponseHandler() {

			public void handleResponse(EpicHttpResponse response) {
				EpicLog.i("Response: " + response.body);
				if(response.body.length() > 0) {
					final String[] parts = response.body.split(";");
					EpicLog.i("Found " + parts.length + " friends");
//					// TODO: hack since split returns 1 extra usually
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
					emails[0] = "<<Random Opponent>>";
				} else {
					emails = new String[] { "<<Random Opponent>>" };
				}
//				
				EpicLog.v("Refreshing Friends List...");
		        ListDataSource src = new ListDataSource(new String[][] { emails }, new String[] { "Select an Opponent" });
		        start.getTableView().setDataSource(src);
		        start.getTableView().reloadData();
			}
			
			public void handleFailure(Exception e) {
				EpicLog.e(e.toString());
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
        
        controller.setTitle("Challenges");
        controller.getTabBarItem().setImage(UIImage.imageNamed("challenge_icon.png"));
        top.setTitle("Top Players");
        top.getTabBarItem().setImage(UIImage.imageNamed("icon_web.png"));
        start.setTitle("Start Challenge");
        start.getTabBarItem().setImage(UIImage.imageNamed("icon_web.png"));
        
        list.add(controller);
        list.add(top);
        list.add(start);
        this.setViewControllers(list);
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
		Main.navc.setNavigationBarHidden(false, true);
		
		wagerList.getTableView().setDelegate(new UITableViewDelegate() {
			public void didSelectRowAtIndexPath(UITableView tableview, NSIndexPath indexPath) {
				final int wager = options[indexPath.getRow()];
				
				if(PlayerState.getTokens() < wager) {
					// prompt for tokens
//					EpicDialogBuilder b = new EpicDialogBuilder();
//					b.setMessage("You do not have enough tokens for this wager. Please select another option or get more tokens.").
//					setPositiveButton("Get More Tokens", new EpicClickListener() {
//						public void onClick() {
//							EpicPlatform.changeScreen(new ScreenBuyTokens(new ScreenMainMenu()));
//						}
//					}).
//					setNegativeButton("OK", new EpicClickListener() {
//						public void onClick() {
//							selectWagerAndSendChallengeTo(opponent_id);
//						}
//					}).show();
//					
//					dialog.dismiss();
//					return;
				} else {

					if(opponent_id == null) {
						
						if(PlayerState.getState().currentChallenge != null) {
							EpicNotification n = new EpicNotification("You are already in a challenge!", new String[] { "Click here to play your challenge."}, EpicImages.challenge_icon, 6);
	//						n.clickCallback = new EpicClickListener() {
	//							public void onClick() {
	//								EpicPlatform.changeScreen(new ScreenNursery());
	//							}
	//						};
							
							EpicPlatform.doToastNotification(n);
							return;
						}
	
						WordsHttp.sendRandomChallenge(wager, new EpicHttpResponseHandler() {
							public void handleResponse(EpicHttpResponse response) {
								if(response.responseCode == 200) {
									PlayerState.getState().openChallenges++;
									if(PlayerState.getState().setCurrentChallengeId(response.body, wager)) {
										EpicPlatform.doToastNotification(new EpicNotification("Challenge Begun!", new String[] { "Your next game will be your entry in this challenge."}, EpicImages.challenge_icon, 5));
										Main.navc.popToRootViewControllerAnimated(true);
										Main.navc.setNavigationBarHidden(true, true);
										EpicPlatform.changeScreen(new ScreenNursery());
									}
								} else {
									EpicLog.i("Response (status " + response.responseCode + ") was: " + response.body);
								}
							}
							
							public void handleFailure(Exception e) {
								EpicLog.e(e.toString());
								EpicPlatform.doToastNotification(new EpicNotification("Problem sending challenge request", new String[] { "Please try again later or contact support." }));
							}
						});
						
						PlayerState.onChallengeComplete(65);
						
					} else {
						if(PlayerState.getState().currentChallenge != null) {
							EpicNotification n = new EpicNotification("You are already in a challenge!", new String[] { "Click here to play your challenge."}, EpicImages.challenge_icon, 6);
	//						n.clickCallback = new EpicClickListener() {
	//							public void onClick() {
	//								Main.navc.popToRootViewControllerAnimated(true);
	//								EpicPlatform.changeScreen(new ScreenNursery());
	//							}
	//						};
							EpicPlatform.doToastNotification(n);
							return;
						}
	
						WordsHttp.sendChallenge(opponent_id, wager, new EpicHttpResponseHandler() {
							public void handleResponse(EpicHttpResponse response) {
								if(response.responseCode == 200) {
									PlayerState.getState().openChallenges++;
									if(PlayerState.getState().setCurrentChallengeId(response.body, wager)) {
										EpicPlatform.doToastNotification(new EpicNotification("Challenge Begun!", new String[] { "Your next game will be your entry in this challenge."}, EpicImages.challenge_icon, 5));
										Main.navc.popToRootViewControllerAnimated(true);
										Main.navc.setNavigationBarHidden(true, true);
										EpicPlatform.changeScreen(new ScreenNursery());
									}
								} else if(response.responseCode == 403) {
									EpicNotification n = new EpicNotification("Your challenge was declined.", new String[] { "Your opponent is not accepting challenges at this time." }, EpicImages.challenge_icon, 4);
									EpicPlatform.doToastNotification(n);
								} else {
									EpicLog.i("Response (status " + response.responseCode + ") was: " + response.body);
									handleFailure(new EpicFrameworkException("Got response: " + response.body));
								}
							}
							
							public void handleFailure(Exception e) {
								EpicLog.e(e.toString());
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
	public void viewWillDisappear(boolean animated) {
		super.viewWillDisappear(animated);
		//Main.navc.setNavigationBarHidden(true, true);
	}
	
	@Override
	public boolean shouldAutorotateToInterfaceOrientation(int uiInterfaceOrientation) {
		return false;
	}
	
}
