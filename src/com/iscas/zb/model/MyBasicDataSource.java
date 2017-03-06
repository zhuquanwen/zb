package com.iscas.zb.model;



import org.apache.commons.dbcp.BasicDataSource;

import com.iscas.zb.tools.EncryptTools;

/**
*@date: 2017年3月6日
*@author: zhuquanwen
*@desc: 重写basicDatasource的url,password,username方法，进行解密
*/
public class MyBasicDataSource extends BasicDataSource{

	@Override
	public synchronized String getPassword() {
		return super.getPassword();
	}

	@Override
	public synchronized String getUrl() {
		return super.getUrl();
	}

	@Override
	public synchronized String getUsername() {
		return super.getUsername();
	}

	@Override
	public synchronized void setPassword(String password) {
		try {
			super.setPassword(EncryptTools.decrtpt(password));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void setUrl(String url) {
		try {
			super.setUrl(EncryptTools.decrtpt(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void setUsername(String username) {
		try {
			super.setUsername(EncryptTools.decrtpt(username));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
