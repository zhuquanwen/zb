package com.iscas.zb.controller;


import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.iscas.zb.Main;
import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.jaxb.JTable;
import com.iscas.zb.service.TableService;
import com.iscas.zb.tools.DialogTools;

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
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author Administrator
 *
 */
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
	private Hyperlink turnPageLink;
	//关联表hbox1
	private HBox hBox1;
	//关联表hbox2
	private HBox hBox2;
	//关联表hbox3
	private HBox hBox3;

	private TableService tableService;
	/**表格名称*/
	private String tableName;
	/**当前页*/
	private Integer page = 1;
	/**每页条目*/
	private Integer pageSize = Integer.valueOf(StaticData.default_table_page_size);
	/**总页数*/
	private Integer totalPage;
	/**行右键菜单*/
	private ContextMenu rowContextMenu;

	 public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@FXML
	 private void initialize() {
		 tableService = TableService.getSingleton();
		 initPageSizeCombobox();
		 initContextMenu();
		 initTable();
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
			DialogTools.info("提示", "模拟更改页数事件--待开发......");
		});
	}
	/**初始化表格*/
	@SuppressWarnings("rawtypes")
	private void initTable(){
		tableView.getColumns().clear();
		//先创建列
		LinkedHashMap<String, String> colMap = tableService.getColNamesByTableName("test");
		Iterator<Entry<String, String>> iterator= colMap.entrySet().iterator();
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
		//添加内容
		tableView.setItems(tableService.getTableData(tableName,page,pageSize));

		//添加右键菜单
		tableView.setRowFactory(new Callback<TableView, TableRow>() {
	           @Override
	           public TableRow call(TableView param) {
	               return new TableRowControl();
	           }
	       });
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
}
