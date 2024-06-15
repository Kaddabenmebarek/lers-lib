package org.research.kadda.labinventory.data;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ReservationHistoryDto implements Serializable {

	private static final long serialVersionUID = 7191600311819826383L;

	List<Integer> instrIds;
	Integer reservationId;
	boolean strict;
	String startDate;
	String endDate;

	public ReservationHistoryDto() {
		super();
	}

	public ReservationHistoryDto(List<Integer> instrIds, Integer reservationId, boolean strict, String startDate,
			String endDate) {
		super();
		this.instrIds = instrIds;
		this.reservationId = reservationId;
		this.strict = strict;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public List<Integer> getInstrIds() {
		return instrIds;
	}

	public void setInstrIds(List<Integer> instrIds) {
		this.instrIds = instrIds;
	}

	public Integer getReservationId() {
		return reservationId;
	}

	public void setReservationId(Integer reservationId) {
		this.reservationId = reservationId;
	}

	public boolean isStrict() {
		return strict;
	}

	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
