package com.iscas.zb.init;

import com.iscas.zb.data.StaticData;
import com.iscas.zb.tools.EnToChTools;

/**
*@date: 2017��2��22��
*@author: zhuquanwen
*@desc: ����һ����˵��
*/
public class TranslateInit {
	public static void translateInit(){
		EnToChTools etct = StaticData.context.getBean(EnToChTools.class);
		etct.getColMap();
		etct.getContentMap();
		etct.getTableMap();
	}
}
