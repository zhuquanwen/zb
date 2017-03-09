package com.iscas.zb.model.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
*@date: 2017��2��22��
*@author: zhuquanwen
*@desc: jaxb ���ݿ���varchar2��char���͵��ǲ���Ҫ��ʾӢ���е�����disponse_col��ǩ��
*		xml��Ӧ��ģ��
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
