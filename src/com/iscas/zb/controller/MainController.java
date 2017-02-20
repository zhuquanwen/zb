package com.iscas.zb.controller;


import java.io.File;

import org.controlsfx.dialog.Dialogs;

import com.iscas.zb.Main;
import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.jaxb.JTable;
import com.iscas.zb.service.MainService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
	                            	if (t.getClickCount() == 1) {
	            		            	Dialogs.create()
	            		                .owner(Main.stage)
	            		                .title("提示")
	            		                .masthead("提示")
	            		                .message("弹出表格信息窗口")

	            		                .showInformation();


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
