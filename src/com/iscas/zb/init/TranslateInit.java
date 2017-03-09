package com.iscas.zb.init;

import com.iscas.zb.data.StaticData;
import com.iscas.zb.tools.EnToChTools;

/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: 翻译初始化
*/
public class TranslateInit {
	public static void translateInit(){
		EnToChTools etct = StaticData.context.getBean(EnToChTools.class);
		etct.getColMap(false);
		etct.getContentMap(false);
		etct.getTableMap(false);
	}
}
