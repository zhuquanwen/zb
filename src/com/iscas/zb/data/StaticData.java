package com.iscas.zb.data;

import java.util.Map;

import com.iscas.zb.model.jaxb.JTable;

import javafx.scene.control.TreeItem;

public class StaticData {
	/**�˵���������*/
	public final static String config_table_menu = "config/table_menu.xml";

	/**�˵�����*/
	public  static Map<Integer,TreeItem<JTable>> treeItemMap;

	/**ÿҳ��ʾ��ĿpageSize*/
	public static final String[] table_page_size =
			new String[]{"10","20","50","200","500","ȫ����ʾ"};

	/**Ĭ��ÿҳ��ʾ��ĿpageSize*/
	public static  String default_table_page_size = "10";

}
