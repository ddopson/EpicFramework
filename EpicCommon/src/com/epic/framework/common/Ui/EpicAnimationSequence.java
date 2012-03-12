package com.epic.framework.common.Ui;

import java.util.ArrayList;

import com.epic.framework.common.util.*;

public class EpicAnimationSequence {
	public EpicImage[] frames;
	String baseName;
	public String owner;

	public static class EpicAnimationFrame {
		EpicImage image;
	}
	
	private static ArrayList<EpicAnimationSequence> allAnimations = new ArrayList<EpicAnimationSequence>();
	
	public EpicAnimationSequence(EpicImage baseImage, int nFrames) {
		String baseName = baseImage.name;
		this.baseName = baseName;
		this.frames = new EpicImage[nFrames];
		int index = baseName.length() - "_000".length();
		if(!baseName.substring(index).equals("_000")) {
			throw EpicFail.invalid_argument("baseName should end in '_000' baseName='" + baseName + "'");
		}
		baseName = baseName.substring(0, index);
		for(int i = 0; i < frames.length; i++) {
			frames[i] = EpicImageFromResource.lookupByNameOrThrow(baseName + "_" + StringHelper.zeroPaddedInt(i, 3));
		}
		allAnimations.add(this);
	}

	public EpicImage getFrame(int n) {
		return frames[n % frames.length];
	}

	public int length() {
		return frames.length;
	}
}
