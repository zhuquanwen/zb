package com.iscas.zb.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.util.StringUtils;

import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.JdbcInfo;

/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: 读取properties文件工具类
*/
public class PropsTools {
	/**读取JDBC信息*/
	public static JdbcInfo getJdbcProp() throws IOException{
		File file = new File(StaticData.jdbc_properties);
		Properties pro = new Properties();
		FileInputStream fis = new FileInputStream(file);
		pro.load(fis);
		String url = pro.getProperty("jdbc.url");
		String username = pro.getProperty("jdbc.username");
		String password = pro.getProperty("jdbc.password");
		JdbcInfo ji = new JdbcInfo(url,username,password);
		return ji;
	}
	/**初始化staticData
	 * @throws IOException */
	public  static void readToStaticData() throws IOException{
		File file = new File(StaticData.static_info_properties);
		Properties pro = new Properties();
		FileInputStream fis = new FileInputStream(file);
		pro.load(fis);
		String jdbcProperties = pro.getProperty("jdbc_properties");
		if(!StringUtils.isEmpty(jdbcProperties)){
			StaticData.jdbc_properties = jdbcProperties;
		}
		String config_table_menu = pro.getProperty("config_table_menu");
		if(!StringUtils.isEmpty(config_table_menu)){
			StaticData.config_table_menu = config_table_menu;
		}
		String cccdt = pro.getProperty("config_col_char_disponse_translate");
		if(!StringUtils.isEmpty(cccdt)){
			StaticData.config_col_char_disponse_translate = cccdt;
		}
		String cte = pro.getProperty("child_table_except");
		if(!StringUtils.isEmpty(cte)){
			StaticData.child_table_except = cte;
		}
		String cta = pro.getProperty("child_table_add");
		if(!StringUtils.isEmpty(cta)){
			StaticData.child_table_add = cta;
		}
		String ca = pro.getProperty("combobox_add");
		if(!StringUtils.isEmpty(ca)){
			StaticData.combobox_add = ca;
		}
		String cu = pro.getProperty("col_unedit");
		if(!StringUtils.isEmpty(cu)){
			StaticData.col_unedit = cu;
		}
		String dtps = pro.getProperty("default_table_page_size");
		if(!StringUtils.isEmpty(dtps)){
			try{
				 Integer.parseInt(dtps);
				 StaticData.default_table_page_size = dtps;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		String tcn = pro.getProperty("translate_content_name");
		if(!StringUtils.isEmpty(tcn)){
			StaticData.translate_content_name = tcn;
		}
		String ttn = pro.getProperty("translate_table_name");
		if(!StringUtils.isEmpty(ttn)){
			StaticData.translate_table_name = ttn;
		}
		String tcnx = pro.getProperty("translate_col_name");
		if(!StringUtils.isEmpty(tcnx)){
			StaticData.translate_col_name = tcnx;
		}

	}

}
