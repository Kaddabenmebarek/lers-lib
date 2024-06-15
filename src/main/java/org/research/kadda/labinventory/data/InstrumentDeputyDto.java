package org.research.kadda.labinventory.data;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class InstrumentDeputyDto implements Serializable {

	private static final long serialVersionUID = -4444137418621031331L;

	private Integer id;
	private Integer instrumentId;
	private String deputy;

	public InstrumentDeputyDto() {
	}

	public InstrumentDeputyDto(Integer id, Integer instrumentId, String deputy) {
		super();
		this.id = id;
		this.instrumentId = instrumentId;
		this.deputy = deputy;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(Integer instrumentId) {
		this.instrumentId = instrumentId;
	}

	public String getDeputy() {
		return deputy;
	}

	public void setDeputy(String deputy) {
		this.deputy = deputy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deputy == null) ? 0 : deputy.hashCode());
		result = prime * result + id;
		result = prime * result + instrumentId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstrumentDeputyDto other = (InstrumentDeputyDto) obj;
		if (deputy == null) {
			if (other.deputy != null)
				return false;
		} else if (!deputy.equals(other.deputy))
			return false;
		if (id != other.id)
			return false;
		if (instrumentId != other.instrumentId)
			return false;
		return true;
	}

}
