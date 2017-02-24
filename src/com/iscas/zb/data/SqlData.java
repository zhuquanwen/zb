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

	/**查询主表对应的子表信息SQL*/
	public static String tableRelationSql = "select a.table_name 外键表名,a.column_name 外键列名,b.table_name 主键表名,b.column_name 主键列名 " +
			" from " +
			" (select a.constraint_name,b.table_name,b.column_name,a.r_constraint_name " +
			" from user_constraints a, user_cons_columns b " +
			" WHERE a.constraint_type='R' " +
			" and a.constraint_name=b.constraint_name" +
			" ) a," +
			" (select distinct a.r_constraint_name,b.table_name,b.column_name " +
			" from user_constraints a, user_cons_columns b " +
			" WHERE   a.constraint_type='R' " +
			" and " +
			" a.r_constraint_name=b.constraint_name) " +
			" b " +
			" where a.r_constraint_name=b.r_constraint_name "
			+ " order by 主键表名,外键表名,外键列名,主键列名 ";
}
