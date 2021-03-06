package com.iscas.zb.tools;

import org.apache.log4j.Logger;
import org.apache.xbean.spring.context.ResourceXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

import com.iscas.zb.data.StaticData;


/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: spring工具
*/
public class SpringTools {
	private static Logger log = Logger.getLogger(SpringTools.class);

	/**加载spring*/
	public static void initSpring(){
//		context = new ClassPathXmlApplicationContext(
//                new String[] { "classpath:applicationContext.xml" });
		StaticData.context = new ResourceXmlApplicationContext(new FileSystemResource("config/applicationContext.xml"));
		log.info("--spring启动--");
	}
}
