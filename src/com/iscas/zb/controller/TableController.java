package com.iscas.zb.controller;


import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.GroupLayout.Alignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.iscas.zb.Main;
import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.ChildRelation;
import com.iscas.zb.model.HandlerModel;
import com.iscas.zb.model.jaxb.JTable;
import com.iscas.zb.service.TableEditService;
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
import javafx.scene.control.SelectionMode;
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
@Scope("prototype")
@SuppressWarnings("unchecked")
public class TableController {
	//�������
	@FXML
	private Button addButton;
	//���༭
	@FXML
	private Button editButton;
	//���//����ɾ��
	@FXML
	private Button deleteButton;
	//���ˢ��
	@FXML
	private Button refreshButton;

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
	@FXML
	private Hyperlink turnPageLink;
	//������hbox1
	@FXML
	private HBox hBox1;
	//������hbox2
	@FXML
	private HBox hBox2;
	//������hbox3
	@FXML
	private HBox hBox3;
	//ȫѡ
	@FXML
	private Button checkBoxSelectButton;
	//��������ɾ��
	@FXML
	private Button cascadeDeleteButton;


	private Stage stage;
	@Autowired(required=true)
	private TableService tableService;
	/**�������*/
	private String tableName;
	/**��ǰҳ*/
	private Integer page = 1;
	/**ÿҳ��Ŀ*/
	private Integer pageSize = Integer.valueOf(StaticData.default_table_page_size);
	/**��ҳ��*/
	private Integer totalPage = 1;
	/**���Ҽ��˵�*/
	private ContextMenu rowContextMenu;

	/**�������Ϣ*/
	private LinkedHashMap<String,Map<String,String>> colInfoMap = null;
	/**���е���Ӣ������Ϣ*/
	private LinkedHashMap<String,String> colEnChMap = null;

	/**��������ַ�����Map��ʽ*/
	private Map<String,String> disColMap = null;

	private String sqlCondition = " where 1=1 ";
	private String selectCondition = " ";
	private Boolean childFlag = false;
	private ObservableList obList = null;

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
	/**��ʼ��������ť*/
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
							DialogTools.warn(stage, "����", "����!", "��ѡ��һ����¼!");
							return;
						}

						Map  mapx = (Map) tableView.getItems().get(index);
						//DialogTools.info("��ʾ", "�����ӱ�--������--");
                		//��ת���ӱ��ҳ��
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
                            			"-��������" + tableName ;
                            stage.setTitle(title);
                            stage.showAndWait();

						} catch (Exception ex) {
							ex.printStackTrace();
							DialogTools.error("����", "������!", "��ѯ�����ݳ���!");
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

	 /**�༭����*/
	 private void editRow(){
		//����༭
		//��ǰѡ�е���
		Integer index = tableView.getSelectionModel().getSelectedIndex();
		if(index < 0 ){
			DialogTools.error("����", "������!", "��ѡ��һ�н��б༭!");
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

             stage.show();
             stage.setTitle("�б�༭");
			} catch (Exception e) {
				e.printStackTrace();
				DialogTools.error("����", "������!", "���༭����!");
			}
	 }
	 /**��ͨɾ��*/
	 private void normalDelete(){
		 //��ͨɾ������ɾ���ӱ���Ϣ�����ӱ��Ӧ��Ϣ��Ϊnull
		 Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
 				.getSelectedItem();
		 if(map == null || map.size()  <= 0){
			 DialogTools.warn(stage,"����", "����","��ѡ��һ��Ҫɾ���ļ�¼!" );
		 }
		 tableService.normalDelete(map,tableName);
		 this.selectTable(HandlerModel.DELETE);

	 }
	 /**����ɾ��*/
	 private void cascadeDelete() {
		//����ɾ�������ӱ��Ӧ�ж�ɾ��
		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
	 				.getSelectedItem();
		if(map == null || map.size()  <= 0){
			DialogTools.warn(stage,"����", "����","��ѡ��һ��Ҫɾ���ļ�¼!" );
		}
		tableService.cascadeDelete(map,tableName);
		this.selectTable(HandlerModel.DELETE);
	 }

	/**
	 * ��ʼ���Ҽ��˵�
	 */
	private void initContextMenu() {

		MenuItem mi1 = new MenuItem("�༭");
		MenuItem mi2 = new MenuItem("ɾ��");
		MenuItem mi4 = new MenuItem("����ɾ��");
		MenuItem mi3 = new MenuItem("�и���");
		mi1.setOnAction(event -> {
			editRow();
		});
		mi2.setOnAction(event -> {
			normalDelete();
		});
		mi3.setOnAction(event -> {
			//����༭
			//��ǰѡ�е���
    		Map<String,Object> map = (Map<String,Object>)tableView.getSelectionModel()
    				.getSelectedItem();
    		DialogTools.info("��Ϣ", "�Ҽ������и���--������......");
		});
		mi4.setOnAction(event -> {
			cascadeDelete();
		});
		rowContextMenu = new ContextMenu(mi1,mi2,mi4,mi3);

	}



	/**
	 * ��ʼ��pageSize
	 */

	private  void initPageSizeCombobox(){
		pageSizeCombobox.setItems(tableService.getPageSizeList());
		pageSizeCombobox.setValue(StaticData.default_table_page_size);
		pageSizeCombobox.setOnAction((Event event) -> {
			//DialogTools.info("��ʾ", "ģ�����ҳ���¼�--������......");
			String value = (String) pageSizeCombobox.getValue();
			if("ȫ��".equals(value)){
				pageSize = tableService.getTotal(tableName, sqlCondition);
			}else{
				pageSize = Integer.valueOf(value);
			}
			selectTable(HandlerModel.UNKOWN);

		});
	}

	public void selectTable(String sqlCondition){
		this.sqlCondition = sqlCondition;
		if(sqlCondition == null){
			sqlCondition = " where 1 = 1 ";
		}
		selectTable(HandlerModel.UNKOWN);
	}
	public void selectTable(HandlerModel hm, Integer count){
		switch (hm) {
		case BATHDELETE:
		{
			//����ɾ��
			if(count >= pageSize){
				page = page > 1 ? page -1 : 1;
			}
			selectTable(HandlerModel.UNKOWN);
		}
		default:
			break;
		}
	}


	/**��ʼ�����*/
	@SuppressWarnings("rawtypes")
	public void selectTable(HandlerModel hm){
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

		tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		String condition = sqlCondition + selectCondition;
		tableView.getColumns().clear();
		//�������Ϣ
		colInfoMap = tableService.getColInfosByTableName(tableName,TableController.this);

		//�ȴ���checkbox��
		TableColumn<Map<String,Object>, Object> col1 = new
	    		TableColumn<Map<String,Object>, Object>();
	    col1.setCellFactory(new TaskCellFactory());
	    col1.setCellValueFactory(new MapValueFactory("cb"));

	    tableView.getColumns().add(col1);

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
		//���totalPage
		//totalPage = tableService.getTotalPage(tableName,pageSize," where 1 = 1 ");

		//�������
		obList = tableService.getTableData(tableName,page,pageSize,colInfoMap,TableController.this, condition  );
		tableView.setItems(obList);
		if(obList != null && obList.size() > 0){
			tableView.getSelectionModel().select(0);
		}

		//����Ҽ��˵�
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
		//�ָ���ʼ������
		firstPageLink.setDisable(false);
		lastPageLink.setDisable(false);
		frontPageLink.setDisable(false);
		nextPageLink.setDisable(false);

		//���е�ҳ������
		Integer total = tableService.getTotal(tableName, condition);
		totalPage = total%pageSize == 0 ? total/pageSize : total/pageSize + 1;
		totalPageLabel.setText("��" + totalPage + "ҳ");
		//�ڼ�ҳ����
		currentPageLabel.setText("��" + page + "ҳ");

		//��ҳ��ť����
		if(page == 1){
			firstPageLink.setDisable(true);
		}
		//ĩҳ��ť����
		if(page == totalPage){
			lastPageLink.setDisable(true);
		}
		//��һҳ����
		if(page <= 1){
			frontPageLink.setDisable(true);
		}
		//��һҳ����
		if(page >= totalPage){
			nextPageLink.setDisable(true);
		}

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
		        		editRow();
		        	}

		        });
		        cell.setAlignment(Pos.CENTER);
		        return cell;
		    }
		}
	 /**����*/
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
          stage.setTitle("��������");
			} catch (Exception ex) {
				ex.printStackTrace();
				DialogTools.error("����", "������!", "�������ݳ���!");
			}
	 }
	 /**ȫѡ*/
	 public void processCheckBoxSelect(ActionEvent e){
		 String text = checkBoxSelectButton.getText();
		 if("ȫѡ".equals(text)){
			 ObservableList obList = tableView.getItems();
			 if(obList != null && obList.size() > 0){
				 obList.forEach(map -> {
					 Map dataMap = (Map)map;
					 CheckBox cb = (CheckBox)dataMap.get("cb");
					 cb.setSelected(true);
				 });
				 checkBoxSelectButton.setText("��ѡ");
			 }

		 }else{
			 ObservableList obList = tableView.getItems();
			 if(obList != null && obList.size() > 0){
				 obList.forEach(map -> {
					 Map dataMap = (Map)map;
					 CheckBox cb = (CheckBox)dataMap.get("cb");
					 cb.setSelected(false);
				 });
				 checkBoxSelectButton.setText("ȫѡ");
			 }
		 }

	 }
	 /**����ɾ��*/
	 public void processDelete(ActionEvent e){
		 Integer count = 0;
 		ObservableList items = tableView.getItems();
 		if(items != null && items.size() > 0){
			for(Object map : items){
				Map dataMap = (Map)map;
				CheckBox cb = (CheckBox)dataMap.get("cb");
				if(cb.isSelected()){
					//�����ѡ��ѡ�У���ô���ô���ɾ��
					tableService.normalDelete(dataMap, tableName);
					count++ ;
				}
			}
			if(count > 0){
				this.selectTable(HandlerModel.BATHDELETE ,count);
			}

		}


	 }
	 /**��������ɾ��*/
	 public void processCascadeDelete(ActionEvent e){
		 Integer count = 0;
	 		ObservableList items = tableView.getItems();
	 		if(items != null && items.size() > 0){
				for(Object map : items){
					Map dataMap = (Map)map;
					CheckBox cb = (CheckBox)dataMap.get("cb");
					if(cb.isSelected()){
						//�����ѡ��ѡ�У���ô���ô���ɾ��
						tableService.cascadeDelete(dataMap, tableName);
						count++ ;
					}
				}
				if(count > 0){
					this.selectTable(HandlerModel.BATHDELETE ,count);
				}

			}
	 }

	 /**ˢ��*/
	 public void processRefresh(ActionEvent e){
		 selectTable(HandlerModel.UNKOWN);
	 }
	 /**��ҳ*/
	 public void processFirstPage(ActionEvent e){
		 page = 1;
		 selectTable(HandlerModel.UNKOWN);
	 }
	 //��һҳ
	 public void processFrontPage(ActionEvent e){
		 page = page - 1;
		 selectTable(HandlerModel.UNKOWN);
	 }
	 //��һҳ
	 public void processNextPage(ActionEvent e){
		 page = page + 1;
		 selectTable(HandlerModel.UNKOWN);
	 }
	 //βҳ
	 public void processLastPage(ActionEvent e){
		 page = totalPage;
		 selectTable(HandlerModel.UNKOWN);
	 }

	 //ҳ����ת
	 public void processTurnPage(ActionEvent ex){
		 String message = "";
		 String  turnPageStr = turnPageTextField.getText();
		 Integer turnPage = 0;
		 try{
			 turnPage = Integer.parseInt(turnPageStr);
		 }catch(Exception e){
			 e.printStackTrace();
			 message = "����ҳ�����Ϸ�";
			 DialogTools.warn(stage, "����", "����!", message);
			 return;
		 }
		 if(turnPage <= 0 || turnPage > totalPage){
			 message = "��תҳ���������0�Ҳ��������ҳ��";
			 DialogTools.warn(stage, "����", "����!", message);
			 return;
		 }
		 page = turnPage;
		 selectTable(HandlerModel.UNKOWN);
	 }
}
