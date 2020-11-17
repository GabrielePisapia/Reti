package com.sm.reti.fabrikam_functions;

import java.util.ArrayList;

public class RistoBean {

	public RistoBean(String nomeRistorante,ArrayList<String>cucina,float rate,ArrayList<ReviewsBean> recensioni, String city) {
		this.nomeRistorante = nomeRistorante;
		this.cucina = cucina;
		this.rate = rate;
		this.recensioni = recensioni;
		this.city = city;
		
	}
	
	public ArrayList<String> getCucina() {
		return cucina;
	}
	public void setCucina(ArrayList<String> cucina) {
		this.cucina = cucina;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public String getNomeRistorante() {
		return nomeRistorante;
	}
	public void setNomeRistorante(String nomeRistorante) {
		this.nomeRistorante = nomeRistorante;
	}
	public ArrayList<ReviewsBean> getRecensioni() {
		return recensioni;
	}
	public void setRecensioni(ArrayList<ReviewsBean> recensioni) {
		this.recensioni = recensioni;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	private ArrayList<String> cucina;
	private float rate;
	private String nomeRistorante;
	private ArrayList<ReviewsBean> recensioni;
	private String city;
}
