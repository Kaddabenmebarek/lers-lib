package org.research.kadda.labinventory.data;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SynthesisLibraryOrderDto implements Serializable {

	private static final long serialVersionUID = -3256711652014223151L;
	private int id;
	private String title;
	private String libraryOutcome;
	private String link;
	private String project;
	private String username;
	private String requester;
	private Date requestTime;
	private Date fromTime;
	private Date toTime;
	private Date creationTime;
	private int done;
	private String connectedUser;
	private String departmentName;
	private String compound;
	private Float quantity;
	private String unit;
	private Date updateTime;
	private String base64Img;
	private String fromTimeToDisplay;
	private String toTimeToDisplay;
	private String requestTimeToDisplay;
	private String isStructureAvailable;
	private String eventColor;
	private String remarks;
	private Date doneTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLibraryoutcome() {
		return libraryOutcome;
	}

	public void setLibraryoutcome(String libraryOutcome) {
		this.libraryOutcome = libraryOutcome;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
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

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public int getDone() {
		return done;
	}

	public void setDone(int done) {
		this.done = done;
	}

	public String getConnectedUser() {
		return connectedUser;
	}

	public void setConnectedUser(String connectedUser) {
		this.connectedUser = connectedUser;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getCompound() {
		return compound;
	}

	public void setCompound(String compound) {
		this.compound = compound;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBase64Img() {
		return base64Img;
	}

	public void setBase64Img(String base64Img) {
		this.base64Img = base64Img;
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

	public String getRequestTimeToDisplay() {
		return requestTimeToDisplay;
	}

	public void setRequestTimeToDisplay(String requestTimeToDisplay) {
		this.requestTimeToDisplay = requestTimeToDisplay;
	}

	public String getIsStructureAvailable() {
		return isStructureAvailable;
	}

	public void setIsStructureAvailable(String isStructureAvailable) {
		this.isStructureAvailable = isStructureAvailable;
	}

	public String getLibraryOutcome() {
		return libraryOutcome;
	}

	public void setLibraryOutcome(String libraryOutcome) {
		this.libraryOutcome = libraryOutcome;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}

	public String getEventColor() {
		return eventColor;
	}

	public void setEventColor(String eventColor) {
		this.eventColor = eventColor;
	}

}
