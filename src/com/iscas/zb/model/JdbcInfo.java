package com.iscas.zb.model;
/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: JDBC连接信息
*/
public class JdbcInfo {
	private String url;
	private String username;
	private String password;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public JdbcInfo(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}
	public JdbcInfo() {
		super();
	}

}
