package org.research.kadda.labinventory.data;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class InstrumentPriorityUsersDto implements Serializable {

	private static final long serialVersionUID = -4444137418621031331L;

	private Integer id;
	private Integer instrumentId;
	private String priorityUser;

	public InstrumentPriorityUsersDto() {
	}

	public InstrumentPriorityUsersDto(Integer id, Integer instrumentId, String priorityUser) {
		super();
		this.id = id;
		this.instrumentId = instrumentId;
		this.priorityUser = priorityUser;
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
	
	public String getPriorityUser() {
		return priorityUser;
	}

	public void setPriorityUser(String priorityUser) {
		this.priorityUser = priorityUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((priorityUser == null) ? 0 : priorityUser.hashCode());
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
		InstrumentPriorityUsersDto other = (InstrumentPriorityUsersDto) obj;
		if (priorityUser == null) {
			if (other.priorityUser != null)
				return false;
		} else if (!priorityUser.equals(other.priorityUser))
			return false;
		if (id != other.id)
			return false;
		if (instrumentId != other.instrumentId)
			return false;
		return true;
	}

}
