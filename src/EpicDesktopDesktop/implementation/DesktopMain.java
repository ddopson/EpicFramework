package com.epic.framework.implementation;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.epic.framework.implementation.ArchPlatform;
import com.epic.framework.common.Ui.EpicClickListener;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.EpicStopwatch;
import com.epic.framework.common.util.StringHelper;
//import com.google.monitoring.runtime.instrumentation.AllocationRecorder;
//import com.google.monitoring.runtime.instrumentation.Sampler;

public class DesktopMain {
	private static JFrame mainFrame;
	public static void MainMethod(String[] args) throws InterruptedException {
		//		AllocationRecorder.addSampler(new Sampler() {
		//			public void sampleAllocation(int count, String desc, Object newObj, long size) {
		//				EpicStopwatch.reportAllocation(count, desc, newObj, size);
		//			}
		//		});
		mainFrame = new JFrame();
		mainFrame.setContentPane(EpicNativeGameFrame.get());
		EpicPlatform.initialize(EpicNativeGameFrame.get(), EpicSimulator.currentScreenSize.width, EpicSimulator.currentScreenSize.height, null, null);
		mainFrame.setJMenuBar(new EpicSimulatorMenuBar());
		mainFrame.pack();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
				new KeyEventDispatcher() {			
					public boolean dispatchKeyEvent(KeyEvent e) {
//						EpicLog.i("key event: " + StringHelper.namedArgList("id", e.getID(), "code", e.getKeyCode(), "char", e.getKeyChar(), "mods", e.getModifiersEx()));
						if(e.getID() == KeyEvent.KEY_TYPED) {
							EpicPlatform.onPlatformKeyPress(e.getKeyChar());
						} else if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == 37) {
							EpicPlatform.onPlatformNavigationMovement(-1, 0); // LEFT
						} else if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == 38) {
							EpicPlatform.onPlatformNavigationMovement(0, -1); // UP
						} else if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == 39) {
							EpicPlatform.onPlatformNavigationMovement(1, 0); // RIGHT
						} else if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == 40) {
							EpicPlatform.onPlatformNavigationMovement(0, 1); // DOWN
						}
						return false;
					}
				}
		);
//		createStatsWindow();
	}

	public static void createStatsWindow() {
		final JFrame statsFrame = new JFrame();
		Point loc = mainFrame.getLocation();
		loc.x += mainFrame.getWidth();
		statsFrame.setLocation(loc);
		statsFrame.setLayout(new FlowLayout());
		final JTextArea statsData = new JTextArea() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(400, 400);
			}
		};
		statsData.setWrapStyleWord(true);
		statsData.setText("Hello World");
		statsFrame.add(statsData);

		EpicStopwatch.setFrameUpdateAction(new EpicClickListener() {
			public void onClick() {
				statsData.setText(EpicStopwatch.getDebugString() + ArchPlatform.getMemoryStats());
				statsFrame.pack();
			}
		});

		final JButton resetButton = new JButton();
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EpicStopwatch.reset();
			}
		});
		statsFrame.add(resetButton);
		statsFrame.setVisible(true);
	}
}