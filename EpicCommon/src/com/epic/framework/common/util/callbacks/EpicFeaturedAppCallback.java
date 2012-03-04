package com.epic.framework.common.util.callbacks;

import com.epic.framework.common.util.EpicFeaturedOffer;

public interface EpicFeaturedAppCallback {
	public void getFeaturedAppResponseFailed(String error);
	public void getFeaturedAppResponse(EpicFeaturedOffer featuredAppObject);
}
