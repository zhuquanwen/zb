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
	public static String commonPageSql="SELECT B.* FROM " +
				"( " +
				   "SELECT A.*, ROWNUM RN " +
				   "FROM (SELECT t.*,t.rowid as rid FROM @tableName t  @condition) A " +
				   "WHERE ROWNUM <= @up " +
				") B " +
				"WHERE RN >= @down";

	/**查询一个表的所有列信息*/
	public static String selectColSql = "SELECT COLUMN_NAME,"
			+ "DATA_TYPE,NULLABLE FROM user_tab_columns "
			+ "WHERE TABLE_NAME='@tableName' ORDER BY COLUMN_ID";

	/**查询条目*/
	public static String selectTotal = "select count(*) as total from @tableName @condition";

	/**查询主表对应的子表信息SQL*/
	public static String tableRelationSql = "select a.table_name CHILDTABLENAME,a.column_name CHILDCOLNAME,b.table_name MAINTABLENAME,b.column_name MAINCOLNAME " +
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
			" where a.r_constraint_name=b.r_constraint_name ";
	/**查询主表对应的子表信息SQL orderby1*/
	public static String tableRelationSqlOrderBy1 = " order by MAINTABLENAME,CHILDTABLENAME,CHILDCOLNAME,MAINCOLNAME ";
	/**查询主表对应的子表信息SQL orderby2*/
	public static String tableRelationSqlOrderBy2 = " order by CHILDTABLENAME,MAINTABLENAME,CHILDCOLNAME,MAINCOLNAME ";

	/**获取一个表的主键和唯一键*/
	public static String getUniqueSql = "select t1.table_name,"
			+ "t2.column_name,t2.position from user_constraints t1,"
			+ "user_cons_columns t2 where t1.table_name = t2.table_name "
			+ " and t1.constraint_name = t2.constraint_name and "
			+ "(t1.constraint_type = 'P' or t1.constraint_type = 'U') "
			+ " and t1.table_name = '@tableName'";

	/**获取一个表的主键*/
	public static String getPrimarySql = "select t1.table_name,"
			+ "t2.column_name,t2.position from user_constraints t1,"
			+ "user_cons_columns t2 where t1.table_name = t2.table_name "
			+ " and t1.constraint_name = t2.constraint_name and "
			+ "(t1.constraint_type = 'P' ) "
			+ " and t1.table_name = '@tableName'";
}
