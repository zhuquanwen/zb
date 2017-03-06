package com.iscas.zb.controller;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.iscas.zb.Main;
import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.ChildRelation;
import com.iscas.zb.model.HandlerModel;
import com.iscas.zb.service.TableService;
import com.iscas.zb.service.UniqueKeyChangeService;
import com.iscas.zb.tools.DialogTools;
import com.iscas.zb.tools.EnToChTools;
import com.iscas.zb.tools.SpringFxmlLoader;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
@SuppressWarnings("unchecked")
public class TableController {
	//表格新增
	@FXML
	private Button addButton;

	//表格//批量删除
	@FXML
	private Button deleteButton;
	//表格刷新
	@FXML
	private Button refreshButton;
	//显示/隐藏中文
	@FXML
	private Button viewChButton;

	//表格汉化刷新
	@FXML
	private Button refreshChButton;
	//查询
	@FXML
	private Button selectButton;

	//表格显示窗口
	@FXML
	private TableView tableView;
	//每页显示多少条
	@FXML
	private ComboBox pageSizeCombobox;
	//首页
	@FXML
	private Hyperlink firstPageLink;
	//上一页
	@FXML
	private Hyperlink frontPageLink;
	//当前页
	@FXML
	private Label currentPageLabel;
	//下一页
	@FXML
	private Hyperlink nextPageLink;
	//尾页
	@FXML
	private Hyperlink lastPageLink;
	//总页数
	@FXML
	private Label totalPageLabel;
	//跳转的text
	@FXML
	private TextField turnPageTextField;
	//跳转确定
	@FXML
	private Hyperlink turnPageLink;
	//关联表hbox1
	@FXML
	private HBox hBox1;
	//关联表hbox2
	@FXML
	private HBox hBox2;
	//关联表hbox3
	@FXML
	private HBox hBox3;
	//全选
	@FXML
	private Button checkBoxSelectButton;
	//批量级联删除
	@FXML
	private Button cascadeDeleteButton;
	//加载按钮
	@FXML
	private ProgressIndicator progressIndicator;


	private Stage stage;
	@Autowired(required=true)
	private TableService tableService;
	@Autowired(required=true)
	private UniqueKeyChangeService uniqueKeyChangeService;
	@Autowired(required=true)
	private EnToChTools enToChTools;

	/**表格名称*/
	private String tableName;
	/**当前页*/
	private Integer page = 1;
	/**每页条目*/
	private Integer pageSize = Integer.valueOf(StaticData.default_table_page_size);
	/**总页数*/
	private Integer totalPage = 1;
	/**行右键菜单*/
	private ContextMenu rowContextMenu;

	/**表的列信息*/
	private LinkedHashMap<String,Map<String,String>> colInfoMap = null;
	/**表列的中英对照信息*/
	private LinkedHashMap<String,String> colEnChMap = null;

	/**不翻译的字符串列Map形式*/
	private Map<String,String> disColMap = null;

	private String sqlCondition = " where 1=1 ";
	private String selectCondition = " ";
	private Boolean childFlag = false;
	private ObservableList obList = null;
	private boolean viewColFlag = true;

	 public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSelectCondition() {
		return selectCondition;
	}

	public void setSelectCondition(String selectCondition) {
		this.selectCondition = selectCondition;
	}

	public String getSqlCondition() {
		return sqlCondition;
	}

	public void setSqlCondition(String sqlCondition) {
		this.sqlCondition = sqlCondition;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public LinkedHashMap<String, String> getColEnChMap() {
		return colEnChMap;
	}

	public void setColEnChMap(LinkedHashMap<String, String> colEnChMap) {
		this.colEnChMap = colEnChMap;
	}

	public Map<String, String> getDisColMap() {
		return disColMap;
	}

	public void setDisColMap(Map<String, String> disColMap) {
		this.disColMap = disColMap;
	}

	public LinkedHashMap<String, Map<String, String>> getColInfoMap() {
		return colInfoMap;
	}

	public void setColInfoMap(LinkedHashMap<String, Map<String, String>> colInfoMap) {
		this.colInfoMap = colInfoMap;
	}

	public Boolean getChildFlag() {
		return childFlag;
	}

	public void setChildFlag(Boolean childFlag) {
		this.childFlag = childFlag;
	}

	@FXML
	 private void initialize() {
		 initPageSizeCombobox();
		 initContextMenu();
		 //initRelationTable();
		 //initTable();
		 initToolTips();
	 }

	private void allButtonsDisabled(boolean flag){
		addButton.setDisable(flag);
		deleteButton.setDisable(flag);
		refreshButton.setDisable(flag);
		refreshChButton.setDisable(flag);
		pageSizeCombobox.setDisable(flag);
		firstPageLink.setDisable(flag);
		frontPageLink.setDisable(flag);
		currentPageLabel.setDisable(flag);
		nextPageLink.setDisable(flag);
		lastPageLink.setDisable(flag);
		totalPageLabel.setDisable(flag);
		turnPageTextField.setDisable(flag);
		turnPageLink.setDisable(flag);
		checkBoxSelectButton.setDisable(flag);
		cascadeDeleteButton.setDisable(flag);
		viewChButton.setDisable(flag);
		selectButton.setDisable(flag);
		if(flag){
			progressIndicator.setVisible(true);
		}else{
			progressIndicator.setVisible(false);
		}
	}

	private void initToolTips() {
		addButton.setTooltip(new Tooltip("新增一条记录"));
		deleteButton.setTooltip(new Tooltip("批量删除被选中的复选框对应的记录"));
		refreshButton.setTooltip(new Tooltip("刷新当前页信息"));
		refreshChButton.setTooltip(new Tooltip("刷新汉化表信息(系统默认10分钟刷新一次，点此按钮可立即刷新)"));
		pageSizeCombobox.setTooltip(new Tooltip("控制每页显示的条目"));
		firstPageLink.setTooltip(new Tooltip("跳转至第一页"));
		frontPageLink.setTooltip(new Tooltip("上一页"));
		currentPageLabel.setTooltip(new Tooltip("当前页码"));
		nextPageLink.setTooltip(new Tooltip("下一页"));
		lastPageLink.setTooltip(new Tooltip("跳转至最后一页"));
		totalPageLabel.setTooltip(new Tooltip("能够显示的总页数"));
		turnPageTextField.setTooltip(new Tooltip("跳转页面的输入"));
		turnPageLink.setTooltip(new Tooltip("确认页面跳转"));
		checkBoxSelectButton.setTooltip(new Tooltip("全选或反选复选框"));
		cascadeDeleteButton.setTooltip(new Tooltip("批量级联删除选中复选框对应的记录"));
		viewChButton.setTooltip(new Tooltip("显示或者隐藏中文对照列"));
		selectButton.setTooltip(new Tooltip("查询过滤内容"));
	}

	/**初始化关联表按钮*/
	 private void initRelationTable() {
		 hBox1.getChildren().clear();
		 hBox2.getChildren().clear();
		 hBox3.getChildren().clear();
		if(!childFlag){
			List<ChildRelation> crs = StaticData.tableRelationViewMap.get(tableName.toUpperCase());
			if(crs != null && crs.size() > 0){
				for (int i = 0; i < crs.size(); i++) {
					ChildRelation cr = crs.get(i);
					String childTableName = cr.getChildTableName();
					String childTableNameCh = EnToChTools.enToCh_table(childTableName);
					Button button = new Button();
					button.setText(childTableNameCh);
					button.setTextAlignment(TextAlignment.CENTER);
					button.setPrefWidth(150);
					Tooltip ti = new Tooltip(childTableNameCh + "[" + childTableName + "]");
					button.setTooltip(ti);
					button.setOnAction(e -> {
						Integer index = tableView.getSelectionModel().getSelectedIndex();


						if(index < 0){
							DialogTools.warn(stage, "警告", "警告!", "请选择一条记录!");
							return;
						}

						Map  mapx = (Map) tableView.getItems().get(index);
						//DialogTools.info("提示", "弹出子表--待开发--");
                		//跳转至子表格页面
                		Stage stage = new Stage();
                		//stage.setTitle(f.getName());
            			AnchorPane root = null;
            			SpringFxmlLoader loader = new SpringFxmlLoader();
						try {
							root = (AnchorPane) loader.springLoad("view/TableView.fxml", Main.class);
                            TableController controller = loader.getController();
                            controller.setStage(stage);
                            controller.setTableName(childTableName);
                            controller.setChildFlag(true);
                            List<String> ccns =  cr.getChildColNames();
                            List<String> cns = cr.getColNames();
                            String condition = " where 1=1  " ;
                            if(ccns != null && ccns.size() == 1 &&
                            		cns != null && cns.size() == 1){

                            	condition += " and " +ccns.get(0) + " = '"+ mapx.get(cns.get(0)) +"'";
                            }

                            if(ccns != null && ccns.size() == 2 &&
                            		cns != null && cns.size() == 2){
                            	condition += " and " + ccns.get(0) + " = '"+ mapx.get(cns.get(0)) +"' "
                            			+ " and " + ccns.get(1) + " = '"+ mapx.get(cns.get(1)) +"' ";
                            }


                            controller.selectTable(condition);

                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            Integer total = tableService.getTotal(childTableName,  condition);
                            String title = childTableNameCh + "[" + childTableName + "]" + "[" + total + "]" +
                            			"-关联主表" + tableName ;
                            stage.setTitle(title);
                            stage.showAndWait();

						} catch (Exception ex) {
							ex.printStackTrace();
							DialogTools.error("错误", "出错了!", "查询表单数据出错!");
						}


					});
					if(i <= 3){
						hBox1.getChildren().add(button);
					}else if(i > 3 && i <= 6){
						hBox2.getChildren().add(button);
					}else{
						hBox3.getChildren().add(button);
					}
				}
			}
		}

	}

	 /**编辑操作*/
	 private void editRow(){
		//进入编辑
		//当前选中的行
		Integer index = tableView.getSelectionModel().getSelectedIndex();
		if(index < 0 ){
			DialogTools.error("错误", "出错了!", "请选中一行进行编辑!");
		}
 		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel().getSelectedItem();
 		AnchorPane root = null;
			SpringFxmlLoader loader = new SpringFxmlLoader();
			Stage stage = new Stage();
			try {
				root = (AnchorPane) loader.springLoad("view/TableEditView.fxml", Main.class);
				TableEditController controller = loader.getController();
				controller.setTableName(tableName);
				controller.setInsertFlag(false);
				controller.setStage(stage);
				controller.setRowMap(map);
				controller.select();
				controller.setTc(TableController.this);
				Scene scene = new Scene(root);
             stage.setScene(scene);
             stage.initModality(Modality.APPLICATION_MODAL);
             stage.show();
             stage.setTitle("列表编辑");
			} catch (Exception e) {
				e.printStackTrace();
				DialogTools.error("错误", "出错了!", "表单编辑出错!");
			}
	 }
	 /**普通删除*/
	 private void normalDelete(){
		 //普通删除，不删除子表信息，将子表对应信息置为null
		 Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
 				.getSelectedItem();
		 if(map == null || map.size()  <= 0){
			 DialogTools.warn(stage,"警告", "警告","请选择一条要删除的记录!" );
		 }
		 new Thread(new Runnable() {
				 @Override public void run() {
				 Platform.runLater(new Runnable() {
					@Override public void run() {
						allButtonsDisabled(true);
					}
				 });
				 try{
					 tableService.normalDelete(map,tableName);
				 }catch(Exception e){
					 e.printStackTrace();
					 Platform.runLater(new Runnable() {
							@Override public void run() {
								 DialogTools.error(stage, "错误", "出错了","删除出错");
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
							TableController.this.selectTable(HandlerModel.DELETE);
						}
					 });
				 }
				}).start();
	 }
	 /**级联删除*/
	 private void cascadeDelete() {
		//级联删除，将子表对应行都删掉
		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
	 				.getSelectedItem();
		if(map == null || map.size()  <= 0){
			DialogTools.warn(stage,"警告", "警告","请选择一条要删除的记录!" );
		}
		 new Thread(new Runnable() {
			 @Override public void run() {
			 Platform.runLater(new Runnable() {
				@Override public void run() {
					allButtonsDisabled(true);
				}
			 });
			 try{
				 tableService.cascadeDelete(map,tableName);
			 }catch(Exception e){
				 e.printStackTrace();
				 Platform.runLater(new Runnable() {
						@Override public void run() {
							 DialogTools.error(stage, "错误", "出错了","级联删除出错");
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
						TableController.this.selectTable(HandlerModel.DELETE);
					}
				 });
			 }
			}).start();
	 }

	/**
	 * 初始化右键菜单
	 */
	private void initContextMenu() {

		MenuItem mi1 = new MenuItem("编辑");
		MenuItem mi2 = new MenuItem("删除");
		MenuItem mi4 = new MenuItem("级联删除");
		MenuItem mi3 = new MenuItem("行复制");
		MenuItem mi5 = new MenuItem("级联行复制");
		mi1.setOnAction(event -> {
			editRow();
		});
		mi2.setOnAction(event -> {
			normalDelete();
		});
		mi3.setOnAction(event -> {
			Map<String,Object> rowMap = (Map<String,Object>)tableView.getSelectionModel().getSelectedItem();
			Map<String,Object> updateMap = new HashMap<String,Object>();
			ObservableList<Map>  changeObList = uniqueKeyChangeService.getChangeInfos(rowMap,updateMap,tableName);
			if(changeObList != null && changeObList.size() > 0){
				//进入行复制
				Stage stage = new Stage();
	    		//stage.setTitle(f.getName());
				AnchorPane root = null;
				SpringFxmlLoader loader = new SpringFxmlLoader();
				try {
					root = (AnchorPane) loader.springLoad("view/UniqueKeyChangeView.fxml", Main.class);


	                UniqueKeyChangeController controller = loader.getController();
	                controller.setRowMap(rowMap);
	                controller.setObList(changeObList);
	                controller.setStage(stage);
	                controller.setCascadeFlag(false);
	                controller.setUpdateMap(updateMap);
	                controller.setTableController(TableController.this);
	                controller.setTableName(tableName);
	                controller.select();
	                Scene scene = new Scene(root);
	                stage.setScene(scene);
	                stage.initModality(Modality.APPLICATION_MODAL);
	                stage.setTitle("修改主键和唯一键");
	                stage.show();
				} catch (Exception e) {
					e.printStackTrace();
					DialogTools.error("错误", "出错了!", "生成修改主键唯一键页面出错!");
				}
			}else{
				try{
					this.insertCopy(rowMap,updateMap,tableName,false);
				}catch(Exception ex){
					ex.printStackTrace();
					DialogTools.exception(stage, "错误", "出错了!", "复制数据出现错误", ex);
				}
			}

		});
		mi4.setOnAction(event -> {
			cascadeDelete();
		});

		mi5.setOnAction(event -> {
			Map<String,Object> rowMap = (Map<String,Object>)tableView.getSelectionModel().getSelectedItem();
			Map<String,Object> updateMap = new HashMap<String,Object>();
			ObservableList<Map>  changeObList = uniqueKeyChangeService.getChangeInfos(rowMap,updateMap,tableName);
			if(changeObList != null && changeObList.size() > 0){
				//进入行复制
				Stage stage = new Stage();
	    		//stage.setTitle(f.getName());
				AnchorPane root = null;
				SpringFxmlLoader loader = new SpringFxmlLoader();
				try {
					root = (AnchorPane) loader.springLoad("view/UniqueKeyChangeView.fxml", Main.class);


	                UniqueKeyChangeController controller = loader.getController();
	                controller.setRowMap(rowMap);
	                controller.setObList(changeObList);
	                controller.setStage(stage);
	                controller.setCascadeFlag(true);
	                controller.setUpdateMap(updateMap);
	                controller.setTableController(TableController.this);
	                controller.setTableName(tableName);
	                controller.select();
	                Scene scene = new Scene(root);
	                stage.setScene(scene);
	                stage.initModality(Modality.APPLICATION_MODAL);
	                stage.setTitle("修改主键和唯一键(*子表有与主表无关的主键或唯一键，无法实现级联复制)");
	                stage.show();
				} catch (Exception e) {
					e.printStackTrace();
					DialogTools.error("错误", "出错了!", "生成修改主键唯一键页面出错!");
				}
			}else{
				try{
					boolean flag = this.insertCopy(rowMap,updateMap,tableName,true);
					if(!flag){
						DialogTools.warn(stage, "警告", "警告","级联复制子表出错,问题可能为:"
								+ "1.子表可能存在主键或唯一键;2.数据库存在触发器，已经实现了复制");
					}
				}catch(Exception ex){
					ex.printStackTrace();
					DialogTools.exception(stage, "错误", "出错了!", "复制数据出现错误", ex);
				}
			}

		});
		rowContextMenu = new ContextMenu(mi1,mi2,mi4,mi3,mi5);

	}



	/**复制新增*/
	public boolean insertCopy(Map<String, Object> rowMap, Map<String, Object> updateMap,
			String tableName2, boolean cascadeFlag) {
		boolean flag = uniqueKeyChangeService.copyRow(rowMap,updateMap,tableName,cascadeFlag);
		this.selectTable(HandlerModel.INSERT);
		return flag;

	}

	/**
	 * 初始化pageSize
	 */

	private  void initPageSizeCombobox(){
		pageSizeCombobox.setItems(tableService.getPageSizeList());
		pageSizeCombobox.setValue(StaticData.default_table_page_size);
		pageSizeCombobox.setOnAction((Event event) -> {
			//DialogTools.info("提示", "模拟更改页数事件--待开发......");
			String value = (String) pageSizeCombobox.getValue();
			if("全部".equals(value)){
				pageSize = tableService.getTotal(tableName, sqlCondition);
			}else{
				pageSize = Integer.valueOf(value);
			}
			selectTable(HandlerModel.UNKOWN);

		});
	}

	public void selectTable(String sqlCondition){
		this.sqlCondition = sqlCondition;
		if(this.sqlCondition == null){
			this.sqlCondition = " where 1 = 1 ";
		}
		selectTable(HandlerModel.UNKOWN);
	}
	public void selectTable(HandlerModel hm, Integer count){
		switch (hm) {
		case BATHDELETE:
		{
			//批量删除
			if(count >= pageSize){
				page = page > 1 ? page -1 : 1;
			}
			selectTable(HandlerModel.UNKOWN);
		}
		default:
			break;
		}
	}


	/**初始化表格*/
	@SuppressWarnings("rawtypes")
	public void selectTable(HandlerModel hm){
		tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		String condition = sqlCondition + selectCondition;
		tableView.getColumns().clear();
		//totalPage = tableService.getTotal(tableName,  condition);
		switch (hm) {
		case INSERT:
		{
			sqlCondition = " where 1 = 1 ";
			Integer total = tableService.getTotal(tableName,  " where 1 =1 ");
			totalPage = total % pageSize == 0 ?
					total/pageSize  : total/pageSize +1;
			page = totalPage;
			break;
		}

		case DELETE:
		{
			ObservableList obList = tableView.getItems();
			if(obList != null && obList.size() >0 ){
				Integer size = obList.size();
				if(size <= 1){
					page = page > 1 ? page -1 : 1;
				}
			}else{
				page = page > 1 ? page -1 : 1;
			}
			break;
		}
		default:
			break;
		}


		//获得列信息
		colInfoMap = tableService.getColInfosByTableName(tableName,TableController.this);

		//先创建checkbox列
		TableColumn<Map<String,Object>, Object> col1 = new
	    		TableColumn<Map<String,Object>, Object>();
	    col1.setCellFactory(new TaskCellFactory());
	    col1.setCellValueFactory(new MapValueFactory("cb"));

	    tableView.getColumns().add(col1);

		Iterator<Entry<String, String>> iterator= colEnChMap.entrySet().iterator();
		String lastCol = "";
		while(iterator.hasNext())
		{
		    Entry<String, String> entry = iterator.next();
		    String colEn = entry.getKey();
		    String colCh = entry.getValue();
		    TableColumn<Map<String,Object>, Object> col = new
		    		TableColumn<Map<String,Object>, Object>(colCh);
		    col.setCellFactory(new TaskCellFactory());
		    col.setCellValueFactory(new MapValueFactory(colEn));
		    //如果点击隐藏中文列
		    if(!viewColFlag && colEn != null && colEn.endsWith("_EN")
		    		&& colEn.contains(lastCol)){
		    	//啥都不干
		    }else{
		    	tableView.getColumns().add(col);
		    }
		    lastCol = colEn;
		}
		//获得totalPage
		//totalPage = tableService.getTotalPage(tableName,pageSize," where 1 = 1 ");

		new Thread(new Runnable() {
			 @Override public void run() {
			 Platform.runLater(new Runnable() {
				@Override public void run() {
					allButtonsDisabled(true);
				}
			 });
			 try{
				 obList = tableService.getTableData(tableName,page,pageSize,colInfoMap,TableController.this, condition  );
			 }catch(Exception e){
				 e.printStackTrace();
				 Platform.runLater(new Runnable() {
						@Override public void run() {
							 DialogTools.error(stage, "错误", "出错了","查询表内容出错");
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
						tableView.setItems(obList);
						if(obList != null && obList.size() > 0){
							tableView.getSelectionModel().select(0);
						}

						//添加右键菜单
						tableView.setRowFactory(new Callback<TableView, TableRow>() {
					           @Override
					           public TableRow call(TableView param) {
					               return new TableRowControl();
					           }
					       });
						setDownMenu(condition);
						initRelationTable();
						 progressIndicator.setVisible(false);
					}
				 });
			 }
			}).start();

	}

	private void setDownMenu(String condition) {
		//恢复初始化设置
		firstPageLink.setDisable(false);
		lastPageLink.setDisable(false);
		frontPageLink.setDisable(false);
		nextPageLink.setDisable(false);

		//共有的页数设置
		Integer total = tableService.getTotal(tableName, condition);
		totalPage = total%pageSize == 0 ? total/pageSize : total/pageSize + 1;
		totalPageLabel.setText("共" + totalPage + "页");
		//第几页设置
		currentPageLabel.setText("第" + page + "页");

		//首页按钮设置
		if(page == 1){
			firstPageLink.setDisable(true);
		}
		//末页按钮设置
		if(page == totalPage){
			lastPageLink.setDisable(true);
		}
		//上一页设置
		if(page <= 1){
			frontPageLink.setDisable(true);
		}
		//下一页设置
		if(page >= totalPage){
			nextPageLink.setDisable(true);
		}

	}

	/**行右键菜单等*/
	private class TableRowControl extends TableRow {

        public TableRowControl() {
            super();
            this.setContextMenu(rowContextMenu);
        }
    }


	/**编辑内部类*/
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
		        		editRow();
		        	}

		        });
		        cell.setAlignment(Pos.CENTER);
		        return cell;
		    }
		}
	 /**新增*/
	 public void processAdd(ActionEvent e){
		 Map<String,Object> emptyMap = tableService.getEmptyMap(tableName,TableController.this);
		 AnchorPane root = null;
			SpringFxmlLoader loader = new SpringFxmlLoader();
			Stage stage = new Stage();
			try {
				root = (AnchorPane) loader.springLoad("view/TableEditView.fxml", Main.class);
				TableEditController controller = loader.getController();
				controller.setTableName(tableName);
				controller.setInsertFlag(true);
				controller.setStage(stage);
				controller.setRowMap(emptyMap);
				controller.select();
				controller.setTc(TableController.this);

				Scene scene = new Scene(root);
          stage.setScene(scene);

          stage.show();
          stage.setTitle("新增数据");
			} catch (Exception ex) {
				ex.printStackTrace();
				DialogTools.error("错误", "出错了!", "新增数据出错!");
			}
	 }
	 /**全选*/
	 public void processCheckBoxSelect(ActionEvent e){
		 String text = checkBoxSelectButton.getText();
		 if("全选".equals(text)){
			 ObservableList obList = tableView.getItems();
			 if(obList != null && obList.size() > 0){
				 obList.forEach(map -> {
					 Map dataMap = (Map)map;
					 CheckBox cb = (CheckBox)dataMap.get("cb");
					 cb.setSelected(true);
				 });
				 checkBoxSelectButton.setText("反选");
			 }

		 }else{
			 ObservableList obList = tableView.getItems();
			 if(obList != null && obList.size() > 0){
				 obList.forEach(map -> {
					 Map dataMap = (Map)map;
					 CheckBox cb = (CheckBox)dataMap.get("cb");
					 cb.setSelected(false);
				 });
				 checkBoxSelectButton.setText("全选");
			 }
		 }

	 }

	 /**刷新汉化*/
	 public void processRefreshCh(ActionEvent e){
		 new Thread(new Runnable() {
			 @Override public void run() {
			 Platform.runLater(new Runnable() {
				@Override public void run() {
					allButtonsDisabled(true);
				}
			 });
			 try{
				 enToChTools.getColMap(true);
				 enToChTools.getContentMap(true);
				 enToChTools.getTableMap(true);
			 }catch(Exception e){
				 e.printStackTrace();
				 Platform.runLater(new Runnable() {
						@Override public void run() {
							 DialogTools.error(stage, "错误", "出错了","刷新汉化出错");
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
						selectTable(HandlerModel.UNKOWN);
					}
				 });
			 }
			}).start();
	 }

	 /**批量删除*/
	 public void processDelete(ActionEvent e){


		 Integer[] count = new Integer[1];
		 count[0] = 0;
 		ObservableList items = tableView.getItems();
 		if(items != null && items.size() > 0){
 			 new Thread(new Runnable() {
 				 @Override public void run() {
 				 Platform.runLater(new Runnable() {
 					@Override public void run() {
 						allButtonsDisabled(true);
 					}
 				 });
 				 try{
 					for(Object map : items){
 						Map dataMap = (Map)map;
 						CheckBox cb = (CheckBox)dataMap.get("cb");
 						if(cb.isSelected()){
 							//如果复选框被选中，那么就让此行删除
 							tableService.normalDelete(dataMap, tableName);
 							count[0]++ ;
 						}
 					}
 				 }catch(Exception e){
 					 e.printStackTrace();
 					 Platform.runLater(new Runnable() {
 						@Override public void run() {
 							 DialogTools.error(stage, "错误", "出错了","批量删除出错");
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
 							if(count[0] > 0){
 								TableController.this.selectTable(HandlerModel.BATHDELETE ,count[0]);
 							}
 						}
 					 });
 				 }
 				}).start();
		}
	 }
	 /**批量级联删除*/
	 public void processCascadeDelete(ActionEvent e){
		 Integer[] count = new Integer[1];
		 count[0] = 0;
 		ObservableList items = tableView.getItems();
 		if(items != null && items.size() > 0){
 			 new Thread(new Runnable() {
 				 @Override public void run() {
 				 Platform.runLater(new Runnable() {
 					@Override public void run() {
 						allButtonsDisabled(true);
 					}
 				 });
 				 try{
 					for(Object map : items){
 						Map dataMap = (Map)map;
 						CheckBox cb = (CheckBox)dataMap.get("cb");
 						if(cb.isSelected()){
 							//如果复选框被选中，那么就让此行删除
 							tableService.cascadeDelete(dataMap, tableName);
 							count[0]++ ;
 						}
 					}
 				 }catch(Exception e){
 					 e.printStackTrace();
 					 Platform.runLater(new Runnable() {
 						@Override public void run() {
 							 DialogTools.error(stage, "错误", "出错了","批量级联删除出错");
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
 							if(count[0] > 0){
 								TableController.this.selectTable(HandlerModel.BATHDELETE ,count[0]);
 							}
 						}
 					 });
 				 }
 				}).start();
		}

	 }

	 /**刷新*/
	 public void processRefresh(ActionEvent e){
		 page = 1;
		 selectCondition = " ";
		 selectTable(HandlerModel.UNKOWN);
	 }
	 /**首页*/
	 public void processFirstPage(ActionEvent e){
		 page = 1;
		 selectTable(HandlerModel.UNKOWN);
	 }
	 //上一页
	 public void processFrontPage(ActionEvent e){
		 page = page - 1;
		 selectTable(HandlerModel.UNKOWN);
	 }
	 //下一页
	 public void processNextPage(ActionEvent e){
		 page = page + 1;
		 selectTable(HandlerModel.UNKOWN);
	 }
	 //尾页
	 public void processLastPage(ActionEvent e){
		 page = totalPage;
		 selectTable(HandlerModel.UNKOWN);
	 }

	 //页面跳转
	 public void processTurnPage(ActionEvent ex){
		 String message = "";
		 String  turnPageStr = turnPageTextField.getText();
		 Integer turnPage = 0;
		 try{
			 turnPage = Integer.parseInt(turnPageStr);
		 }catch(Exception e){
			 e.printStackTrace();
			 message = "输入页数不合法";
			 DialogTools.warn(stage, "警告", "警告!", message);
			 return;
		 }
		 if(turnPage <= 0 || turnPage > totalPage){
			 message = "跳转页数必须大于0且不大于最大页数";
			 DialogTools.warn(stage, "警告", "警告!", message);
			 return;
		 }
		 page = turnPage;
		 selectTable(HandlerModel.UNKOWN);
	 }
	 /**显示/隐藏中文列*/
	 public void processViewCh(ActionEvent e){
		 if("隐藏中文列".equals(viewChButton.getText())){
			 viewChButton.setText("显示中文列");
			 viewColFlag = false;
		 }else{
			 viewChButton.setText("隐藏中文列");
			 viewColFlag = true;
		 }
		 selectTable(HandlerModel.UNKOWN);
	 }
	 /**查询*/
	 public void processSelect(ActionEvent e){
		 Stage stage = new Stage();
		 stage.setTitle("查询");
		 SpringFxmlLoader sfl = new SpringFxmlLoader();
		 try {
			 AnchorPane root = (AnchorPane) sfl.springLoad("view/SelectView.fxml", Main.class);
			 SelectController  controller = sfl.getController();
			 controller.setStage(stage);
			 controller.setTableName(tableName);
			 controller.setCondition(sqlCondition);
			 controller.setTc(TableController.this);
			 controller.checkNormalSelectEnabled();
			 controller.initComoboBox();
			 stage.initModality(Modality.APPLICATION_MODAL);
			 Scene scene = new Scene(root);
			 stage.setScene(scene);
			 stage.setResizable(false);
			 stage.show();
		 } catch (Exception e1) {
			e1.printStackTrace();
			DialogTools.error(stage,"错误", "出错了", "生成查询页面出错!");
		}
	 }
}
