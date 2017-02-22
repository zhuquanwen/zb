package com.iscas.zb.service;


import org.springframework.stereotype.Service;

@Service
public class MainService {
//	 private volatile static MainService singleton;
//	 private MainService (){}
//	 public static MainService getSingleton() {
//		 if (singleton == null) {
//		     synchronized (MenuService.class) {
//		     if (singleton == null) {
//		         singleton = new MainService();
//		     }
//		     }
//		 }
//		 return singleton;
//	 }
	public void test(){
		System.out.println(11111);
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
