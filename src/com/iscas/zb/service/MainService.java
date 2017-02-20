package com.iscas.zb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iscas.zb.model.TableMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;

public class MainService {
	 private volatile static MainService singleton;
	 private MainService (){}
	 public static MainService getSingleton() {
		 if (singleton == null) {
		     synchronized (MenuService.class) {
		     if (singleton == null) {
		         singleton = new MainService();
		     }
		     }
		 }
		 return singleton;
	 }


//	public ObservableList getTable(Label label, int index){
//		 TreeItem<String> rootItem = new TreeItem<String> ("Inbox");
//	     rootItem.setExpanded(true);
//	     for (int i = 1; i < 6; i++) {
//	         TreeItem<String> item = new TreeItem<String> ("Message" + i);
//	         rootItem.getChildren().add(item);
//	     }
//		 return obList;
//	}
}
