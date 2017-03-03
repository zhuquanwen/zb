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
*@date: 2017��3��3��
*@author: zhuquanwen
*@desc: ����һ����˵��
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
	private String condition;//��ѯ����
	private List<String> primaryKeys;//����
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

	/**��ͨ��ѯ�ύ*/
	public void processNormalSelect(ActionEvent e){
		new Thread(new Runnable() {
			 @Override public void run() {
			 Platform.runLater(new Runnable() {
				@Override public void run() {
					disabledButtons(true);
				}
			 });
			 try{
				//������ӱ�Ļ�,�ò�ѯ�����ָ�Ϊ1=1��Ҳ����˵�ӱ������ѯ��ʱ����ʧ��

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
					//��ѯһ������Ŀ����һ�������ѯ������û�д�������д�ֱ���׳����쳣�����TC���޸�,�������һЩ����Ҫ�Ĵ���
					Integer total = tableService.getTotal(tableName, " where 1 = 1 "  + selectString);

					tc.setSelectCondition(selectString);
					tc.setPage(1);
					tc.setSqlCondition(" where 1 =1 ");

			 }catch(Exception e){
				 e.printStackTrace();
				 Platform.runLater(new Runnable() {
						@Override public void run() {
							 DialogTools.error(stage, "����", "������","��ѯ����");
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

	/**��ͨ��ѯȡ��*/
	public void processNormalCancel(ActionEvent e){
		stage.close();
	}

	/**���ɲ�����������ѯ*/
	public void checkNormalSelectEnabled(){
		primaryKeys = selectService.getPrimaryKeys(tableName);
		if(primaryKeys == null || primaryKeys.size() == 0){
			disabledNormalButtons(true);
			normalPI.setVisible(false);
			tipLabel.setText("*�˱�û����������֧��������ѯ");
		}
	}
}
