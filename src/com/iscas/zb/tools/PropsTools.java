package com.iscas.zb.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.JdbcInfo;

/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: 读取properties文件工具类
*/
public class PropsTools {
	/**读取JDBC信息*/
	public static JdbcInfo getJdbcProp() throws IOException{
		File file = new File(StaticData.jdbc_properties);
		Properties pro = new Properties();
		FileInputStream fis = new FileInputStream(file);
		pro.load(fis);
		String url = pro.getProperty("url");
		String username = pro.getProperty("username");
		String password = pro.getProperty("password");
		JdbcInfo ji = new JdbcInfo(url,username,password);
		return ji;
	}
}
