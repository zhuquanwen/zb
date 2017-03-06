package com.iscas.zb.tools;

import com.iscas.zb.data.StaticData;

/**
*@date: 2017年3月6日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
public class EncryptTools {
	public static String encrypt(String str) throws Exception{

		return DESTools.encrypt(str, StaticData.des_key);
	}
	public static String decrtpt(String str) throws Exception{
		return DESTools.decrypt(str, StaticData.des_key);
	}
}
