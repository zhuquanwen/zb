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
	//�������
	@FXML
	private Button addButton;
	//���༭
	@FXML
	private Button editButton;
	//���ɾ��
	@FXML
	private Button deleteButton;
	//�����ʾ����
	@FXML
	private TableView tableView;
	//ÿҳ��ʾ������
	@FXML
	private ComboBox pageSizeCombobox;
	//��ҳ
	@FXML
	private Hyperlink firstPageLink;
	//��һҳ
	@FXML
	private Hyperlink frontPageLink;
	//��ǰҳ
	@FXML
	private Label currentPageLabel;
	//��һҳ
	@FXML
	private Hyperlink nextPageLink;
	//βҳ
	@FXML
	private Hyperlink lastPageLink;
	//��ҳ��
	@FXML
	private Label totalPageLabel;
	//��ת��text
	@FXML
	private TextField turnPageTextField;
	//��תȷ��
	private Hyperlink turnPageLink;
	//������hbox1
	private HBox hBox1;
	//������hbox2
	private HBox hBox2;
	//������hbox3
	private HBox hBox3;

	private TableService tableService;
	/**�������*/
	private String tableName;
	/**��ǰҳ*/
	private Integer page = 1;
	/**ÿҳ��Ŀ*/
	private Integer pageSize = Integer.valueOf(StaticData.default_table_page_size);
	/**��ҳ��*/
	private Integer totalPage;
	/**���Ҽ��˵�*/
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
	 * ��ʼ���Ҽ��˵�
	 */
	private void initContextMenu() {

		MenuItem mi1 = new MenuItem("�༭");
		MenuItem mi2 = new MenuItem("ɾ��");
		MenuItem mi3 = new MenuItem("�и���");
		mi1.setOnAction(event -> {
			//����༭
			//��ǰѡ�е���
    		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
    				.getSelectedItem();
    		DialogTools.info("��Ϣ", "�Ҽ������б༭--������......");
		});
		mi2.setOnAction(event -> {
			//����༭
			//��ǰѡ�е���
    		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
    				.getSelectedItem();
    		DialogTools.info("��Ϣ", "�Ҽ�����ɾ��--������......");
		});
		mi3.setOnAction(event -> {
			//����༭
			//��ǰѡ�е���
    		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
    				.getSelectedItem();
    		DialogTools.info("��Ϣ", "�Ҽ������и���--������......");
		});
		rowContextMenu = new ContextMenu(mi1,mi2,mi3);

	}

	/**
	 * ��ʼ��pageSize
	 */

	private  void initPageSizeCombobox(){
		pageSizeCombobox.setItems(tableService.getPageSizeList());
		pageSizeCombobox.setValue(StaticData.default_table_page_size);
		pageSizeCombobox.setOnAction((Event event) -> {
			DialogTools.info("��ʾ", "ģ�����ҳ���¼�--������......");
		});
	}
	/**��ʼ�����*/
	@SuppressWarnings("rawtypes")
	private void initTable(){
		tableView.getColumns().clear();
		//�ȴ�����
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
		//�������
		tableView.setItems(tableService.getTableData(tableName,page,pageSize));

		//����Ҽ��˵�
		tableView.setRowFactory(new Callback<TableView, TableRow>() {
	           @Override
	           public TableRow call(TableView param) {
	               return new TableRowControl();
	           }
	       });
	}

	/**���Ҽ��˵���*/
	private class TableRowControl extends TableRow {

        public TableRowControl() {
            super();
            this.setContextMenu(rowContextMenu);


        }
    }


	/**�༭�ڲ���*/
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
		        //˫������༭
		        cell.setOnMouseClicked(event -> {
		        	if(event.getClickCount() == 2){
		        		//��ǰѡ�е���
		        		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
		        				.getSelectedItem();
		        		DialogTools.info("��Ϣ", "˫�������б༭--������......");
		        	}
		        });
		        return cell;
		    }
		}
	 /**����*/
	 public void processAdd(ActionEvent e){
		//��ǰѡ�е���
 		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
 				.getSelectedItem();
 		DialogTools.info("��Ϣ", "���뵥������--������......");
	 }
	 /**�༭*/
	 public void processEdit(ActionEvent e){
		//��ǰѡ�е���
 		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
 				.getSelectedItem();
 		DialogTools.info("��Ϣ", "���뵥���༭--������......");
	 }
	 /**ɾ��*/
	 public void processDelete(ActionEvent e){
		//��ǰѡ�е���
 		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
 				.getSelectedItem();
 		DialogTools.info("��Ϣ", "���뵥��ɾ��--������......");
	 }
}
