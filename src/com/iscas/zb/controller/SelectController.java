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
*@date: 2017��3��3��
*@author: zhuquanwen
*@desc: ��ѯҳ���Ӧ�Ŀ�����
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
	private String condition;//��ѯ����
	private List<String> primaryKeys;//����
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

	/**��ʼ���߼���ѯ��2��combobox*/
	@SuppressWarnings("unchecked")
	public void initComoboBox(){
		String sql = "SELECT * FROM " + tableName + " where 1 = 1 ";
		textField1.setText(sql);

		//��ʼ���߼���ѯ������
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



		//��ʼ���߼���ѯSQL����
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
					@SuppressWarnings("unused")
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

	/**�߼���ѯȡ��*/
	public void processHighCancel(ActionEvent e){
		stage.close();
	}
	/**�߼���ѯ*/
	public void processHighSelect(ActionEvent e ){
		new Thread(new Runnable() {
			 @Override public void run() {
			 Platform.runLater(new Runnable() {
				@Override public void run() {
					disabledButtons(true);
				}
			 });
			 try{
				//������ӱ�Ļ�,�ò�ѯ�����ָ�Ϊ1=1��Ҳ����˵�ӱ������ѯ��ʱ����ʧ��

					String selectString = " ";
					String text = textArea1.getText();
					if(text != null && !"".equals(text)){
						selectString += text;
					}

					//��ѯһ������Ŀ����һ�������ѯ������û�д�������д�ֱ���׳����쳣�����TC���޸�,�������һЩ����Ҫ�Ĵ���
					@SuppressWarnings("unused")
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
