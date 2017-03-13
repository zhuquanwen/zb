package com.iscas.zb.tools;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iscas.zb.dao.UnEntityDao;
import com.iscas.zb.data.SqlData;
import com.iscas.zb.resource.ClassLoad;

import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
*@date: 2017年2月23日
*@author: zhuquanwen
*@desc: 通用工具类
*/
@SuppressWarnings(value={"unchecked","rawtypes"})
public class CommonTools {

	public static List<Map> getDBList(UnEntityDao dao,String sql){
		Map paramMap = new HashMap();
		paramMap.put("sql", sql);
		List<Map> mapList = dao.selectDataToList(paramMap);
		return mapList;
	}

	public static List<Map> getTableCols(String tableName,UnEntityDao unEntityDao){
		String sql =SqlData.selectColSql;
		sql = sql.replace("@tableName", tableName);
		List<Map> maps = CommonTools.getDBList(unEntityDao, sql);
		return maps;

	}

	public static void setIcon(Stage stage){
		InputStream is = ClassLoad.class.getResourceAsStream("zb.jpg");
		stage.getIcons().add(new Image(is));
	}
	public static String getUnitTableNameByType(String ty){
		String type = "UNKOWN";
		switch (ty) {
		case "GU":
			type = "GROUND_UNIT";
			break;
		case "SQ":
			type = "SQA";
			break;
		case "AB":
			type = "AIRBASE";
			break;
		case "SU":
			type = "SUPPORT_UNIT";
			break;
		case "NV":
			type = "NAVI_UNIT";
			break;
		case "FU":
			type = "FARP";
			break;

		default:
			break;
		}
		return type;
	}
}
