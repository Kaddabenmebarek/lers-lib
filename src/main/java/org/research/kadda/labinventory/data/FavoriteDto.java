package org.research.kadda.labinventory.data;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class FavoriteDto implements Serializable {

	private static final long serialVersionUID = 3172461108750679604L;
	private int id;
	private String userName;
	private Date creationDate;
	private int isActive;
	private Integer instrid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public Integer getInstrid() {
		return instrid;
	}

	public void setInstrid(Integer instrid) {
		this.instrid = instrid;
	}

}
