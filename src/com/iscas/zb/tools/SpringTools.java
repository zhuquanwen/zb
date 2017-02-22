package com.iscas.zb.tools;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
*@date: 2017��2��22��
*@author: zhuquanwen
*@desc: ����һ����˵��
*/
public class SpringTools {
	private static Logger log = Logger.getLogger(SpringTools.class);
	public static ClassPathXmlApplicationContext context = null;
	/**����spring*/
	public static void initSpring(){
		context = new ClassPathXmlApplicationContext(
                new String[] { "classpath:applicationContext.xml" });
		log.info("--spring����--");
	}
}
