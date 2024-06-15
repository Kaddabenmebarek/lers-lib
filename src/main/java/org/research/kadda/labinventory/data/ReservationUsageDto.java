package org.research.kadda.labinventory.data;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ReservationUsageDto implements Serializable {

	private static final long serialVersionUID = 5822371402819842985L;

	int id;
	Integer reservationId;
	String project;
	String compound;
	String batch;
	String sample;
	String sampleType;
	String specie;

	public ReservationUsageDto() {
	}

	public ReservationUsageDto(int id, Integer reservationId, String project, String compound, String batch,
			String sample,String sampleType, String specie) {
		super();
		this.id = id;
		this.reservationId = reservationId;
		this.project = project;
		this.compound = compound;
		this.batch = batch;
		this.sample = sample;
		this.sampleType = sampleType;
		this.specie = specie;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getReservationId() {
		return reservationId;
	}

	public void setReservationId(Integer reservationId) {
		this.reservationId = reservationId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getCompound() {
		return compound;
	}

	public void setCompound(String compound) {
		this.compound = compound;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getSample() {
		return sample;
	}

	public void setSample(String sample) {
		this.sample = sample;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public String getSpecie() {
		return specie;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}
	
	

}
