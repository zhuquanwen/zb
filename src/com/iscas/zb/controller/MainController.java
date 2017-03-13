package com.iscas.zb.controller;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.iscas.zb.Main;
import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.jaxb.JTable;
import com.iscas.zb.service.MainService;
import com.iscas.zb.service.TableService;
import com.iscas.zb.tools.CommonTools;
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
/**
*@date: 2017��2��25��
*@author: zhuquanwen
*@desc: ��ҳ���Ӧ������
*/
@Controller
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class MainController {
	Logger log = Logger.getLogger(MainController.class);
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
	private Stage stage;


	public Stage getStage() {
		return stage;
	}


	public void setStage(Stage stage) {
		this.stage = stage;
	}


	public TreeView getTreeView() {
		return treeView;
	}


	 @FXML
	 private void initialize() {
		 initTreeView(0);
		 mainService.getIp();
	 }
	 @SuppressWarnings({ "unchecked", "finally" })
	public void initTreeView(int index){
		 if(7 == index){
			 //���ӱ����
			 AnchorPane root = null;
 			SpringFxmlLoader loader = new SpringFxmlLoader();
				try {
					root = (AnchorPane) loader.springLoad("view/TreeView.fxml", Main.class);
				 Stage stage = new Stage();
                 TreeController controller = loader.getController();
                 controller.setStage(stage);
                 controller.createTree();
                 Scene scene = new Scene(root);
                 stage.setScene(scene);
                 CommonTools.setIcon(stage);
                 String title = "���ӱ����";
                 stage.setTitle(title);
                 stage.show();

				} catch (Exception e) {
					e.printStackTrace();

					DialogTools.error(MainController.this.stage,"����", "������!", "�����ҳ�����!");
				}finally {
					return;
				}

		 }

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
	                            	//��ǰ�ڵ����ӽڵ��ʱ�����ɱ��stage

	                            	if(getChildren().size() == 1){
	                            		if (t.getClickCount() == 1) {
//
		                            		//��ת�����ҳ��
		                            		Stage stage = new Stage();
		                            		//stage.setTitle(f.getName());
		                        			AnchorPane root = null;
		                        			SpringFxmlLoader loader = new SpringFxmlLoader();
											try {
												root = (AnchorPane) loader.springLoad("view/tableView.fxml", Main.class);


			                                    TableController controller = loader.getController();
			                                    controller.setStage(stage);
			                                    controller.setTableName(f.getNameEn());
			                                    controller.setChildFlag(false);
			                                    controller.selectTable(" where 1=1 ");

			                                    Scene scene = new Scene(root);
			                                    stage.setScene(scene);
			                                    log.info("--������" + f.getName() + "--");
			                                    CommonTools.setIcon(stage);
			                                    stage.show();
			                                    Integer total = tableService.getTotal(f.getNameEn(),  " where 1 =1 ");
			                                    String title = f.getName() + "[" + f.getNameEn() + "]" + "[" + total + "]";

			                                    //����Ǽ���������ı༭�����˵��
			                                    if(StaticData.translate_col_name.equalsIgnoreCase(f.getNameEn()) ||
			                                    		StaticData.translate_content_name.equalsIgnoreCase(f.getNameEn()) ||
			                                    		StaticData.translate_table_name.equalsIgnoreCase(f.getNameEn())){
			                                    	String tips = "(*ϵͳĬ��10����ˢ��һ�κ�����Ϣ,��ʹ�����༭������Ч,�ɵ��ˢ�º�����ť)";
			                                    	title += tips;
			                                    }

			                                    stage.setTitle(title);
											} catch (Exception e) {
												e.printStackTrace();
												log.error(e.getStackTrace());
												log.error(e.getMessage());
												DialogTools.error(MainController.this.stage,"����", "������!", "��ѯ�����ݳ���!");
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


}
