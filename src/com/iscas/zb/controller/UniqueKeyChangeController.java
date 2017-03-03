package com.iscas.zb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.iscas.zb.model.HandlerModel;
import com.iscas.zb.service.UniqueKeyChangeService;
import com.iscas.zb.tools.DialogTools;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
*@date: 2017��3��2��
*@author: zhuquanwen
*@desc: ����һ����˵��
*/
@Controller
@Scope("prototype")
public class UniqueKeyChangeController {
	@FXML
	private TableView tableView;
	@FXML
	private Button commitButton;
	@FXML
	private Button cancelButton;
	@FXML
	private ProgressIndicator progressIndicator;
	@Autowired
	private UniqueKeyChangeService uniqueKeyChangeService;
	private ObservableList obList;
	private Map<String,Object> rowMap ;
	private Map<String,Object> updateMap = new HashMap<String,Object>();
	private String tableName;
	private TableController tableController;
	private Stage stage;
	private boolean cascadeFlag = false;


	public boolean isCascadeFlag() {
		return cascadeFlag;
	}
	public void setCascadeFlag(boolean cascadeFlag) {
		this.cascadeFlag = cascadeFlag;
	}
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	public Map<String, Object> getUpdateMap() {
		return updateMap;
	}
	public void setUpdateMap(Map<String, Object> updateMap) {
		this.updateMap = updateMap;
	}
	public TableController getTableController() {
		return tableController;
	}
	public void setTableController(TableController tableController) {
		this.tableController = tableController;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Map<String, Object> getRowMap() {
		return rowMap;
	}
	public void setRowMap(Map<String, Object> rowMap) {
		this.rowMap = rowMap;
	}

	public ObservableList getObList() {
		return obList;
	}
	public void setObList(ObservableList obList) {
		this.obList = obList;
	}
	@FXML
	private void initialize() {
		progressIndicator.setVisible(false);
	}

	private void allButtonsDisabled(boolean flag){
		commitButton.setDisable(flag);
		cancelButton.setDisable(flag);
		if(flag){
			progressIndicator.setVisible(true);
		}else{
			progressIndicator.setVisible(false);
		}
	}
	/**ȷ�ϸ���*/
	public void processCommit(ActionEvent e){
		try{
			new Thread(new Runnable() {
				 @Override public void run() {
				 Platform.runLater(new Runnable() {
					@Override public void run() {
						allButtonsDisabled(true);
					}
				 });
				 try{
					 boolean flag = tableController.insertCopy(rowMap, updateMap, tableName ,cascadeFlag);
						if(!flag){
							DialogTools.warn(stage, "����", "����","���������ӱ����,�������Ϊ:"
									+ "1.�ӱ���ܴ���������Ψһ��,û��ʵ�ָ���;2.���ݿ���ڴ��������Ѿ�ʵ���˸���");
							return;
						}
				 }catch(Exception e){
					 e.printStackTrace();
					 DialogTools.error(stage, "����", "������","�����ύ����");
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
							stage.close();
						}
					 });
				 }
				}).start();

		}catch(Exception ex){
			ex.printStackTrace();
			DialogTools.exception(stage, "����", "������!", "�������ݳ��ִ���", ex);
		}

	}
	/**ȡ������*/
	public void processCancel(ActionEvent e){
		stage.close();
	}
	/**��ʾ��ѯ��Ϣ*/
	public void select() {
		//������
		tableView.getColumns().clear();
		tableView.getSelectionModel().setCellSelectionEnabled(true);
		TableColumn<Map<String,Object>, Object> col1 = new
		    		TableColumn<Map<String,Object>, Object>("����");
		col1.setCellFactory(new TaskCellFactory());
		col1.setCellValueFactory(new MapValueFactory("key"));
		col1.setEditable(false);
		col1.setPrefWidth(260);

		TableColumn<Map<String,Object>, Object> col2 = new
	    		TableColumn<Map<String,Object>, Object>("����ֵ");
		col2.setCellFactory(new TaskCellFactory());
		col2.setCellValueFactory(new MapValueFactory("value"));
		col2.setPrefWidth(150);
		tableView.getColumns().add(col1);
		tableView.getColumns().add(col2);
		//�������
		tableView.setItems(obList);
	}

	private class TaskCellFactory implements Callback<TableColumn<Map<String,Object>, Object>, TableCell<Map<String,Object>, Object>> {
		   @Override
		   public TableCell<Map<String,Object>, Object> call(TableColumn<Map<String,Object>, Object> param) {
			   TableCell<Map<String,Object>, Object> cell = new TableCell<Map<String,Object>, Object>(){
				   @Override
					protected void updateItem(Object item, boolean empty) {
						if (item == getItem()) return;

	                    super.updateItem(item, empty);

	                    if (item == null) {
	                        super.setText(null);
	                        super.setGraphic(null);
	                    }
	                    else if (item instanceof Node) {
	                        super.setText(null);
	                        super.setGraphic((Node)item);
	                    }
	                    else {
	                        super.setText(item.toString());
	                        super.setGraphic(null);
	                    }

					}
			   };
			   cell.setAlignment(Pos.CENTER);
		       return cell;
		   }
	}
}
