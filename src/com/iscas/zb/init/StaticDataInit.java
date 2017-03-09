package com.iscas.zb.init;

import java.io.IOException;

import com.iscas.zb.tools.PropsTools;

/**
*@date: 2017年3月7日
*@author: zhuquanwen
*@desc: 静态数据初始化
*/
public class StaticDataInit {
	public static void init() throws IOException{
		PropsTools.readToStaticData();
	}
}
