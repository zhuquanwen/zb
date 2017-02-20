package com.iscas.zb.service;

public class MenuService {
	 private volatile static MenuService singleton;
	 private MenuService (){}
	 public static MenuService getSingleton() {
		 if (singleton == null) {
		     synchronized (MenuService.class) {
		     if (singleton == null) {
		         singleton = new MenuService();
		     }
		     }
		 }
		 return singleton;
	 }


}
