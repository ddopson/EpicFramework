package com.epic.framework.Ui;

import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.JApplet;

import com.epic.framework.util.EpicLog;


@SuppressWarnings("serial")
public class AppletMain extends JApplet {
	private static AppletMain applet;
	public AppletMain() {
		applet = this;
		EpicPlatform.initialize(EpicNativeGameFrame.get());
		this.setContentPane(EpicNativeGameFrame.get());
		EpicLog.i("Applet Double Buffering? = '" + this.getRootPane().isDoubleBuffered() + "'");
		this.getRootPane().setDoubleBuffered(true);
		this.setJMenuBar(new EpicSimulatorMenuBar());
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
				new KeyEventDispatcher() {			
					public boolean dispatchKeyEvent(KeyEvent e) {
						if(e.getID() == KeyEvent.KEY_TYPED) {
							EpicPlatform.onPlatformKeyPress(e.getKeyChar());
						}
						return false;
					}
				}
		);
		this.setBackground(null);
	}
	public void foo() {
	}

	public void update(Graphics g) {
//		EpicLog.d("AppletMain.update");
		paint(g);
	}
	public void paintAll(Graphics g) {
		EpicLog.d("AppletMain.paintAll");
		paint(g);
	}
	public void paintComponents(Graphics g) {
		EpicLog.d("AppletMain.paintComponents");
		super.paintComponents(g);
	}
	public void paint(Graphics g)  
	{ 
		EpicLog.d("AppletMain.paint (not)");
		paintComponents(g);
	}
}
