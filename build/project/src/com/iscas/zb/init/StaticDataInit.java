package com.iscas.zb.init;

import java.io.IOException;

import com.iscas.zb.tools.PropsTools;

/**
*@date: 2017年3月7日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
public class StaticDataInit {
	public static void init() throws IOException{
		PropsTools.readToStaticData();
	}
}
