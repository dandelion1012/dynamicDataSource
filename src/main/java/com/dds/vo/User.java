package com.dds.vo;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -8615976903324927086L;
	private int id = 0;
	private String userName = null;
	private String dbName = null;
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
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
}
