package org.thinkadv.enxita.orm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.thinkadv.enxita.youtubereviewsapp.model.MobilePhone;
import org.thinkadv.enxita.youtubereviewsapp.model.Review;

public class DAOUtils {
	private static DAOUtils daoUtils = null;

	private DAOUtils() {

	}

	public static DAOUtils getInstance() {
		if (daoUtils == null) {
			daoUtils = new DAOUtils();
		}

		return daoUtils;
	}

	public List<Review> getReviewList() throws Exception {
		List<Review> reviewList = HibernateUtil.getSession()
				.createCriteria(Review.class).list();
		HibernateUtil.close();
		return reviewList;
	}

	public List<MobilePhone> getMobilePhoneList() throws Exception {
		List<MobilePhone> mobilePhoneList = HibernateUtil.getSession()
				.createCriteria(MobilePhone.class).list();
		HibernateUtil.close();
		return mobilePhoneList;
	}

	public Set<Review> getReviewsForMobilePhone(String mobileName)
			throws Exception {
		Set<Review> reviewList = null;
		MobilePhone mobilePhone = getMobilePhoneByName(mobileName);
		if (mobilePhone != null) {

			reviewList = mobilePhone.getReviewsList();
			Hibernate.initialize(mobilePhone.getReviewsList());
			for (Review review : reviewList) {
				Hibernate.initialize(review.getMobilePhone());
			}
			// mobilePhone.setReviewsList(reviewList);
		}
		HibernateUtil.close();

		return reviewList;
	}

	public boolean mobilePhoneExistsAlready(String mobileName) throws Exception {
		boolean alreadyExists = false;

		MobilePhone mobilePhone = getMobilePhoneByName(mobileName);
		if (mobilePhone != null) {
			alreadyExists = true;
		}
		HibernateUtil.close();

		return alreadyExists;
	}

	private MobilePhone getMobilePhoneByName(String mobileName) {
		Query query = HibernateUtil.getSession().createQuery(
				"from MobilePhone where mobileName = :mobileName");
		query.setParameter("mobileName", mobileName);
		MobilePhone mobilePhone = (MobilePhone) query.uniqueResult();
		return mobilePhone;
	}

	public boolean saveMobilePhone(MobilePhone mobilePhone) throws Exception {

		HibernateUtil.getSession().saveOrUpdate(mobilePhone);
		HibernateUtil.close();

		return true;
	}

	public void saveMobilePhoneWithReviews(String mobilePhoneName,
			Set<Review> reviewsList) {
		Transaction tx = HibernateUtil.getTransaction();
		MobilePhone mobilePhone = getMobilePhoneByName(mobilePhoneName);
		if (mobilePhone == null) {
			mobilePhone = new MobilePhone();
			mobilePhone.setMobileName(mobilePhoneName);
		}
		mobilePhone.setReviewsList(reviewsList);
		HibernateUtil.getSession().saveOrUpdate(mobilePhone);

		for (Review review : reviewsList) {
			review.setMobilePhone(mobilePhone);
			HibernateUtil.getSession().saveOrUpdate(review);
		}

		HibernateUtil.getSession().saveOrUpdate(mobilePhone);
		tx.commit();
		HibernateUtil.close();

	}

	public void deleteReviewByVideoId(String videoId) {

		Review deletedReview = (Review) HibernateUtil.getSession().load(
				Review.class, videoId);

		HibernateUtil.getSession().delete(deletedReview);

		HibernateUtil.close();
	}
	
	public Map<String, Set<Review>> getMobilePhoneReviewsMap() {
		Map<String, Set<Review>> reviewsMap = new HashMap<String, Set<Review>>();
		
		
		List<MobilePhone> mobilePhoneList = HibernateUtil.getSession()
				.createCriteria(MobilePhone.class).list();
		
		for (MobilePhone mobilePhone : mobilePhoneList) {
			Hibernate.initialize(mobilePhone.getReviewsList());
			reviewsMap.put(mobilePhone.getMobileName(), mobilePhone.getReviewsList());
		}
		
		return reviewsMap;
	}

}
