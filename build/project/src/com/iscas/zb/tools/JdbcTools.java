package com.iscas.zb.tools;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.JdbcInfo;
import org.apache.log4j.Logger;

/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
public class JdbcTools {
	private static Logger log = Logger.getLogger(JdbcTools.class);

	/**
	* @Title: getConnection
	* @Description: 获取数据库连接
	* @throws ClassNotFoundException
	* @throws SQLException
	* @throws IOException
	* @return Connection    数据库连接
	*/
	public static Connection  getConnection() throws ClassNotFoundException, SQLException, IOException{
		if(StaticData.conn == null){
			JdbcInfo ji = PropsTools.getJdbcProp();
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
	        String url = ji.getUrl();
	        String user = ji.getUsername();
	        String password = ji.getPassword();
	        StaticData.conn = DriverManager.getConnection(url, user, password);// 获取连接
	        log.info("--获得JDBC连接--");
		}

		return StaticData.conn;

	}
	public static Connection  getConnection(String username, String password) throws Exception{
		Connection conn = null;
		JdbcInfo ji = PropsTools.getJdbcProp();
		Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
	    String url = ji.getUrl();
	    conn = DriverManager.getConnection(EncryptTools.decrtpt(url), username, password);// 获取连接
	    log.info("--获得JDBC连接--");
	    return conn;
	}

	/**
	* @Title: getStatement
	* @Description: 获取statement
	* @throws SQLException
	* @return Statement
	*/
	public static Statement getStatement() throws SQLException{
		Statement s = StaticData.conn.createStatement();
		return s;
	}




	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(conn);
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
