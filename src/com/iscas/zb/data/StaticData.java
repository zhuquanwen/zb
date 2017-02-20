package com.iscas.zb.data;

import java.util.Map;

import com.iscas.zb.model.jaxb.JTable;

import javafx.scene.control.TreeItem;

public class StaticData {
	/**菜单配置名称*/
	public final static String config_table_menu = "config/table_menu.xml";

	/**菜单对象*/
	public  static Map<Integer,TreeItem<JTable>> treeItemMap;
}
