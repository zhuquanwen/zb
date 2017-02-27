package com.iscas.zb.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iscas.zb.data.StaticData;
import com.iscas.zb.tools.EnToChTools;
import com.iscas.zb.tools.TableRelationTools;

/**
*@date: 2017年2月27日
*@author: zhuquanwen
*@desc: 这是一个类说明
*/
public class FirstScheduledJob extends QuartzJobBean {


    private EnToChTools enToChTools;
    private TableRelationTools tableRelationTools;

    @Override
    protected void executeInternal(JobExecutionContext arg0)
            throws JobExecutionException {
       // enToChTools = StaticData.context.getBean(EnToChTools.class);
        enToChTools.getColMap(true);
        enToChTools.getContentMap(true);
        enToChTools.getTableMap(true);
        System.out.println(tableRelationTools);
        tableRelationTools.getRelation(true);
    }

	public TableRelationTools getTableRelationTools() {
		return tableRelationTools;
	}

	public void setTableRelationTools(TableRelationTools tableRelationTools) {
		this.tableRelationTools = tableRelationTools;
	}

	public EnToChTools getEnToChTools() {
		return enToChTools;
	}

	public void setEnToChTools(EnToChTools enToChTools) {
		this.enToChTools = enToChTools;
	}


}
