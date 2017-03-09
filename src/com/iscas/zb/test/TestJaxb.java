package com.iscas.zb.test;

import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.jaxb.ChildAdds;
import com.iscas.zb.model.jaxb.ChildExcepts;
import com.iscas.zb.model.jaxb.DisColTrans;
import com.iscas.zb.tools.JaxbTools;

/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
@SuppressWarnings("unused")
public class TestJaxb {
	public static void main(String[] args) {
		//test1();
		//test2();
		//test3();
		test4();
	}

	private static void test1(){
		try {
			DisColTrans  dct = JaxbTools.xmlToObj(StaticData.config_col_char_disponse_translate, new DisColTrans());
			System.out.println(dct);
			System.out.println(dct.getDcols().size());
			System.out.println(dct.getDcols().get(0).getColName());
			System.out.println(dct.getDcols().get(0).getTableName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void test2(){
		try {
			ChildExcepts ce = JaxbTools.xmlToObj(StaticData.child_table_except, new ChildExcepts());
			System.out.println(ce);
			System.out.println(ce.getCes().size());
			System.out.println(ce.getCes().get(0).getParentTableName());
			System.out.println(ce.getCes().get(0).getChildTableName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void test3(){
		try {
			ChildAdds ce = JaxbTools.xmlToObj(StaticData.child_table_add, new ChildAdds());
			System.out.println(ce);
			System.out.println(ce.getCas().size());
			System.out.println(ce.getCas().get(0).getParentTableName());
			System.out.println(ce.getCas().get(0).getChildTableName());
			System.out.println(ce.getCas().get(0).getParentColName1());
			System.out.println(ce.getCas().get(0).getParentColName2());
			System.out.println(ce.getCas().get(0).getChildColName1());
			System.out.println(ce.getCas().get(0).getChildColName2());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void test4(){
		try {
			ChildAdds ce = JaxbTools.xmlToObj(StaticData.combobox_add, new ChildAdds());
			System.out.println(ce);
			System.out.println(ce.getCas().size());
			System.out.println(ce.getCas().get(0).getParentTableName());
			System.out.println(ce.getCas().get(0).getChildTableName());
			System.out.println(ce.getCas().get(0).getParentColName1());
			System.out.println(ce.getCas().get(0).getParentColName2());
			System.out.println(ce.getCas().get(0).getChildColName1());
			System.out.println(ce.getCas().get(0).getChildColName2());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
