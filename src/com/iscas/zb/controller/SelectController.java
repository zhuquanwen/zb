package com.iscas.zb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.iscas.zb.model.HandlerModel;
import com.iscas.zb.service.SelectService;
import com.iscas.zb.service.TableService;
import com.iscas.zb.tools.DialogTools;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
*@date: 2017年3月3日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
@Controller
@Scope("prototype")
public class SelectController {
	@FXML
	private Button normalSButton;
	@FXML
	private Button normalCButton;
	@FXML
	private ProgressIndicator normalPI;
	@FXML
	private TextField normalTextField;
	@FXML
	private Label tipLabel;
	@Autowired(required=true)
	private SelectService selectService;
	@Autowired(required=true)
	private TableService tableService;

	private String tableName;
	private Stage stage;
	private String condition;//查询条件
	private List<String> primaryKeys;//主键
	private TableController tc;

	public TableController getTc() {
		return tc;
	}

	public void setTc(TableController tc) {
		this.tc = tc;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	private void initialize() {
		normalPI.setVisible(false);

	}

	public void disabledButtons(boolean disabledFlag){
		normalSButton.setDisable(disabledFlag);
		normalCButton.setDisable(disabledFlag);
		normalTextField.setDisable(disabledFlag);
		if(disabledFlag){
			normalPI.setVisible(true);
		}else{
			normalPI.setVisible(false);
		}
	}
	public void disabledNormalButtons(boolean disabledFlag){
		normalSButton.setDisable(disabledFlag);
		normalCButton.setDisable(disabledFlag);
		normalTextField.setDisable(disabledFlag);
	}

	/**普通查询提交*/
	public void processNormalSelect(ActionEvent e){
		new Thread(new Runnable() {
			 @Override public void run() {
			 Platform.runLater(new Runnable() {
				@Override public void run() {
					disabledButtons(true);
				}
			 });
			 try{
				//如果是子表的话,让查询条件恢复为1=1，也就是说子表关联查询的时候消失了

					String selectString = " and (";
					for (int i = 0; i < primaryKeys.size(); i++) {
						String pk = primaryKeys.get(i);
						String  text =  normalTextField.getText();
						if(text == null || "".equals(text)){
							if(i == 0){
								selectString += " 1 = 1 ";
							}
						}else{
							if(i == 0){
								selectString +=   pk + " like '%"+ text +"%'";
							}else{
								selectString +=  " or " + pk + " = '%"+ text +"%'";
							}

						}
					}
					selectString +=  " ) ";
					//查询一下总条目，看一下这个查询条件有没有错误，如果有错，直接抛出到异常里，不对TC做修改,以免造成一些不必要的错误
					Integer total = tableService.getTotal(tableName, " where 1 = 1 "  + selectString);

					tc.setSelectCondition(selectString);
					tc.setPage(1);
					tc.setSqlCondition(" where 1 =1 ");

			 }catch(Exception e){
				 e.printStackTrace();
				 Platform.runLater(new Runnable() {
						@Override public void run() {
							 DialogTools.error(stage, "错误", "出错了","查询出错");
							 return;
						}
				 });
				 return;
			 }finally{
				 Platform.runLater(new Runnable() {
						@Override public void run() {
							disabledButtons(false);
						}
				 });
			 }


			  Platform.runLater(new Runnable() {
					@Override public void run() {
						tc.selectTable(HandlerModel.UNKOWN);
						stage.close();
					}
				 });
			 }
			}).start();



	}

	/**普通查询取消*/
	public void processNormalCancel(ActionEvent e){
		stage.close();
	}

	/**检查可不可以主键查询*/
	public void checkNormalSelectEnabled(){
		primaryKeys = selectService.getPrimaryKeys(tableName);
		if(primaryKeys == null || primaryKeys.size() == 0){
			disabledNormalButtons(true);
			normalPI.setVisible(false);
			tipLabel.setText("*此表没有主键，不支持主键查询");
		}
	}
}
