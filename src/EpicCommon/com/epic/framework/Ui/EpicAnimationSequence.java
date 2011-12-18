package com.epic.framework.Ui;

import java.util.ArrayList;

import com.epic.framework.util.*;

public class EpicAnimationSequence {
	public EpicBitmap[] frames;
	String baseName;
	public String owner;

	public static class EpicAnimationFrame {
		EpicBitmap image;
	}
	
	private static ArrayList<EpicAnimationSequence> allAnimations = new ArrayList<EpicAnimationSequence>();
	
	public EpicAnimationSequence(EpicBitmap baseImage, int nFrames) {
		String baseName = baseImage.getName();
		this.baseName = baseName;
		this.frames = new EpicBitmap[nFrames];
		int index = baseName.length() - "_000".length();
		if(!baseName.substring(index).equals("_000")) {
			throw EpicFail.invalid_argument("baseName should end in '_000' baseName='" + baseName + "'");
		}
		baseName = baseName.substring(0, index);
		for(int i = 0; i < frames.length; i++) {
			frames[i] = EpicBitmap.lookupByNameOrThrow(baseName + "_" + StringHelper.zeroPaddedInt(i, 3));
		}
		allAnimations.add(this);
	}

	public EpicBitmap getFrame(int n) {
		return frames[n % frames.length];
	}

	public int length() {
		return frames.length;
	}
}
