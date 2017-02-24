package com.iscas.zb.controller;


import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.iscas.zb.Main;
import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.ChildRelation;
import com.iscas.zb.model.jaxb.JTable;
import com.iscas.zb.service.TableService;
import com.iscas.zb.tools.DialogTools;
import com.iscas.zb.tools.EnToChTools;
import com.iscas.zb.tools.SpringFxmlLoader;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author Administrator
 *
 */
@Controller
@SuppressWarnings("unchecked")
public class TableController {
	//表格新增
	@FXML
	private Button addButton;
	//表格编辑
	@FXML
	private Button editButton;
	//表格删除
	@FXML
	private Button deleteButton;
	//表格刷新
	@FXML
	private Button refreshButton;

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
	private Stage stage;
	@Autowired(required=true)
	private TableService tableService;
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
                            controller.selectTable();

                            Scene scene = new Scene(root);
                            stage.setScene(scene);

                            stage.show();
                            Integer total = tableService.getTotal(childTableName,  " where 1 =1 ");
                            String title = childTableNameCh + "[" + total + "]";
                            stage.setTitle(title);
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

	/**
	 * 初始化右键菜单
	 */
	private void initContextMenu() {

		MenuItem mi1 = new MenuItem("编辑");
		MenuItem mi2 = new MenuItem("删除");
		MenuItem mi3 = new MenuItem("行复制");
		mi1.setOnAction(event -> {
			//进入编辑
			//当前选中的行
    		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
    				.getSelectedItem();
    		DialogTools.info("信息", "右键进入行编辑--待开发......");
		});
		mi2.setOnAction(event -> {
			//进入编辑
			//当前选中的行
    		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
    				.getSelectedItem();
    		DialogTools.info("信息", "右键进入删除--待开发......");
		});
		mi3.setOnAction(event -> {
			//进入编辑
			//当前选中的行
    		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
    				.getSelectedItem();
    		DialogTools.info("信息", "右键进入行复制--待开发......");
		});
		rowContextMenu = new ContextMenu(mi1,mi2,mi3);

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
			selectTable();

		});
	}

	public void selectTable(String sqlCondition){
		this.sqlCondition = sqlCondition;
		if(sqlCondition == null){
			sqlCondition = " where 1 = 1 ";
		}
		selectTable();
	}
	/**初始化表格*/
	@SuppressWarnings("rawtypes")
	public void selectTable(){
		String condition = sqlCondition + selectCondition;
		tableView.getColumns().clear();
		//获得列信息
		colInfoMap = tableService.getColInfosByTableName(tableName,TableController.this);

		//先创建列

		Iterator<Entry<String, String>> iterator= colEnChMap.entrySet().iterator();
		while(iterator.hasNext())
		{
		    Entry<String, String> entry = iterator.next();
		    String colEn = entry.getKey();
		    String colCh = entry.getValue();
		    TableColumn<Map<String,Object>, Object> col = new
		    		TableColumn<Map<String,Object>, Object>(colCh);
		    col.setCellFactory(new TaskCellFactory());
		    col.setCellValueFactory(new MapValueFactory(colEn));
		    tableView.getColumns().add(col);
		}
		//获得totalPage
		//totalPage = tableService.getTotalPage(tableName,pageSize," where 1 = 1 ");

		//添加内容
		tableView.setItems(tableService.getTableData(tableName,page,pageSize,colInfoMap,TableController.this, condition  ));

		//添加右键菜单
		tableView.setRowFactory(new Callback<TableView, TableRow>() {
	           @Override
	           public TableRow call(TableView param) {
	               return new TableRowControl();
	           }
	       });
		setDownMenu(condition);
		initRelationTable();
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
		        		//当前选中的行
		        		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
		        				.getSelectedItem();
		        		DialogTools.info("信息", "双击进入行编辑--待开发......");
		        	}
		        });
		        return cell;
		    }
		}
	 /**新增*/
	 public void processAdd(ActionEvent e){
		//当前选中的行
 		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
 				.getSelectedItem();
 		DialogTools.info("信息", "进入单击新增--待开发......");
	 }
	 /**编辑*/
	 public void processEdit(ActionEvent e){
		//当前选中的行
 		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
 				.getSelectedItem();
 		DialogTools.info("信息", "进入单击编辑--待开发......");
	 }
	 /**删除*/
	 public void processDelete(ActionEvent e){
		//当前选中的行
 		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
 				.getSelectedItem();
 		DialogTools.info("信息", "进入单击删除--待开发......");
	 }

	 /**刷新*/
	 public void processRefresh(ActionEvent e){
		 selectTable();
	 }
	 /**首页*/
	 public void processFirstPage(ActionEvent e){
		 page = 1;
		 selectTable();
	 }
	 //上一页
	 public void processFrontPage(ActionEvent e){
		 page = page - 1;
		 selectTable();
	 }
	 //下一页
	 public void processNextPage(ActionEvent e){
		 page = page + 1;
		 selectTable();
	 }
	 //尾页
	 public void processLastPage(ActionEvent e){
		 page = totalPage;
		 selectTable();
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
		 selectTable();
	 }
}
