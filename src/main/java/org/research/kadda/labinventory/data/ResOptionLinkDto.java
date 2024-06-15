package org.research.kadda.labinventory.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ResOptionLinkDto {

	private Integer instId;
	private Integer optId;
	
	public ResOptionLinkDto() {
		super();
	}
	
	public ResOptionLinkDto(Integer instId, Integer optId) {
		super();
		this.instId = instId;
		this.optId = optId;
	}


	public Integer getInstId() {
		return instId;
	}
	public void setInstId(Integer instId) {
		this.instId = instId;
	}
	public Integer getOptId() {
		return optId;
	}
	public void setOptId(Integer optId) {
		this.optId = optId;
	}
	
}
