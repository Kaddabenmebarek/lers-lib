package org.research.kadda.labinventory.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ReservationDto implements Serializable {

	private static final long serialVersionUID = -8961353425075088310L;

	int id;
	Integer instrid;
	Date fromTime;
	Date toTime;
	String username;
	String remark;
	Integer resoptid;
	Integer ratio;
	List<String> deputies;
	ReservationUsageDto reservationUsage;
	String instrumentName;
	String instrumentOwner;
	String reservationOptions;
	String fromTimeToDisplay;
	String toTimeToDisplay;

	public ReservationDto() {
	}

	public ReservationDto(int id, Integer instrid, Date fromTime, Date toTime, String username, String remark,
			Integer resoptid) {
		this(id, instrid, fromTime, toTime, username, remark, resoptid, 0);
	}

	public ReservationDto(int id, Integer instrid, Date fromTime, Date toTime, String username, String remark,
			Integer resoptid, Integer ratio) {
		this.id = id;
		this.instrid = instrid;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.username = username;
		this.remark = remark;
		this.resoptid = resoptid;
		this.ratio = ratio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getInstrid() {
		return instrid;
	}

	public void setInstrid(Integer instrument) {
		this.instrid = instrument;
	}

	public Date getFromTime() {
		return fromTime;
	}

	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}

	public Date getToTime() {
		return toTime;
	}

	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getResoptid() {
		return resoptid;
	}

	public void setResoptid(Integer resoption) {
		this.resoptid = resoption;
	}

	public Integer getRatio() {
		return ratio;
	}

	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}

	public List<String> getDeputies() {
		return deputies;
	}

	public void setDeputies(List<String> deputies) {
		this.deputies = deputies;
	}

	public ReservationUsageDto getReservationUsageDto() {
		return reservationUsage;
	}

	public void setReservationUsageDto(ReservationUsageDto reservationUsageDto) {
		this.reservationUsage = reservationUsageDto;
	}

	public String getInstrumentName() {
		return instrumentName;
	}

	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}

	public String getInstrumentOwner() {
		return instrumentOwner;
	}

	public void setInstrumentOwner(String instrumentOwner) {
		this.instrumentOwner = instrumentOwner;
	}

	public String getReservationOptions() {
		return reservationOptions;
	}

	public void setReservationOptions(String reservationOptions) {
		this.reservationOptions = reservationOptions;
	}

	public ReservationUsageDto getReservationUsage() {
		return reservationUsage;
	}

	public void setReservationUsage(ReservationUsageDto reservationUsage) {
		this.reservationUsage = reservationUsage;
	}

	public String getFromTimeToDisplay() {
		return fromTimeToDisplay;
	}

	public void setFromTimeToDisplay(String fromTimeToDisplay) {
		this.fromTimeToDisplay = fromTimeToDisplay;
	}

	public String getToTimeToDisplay() {
		return toTimeToDisplay;
	}

	public void setToTimeToDisplay(String toTimeToDisplay) {
		this.toTimeToDisplay = toTimeToDisplay;
	}
	

}
