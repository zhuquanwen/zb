package com.iscas.zb.data;
/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: SQL语句
*/
public class SqlData {
	/**普通查询一个表的SQL*/
	public static String commonSql = "select * from @tableName";

	/**普通分页查询一个表的SQL*/
	public static String commonPageSql="SELECT * FROM " +
				"( " +
				   "SELECT A.*, ROWNUM RN " +
				   "FROM (SELECT * FROM @tableName @condition) A " +
				   "WHERE ROWNUM <= @up " +
				") " +
				"WHERE RN >= @down";

	/**查询一个表的所有列信息*/
	public static String selectColSql = "SELECT COLUMN_NAME,"
			+ "DATA_TYPE,NULLABLE FROM user_tab_columns "
			+ "WHERE TABLE_NAME='@tableName' ORDER BY COLUMN_ID";

	/**查询条目*/
	public static String selectTotal = "select count(*) as total from @tableName @condition";
}
