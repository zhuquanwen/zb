package com.iscas.zb.model;

import java.util.HashMap;
import java.util.Map;

import com.iscas.zb.service.TableEditService;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
*@date: 2017年2月28日
*@author: zhuquanwen
*@desc: 表格可编辑单元格
*/
@SuppressWarnings(value = {"unused","rawtypes"})
public class EditTableCell<T,R> extends TableCell<Map<String,Object>, Object> {

	private Stage stage;
	private ObservableList obList;
	private String tableName;
	private Map<Map<String,String>,String> unEditMap;
	private TableEditService tableEditService;
	private Map<String,Object> updateMap;
	private Map<String,TextField> updateChMap;
	private boolean insertFlag;
	public EditTableCell(Stage stage, ObservableList obList, String tableName,
			Map<Map<String, String>, String> unEditMap,TableEditService tableEditService
			,Map<String,Object> updateMap,Map<String,TextField> updateChMap
			,boolean insertFlag) {
		super();

		this.stage = stage;
		this.obList = obList;
		this.tableName = tableName;
		this.unEditMap = unEditMap;
		this.tableEditService = tableEditService;
		this.updateMap = updateMap;
		this.updateChMap = updateChMap;
		this.insertFlag = insertFlag;


	}
	@Override
	protected void updateItem(Object item, boolean empty) {
		if (item == getItem()) return;

        super.updateItem(item, empty);
        if("属性".equals(this.getTableColumn().getText())){
			//this.setStyle("-fx-background-color: #aaaaaa; -fx-table-cell-border-color: black;");
        	//this.setStyle(" -fx-text-fill: black;");
		}else{
			 //如果为配置的不可编辑列，也不可编辑
			if(!insertFlag){
				Integer index = this.getIndex();
				if(index >= 0 ){
					Map map = (Map)obList.get(index);
					Map<String,String> mapx = new HashMap<String,String>();
					mapx.put(tableName, (String)map.get("colName"));
					if(unEditMap.get(mapx) != null){
						this.setStyle(" -fx-text-fill: #ADADAD;");
					}
				}
			}

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
	public void startEdit1(Object item){
		super.startEdit();
		updateItem(item, true);
	}
}
