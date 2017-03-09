package com.iscas.zb.tools;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
*@date: 2017年3月9日
*@author: zhuquanwen
*@desc: 新版controlsfx 生成dialogs
*/
public class ControlsFxDialog {

    private Stage stage;
	/**错误*/
	public  void error( Stage stage,String title,
			String masthead, String message){
		this.stage = stage;
		  Alert dlg = createAlert(AlertType.ERROR,title,masthead,message);
          dlg.show();
	}

	 private Alert createAlert(AlertType type ,String title,
				String masthead, String message) {
	        Window owner = stage;
	        Alert dlg = new Alert(type, "");
	        dlg.initModality(Modality.APPLICATION_MODAL);
	        dlg.initOwner(owner);
	        dlg.setTitle(title);
	        dlg.getDialogPane().setContentText(message);
	        if(masthead != null){
	        	dlg.getDialogPane().setHeaderText(masthead);
	        }

	        return dlg;
	 }

	public void info(Stage stage, String title, String masthead, String message) {
		this.stage = stage;
		  Alert dlg = createAlert(AlertType.INFORMATION,title,masthead,message);
        dlg.show();

	}

	public void warn(Stage stage, String title, String masthead, String message) {
		this.stage = stage;
		Alert dlg = createAlert(AlertType.WARNING,title,masthead,message);
     	dlg.show();

	}

}
