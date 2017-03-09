package com.iscas.zb.model;
/**
*@date: 2017��3��6��
*@author: zhuquanwen
*@desc: �˵�
*/
public enum TableMenu {
	UNKOWN("δ֪",-1),
	JMCS("��ģ����",0),
	STZB("ʵ������",1),
	SSLB("װ����ʩ���",2),
	SSST("װ����ʩʵ��",3),
	YXSJ("ԭ������",4),
	HSXS("����ϵ��",5),
	ZYDZ("��Ӣ����",6)
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
