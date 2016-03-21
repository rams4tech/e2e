package org.thinkadv.enxita.youtubereviewsapp.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.junit.Ignore;

@Entity
public class MobilePhone {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="mobileId")
	private int id;
	@Column(name="moibleName",unique=true)
	private String mobileName;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mobilePhone")
	private Set<Review> reviewsList;
	
	
	public MobilePhone() {

	}

	public MobilePhone(int id, String mobileName) {
		super();
		this.id = id;
		this.mobileName = mobileName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobileName() {
		return mobileName;
	}

	public void setMobileName(String mobileName) {
		this.mobileName = mobileName;
	}

	@JsonIgnore
	public Set<Review> getReviewsList() {
		return reviewsList;
	}

	public void setReviewsList(Set<Review> reviewsList) {
		this.reviewsList = reviewsList;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		} else if (this == obj) {
			return true;
		} else {
			return this.getId() == ((MobilePhone)obj).getId();
		}
	}
	
	
}
