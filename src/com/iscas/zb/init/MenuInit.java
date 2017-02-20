package com.iscas.zb.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iscas.zb.data.StaticData;
import com.iscas.zb.model.TableMenu;
import com.iscas.zb.model.jaxb.JTable;
import com.iscas.zb.model.jaxb.JTableMenu;
import com.iscas.zb.model.jaxb.JTables;
import com.iscas.zb.tools.JaxbTools;

import javafx.scene.control.TreeItem;

public class MenuInit {
	public static void menuInit() throws Exception{
		JTableMenu tm = JaxbTools.xmlToObj(StaticData.config_table_menu, new JTableMenu());
		Map<Integer,TreeItem<JTable>> treeItemMap = new HashMap<Integer,TreeItem<JTable>>();
		StaticData.treeItemMap = treeItemMap;
		if(tm != null){
			tm.getTm().forEach(tables -> {
				Integer index = tables.getIndex();
				List<JTable> ts = tables.getTable();
				TableMenu tmem = TableMenu.getByIndex(index);
				JTable j = new JTable();
				j.setName(tmem.getName());
				TreeItem<JTable> rootItem = new TreeItem<JTable> (j);
			    treeItemMap.put(index, rootItem);
				rootItem.setExpanded(true);
			    if(ts != null){

			    	for (JTable table : ts) {
			    		TreeItem<JTable> firstMenu = new TreeItem<JTable>(table);
			    		rootItem.getChildren().add(firstMenu);
			    		if(table.getTbs() != null && table.getTbs().size() > 0){
			    			for (JTables jTables : table.getTbs()) {
			    				for (JTable tb : jTables.getTable()) {
			    					TreeItem<JTable> sencondMenu = new TreeItem<JTable>(tb);
			    					firstMenu.getChildren().add(sencondMenu);
								}
							}

			    		}
					}


			    }
			});
		}




	}
}
