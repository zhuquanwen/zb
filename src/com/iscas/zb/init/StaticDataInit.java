package com.iscas.zb.init;

import java.io.IOException;

import com.iscas.zb.tools.PropsTools;

/**
*@date: 2017��3��7��
*@author: zhuquanwen
*@desc: ����һ����˵��
*/
public class StaticDataInit {
	public static void init() throws IOException{
		PropsTools.readToStaticData();
	}
}
