package org.thinkadv.enxita.youtubereviewsapp;

import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.thinkadv.enxita.youtubereviewsapp.model.Review;
import org.thinkadv.enxita.youtubereviewsapp.youtubeapi.Search;


/**
 * Unit test for simple App.
 */
public class YouTubeSearchTest extends TestCase {
	
	private Search search = Search.getInstance();
	
public void testNexus5Search() throws Exception {
	Set<Review> reviewList = search.fetchReviews("Nexus6");
	for (Review review: reviewList) {
		assertNotNull(review.getVideoId());
	}
}
	public void testSearch() throws Exception {
		
		Set<Review> reviewList = search.fetchReviews("Iphone 5s");
		for (Review review: reviewList) {
			assertNotNull(review.getVideoId());
//			assertNotNull(review.getMobilePhone().getMobileName());
		}
		
	}
	
	public void testHibernate() {
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		// Retreivee
		Review employee = (Review) session.get(Review.class, "1");
		System.out.println(employee);
	}
}
