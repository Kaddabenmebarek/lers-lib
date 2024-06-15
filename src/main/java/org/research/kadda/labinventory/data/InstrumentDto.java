package org.research.kadda.labinventory.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Author: Kadda
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class InstrumentDto {
	private int id;
	private String name;
	private String status;
	private String description = "";
	private String location;
	private int reservable = 1;
	private String username;
	private String deputy;
	private String groupname;
	private String infotitle = "";
	private String infomessage = "";
	private int ispublic = 0;
	private int favorite;
	private int selectoverlap = 0;
	private List<String> deputies;
	private String deputiesAsJson;
	private List<String> priorityUsers;
	private String priorityUsersAsJson;
	private String ratioComment;
	private int emailNotification;
	private int stepIncrement;
	private String startTimepoint;
	private Integer maxDays; 
	private String multiOptionFieldAsJson;
	private Integer highlightComment;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getReservable() {
		return reservable;
	}

	public void setReservable(int reservable) {
		this.reservable = reservable;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDeputy() {
		return deputy;
	}

	public void setDeputy(String deputy) {
		this.deputy = deputy;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupName) {
		this.groupname = groupName;
	}

	public String getInfoTitle() {
		return infotitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infotitle = infoTitle;
	}

	public String getInfoMessage() {
		return infomessage;
	}

	public void setInfoMessage(String infoMessage) {
		this.infomessage = infoMessage;
	}

	public int getIsPublic() {
		return ispublic;
	}

	public void setIsPublic(int isPublic) {
		this.ispublic = isPublic;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public List<String> getDeputies() {
		return deputies;
	}

	public void setDeputies(List<String> deputies) {
		this.deputies = deputies;
	}

	public String getDeputiesAsJson() {
		return deputiesAsJson;
	}

	public void setDeputiesAsJson(String deputiesAsJson) {
		this.deputiesAsJson = deputiesAsJson;
	}

	public List<String> getPriorityUsers() {
		return priorityUsers;
	}

	public void setPriorityUsers(List<String> priorityUsers) {
		this.priorityUsers = priorityUsers;
	}

	public String getPriorityUsersAsJson() {
		return priorityUsersAsJson;
	}

	public void setPriorityUsersAsJson(String priorityUsersAsJson) {
		this.priorityUsersAsJson = priorityUsersAsJson;
	}

	public String getRatioComment() {
		return ratioComment;
	}

	public void setRatioComment(String ratioComment) {
		this.ratioComment = ratioComment;
	}

	public int getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(int emailNotification) {
		this.emailNotification = emailNotification;
	}

	public int getSelectOverlap() {
		return selectoverlap;
	}

	public void setSelectOverlap(int selectoverlap) {
		this.selectoverlap = selectoverlap;
	}

	public int getStepIncrement() {
		return stepIncrement;
	}

	public void setStepIncrement(int stepIncrement) {
		this.stepIncrement = stepIncrement;
	}

	public String getInfotitle() {
		return infotitle;
	}

	public void setInfotitle(String infotitle) {
		this.infotitle = infotitle;
	}

	public String getInfomessage() {
		return infomessage;
	}

	public void setInfomessage(String infomessage) {
		this.infomessage = infomessage;
	}

	public int getIspublic() {
		return ispublic;
	}

	public void setIspublic(int ispublic) {
		this.ispublic = ispublic;
	}

	public int getSelectoverlap() {
		return selectoverlap;
	}

	public void setSelectoverlap(int selectoverlap) {
		this.selectoverlap = selectoverlap;
	}

	public String getStartTimepoint() {
		return startTimepoint;
	}

	public void setStartTimepoint(String startTimepoint) {
		this.startTimepoint = startTimepoint;
	}

	public Integer getMaxDays() {
		return maxDays;
	}

	public void setMaxDays(Integer maxDays) {
		this.maxDays = maxDays;
	}

	public String getMultiOptionFieldAsJson() {
		return multiOptionFieldAsJson;
	}

	public void setMultiOptionFieldAsJson(String multiOptionFieldAsJson) {
		this.multiOptionFieldAsJson = multiOptionFieldAsJson;
	}

	public Integer getHighlightComment() {
		return highlightComment;
	}

	public void setHighlightComment(Integer highlightComment) {
		this.highlightComment = highlightComment;
	}

}
