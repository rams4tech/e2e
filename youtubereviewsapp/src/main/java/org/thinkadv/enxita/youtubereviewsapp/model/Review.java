package org.thinkadv.enxita.youtubereviewsapp.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
public class Review {

	@Id
	private String videoId;
	private String reviewTitle;
	private String reviewDescription;
	private String channelTitle;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date publishedDate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mobileId", nullable = false)
	private MobilePhone mobilePhone;

	public Review() {

	}

	public Review(String videoId, String reviewTitle, String reviewDescription,
			String channelTitle, Date publishedDate) {
		super();
		this.videoId = videoId;
		this.reviewTitle = reviewTitle;
		this.reviewDescription = reviewDescription;
		this.channelTitle = channelTitle;
		this.publishedDate = publishedDate;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getReviewTitle() {
		return reviewTitle;
	}

	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}

	public String getReviewDescription() {
		return reviewDescription;
	}

	public void setReviewDescription(String reviewDescription) {
		this.reviewDescription = reviewDescription;
	}

	public String getChannelTitle() {
		return channelTitle;
	}

	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	@Override
	public String toString() {
		return "Review [videoId=" + videoId + ", reviewTitle=" + reviewTitle
				+ ", reviewDescription=" + reviewDescription
				+ ", channelTitle=" + channelTitle + ", publishedDate="
				+ publishedDate + "]";
	}

	@JsonIgnore
	public MobilePhone getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(MobilePhone mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (obj == this) {
			return true;
		} else {
			return this.getVideoId() == ((Review)obj).getVideoId();
		}
	}
	
	

}
