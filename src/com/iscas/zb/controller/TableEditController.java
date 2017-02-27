package com.iscas.zb.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.iscas.zb.data.SqlData;
import com.iscas.zb.service.TableEditService;
import com.iscas.zb.tools.DialogTools;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
*@date: 2017年2月27日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
@Controller
@Scope("prototype")
public class TableEditController {
	@Autowired
	private TableEditService tableEditService;
	@FXML
	private TableView tableView;
	private Stage stage;
	private String tableName;
	private Map rowMap;
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



	public Map getRowMap() {
		return rowMap;
	}
	public void setRowMap(Map rowMap) {
		this.rowMap = rowMap;
	}
	@FXML
	private void initialize() {


	}
	/**确定*/
	public void processCommit(ActionEvent e){

	}
	/**取消*/
	public void processCancel(ActionEvent e){
		stage.close();
	}
	/**显示查询信息*/
	public void select() {
		//生成列
		tableView.getColumns().clear();
		TableColumn<Map<String,Object>, Object> col1 = new
		    		TableColumn<Map<String,Object>, Object>("属性");
		col1.setCellFactory(new TaskCellFactory());
		col1.setCellValueFactory(new MapValueFactory("key"));
		col1.setEditable(false);
		col1.setPrefWidth(278);
		TableColumn<Map<String,Object>, Object> col2 = new
	    		TableColumn<Map<String,Object>, Object>("属性值");
		col2.setCellFactory(new TaskCellFactory());
		col2.setCellValueFactory(new MapValueFactory("value"));
		col2.setPrefWidth(200);
		tableView.getColumns().add(col1);
		tableView.getColumns().add(col2);
		//填充数据
		ObservableList obList = tableEditService.rowMapToColMap(rowMap, tableName);
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
		        //双击进入编辑
		        cell.setOnMouseClicked(event -> {
		        	if(event.getClickCount() == 2){
		        		//当前选中的行
//		        		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
//		        				.getSelectedItem();
		        		DialogTools.info("信息", "双击进入行编辑--待开发......");
		        	}

		        });
		        return cell;
		    }
		}
}
