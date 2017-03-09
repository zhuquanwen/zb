package com.iscas.zb.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.iscas.zb.model.HandlerModel;
import com.iscas.zb.service.SelectService;
import com.iscas.zb.service.TableService;
import com.iscas.zb.tools.DialogTools;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
*@date: 2017年3月3日
*@author: zhuquanwen
*@desc: 查询页面对应的控制器
*/
@Controller
@Scope("prototype")
@SuppressWarnings("rawtypes")
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
	@FXML
	private TextArea textArea1;
	@FXML
	private TextField textField1;

	@FXML
	private ComboBox conditionCombobox;
	@FXML
	private Button highSelectButton;
	@FXML
	private Button highCancelButton;
	@FXML
	private ComboBox colCombobox;
	@FXML
	private ProgressIndicator pi2;

	@Autowired(required=true)
	private SelectService selectService;
	@Autowired(required=true)
	private TableService tableService;

	private String tableName;
	private Stage stage;
	private String condition;//查询条件
	private List<String> primaryKeys;//主键
	private TableController tc;
	private LinkedHashMap<String,String> comboMap = new LinkedHashMap<String,String>();
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
		pi2.setVisible(false);


	}

	/**初始化高级查询的2个combobox*/
	@SuppressWarnings("unchecked")
	public void initComoboBox(){
		String sql = "SELECT * FROM " + tableName + " where 1 = 1 ";
		textField1.setText(sql);

		//初始化高级查询的列名
		ObservableList<String> cols = selectService.getCols(tableName);
		colCombobox.setItems(cols);
		colCombobox.setOnAction(e -> {
			Object value = colCombobox.getValue();
			if(value != null){
				String text = textArea1.getText();
				if(text == null){
					text = value.toString();
				}else{
					text += value.toString();
				}
				textArea1.setText(text);
			}
		});



		//初始化高级查询SQL符号
		comboMap.put("where", " WHERE ");
		comboMap.put("and", " AND ");
		comboMap.put("or", " or ");
		comboMap.put("between", " BETWEEN '' AND '' ");
		comboMap.put("=", " = ");
		comboMap.put(">", " > ");
		comboMap.put(">=", " >= ");
		comboMap.put("<", " < ");
		comboMap.put("<=", " <= ");
		comboMap.put("''", " '' ");
		comboMap.put("like", " LIKE '%%' ");

		Set<String> keys = comboMap.keySet();
		ObservableList<String> conditions = FXCollections.observableArrayList(keys);
		conditionCombobox.setItems(conditions);
		conditionCombobox.setOnAction(e -> {
			Object value = conditionCombobox.getValue();
			if(value != null){
				String condi = comboMap.get(value.toString());
				if(condi != null){
					String text = textArea1.getText();
					if(text == null){
						text = condi.toString();
					}else{
						text += condi.toString();
					}
					textArea1.setText(text);
				}
			}
		});
	}

	public void disabledButtons(boolean disabledFlag){


		normalSButton.setDisable(disabledFlag);
		normalCButton.setDisable(disabledFlag);
		normalTextField.setDisable(disabledFlag);

		textArea1.setDisable(disabledFlag);
		textField1.setDisable(disabledFlag);
		conditionCombobox.setDisable(disabledFlag);
		highCancelButton.setDisable(disabledFlag);
		highSelectButton.setDisable(disabledFlag);
		colCombobox.setDisable(disabledFlag);
		if(disabledFlag){
			normalPI.setVisible(true);
			pi2.setVisible(true);
		}else{
			normalPI.setVisible(false);
			pi2.setVisible(false);
		}
	}
	public void disabledNormalButtons(boolean disabledFlag){
		normalSButton.setDisable(disabledFlag);
		normalCButton.setDisable(disabledFlag);
		normalTextField.setDisable(disabledFlag);
	}

	public void disabledHighButtons(boolean disabledFlag){
		textArea1.setDisable(disabledFlag);
		textField1.setDisable(disabledFlag);
		conditionCombobox.setDisable(disabledFlag);
		highCancelButton.setDisable(disabledFlag);
		highSelectButton.setDisable(disabledFlag);
		colCombobox.setDisable(disabledFlag);
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
					@SuppressWarnings("unused")
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

	/**高级查询取消*/
	public void processHighCancel(ActionEvent e){
		stage.close();
	}
	/**高级查询*/
	public void processHighSelect(ActionEvent e ){
		new Thread(new Runnable() {
			 @Override public void run() {
			 Platform.runLater(new Runnable() {
				@Override public void run() {
					disabledButtons(true);
				}
			 });
			 try{
				//如果是子表的话,让查询条件恢复为1=1，也就是说子表关联查询的时候消失了

					String selectString = " ";
					String text = textArea1.getText();
					if(text != null && !"".equals(text)){
						selectString += text;
					}

					//查询一下总条目，看一下这个查询条件有没有错误，如果有错，直接抛出到异常里，不对TC做修改,以免造成一些不必要的错误
					@SuppressWarnings("unused")
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
