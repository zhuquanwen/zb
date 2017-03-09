package com.iscas.zb.tools;

import javafx.stage.Stage;
/**
*@date: 2017年3月9日
*@author: zhuquanwen
*@desc: Dialogs类
*/
public class DialogTools {
	/**提示*/
	public static  void info(Stage stage, String title,
			String masthead, String message ){
		new ControlsFxDialog().info(stage, title, masthead, message);

	}
	/**警告*/
	public static void warn(Stage stage, String title,
			String masthead, String message){
		new ControlsFxDialog().warn(stage, title, masthead, message);
	}


	/**错误*/
	public static void error(Stage stage, String title,
			String masthead, String message){

		new ControlsFxDialog().error(stage, title, masthead, message);
	}


}
