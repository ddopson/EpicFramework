package com.epic.framework.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import org.xmlvm.iphone.CGPoint;
import org.xmlvm.iphone.CGRect;
import org.xmlvm.iphone.UIEvent;
import org.xmlvm.iphone.UIFont;
import org.xmlvm.iphone.UIGraphics;
import org.xmlvm.iphone.UITouch;
import org.xmlvm.iphone.UITouchPhase;
import org.xmlvm.iphone.UIView;

import com.epic.framework.common.Ui.EpicCanvas;
import com.epic.framework.common.Ui.EpicPercentLayout.LayoutChild;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.Ui.EpicPlatformInterface;
import com.epic.framework.common.util.EpicLog;

public class EpicUiView extends UIView implements EpicPlatformInterface {
	public EpicUiView(CGRect rect) {
	    super(rect);
	    
	    EpicPlatform.initialize(this, null, null);
	  }
	
	  @Override
	  public void drawRect(CGRect rect) { 
		  EpicLog.v("EpicUiView.drawRect");
		  EpicCanvas c = EpicCanvas.get(UIGraphics.getCurrentContext());
		  EpicLog.v("EpicUiView.drawRect - about to draw currentScreen");
		  EpicPlatform.onPlatformPaint(c);
		  EpicLog.v("EpicUiView.drawRect - done");
	} 
	  
	  /**
       * List of points that represents the locations of fingers on the
       * screen.
       */
      private List<CGPoint> points = new ArrayList<CGPoint>();

      /**
       * This method converts the events encapsulated by an UIEvent into a
       * list of CGPoints. The list is stored in 'points'.
       */
      private void getTouches(UIEvent event) {
    	  EpicLog.i("Getting touches...");
          /**
           * Erase previous list of points.
           */
          points.clear();
          /**
           * Iterate over all UITouches that are associated with this
           * UIEvent
           */
          for (UITouch touch : event.allTouches()) {
              /**
               * If a UITouch has the phase UITouchPhaseEnded (finger
               * lifted from screen) it is not included.
               */
              if (touch.getPhase() != UITouchPhase.Ended) {
                  /**
                   * The locationInView() method retrieves the coordinates
                   * of the UITouch relative to a specific view (which is
                   * the custom view in this case).
                   */
                  points.add(touch.locationInView(this));
              }
          }
          
          for(CGPoint p : points) {
        	  EpicPlatform.onPlatformTouchFinished((int)p.x, (int)p.y);
//        	  currentScreen.onClick((int) p.x, (int) p.y);
          }
      }

      /**
       * Called whenever a finger touches the screen.
       */
      @Override
      public void touchesBegan(Set<UITouch> touches, UIEvent event) {
          getTouches(event);
      }

      /**
       * Called whenever a finger is lifted from the screen.
       */
      @Override
      public void touchesEnded(Set<UITouch> touches, UIEvent event) {
          getTouches(event);
      }

      /**
       * Called whenever a finger that is already touching the screen is
       * moved across the screen.
       */
      @Override
      public void touchesMoved(Set<UITouch> touches, UIEvent event) {
          getTouches(event);
      }

	@Override
	public void requestRepaint() {
		this.setNeedsDisplay();
	}

	@Override
	public void requestRelayout() {
		this.setNeedsDisplay();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void layoutChild(LayoutChild child, int l, int r, int t, int b, boolean firstLayout) {
		// TODO Auto-generated method stub
		
	}
}
