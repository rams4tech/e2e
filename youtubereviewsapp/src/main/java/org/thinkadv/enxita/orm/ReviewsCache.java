package org.thinkadv.enxita.orm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.thinkadv.enxita.youtubereviewsapp.model.Review;

public class ReviewsCache {

	private static ReviewsCache reviewsCache;
	
	private Map<String, Set<Review>> cacheMap = new HashMap<String, Set<Review>>();
	
	private ReviewsCache() {
		initializeCache();
	}
	
	private void initializeCache() {
		this.cacheMap = DAOUtils.getInstance().getMobilePhoneReviewsMap();
	}

	public static ReviewsCache getInstance() {
		if (reviewsCache == null) {
			reviewsCache = new ReviewsCache();
		}
		return reviewsCache;
	}

	public Map<String, Set<Review>> getCacheMap() {
		return cacheMap;
	}

	public void setCacheMap(Map<String, Set<Review>> cacheMap) {
		this.cacheMap = cacheMap;
	}
}
