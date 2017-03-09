package com.iscas.zb.model.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
*@date: 2017年2月22日
*@author: zhuquanwen
*@desc: jaxb 数据库中varchar2或char类型但是不需要显示英文列的配置disponse_col标签，
*		xml对应的模型
*/

@XmlRootElement(name="disponse_col")
public class DisColTrans {
	private List<Dcol> dcols;

	public List<Dcol> getDcols() {
		return dcols;
	}
	@XmlElement(name="dcol")
	public void setDcols(List<Dcol> dcols) {
		this.dcols = dcols;
	}

}
