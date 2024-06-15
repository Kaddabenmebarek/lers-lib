package org.research.kadda.labinventory.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class GroupInstrumentDto {

	private Integer instId;
	private Integer gId;
	
	public GroupInstrumentDto() {
		super();
	}
	
	public GroupInstrumentDto(Integer instId, Integer gId) {
		super();
		this.instId = instId;
		this.gId = gId;
	}

	public Integer getInstId() {
		return instId;
	}
	public void setInstId(Integer instId) {
		this.instId = instId;
	}

	public Integer getgId() {
		return gId;
	}

	public void setgId(Integer gId) {
		this.gId = gId;
	}
	
}
