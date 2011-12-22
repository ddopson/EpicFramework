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

import com.epic.framework.common.util.EpicHttpResponse;
import com.epic.framework.common.util.EpicHttpResponseHandler;
import com.epic.framework.common.util.EpicLog;
import com.realcasualgames.words.OnlineChallenge;
import com.realcasualgames.words.WordsHttp;


public class EpicSocialTabbedView extends UITabBarController {

	private OnlineChallenge[] completedGames;
	private OnlineChallenge[] waitingGames;
	private OnlineChallenge[] pendingGames;
	private String[] players;
	
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
					
			        ListDataSource src = new ListDataSource(new String[][] { pendingTitles, waitingTitles, completedTitles }, new String[] { "Waiting for you", "Waiting for opponent", "Completed"});
			        table.setDataSource(src);
				} else {
					ListDataSource src = new ListDataSource(new String[][] { new String[] { "No Games Found" }}, new String[] { "Online Challenges"});
			        table.setDataSource(src);
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
       			return true;
        	}
        };
        
		WordsHttp.displayOnlineLeaderboard(new EpicHttpResponseHandler() {
			public void handleResponse(EpicHttpResponse response) {
				EpicLog.i("Response: " + response.body);
				players = response.body.split(";");
				// TODO: HACK -- -1 is for split() returning an extra piece of shit for some reason
				String[] toDisplay = new String[players.length-1];
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
		        ListDataSource src = new ListDataSource(new String[][] { toDisplay }, new String[] { "Top Players" });
		        top.getTableView().setDataSource(src);
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
        				UIAlertView alert = new UIAlertView("Issuing Challenge", "This will issue a challenge to " + players[indexPath.getRow()], null, "OK");
        				alert.show();
        			}
        		});
        
        
        controller.setTitle("Challenges");
        controller.getTabBarItem().setImage(UIImage.imageNamed("challenge_icon.png"));
        top.setTitle("Top Players");
        top.getTabBarItem().setImage(UIImage.imageNamed("icon_web.png"));
        
        list.add(controller);
        list.add(top);
        this.setViewControllers(list);
	}
	
	@Override
	public String getTitle() {
		return "Online Challenges";
	}
	
	@Override
	public void viewWillDisappear(boolean animated) {
		super.viewWillDisappear(animated);
		Main.navc.setNavigationBarHidden(true, true);
	}
	
	public boolean shouldAutorotateToInterfaceOrientation(int uiInterfaceOrientation) {
		return true;
	}
	
}
