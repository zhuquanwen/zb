package com.iscas.zb.model;
/**
*@date: 2017年3月6日
*@author: zhuquanwen
*@desc: 菜单
*/
public enum TableMenu {
	UNKOWN("未知",-1),
	JMCS("建模参数",0),
	STZB("实体整编",1),
	SSLB("装备设施类别",2),
	SSST("装备设施实体",3),
	YXSJ("原型数据",4),
	HSXS("毁伤系数",5),
	ZYDZ("中英对照",6)
	;
	private String name;
	private int index;
	private TableMenu(String name, int index) {
		this.name = name;
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public static  TableMenu getByIndex(int index){
		TableMenu tm = TableMenu.UNKOWN;
		for (TableMenu tm0 : TableMenu.values()) {
			if(index == tm0.getIndex()){
				return tm0;
			}
		}
		return tm;
	}
}
