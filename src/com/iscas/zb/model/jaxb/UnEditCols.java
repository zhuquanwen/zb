package com.iscas.zb.model.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
*@date: 2017年2月27日
*@author: zhuquanwen
*@desc: XML对应的模型
*/
@XmlRootElement(name="col_un_edit")
public class UnEditCols {
	private List<UnEditCol> uecs = null;

	public List<UnEditCol> getUecs() {
		return uecs;
	}
	@XmlElement(name="col")
	public void setUecs(List<UnEditCol> uecs) {
		this.uecs = uecs;
	}

}
