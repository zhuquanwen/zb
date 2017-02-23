package com.iscas.zb.data;
/**
*@date: 2017��2��22��
*@author: zhuquanwen
*@desc: SQL���
*/
public class SqlData {
	/**��ͨ��ѯһ�����SQL*/
	public static String commonSql = "select * from @tableName";

	/**��ͨ��ҳ��ѯһ�����SQL*/
	public static String commonPageSql="SELECT * FROM " +
				"( " +
				   "SELECT A.*, ROWNUM RN " +
				   "FROM (SELECT * FROM @tableName @condition) A " +
				   "WHERE ROWNUM <= @up " +
				") " +
				"WHERE RN >= @down";

	/**��ѯһ�������������Ϣ*/
	public static String selectColSql = "SELECT COLUMN_NAME,"
			+ "DATA_TYPE,NULLABLE FROM user_tab_columns "
			+ "WHERE TABLE_NAME='@tableName' ORDER BY COLUMN_ID";

	/**��ѯ��Ŀ*/
	public static String selectTotal = "select count(*) as total from @tableName @condition";
}
