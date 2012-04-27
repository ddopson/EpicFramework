package com.epic.framework.common.Ui;

import java.util.ArrayList;
import java.util.List;

import com.epic.framework.common.Ui2.EpicNativeWidget;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.exceptions.EpicRuntimeException;
import com.epic.framework.implementation.EpicPlatformImplementation;

import static com.epic.framework.common.EpicConfig.*;

public class EpicPercentLayout {
	ArrayList<LayoutChild> children = new ArrayList<LayoutChild>();
	private boolean firstLayout = true;

	public EpicPercentLayout() {
	}

	public class LayoutChild {
		public final int left, top, right, bottom;
		public final boolean absolute;
		public EpicNativeWidget child;
		public LayoutChild(int left, int top, int right, int bottom, EpicNativeWidget child, boolean absolute) {
			this.left = left;
			this.top = top;
			this.right = right;
			this.bottom = bottom;
			this.child = child;
			this.absolute = absolute;
		}
	}

	public EpicNativeWidget addChild(int left, int top, int right, int bottom, EpicNativeWidget child) {
		return addChild(left, top, right, bottom, child, false, false);
	}

	public EpicNativeWidget addChildAbsolute(int left, int top, int right, int bottom, EpicNativeWidget child) {
		return addChild(left, top, right, bottom, child, false, true);
	}

	public EpicNativeWidget addChildMaintainAspect(int left, int top, int right, int bottom, EpicNativeWidget child) {
		EpicFail.not_implemented();
		return addChild(left, top, right, bottom, child, true, false);
	}

	public EpicNativeWidget addFullscreenChild(EpicNativeWidget child) {
		return addChild(0, 0, DESIGN_WIDTH, DESIGN_HEIGHT, child);
	}

	private EpicNativeWidget addChild(int left, int top, int right, int bottom, EpicNativeWidget child, boolean maintainAspect, boolean absolute) {
		if(left < 0) {
			throw EpicFail.invalid_argument("left", left);
		}
		else if(left > right) {
			throw EpicFail.invalid_argument("left > right");
		}
		else if(right > DESIGN_WIDTH) {
			throw EpicFail.invalid_argument("right out of bounds", right);
		}
		else if (top < 0) {
			throw EpicFail.invalid_argument("top < 0", top);
		}
		else if (top > bottom) {
			throw EpicFail.invalid_argument("top > bottom");
		}
		else if (bottom > DESIGN_HEIGHT) {
			throw EpicFail.invalid_argument("bottom out of bounds", bottom);
		}
		children.add(new LayoutChild(left, top, right, bottom, child, absolute));
		return child;
	}

	public List<LayoutChild> getChildren() {
		return this.children;
	}

	public void clear() {
		EpicLog.d("Clearing EpicPercentLayout");
		this.children.clear();
		EpicPlatformImplementation.clear();
		firstLayout = true;
	}

	public void doTheLayout(int width, int height, boolean invertWidgetOrder) {
		EpicLog.df("EpicDesktopPercentLayout.doLayout(%d, %d)", width, height);
		if(invertWidgetOrder) {
			for(int i = children.size() - 1; i >= 0; i--) {
				LayoutChild child = children.get(i);
				doTheLayout(width, height, child);
			}			
		}
		else {
			for(int i = children.size() - 1; i >= 0; i--) {
				doTheLayout(width, height, children.get(i));
			}
		}
		firstLayout = false;
	}

	private void doTheLayout(int width, int height, LayoutChild child) {
		int l,t,r,b;
		if(child.absolute) {
			l = child.left;
			t = child.top;
			r = child.right;
			b = child.bottom;
		}
		else {
			l = (int) (child.left * width / DESIGN_WIDTH);
			t = (int) (child.top * height / DESIGN_HEIGHT);
			r = (int) (child.right * width / DESIGN_WIDTH);
			b = (int) (child.bottom * height / DESIGN_HEIGHT);
		}
		//		if(layoutParams.maintainAspect) {
		//			float desiredAspect = (layoutParams.right - layoutParams.left) / (layoutParams.bottom - layoutParams.top);
		//			float actualAspect = (right - left) / (bottom - top);
		//			EpicLog.e("DEBUG: " + desiredAspect + " vs " + actualAspect);
		//			if(actualAspect > desiredAspect) {
		//				// shrink width
		//				float shrink_factor = (actualAspect - desiredAspect) / actualAspect / 2;
		//				left += (right - left) * shrink_factor;
		//				right -= (right - left) * shrink_factor;
		//			}
		//			else {
		//				float shrink_factor = ((1/actualAspect) - (1/desiredAspect)) / (1/actualAspect) / 2;
		//				top += (bottom - top) * shrink_factor;
		//				bottom -= (bottom - top) * shrink_factor;
		//			}
		//		}

		EpicLog.df(
				"%s Laying out child to (%d-%d) x (%d-%d)  child=(%d-%d) x (%d-%d)", 
				(firstLayout ? "FIRST_TIME" : ""), 
				l, r, t, b, 
				child.left, child.right, child.top, child.bottom
		);
		try {
			EpicFail.assertNotNull(child.child.getNativeObject());
		}
		catch(EpicRuntimeException e) {
			EpicLog.e("child=" + child.child);
			throw e;
		}
		EpicPlatformImplementation.layoutChild(child, l, r, t, b, firstLayout);	
	}
}
