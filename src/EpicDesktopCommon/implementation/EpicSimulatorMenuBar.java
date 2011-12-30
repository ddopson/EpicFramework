package com.epic.framework.implementation;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.epic.framework.common.Ui.EpicMenu;
import com.epic.framework.common.Ui.EpicMenu.EpicMenuItem;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.util.EpicLog;

@SuppressWarnings("serial")
public class EpicSimulatorMenuBar extends JMenuBar {
	JMenu simulatorMenu;
	public EpicSimulatorMenuBar() {
		this.add(new EpicSimulatorMenu("Game", EpicMenu.MENU_GAME));
		this.add(new EpicSimulatorMenu("Debug", EpicMenu.MENU_DEBUG));
		this.simulatorMenu = (JMenu)this.add(new JMenu("Simulator"));
		for(final Dimension size : EpicSimulator.supportedScreenSizes) {
			simulatorMenu.add(new JMenuItem("Set Screen Size - " + size.width + "x" + size.height)).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					EpicLog.w("Setting Screen Size to " + size.width + "x" + size.height);
					EpicSimulator.setScreenSize(size);
				}
			});
		}
		this.setVisible(true);
	}

	private class EpicSimulatorMenu extends JMenu implements MenuListener {
		int epicMenuType;
		public EpicSimulatorMenu(String name, int epicMenuType) {
			super(name);
			this.epicMenuType = epicMenuType;
			this.addMenuListener(this);
		}
		public void menuCanceled(MenuEvent e) { }
		public void menuDeselected(MenuEvent e) { }
		public void menuSelected(MenuEvent e) {
			EpicSimulatorMenu.this.removeAll();
			for(final EpicMenuItem menuItem : EpicPlatform.getMenuItems(epicMenuType)) {
				this.add(new JMenuItem(menuItem.name)).addActionListener(new ActionListener() {					
					public void actionPerformed(ActionEvent e) {
						menuItem.onClicked();
					}
				});
			}
		}
	}
}