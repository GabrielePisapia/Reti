package com.sm.reti.fabrikam_functions;

import java.util.ArrayList;

public class ReviewsBean {

	public ReviewsBean(String titolo,String corpo,float rateReview,String userLocation) {
		this.titolo = titolo;
		this.corpo = corpo;
		this.rateReview = rateReview;
		this.userLocation = userLocation;
	}
	
	public ReviewsBean() {
		// TODO Auto-generated constructor stub
	}

	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getCorpo() {
		return corpo;
	}
	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}
	public float getRateReview() {
		return rateReview;
	}
	public void setRateReview(float rateReview) {
		this.rateReview = rateReview;
	}
	public String getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}





	private String titolo;
	private String corpo;
	private float rateReview;
	private String userLocation;
}
