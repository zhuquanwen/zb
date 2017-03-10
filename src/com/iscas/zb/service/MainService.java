package com.iscas.zb.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
*@date: 2017年3月6日
*@author: zhuquanwen
*@desc: 主页面service
*/

import com.iscas.zb.model.MyBasicDataSource;
@Transactional
@Service
public class MainService {
	@Autowired
	private MyBasicDataSource dataSource;
	public String getIp(){
		String ip = null;
		String url = dataSource.getUrl();
		if(url != null){
			//jdbc:oracle:thin:@172.16.10.156:1521:orcl
			ip = url.substring(url.indexOf("@") + 1, url.lastIndexOf(":"));
			ip = ip.substring(0, ip.lastIndexOf(":"));
		}
		return ip;
	}

}
