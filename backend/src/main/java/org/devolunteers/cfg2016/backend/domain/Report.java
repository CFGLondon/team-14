package org.devolunteers.cfg2016.backend.domain;

public class Report {
	String latitude;
	String longitude;
	String country;
	String disability;
	String actionRecquired;

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDisability() {
		return disability;
	}

	public void setDisability(String disability) {
		this.disability = disability;
	}

	public String getActionRecquired() {
		return actionRecquired;
	}

	public void setActionRecquired(String actionRecquired) {
		this.actionRecquired = actionRecquired;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	String comments;

}
