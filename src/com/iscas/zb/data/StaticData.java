package com.iscas.zb.data;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.iscas.zb.model.ChildRelation;
import com.iscas.zb.model.ColInfo;
import com.iscas.zb.model.jaxb.ChildAdds;
import com.iscas.zb.model.jaxb.ChildExcepts;
import com.iscas.zb.model.jaxb.DisColTrans;
import com.iscas.zb.model.jaxb.JTable;
import com.iscas.zb.model.jaxb.UnEditCols;

import javafx.scene.control.TreeItem;

public class StaticData {
	/**jdbc配置*/
	public final static String jdbc_properties = "config/jdbc.properties";

	/**菜单配置xml名称*/
	public final static String config_table_menu = "config/table_menu.xml";
	/**字符串不需要显示的翻译列配置xml名称*/
	public final static String config_col_char_disponse_translate =
			"config/col_char_disponse_translate.xml";


	/**数据库有关联关系但需要排除的子表XML名称*/
	public final static String child_table_except =
			"config/child_table_except.xml";
	/**数据库没有关联关系但需要加入的子表XML名称*/
	public final static String child_table_add =
			"config/child_table_add.xml";

	/**数据库没有关联关系但需要加入的下拉列表XML名称*/
	public final static String combobox_add =
			"config/combobox_add.xml";

	/**数据库不可编辑列配置的XML名称*/
	public final static String col_unedit =
			"config/col_unedit.xml";

	/**字符串不需要显示的翻译列配置*/
	public static DisColTrans dis_col_trans = null;
	/**数据库有关联关系但需要排除的子表配置*/
	public static ChildExcepts child_excepts = null;
	/**数据库没有关联关系但需要加入的子表配置*/
	public static ChildAdds child_adds = null;
	/**数据库没有关联关系但需要加入的下拉列表配置*/
	public static ChildAdds combobox_adds = null;
	/**数据库不可编辑列配置*/
	public static UnEditCols unEditCols = null;

	/**菜单对象*/
	public  static Map<Integer,TreeItem<JTable>> treeItemMap;

	/**每页显示数目pageSize*/
	public static final String[] table_page_size =
			new String[]{"10","20","50","200","500","全部"};

	/**默认每页显示数目pageSize*/
	public static  String default_table_page_size = "20";

	/**数据库链接*/
	public static Connection conn = null;

	/**内容汉化表内存中的Map*/
	public static Map<String,String> contentTransMap = null;

	/**内容汉化表名称*/
	public final static String translate_content_name = "zb_content_translate".toUpperCase();

	/**表名汉化表名称*/
	public final static String translate_table_name = "zb_table_translate".toUpperCase();

	/**表列信息汉化表名称*/
	public final static String translate_col_name = "zb_col_translate".toUpperCase();

	/**表名汉化表map*/
	public  static Map<String,String> tableTransMap = null;

	/**表列名汉化表map*/
	public  static Map<ColInfo,String> colTransMap = null;

	/**内容汉化信息定时器刷新间隔(分钟)*/
	public static Long translate_content_timer = 10L;

	/**spring context*/
	public static ApplicationContext context = null;

	/**数据库表对应关系*/
	public static Map<String,List<ChildRelation>> tableRelationMap = null;

	/**数据库子表信息*/
	public static Map<String,List<ChildRelation>> tableRelationViewMap = null;

	/**数据库对应关系，子表为key,带额外配置*/
	public static Map<String,List<ChildRelation>> childTableRelationViewMap = null;

	/**数据库表对应关系，子表为key*/
	public static Map<String,List<ChildRelation>> childTableRelationMap = null;
}
