package com.iscas.zb.controller;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.iscas.zb.model.Unit;
import com.iscas.zb.service.TreeService;
import com.iscas.zb.tools.DialogTools;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
*@date: 2017年3月10日
*@author: zhuquanwen
*@desc: 编成树控制器
*/
@Controller
@Scope("prototype")
@SuppressWarnings({"unchecked","rawtypes"})
public class TreeController implements Initializable{
	@FXML
	private TreeView treeView;
	@FXML
	private AnchorPane rightAnchorPane;
	@FXML
	private Button allExpandButton;
	@FXML
	private Button refreshButton;
	@FXML
	private ProgressIndicator pi;
	private Stage stage;
	private TreeItem<Unit> ti = null;
	private Unit u = null;
	@Autowired
	private TreeService treeService;

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pi.setVisible(false);

	}

	/**使得button disabled*/
	public void disabledButtons(boolean flag){
		allExpandButton.setDisable(flag);
		refreshButton.setDisable(flag);
		rightAnchorPane.setDisable(flag);
		if(flag){
			pi.setVisible(true);
			rightAnchorPane.getChildren().clear();

		}else{
			pi.setVisible(false);
		}
	}

	public void createTree(){
		new Thread(new Runnable() {
			 @Override public void run() {
			 Platform.runLater(new Runnable() {
				@Override public void run() {
					disabledButtons(true);
				}
			 });

			 try{
				  ti = treeService.getUnitTree();
			 }catch(Exception e){
				 e.printStackTrace();
				 Platform.runLater(new Runnable() {
						@Override public void run() {
							 DialogTools.error(stage, "错误", "出错了","获取部队数据出错");
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

						treeView.setRoot(null);
						 treeView.setCellFactory(new Callback<TreeView<Unit>, TreeCell<Unit>>() {

					            @Override
					            public TreeCell<Unit> call(TreeView<Unit> unit) {

					            	return new TreeCell<Unit>(){

					                    @Override
					                    protected void updateItem(Unit unit, boolean empty) {
					                    	super.updateItem(unit, empty);
					                    	if(empty){
					                            setText(null);
					                            setGraphic(null);
					                        }else{
					                            setText(unit.getNameCh());
					                            setGraphic(null);
					                            setOnMouseClicked((MouseEvent t) -> {
					                            	if(t.getClickCount() == 2){
					                            		//双击，检查有么有子节点，动态加载
					                            		if("部队编成树".equals(unit.getNameCh())){
					                            			return;
					                            		}
					                            		if(this.getTreeItem().getChildren() != null && this.getTreeItem().getChildren().size() > 0){
					                            			return;
					                            		}
					                            		//获取子节点，并且生成子节点
					                            		List<Unit> us = treeService.getLeaf(unit);
					                            		if(us != null && us.size() > 0){
					                            			TreeItem<Unit> treeUnit = this.getTreeItem();
					                            			treeUnit.setExpanded(true);
					                            			us.forEach( u -> {
					                            				TreeItem<Unit> subTreeUnit = new TreeItem<Unit>(u);
					                            				treeUnit.getChildren().add(subTreeUnit);
					                            			});
					                            		}
					                            	}


					                            } );

					                        }
					                    }

					                };
					            }
					        });


						treeView.setRoot(ti);
					}
				 });
			 }
			}).start();





	}
	/**刷新*/
	public void processRefresh(ActionEvent e){
		createTree();
	}
	/**全部展开*/
	public void processAllExpand(ActionEvent e){
		new Thread(new Runnable() {
			 @Override public void run() {
			 Platform.runLater(new Runnable() {
				@Override public void run() {
					disabledButtons(true);
				}
			 });

			 try{
				  u = expand(ti.getValue());
			 }catch(Exception e){
				 e.printStackTrace();
				 Platform.runLater(new Runnable() {
						@Override public void run() {
							 DialogTools.error(stage, "错误", "出错了","获取部队数据出错");
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

						treeView.setRoot(null);
						if(u != null){
							ti = new TreeItem<Unit> (u);
							getTreeItems(ti);
							treeView.setRoot(ti);
						}

					}
				 });
			 }
			}).start();

	}
	private  Unit expand(Unit u){
		if(u.getUnits().size() > 0){
			for (int i = 0; i < u.getUnits().size(); i++) {
				Unit subUnit = u.getUnits().get(i);
				expand(subUnit);
			}
		}else{
			List<Unit> us = treeService.getLeaf(u);
			if(us != null && us.size() > 0){
				for (int i = 0; i < us.size(); i++) {
					Unit subUnit = us.get(i);
					expand(subUnit);
				}
				u.setUnits(us);
			}
		}
		return u;
	}
	private void getTreeItems(TreeItem<Unit> ti){
		Unit u = ti.getValue();
		if(u != null){

			if(u.getUnits().size() > 0){
				for (int i = 0; i < u.getUnits().size(); i++) {
					Unit subU = u.getUnits().get(i);
					TreeItem<Unit> subTreeItem = new TreeItem<Unit> (subU);
					ti.getChildren().add(subTreeItem);
					getTreeItems(subTreeItem);
				}
			}
		}
	}
}
