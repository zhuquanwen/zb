package com.iscas.zb.init;

import java.io.IOException;
import java.sql.SQLException;

import com.iscas.zb.tools.JdbcTools;

/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
public class JdbcInit {
	public static void connectionInit() throws ClassNotFoundException, SQLException, IOException{
		JdbcTools.getConnection();
	}
}
