package com.iscas.zb.init;

import com.iscas.zb.data.StaticData;
import com.iscas.zb.tools.TableRelationTools;

/**
*@date: 2017年2月24日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
public class TableRelationInit {
	public static void tableRelationInit(){
		TableRelationTools trt = StaticData.context.getBean(TableRelationTools.class);
		trt.getRelation(false);
	}
}
