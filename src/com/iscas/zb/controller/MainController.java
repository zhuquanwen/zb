package com.iscas.zb.controller;


import java.io.IOException;

import com.iscas.zb.Main;
import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.jaxb.JTable;
import com.iscas.zb.service.MainService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainController {

	private Main main;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private TreeView treeView;
	@FXML
	private Label label;
	private MainService mainService;
	public TreeView getTreeView() {
		return treeView;
	}

	public void setMainApp(Main main) {
	        this.main = main;
	 }

	 @FXML
	 private void initialize() {
		 mainService = MainService.getSingleton();
		 initTreeView(0);


	 }
	 @SuppressWarnings("unchecked")
	public void initTreeView(int index){
	        //TreeView<JTable> treeView = new TreeView<JTable>();
	        treeView.setCellFactory(new Callback<TreeView<JTable>, TreeCell<JTable>>() {

	            @Override
	            public TreeCell<JTable> call(TreeView<JTable> jt) {

	            	return new TreeCell<JTable>(){

	                    @Override
	                    protected void updateItem(JTable f, boolean empty) {
	                    	super.updateItem(f, empty);
	                    	if(empty){
	                            setText(null);
	                            setGraphic(null);
	                        }else{
	                            setText(f.getName());
	                            setGraphic(null);
	                            setOnMouseClicked((MouseEvent t) -> {
	                            	//当前节点是子节点的时候，生成表格stage

	                            	if(getChildren().size() == 1){
	                            		if (t.getClickCount() == 1) {
//		            		            	Dialogs.create()
//		            		                .owner(Main.stage)
//		            		                .title("提示")
//		            		                .masthead("提示")
//		            		                .message("弹出表格信息窗口")
	//
//		            		                .showInformation();
		                            		//跳转至表格页面
		                            		Stage stage = new Stage();
		                            		stage.setTitle(f.getName());
		                        			AnchorPane root = new AnchorPane();

		                        			FXMLLoader loader = new FXMLLoader();
		                                    loader.setLocation(Main.class.getResource("view/TableView.fxml"));
		                                    try {
												root = (AnchorPane) loader.load();
											} catch (IOException e) {
												e.printStackTrace();
											}
		                                    TableController controller = loader.getController();
		                                    controller.setTableName(f.getNameEn());
		                                    Scene scene = new Scene(root);
		                                    stage.setScene(scene);
		                                    stage.show();

		            		            }
	                            	}

	                            } );

	                        }
	                    }

	                };
	            }
	        });

	     treeView.setRoot(StaticData.treeItemMap.get(index));

	 }

//	 private class TaskCellFactory implements Callback<TableColumn<TableMenuEntity, String>, TableCell<TableMenuEntity, String>> {
//
//		    @Override
//		    public TableCell<TableMenuEntity, String> call(TableColumn<TableMenuEntity, String> param) {
//		        TextFieldTableCell<TableMenuEntity, String> cell = new TextFieldTableCell<>();
//		        cell.setOnMouseClicked((MouseEvent t) -> {
//		            if (t.getClickCount() == 1) {
//		            	Dialogs.create()
//		                .owner(Main.stage)
//		                .title("提示")
//		                .masthead("提示")
//		                .message("弹出表格信息窗口")
//
//		                .showInformation();
//
//
//		            }
//		        });
//		        //cell.setContextMenu(taskContextMenu);
//		        return cell;
//		    }
//		}
}
