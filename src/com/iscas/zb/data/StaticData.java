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
	/**jdbc����*/
	public final static String jdbc_properties = "config/jdbc.properties";

	/**�˵�����xml����*/
	public final static String config_table_menu = "config/table_menu.xml";
	/**�ַ�������Ҫ��ʾ�ķ���������xml����*/
	public final static String config_col_char_disponse_translate =
			"config/col_char_disponse_translate.xml";


	/**���ݿ��й�����ϵ����Ҫ�ų����ӱ�XML����*/
	public final static String child_table_except =
			"config/child_table_except.xml";
	/**���ݿ�û�й�����ϵ����Ҫ������ӱ�XML����*/
	public final static String child_table_add =
			"config/child_table_add.xml";

	/**���ݿ�û�й�����ϵ����Ҫ����������б�XML����*/
	public final static String combobox_add =
			"config/combobox_add.xml";

	/**���ݿⲻ�ɱ༭�����õ�XML����*/
	public final static String col_unedit =
			"config/col_unedit.xml";

	/**�ַ�������Ҫ��ʾ�ķ���������*/
	public static DisColTrans dis_col_trans = null;
	/**���ݿ��й�����ϵ����Ҫ�ų����ӱ�����*/
	public static ChildExcepts child_excepts = null;
	/**���ݿ�û�й�����ϵ����Ҫ������ӱ�����*/
	public static ChildAdds child_adds = null;
	/**���ݿ�û�й�����ϵ����Ҫ����������б�����*/
	public static ChildAdds combobox_adds = null;
	/**���ݿⲻ�ɱ༭������*/
	public static UnEditCols unEditCols = null;

	/**�˵�����*/
	public  static Map<Integer,TreeItem<JTable>> treeItemMap;

	/**ÿҳ��ʾ��ĿpageSize*/
	public static final String[] table_page_size =
			new String[]{"10","20","50","200","500","ȫ��"};

	/**Ĭ��ÿҳ��ʾ��ĿpageSize*/
	public static  String default_table_page_size = "20";

	/**���ݿ�����*/
	public static Connection conn = null;

	/**���ݺ������ڴ��е�Map*/
	public static Map<String,String> contentTransMap = null;

	/**���ݺ���������*/
	public final static String translate_content_name = "zb_content_translate".toUpperCase();

	/**��������������*/
	public final static String translate_table_name = "zb_table_translate".toUpperCase();

	/**������Ϣ����������*/
	public final static String translate_col_name = "zb_col_translate".toUpperCase();

	/**����������map*/
	public  static Map<String,String> tableTransMap = null;

	/**������������map*/
	public  static Map<ColInfo,String> colTransMap = null;

	/**���ݺ�����Ϣ��ʱ��ˢ�¼��(����)*/
	public static Long translate_content_timer = 10L;

	/**spring context*/
	public static ApplicationContext context = null;

	/**���ݿ���Ӧ��ϵ*/
	public static Map<String,List<ChildRelation>> tableRelationMap = null;

	/**���ݿ��ӱ���Ϣ*/
	public static Map<String,List<ChildRelation>> tableRelationViewMap = null;

	/**���ݿ��Ӧ��ϵ���ӱ�Ϊkey,����������*/
	public static Map<String,List<ChildRelation>> childTableRelationViewMap = null;

	/**���ݿ���Ӧ��ϵ���ӱ�Ϊkey*/
	public static Map<String,List<ChildRelation>> childTableRelationMap = null;
}
