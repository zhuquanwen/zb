package com.iscas.zb.init;

import java.io.IOException;
import java.sql.SQLException;

import com.iscas.zb.tools.JdbcTools;

/**
*@date: 2017��2��22��
*@author: zhuquanwen
*@desc: ����һ����˵��
*/
public class JdbcInit {
	public static void connectionInit() throws ClassNotFoundException, SQLException, IOException{
		JdbcTools.getConnection();
	}
}
