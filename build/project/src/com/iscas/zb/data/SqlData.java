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
	public static String commonPageSql="SELECT B.* FROM " +
				"( " +
				   "SELECT A.*, ROWNUM RN " +
				   "FROM (SELECT t.*,t.rowid as rid FROM @tableName t  @condition) A " +
				   "WHERE ROWNUM <= @up " +
				") B " +
				"WHERE RN >= @down";

	/**��ѯһ�������������Ϣ*/
	public static String selectColSql = "SELECT COLUMN_NAME,"
			+ "DATA_TYPE,NULLABLE FROM user_tab_columns "
			+ "WHERE TABLE_NAME='@tableName' ORDER BY COLUMN_ID";

	/**��ѯ��Ŀ*/
	public static String selectTotal = "select count(*) as total from @tableName @condition";

	/**��ѯ�����Ӧ���ӱ���ϢSQL*/
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
	/**��ѯ�����Ӧ���ӱ���ϢSQL orderby1*/
	public static String tableRelationSqlOrderBy1 = " order by MAINTABLENAME,CHILDTABLENAME,CHILDCOLNAME,MAINCOLNAME ";
	/**��ѯ�����Ӧ���ӱ���ϢSQL orderby2*/
	public static String tableRelationSqlOrderBy2 = " order by CHILDTABLENAME,MAINTABLENAME,CHILDCOLNAME,MAINCOLNAME ";

	/**��ȡһ�����������Ψһ��*/
	public static String getUniqueSql = "select t1.table_name,"
			+ "t2.column_name,t2.position from user_constraints t1,"
			+ "user_cons_columns t2 where t1.table_name = t2.table_name "
			+ " and t1.constraint_name = t2.constraint_name and "
			+ "(t1.constraint_type = 'P' or t1.constraint_type = 'U') "
			+ " and t1.table_name = '@tableName'";

	/**��ȡһ���������*/
	public static String getPrimarySql = "select t1.table_name,"
			+ "t2.column_name,t2.position from user_constraints t1,"
			+ "user_cons_columns t2 where t1.table_name = t2.table_name "
			+ " and t1.constraint_name = t2.constraint_name and "
			+ "(t1.constraint_type = 'P' ) "
			+ " and t1.table_name = '@tableName'";
}
