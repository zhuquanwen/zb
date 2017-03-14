package com.iscas.zb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.iscas.zb.model.Unit;
import com.iscas.zb.service.UnitSortService;
import com.iscas.zb.tools.DialogTools;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
*@date: 2017年2月27日
*@author: zhuquanwen
*@desc: 表格编辑页面对应的控制器
*/
@Controller
@Scope("prototype")
@SuppressWarnings(value={"rawtypes","unchecked"})
public class UnitSortController {
	@FXML
	private TableView tableView;
	@FXML
	private ProgressIndicator progressIndicator;
	@FXML
	private Button commitButton;
	@FXML
	private Label successLabel;
	private Stage stage;
	private Unit unit;
	private TreeItem<Unit> treeView;
	@Autowired
	private UnitSortService unitSortService;

	private Map<String,TextField> updateMap = new HashMap<String,TextField>();
	private ObservableList obList;

	public TreeItem<Unit> getTreeView() {
		return treeView;
	}
	public void setTreeView(TreeItem<Unit> treeView) {
		this.treeView = treeView;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}





	@FXML
	private void initialize() {


		progressIndicator.setVisible(false);
		successLabel.setVisible(false);

	}

	private void allButtonsDisabled(boolean flag){
		commitButton.setDisable(flag);
		if(flag){
			progressIndicator.setVisible(true);
		}else{
			progressIndicator.setVisible(false);
		}
	}


	/**确定*/
	public void processCommit(ActionEvent e){
		successLabel.setVisible(false);
		try{

			 new Thread(new Runnable() {
				 @Override public void run() {
				 Platform.runLater(new Runnable() {
					@Override public void run() {
						allButtonsDisabled(true);
					}
				 });
				 try{
					 unitSortService.commit(updateMap,unit,treeView);
				 }catch(Exception e){
					 e.printStackTrace();
					 Platform.runLater(new Runnable() {
							@Override public void run() {
								 DialogTools.error(stage, "错误", "出错了","编辑提交出错");
								 return;
							}
						 });

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

								successLabel.setVisible(true);

						}
					 });

				 }
				}).start();
		}catch(Exception ex){
			ex.printStackTrace();
			DialogTools.error(stage, "错误", "出现异常", "修改排序提交失败！");
		}


	}


	/**显示查询信息*/
	public void select() {

		//填充数据
		new Thread(new Runnable() {
			 @Override public void run() {
			 Platform.runLater(new Runnable() {
				@Override public void run() {
					allButtonsDisabled(true);
				}
			 });
			 try{
				 obList = unitSortService.getSort(updateMap,unit);
			 }catch(Exception e){
				 e.printStackTrace();
				 Platform.runLater(new Runnable() {
						@Override public void run() {
							 DialogTools.error(stage, "错误", "出错了","显示排序数据出错");
							 return;
						}
				 });
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
						//生成列
						tableView.getColumns().clear();
						tableView.getSelectionModel().setCellSelectionEnabled(true);
						TableColumn<Map<String,Object>, Object> col1 = new
						    		TableColumn<Map<String,Object>, Object>("部队名称");
						col1.setCellFactory(new TaskCellFactory());
						col1.setCellValueFactory(new MapValueFactory("key"));
						col1.setEditable(false);

						col1.setPrefWidth(320);


						TableColumn<Map<String,Object>, Object> col2 = new
					    		TableColumn<Map<String,Object>, Object>("顺序");
						col2.setCellFactory(new TaskCellFactory());
						col2.setCellValueFactory(new MapValueFactory("value"));

						col2.setPrefWidth(250);

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
			   EditTableCell<Map<String,Object>, Object> cell = new EditTableCell<Map<String,Object>, Object>();
		       return cell;
		   }
	}
	private class EditTableCell<T,R> extends TableCell<Map<String,Object>, Object> {


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

	}
}
