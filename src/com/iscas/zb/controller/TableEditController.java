package com.iscas.zb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.EditTableCell;
import com.iscas.zb.model.HandlerModel;
import com.iscas.zb.model.jaxb.UnEditCol;
import com.iscas.zb.service.TableEditService;
import com.iscas.zb.tools.DialogTools;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import oracle.sql.ROWID;

/**
*@date: 2017��2��27��
*@author: zhuquanwen
*@desc: ����һ����˵��
*/
@Controller
@Scope("prototype")
public class TableEditController {
	@Autowired
	private TableEditService tableEditService;
	@FXML
	private Button disponseButton;
	@FXML
	private TableView tableView;
	@FXML
	private ProgressIndicator progressIndicator;
	@FXML
	private Button commitButton;
	@FXML
	private Button cancelButton;
	private Stage stage;
	private String tableName;
	private Map rowMap;
	private boolean disponseCh = false;
	private Map<Map<String,String>,String> unEditMap = new HashMap<Map<String,String>,String>();
	private ObservableList obList;
	private Map<String,Object> updateMap = new HashMap<String,Object>();
	private Map<String,TextField> updateChMap = new HashMap<String,TextField>();
	private TableController tc;
	private boolean insertFlag = false;


	public boolean isInsertFlag() {
		return insertFlag;
	}
	public void setInsertFlag(boolean insertFlag) {
		this.insertFlag = insertFlag;
	}
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}



	public TableController getTc() {
		return tc;
	}
	public void setTc(TableController tc) {
		this.tc = tc;
	}
	public Map getRowMap() {
		return rowMap;
	}
	public void setRowMap(Map rowMap) {
		this.rowMap = rowMap;
	}
	@FXML
	private void initialize() {
		disponseButton.setText("����������");
		disponseCh = false;
		//��ʼ�����ɱ༭��
		this.initUnEdits();
		progressIndicator.setVisible(false);
	}
	private void allButtonsDisabled(boolean flag){
		disponseButton.setDisable(flag);
		commitButton.setDisable(flag);
		cancelButton.setDisable(flag);
		if(flag){
			progressIndicator.setVisible(true);
		}else{
			progressIndicator.setVisible(false);
		}
	}

	private void initUnEdits(){
		if(StaticData.unEditCols != null){
			List<UnEditCol> uecs = StaticData.unEditCols.getUecs();
			if(uecs != null && uecs.size() >0 ){
				uecs.forEach(uec -> {
					Map<String,String> map = new HashMap<String,String>();
					map.put(uec.getTableName(), uec.getColName());
					unEditMap.put(map, "this is flag");
				});
			}
		}
	}
	/**ȷ��*/
	public void processCommit(ActionEvent e){
		try{
			String[] rowid = new String[1];
			Object obj = rowMap.get("RID");
			if(obj != null){
				ROWID rd = (ROWID)obj;
				rowid[0] = rd.stringValue();
			}
			 new Thread(new Runnable() {
				 @Override public void run() {
				 Platform.runLater(new Runnable() {
					@Override public void run() {
						allButtonsDisabled(true);
					}
				 });
				 try{
					 tableEditService.commit(updateMap,updateChMap,tableName,rowid[0],insertFlag);
				 }catch(Exception e){
					 e.printStackTrace();
					 DialogTools.error(stage, "����", "������","�༭�ύ����");
					 return;
				 }finally{
					 Platform.runLater(new Runnable() {
							@Override public void run() {
								allButtonsDisabled(false);
							}
					 });
				 }


				  Platform.runLater(new Runnable() {
						@Override public void run() {
							HandlerModel hm =HandlerModel.UNKOWN;
							if(insertFlag){
								hm = HandlerModel.INSERT;
							}
							tc.selectTable(hm);
							stage.close();
						}
					 });
				 }
				}).start();
		}catch(Exception ex){
			ex.printStackTrace();
			DialogTools.exception(stage, "����", "�����쳣", "�༭�ύʧ�ܣ�", ex);
		}


	}
	/**ȡ��*/
	public void processCancel(ActionEvent e){
		stage.close();
	}
	/**����������*/
	public void processDisponseCh(ActionEvent e){
		String text = disponseButton.getText();
		if("����������".equals(text)){
			disponseCh = true;
			disponseButton.setText("��ʾ������");
		}else{
			disponseCh = false;
			disponseButton.setText("����������");
		}
		select();
	}

	/**��ʾ��ѯ��Ϣ*/
	public void select() {

		//�������
		new Thread(new Runnable() {
			 @Override public void run() {
			 Platform.runLater(new Runnable() {
				@Override public void run() {
					allButtonsDisabled(true);
				}
			 });
			 try{
				 obList = tableEditService.rowMapToColMap(rowMap, tableName ,
							disponseCh,updateMap,updateChMap,unEditMap,insertFlag);
			 }catch(Exception e){
				 e.printStackTrace();
				 DialogTools.error(stage, "����", "������","��ʾ�༭���ݳ���!");
				 return;
			 }finally{
				 Platform.runLater(new Runnable() {
						@Override public void run() {
							allButtonsDisabled(false);
						}
				 });
			 }


			  Platform.runLater(new Runnable() {
					@Override public void run() {
						//������
						tableView.getColumns().clear();
						tableView.getSelectionModel().setCellSelectionEnabled(true);
						TableColumn<Map<String,Object>, Object> col1 = new
						    		TableColumn<Map<String,Object>, Object>("����");
						col1.setCellFactory(new TaskCellFactory());
						col1.setCellValueFactory(new MapValueFactory("key"));
						col1.setEditable(false);
						col1.setPrefWidth(275);
						TableColumn<Map<String,Object>, Object> col2 = new
					    		TableColumn<Map<String,Object>, Object>("����ֵ");
						col2.setCellFactory(new TaskCellFactory());
						col2.setCellValueFactory(new MapValueFactory("value"));
						col2.setPrefWidth(200);
						tableView.getColumns().add(col1);
						tableView.getColumns().add(col2);
						tableView.setItems(obList);
					}
				 });
			 }
			}).start();



	}
	private class TaskCellFactory implements Callback<TableColumn<Map<String,Object>, Object>, TableCell<Map<String,Object>, Object>> {
		   @Override
		   public TableCell<Map<String,Object>, Object> call(TableColumn<Map<String,Object>, Object> param) {
			   EditTableCell<Map<String,Object>, Object> cell = new EditTableCell<Map<String,Object>, Object>(stage,obList, tableName,unEditMap,
		    		   tableEditService,updateMap,updateChMap,insertFlag);
		       return cell;
		   }
	}
}
