package com.example.HelloWorld;

import com.epic.framework.common.EpicConfig;
import com.epic.framework.common.Ui.EpicCanvas;
import com.epic.framework.common.Ui.EpicColor;
import com.epic.framework.common.Ui.EpicMenu;
import com.epic.framework.common.Ui.EpicPercentLayout;
import com.epic.framework.common.Ui.EpicScreen;
import com.epic.framework.common.Ui.MouseTrail;
import com.epic.framework.implementation.DesktopMainIos;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		EpicConfig.INITIAL_SCREEN = new EpicScreen() {
			
			@Override
			protected void onTimerTick() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected void onShow() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected void onPaint(EpicCanvas epicCanvas, int screenWidth, int screenHeight, MouseTrail mouseTrail) {
				epicCanvas.applyFullscreenFill(EpicColor.GREEN);
				epicCanvas.drawCircle(EpicColor.BLACK, 255, 400, 400, 100);
			}
			
			@Override
			protected boolean onNavigationMovement(int x, int y) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			protected void onMenuOpened(EpicMenu menu, EpicMenu debugMenu) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected void onMenuClosed() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected boolean onKeyPress(int c) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			protected void onHide() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected void onCreateUi(EpicPercentLayout bgLayout) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected void onClick(int x, int y) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected boolean onBackKey() {
				// TODO Auto-generated method stub
				return false;
			}
		};
		DesktopMainIos.main(args);
	}
}
