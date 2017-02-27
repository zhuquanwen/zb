package com.iscas.zb.init;

import org.apache.log4j.Logger;

import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.jaxb.ChildAdds;
import com.iscas.zb.model.jaxb.ChildExcepts;
import com.iscas.zb.model.jaxb.DisColTrans;
import com.iscas.zb.model.jaxb.UnEditCols;
import com.iscas.zb.tools.JaxbTools;

/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
public class XmlToObjectInit {
	private static Logger log = Logger.getLogger(XmlToObjectInit.class);
	public static void xmlToObjectInit() throws Exception{
		log.info("--开始初始化XML配置--");
		initDisColTrans();
		initChildExcepts();
		initChildAdds();
		initComboboxAdds();
		initUnEditCols();
		log.info("--初始化XML配置结束--");
	}
	private static void initDisColTrans() throws Exception{
		DisColTrans  dct = JaxbTools.xmlToObj(StaticData.config_col_char_disponse_translate, new DisColTrans());
		StaticData.dis_col_trans = dct;
	}
	private static void initChildExcepts() throws Exception{
		ChildExcepts ce = JaxbTools.xmlToObj(StaticData.child_table_except, new ChildExcepts());
		StaticData.child_excepts = ce;
	}
	private static void initChildAdds() throws Exception{
		ChildAdds ce = JaxbTools.xmlToObj(StaticData.child_table_add, new ChildAdds());
		StaticData.child_adds = ce;
	}
	private static void initComboboxAdds() throws Exception{
		ChildAdds ce = JaxbTools.xmlToObj(StaticData.combobox_add, new ChildAdds());
		StaticData.combobox_adds = ce;
	}

	private static void initUnEditCols() throws Exception{
		UnEditCols uecs = JaxbTools.xmlToObj(StaticData.col_unedit, new UnEditCols());
		StaticData.unEditCols = uecs;
	}

}
