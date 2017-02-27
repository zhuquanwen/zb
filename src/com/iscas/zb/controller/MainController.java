package com.iscas.zb.controller;


import java.io.IOException;

import org.apache.log4j.Logger;
import org.controlsfx.dialog.DialogAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.iscas.zb.Main;
import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.jaxb.JTable;
import com.iscas.zb.service.MainService;
import com.iscas.zb.service.TableService;
import com.iscas.zb.tools.DialogTools;
import com.iscas.zb.tools.SpringFxmlLoader;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
@Controller
@Scope("prototype")
public class MainController {
	Logger log = Logger.getLogger(MainController.class);
	private Main main;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private TreeView treeView;
	@FXML
	private Label label;
	@Autowired(required=true)
	private MainService mainService;
	@Autowired(required=true)
	private TableService tableService;
	public TreeView getTreeView() {
		return treeView;
	}

	public void setMainApp(Main main) {
	        this.main = main;
	 }

	 @FXML
	 private void initialize() {
		 //mainService = MainService.getSingleton();
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
	                            	//��ǰ�ڵ����ӽڵ��ʱ�����ɱ���stage

	                            	if(getChildren().size() == 1){
	                            		if (t.getClickCount() == 1) {
//		            		            	Dialogs.create()
//		            		                .owner(Main.stage)
//		            		                .title("��ʾ")
//		            		                .masthead("��ʾ")
//		            		                .message("����������Ϣ����")
	//
//		            		                .showInformation();
		                            		//��ת������ҳ��
		                            		Stage stage = new Stage();
		                            		//stage.setTitle(f.getName());
		                        			AnchorPane root = null;
		                        			SpringFxmlLoader loader = new SpringFxmlLoader();
											try {
												root = (AnchorPane) loader.springLoad("view/TableView.fxml", Main.class);


			                                    TableController controller = loader.getController();
			                                    controller.setStage(stage);
			                                    controller.setTableName(f.getNameEn());
			                                    controller.setChildFlag(false);
			                                    controller.selectTable(" where 1=1 ");

			                                    Scene scene = new Scene(root);
			                                    stage.setScene(scene);
			                                    log.info("--������" + f.getName() + "--");
			                                    stage.show();
			                                    Integer total = tableService.getTotal(f.getNameEn(),  " where 1 =1 ");
			                                    String title = f.getName() + "[" + f.getNameEn() + "]" + "[" + total + "]";
			                                    stage.setTitle(title);
											} catch (Exception e) {
												e.printStackTrace();
												DialogTools.error("����", "������!", "��ѯ�������ݳ���!");
											}

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
//		                .title("��ʾ")
//		                .masthead("��ʾ")
//		                .message("����������Ϣ����")
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