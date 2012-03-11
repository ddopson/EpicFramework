package com.epic.framework.implementation;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import com.epic.framework.common.Ui.EpicCanvas;
import com.epic.framework.common.Ui.EpicPercentLayout.LayoutChild;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.util.EpicLog;

@SuppressWarnings("serial")
public class EpicNativeGameFrame extends JPanel implements MouseListener, MouseMotionListener {
	private static EpicNativeGameFrame theGameFrame;

	public static EpicNativeGameFrame get() {
		if(theGameFrame == null) {
			theGameFrame = new EpicNativeGameFrame();
		}
		return theGameFrame;
	}
	private EpicNativeGameFrame() {
		this.setVisible(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setLayout(new LayoutManager() {

			public void removeLayoutComponent(Component comp) {
				// TODO Auto-generated method stub
				EpicLog.d("LayoutManager.removeLayoutComponent");
			}

			public Dimension preferredLayoutSize(Container parent) {
				// TODO Auto-generated method stub
				EpicLog.d("LayoutManager.preferredLayoutSize");
				return EpicSimulator.getCurrentScreenSize();
			}

			public Dimension minimumLayoutSize(Container parent) {
				// TODO Auto-generated method stub
				EpicLog.d("LayoutManager.minimumLayoutSize");
				return EpicSimulator.getCurrentScreenSize();
			}

			public void layoutContainer(Container parent) {
				// TODO Auto-generated method stub
				EpicLog.d("LayoutManager.layoutContainer");

				EpicPlatform.onPlatformLayoutRequest(
						getWidth(),
						getHeight(),
						true
				);
			}

			public void addLayoutComponent(String name, Component comp) {
				// TODO Auto-generated method stub
				EpicLog.d("LayoutManager.addLayoutComponent");
			}
		});
	}
	private static BufferedImage buffer;
	private static Graphics bufferGraphics;
	private void ensureBuffer(int width, int height) {
		if(buffer == null || buffer.getWidth() != width || buffer.getHeight() != height) {
			EpicLog.d("Allocating Screen Buffer " + width + "x" + height);
			buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			bufferGraphics = buffer.getGraphics();
		}
	}

	protected void paintComponent(Graphics g) {
		ensureBuffer(this.getWidth(), this.getHeight());
		EpicPlatform.onPlatformPaint(EpicCanvas.get(bufferGraphics));
		g.drawImage(buffer, 0, 0, null);
	}
	//	public void paint(Graphics g) {
	//		EpicPlatform.onPlatformPaint(EpicCanvas.get(g));
	//		super.paintChildren(g);
	//	}


	// MOUSE EVENTS
	public void mousePressed(MouseEvent e) {
		EpicPlatform.onPlatformTouchEvent(e.getX(), e.getY());
	}
	public void mouseDragged(MouseEvent e) {
		EpicPlatform.onPlatformTouchEvent(e.getX(), e.getY());
	}
	public void mouseReleased(MouseEvent e) {
		EpicPlatform.onPlatformTouchFinished(e.getX(), e.getY());
	}
	public void mouseClicked(MouseEvent e) {
		// DDOPSON-2011-07-02 - mouseReleased is always called, so mouseClicked is redundant and uncommenting the following line results in double clicks
		//		EpicPlatform.onPlatformTouchFinished(e.getX(), e.getY());
	}

	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mouseMoved(MouseEvent e) { }


	// EpicPlatformInterface Methods
	public void requestRepaint() {
		this.repaint();
	}

	public void requestRelayout() {
		this.invalidate();
		this.validate();
		this.repaint();
	}

	public void clear() {
		this.removeAll();
	}

	public void layoutChild(LayoutChild child, int l, int r, int t, int b, boolean firstLayout) {
		if(firstLayout) {
			this.add(child.child.getDesktopComponent());
		}
		child.child.getDesktopComponent().setLocation(l, t);
		child.child.getDesktopComponent().setSize(r - l, b - t);
		child.child.getDesktopComponent().doLayout();				
	}
}
