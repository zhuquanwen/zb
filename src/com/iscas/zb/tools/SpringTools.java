package com.iscas.zb.tools;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
public class SpringTools {
	private static Logger log = Logger.getLogger(SpringTools.class);
	public static ClassPathXmlApplicationContext context = null;
	/**加载spring*/
	public static void initSpring(){
		context = new ClassPathXmlApplicationContext(
                new String[] { "classpath:applicationContext.xml" });
		log.info("--spring启动--");
	}
}
