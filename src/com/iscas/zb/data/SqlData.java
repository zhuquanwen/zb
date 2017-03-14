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

	/**��ѯ������ϼ�����*/
	public static String topUnitSql = "select t7.short_name,t7.higher_hq,t7.ty,t7.force_side,t8.sort_order from (select  t1.gu_short_name as short_name,t1.gu_higher_hq as higher_hq, 'GU' as ty ,t1.gu_force_side as force_side from ground_unit t1 where t1.gu_higher_hq is null " +
									" union all select t2.ab_short_name as short_name ,t2.ab_higher_hq as higher_hq, 'AB' as ty,t2.ab_force_side as force_side from airbase t2 where t2.ab_higher_hq is null " +
									" union all select t3.fu_short_name as short_name ,t3.fu_higher_hq as higher_hq, 'FU' as ty ,t3.fu_force_side as force_side from farp t3 where t3.fu_higher_hq is null " +
									" union all select t4.nv_short_name as short_name ,t4.nv_higher_hq as higher_hq, 'NV' as ty ,t4.nv_force_side as force_side from navi_unit t4 where t4.nv_higher_hq is null " +
									" union all select t5.sq_short_name as short_name ,t5.sq_higher_hq as higher_hq, 'SQ' as ty ,t5.sq_force_side as force_side from sqa t5 where t5.sq_higher_hq is null " +
									" union all select t6.su_short_name as short_name ,t6.su_higher_hq as higher_hq, 'SU' as ty ,t6.su_force_side as force_side  from support_unit t6 where t6.su_higher_hq is null) t7 " +
									" left join (SELECT * FROM zb_tree_sort where higher_hq IS NULL) t8 on t7.short_name = t8.unit_short_name  order by t8.sort_order ";

	/**��ѯĳ�������µĲ���*/
	public static String leafUnitSql = "select t7.short_name,t7.higher_hq,t7.ty,t7.force_side,t8.sort_order from (select  t1.gu_short_name as short_name,t1.gu_higher_hq as higher_hq, 'GU' as ty ,t1.gu_force_side as force_side from ground_unit t1 where t1.gu_higher_hq = '@shortName' " +
								" union all select t2.ab_short_name as short_name ,t2.ab_higher_hq as higher_hq, 'AB' as ty,t2.ab_force_side as force_side from airbase t2 where t2.ab_higher_hq = '@shortName' " +
								" union all select t3.fu_short_name as short_name ,t3.fu_higher_hq as higher_hq, 'FU' as ty ,t3.fu_force_side as force_side from farp t3 where t3.fu_higher_hq = '@shortName' " +
								" union all select t4.nv_short_name as short_name ,t4.nv_higher_hq as higher_hq, 'NV' as ty ,t4.nv_force_side as force_side from navi_unit t4 where t4.nv_higher_hq = '@shortName' " +
								" union all select t5.sq_short_name as short_name ,t5.sq_higher_hq as higher_hq, 'SQ' as ty ,t5.sq_force_side as force_side from sqa t5 where t5.sq_higher_hq = '@shortName' " +
								" union all select t6.su_short_name as short_name ,t6.su_higher_hq as higher_hq, 'SU' as ty ,t6.su_force_side as force_side  from support_unit t6 where t6.su_higher_hq = '@shortName' ) t7 " +
								" left join (SELECT * FROM zb_tree_sort where higher_hq = '@shortName') t8 on t7.short_name = t8.unit_short_name  order by t8.sort_order ";

	/**���ղ������Ʋ�ѯһ��������Ϣ*/
	public static String getUnitByShortNameSql = " SELECT t.*,t.rowid as rid FROM @tableName t  where t.@prefix_short_name = '@shortName'";

	/**��ѯ����������޴˼�¼*/
	public static String getTreeSortSql =" select count(*) as SUM from zb_tree_sort where higher_hq @condition and unit_short_name = '@shortName'";
	/**ɾ��һ�������¼*/
	public static String deleteSortSql = " delete from zb_tree_sort where higher_hq @condition and unit_short_name = '@shortName'";
	/**�޸�һ�������¼*/
	public static String updateSortSql = " update zb_tree_sort set sort_order = @order where higher_hq @condtion and unit_short_name = '@shortName'";
	/**����һ����¼*/
	public static String insertSortSql = " insert into zb_tree_sort (higher_hq,unit_short_name,sort_order) values(@v1,@v2,@v3)";
}
