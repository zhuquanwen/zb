package com.iscas.zb.tools;

import javafx.stage.Stage;
/**
*@date: 2017��3��9��
*@author: zhuquanwen
*@desc: Dialogs��
*/
public class DialogTools {
	/**��ʾ*/
	public static  void info(Stage stage, String title,
			String masthead, String message ){
		new ControlsFxDialog().info(stage, title, masthead, message);

	}
	/**����*/
	public static void warn(Stage stage, String title,
			String masthead, String message){
		new ControlsFxDialog().warn(stage, title, masthead, message);
	}


	/**����*/
	public static void error(Stage stage, String title,
			String masthead, String message){

		new ControlsFxDialog().error(stage, title, masthead, message);
	}


}
