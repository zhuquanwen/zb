package com.iscas.zb.init;

import com.iscas.zb.data.StaticData;
import com.iscas.zb.tools.TableRelationTools;

/**
*@date: 2017��2��24��
*@author: zhuquanwen
*@desc: ����һ����˵��
*/
public class TableRelationInit {
	public static void tableRelationInit(){
		TableRelationTools trt = StaticData.context.getBean(TableRelationTools.class);
		trt.getRelation(false);
	}
}
