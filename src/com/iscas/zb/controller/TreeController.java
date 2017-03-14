package com.iscas.zb.controller;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.iscas.zb.Main;
import com.iscas.zb.model.Unit;
import com.iscas.zb.service.TreeService;
import com.iscas.zb.tools.CommonTools;
import com.iscas.zb.tools.DialogTools;
import com.iscas.zb.tools.SpringFxmlLoader;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
*@date: 2017��3��10��
*@author: zhuquanwen
*@desc: �����������
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
	private AnchorPane tab1;
	@FXML
	private AnchorPane tab2;


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

	/**ʹ��button disabled*/
	public void disabledButtons(boolean flag){
		allExpandButton.setDisable(flag);
		refreshButton.setDisable(flag);
		if(flag){
			pi.setVisible(true);
			tab1.getChildren().clear();
			tab2.getChildren().clear();
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
							 DialogTools.error(stage, "����", "������","��ȡ�������ݳ���");
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
					            public TreeCell<Unit> call(TreeView<Unit> treeView) {

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
					                            	if(t.getClickCount() == 1){

					                            		if("���ӱ����".equals(unit.getNameCh())){

					                            		}else{
					                            			//�����ӽڵ�����
						                            		createPropTable(unit);
						                            		//�����ô���ӽڵ㣬��̬����
						                            		if(this.getTreeItem().getChildren() != null && this.getTreeItem().getChildren().size() > 0){
						                            		}else{
						                            			//��ȡ�ӽڵ㣬���������ӽڵ�
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
					                            		}
					                            		//����������Ϣ

					                            		createSortTable(unit,this.getTreeItem());

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
	/**ˢ��*/
	public void processRefresh(ActionEvent e){
		createTree();
	}
	/**ȫ��չ��*/
	public void processAllExpand(ActionEvent e){
		new Thread(new Runnable() {
			 @Override public void run() {
			 Platform.runLater(new Runnable() {
				@Override public void run() {
					treeView.setRoot(null);
					disabledButtons(true);
				}
			 });

			 try{
				  u = expand(ti.getValue());
			 }catch(Exception e){
				 e.printStackTrace();
				 Platform.runLater(new Runnable() {
						@Override public void run() {
							 DialogTools.error(stage, "����", "������","��ȡ�������ݳ���");
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
	/**����������Ϣ*/
	@SuppressWarnings("static-access")
	private void createSortTable(Unit unit,TreeItem<Unit> treeView) {
		tab2.getChildren().clear();


			SpringFxmlLoader loader = new SpringFxmlLoader();

			try {
				AnchorPane p1 = (AnchorPane) loader.springLoad("view/UnitSortView.fxml", Main.class);
				UnitSortController controller = loader.getController();
				controller.setStage(stage);
				controller.setUnit(unit);
				controller.setTreeView(treeView);
				controller.select();
				tab2.getChildren().add(p1);
				tab2.setTopAnchor(p1, 0.0);
				tab2.setBottomAnchor(p1, 0.0);
				tab2.setLeftAnchor(p1, 0.0);
				tab2.setRightAnchor(p1, 0.0);
				//controller.setTc(TableController.this);

			} catch (Exception e) {
				e.printStackTrace();
				DialogTools.error(stage,"����", "������!", "���༭����!");
			}

	}
	/**���ɲ�������*/
	@SuppressWarnings("static-access")
	private void createPropTable(Unit unit){
		tab1.getChildren().clear();
		String tableName = CommonTools.getUnitTableNameByType(unit.getType());
		Map<String,Object> map = treeService.getUnitProp(tableName,unit);

			SpringFxmlLoader loader = new SpringFxmlLoader();

			try {
				AnchorPane p1 = (AnchorPane) loader.springLoad("view/TableEditView.fxml", Main.class);
				TableEditController controller = loader.getController();
				controller.setTableName(tableName);
				controller.setInsertFlag(false);
				controller.setRowMap(map);
				controller.select();
				controller.setTreeFlag(true);
				controller.setStage(stage);
				controller.treeFlagDoSomeThing();
				tab1.getChildren().add(p1);
				tab1.setTopAnchor(p1, 0.0);
				tab1.setBottomAnchor(p1, 0.0);
				tab1.setLeftAnchor(p1, 0.0);
				tab1.setRightAnchor(p1, 0.0);
				//controller.setTc(TableController.this);

			} catch (Exception e) {
				e.printStackTrace();
				DialogTools.error(stage,"����", "������!", "���༭����!");
			}
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
	        if("����".equals(this.getTableColumn().getText())){
				//this.setStyle("-fx-background-color: #aaaaaa; -fx-table-cell-border-color: black;");
	        	//this.setStyle(" -fx-text-fill: black;");
			}else{
				 //���Ϊ���õĲ��ɱ༭�У�Ҳ���ɱ༭
//				if(!insertFlag){
//					Integer index = this.getIndex();
//					if(index >= 0 ){
//						Map map = (Map)obList.get(index);
//						Map<String,String> mapx = new HashMap<String,String>();
//						mapx.put(tableName, (String)map.get("colName"));
//						if(unEditMap.get(mapx) != null){
//							this.setStyle(" -fx-text-fill: #ADADAD;");
//						}
//					}
//				}

			}


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
