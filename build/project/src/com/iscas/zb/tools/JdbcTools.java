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
*@date: 2017��2��22��
*@author: zhuquanwen
*@desc: ����һ����˵��
*/
public class JdbcTools {
	private static Logger log = Logger.getLogger(JdbcTools.class);

	/**
	* @Title: getConnection
	* @Description: ��ȡ���ݿ�����
	* @throws ClassNotFoundException
	* @throws SQLException
	* @throws IOException
	* @return Connection    ���ݿ�����
	*/
	public static Connection  getConnection() throws ClassNotFoundException, SQLException, IOException{
		if(StaticData.conn == null){
			JdbcInfo ji = PropsTools.getJdbcProp();
			Class.forName("oracle.jdbc.driver.OracleDriver");// ����Oracle��������
	        String url = ji.getUrl();
	        String user = ji.getUsername();
	        String password = ji.getPassword();
	        StaticData.conn = DriverManager.getConnection(url, user, password);// ��ȡ����
	        log.info("--���JDBC����--");
		}

		return StaticData.conn;

	}
	public static Connection  getConnection(String username, String password) throws Exception{
		Connection conn = null;
		JdbcInfo ji = PropsTools.getJdbcProp();
		Class.forName("oracle.jdbc.driver.OracleDriver");// ����Oracle��������
	    String url = ji.getUrl();
	    conn = DriverManager.getConnection(EncryptTools.decrtpt(url), username, password);// ��ȡ����
	    log.info("--���JDBC����--");
	    return conn;
	}

	/**
	* @Title: getStatement
	* @Description: ��ȡstatement
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
