package com.iscas.zb.data;

import java.util.Map;

import com.iscas.zb.model.jaxb.JTable;

import javafx.scene.control.TreeItem;

public class StaticData {
	/**菜单配置名称*/
	public final static String config_table_menu = "config/table_menu.xml";

	/**菜单对象*/
	public  static Map<Integer,TreeItem<JTable>> treeItemMap;

	/**每页显示数目pageSize*/
	public static final String[] table_page_size =
			new String[]{"10","20","50","200","500","全部显示"};

	/**默认每页显示数目pageSize*/
	public static  String default_table_page_size = "10";

}
