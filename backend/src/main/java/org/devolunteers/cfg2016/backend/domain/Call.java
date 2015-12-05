package org.devolunteers.cfg2016.backend.domain;

import org.joda.time.DateTime;

public class Call {
	String fromNumber;
	String toNumber;
	DateTime datetime;
	
	String fromCity;
	String fromState;
	String fromCountry;
	
	public String getFromNumber() {
		return fromNumber;
	}
	
	public Call() {
		super();
	}

	public Call(String fromNumber, String toNumber, String fromCity,
			String fromState, String fromCountry) {
		super();
		this.fromNumber = fromNumber;
		this.toNumber = toNumber;
		this.fromCity = fromCity;
		this.fromState = fromState;
		this.fromCountry = fromCountry;
		this.datetime = DateTime.now();
	}

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getFromState() {
		return fromState;
	}

	public void setFromState(String fromState) {
		this.fromState = fromState;
	}

	public String getFromCountry() {
		return fromCountry;
	}

	public void setFromCountry(String fromCountry) {
		this.fromCountry = fromCountry;
	}

	public void setFromNumber(String fromNumber) {
		this.fromNumber = fromNumber;
	}
	public String getToNumber() {
		return toNumber;
	}
	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}
	public DateTime getDatetime() {
		return datetime;
	}
	public void setDatetime(DateTime datetime) {
		this.datetime = datetime;
	}
}
