package com.iscas.zb.data;

import java.sql.Connection;
import java.util.Map;

import com.iscas.zb.model.ColInfo;
import com.iscas.zb.model.jaxb.ChildAdds;
import com.iscas.zb.model.jaxb.ChildExcepts;
import com.iscas.zb.model.jaxb.DisColTrans;
import com.iscas.zb.model.jaxb.JTable;

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

	/**字符串不需要显示的翻译列配置*/
	public static DisColTrans dis_col_trans = null;
	/**数据库有关联关系但需要排除的子表配置*/
	public static ChildExcepts child_excepts = null;
	/**数据库没有关联关系但需要加入的子表配置*/
	public static ChildAdds child_adds = null;
	/**数据库没有关联关系但需要加入的下拉列表配置*/
	public static ChildAdds combobox_adds = null;

	/**菜单对象*/
	public  static Map<Integer,TreeItem<JTable>> treeItemMap;

	/**每页显示数目pageSize*/
	public static final String[] table_page_size =
			new String[]{"10","20","50","200","500","全部显示"};

	/**默认每页显示数目pageSize*/
	public static  String default_table_page_size = "10";

	/**数据库链接*/
	public static Connection conn = null;

	/**内容汉化表内存中的Map*/
	public static Map<String,String> contentTransMap = null;

	/**内容汉化表名称*/
	public final static String translate_content_name = "zb_content_translate";

	/**表名汉化表名称*/
	public final static String translate_table_name = "zb_table_translate";

	/**表列信息汉化表名称*/
	public final static String translate_col_name = "zb_col_translate";

	/**表名汉化表map*/
	public final static Map<String,String> tableTransMap = null;

	/**表列名汉化表map*/
	public final static Map<ColInfo,String> colTransMap = null;

	/**内容汉化信息定时器刷新间隔(分钟)*/
	public static Long translate_content_timer = 10L;

	//

}
