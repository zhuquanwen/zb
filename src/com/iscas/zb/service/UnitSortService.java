package com.iscas.zb.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iscas.zb.dao.UnEntityDao;
import com.iscas.zb.data.SqlData;
import com.iscas.zb.model.Unit;
import com.iscas.zb.tools.CommonTools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
*@date: 2017��3��14��
*@author: zhuquanwen
*@desc: ��������service
*/
@Service
@Transactional
public class UnitSortService {
	@Autowired
	private UnEntityDao unEntityDao;
	public ObservableList getSort(Map<String, TextField> updateMap, Unit unit) {
		ObservableList obList = FXCollections.observableArrayList();
		if(unit.getUnits() != null){
			for (int i = 0; i < unit.getUnits().size(); i++) {
				Unit subUnit = unit.getUnits().get(i);
				String key = subUnit.getNameCh();
				String keyEn = subUnit.getShortName();
				Integer order = subUnit.getOrder();
				TextField tf = new TextField();
				if(order == 9999){
					tf.setText("");
				}else{
					tf.setText(order.toString());
				}
				Map map = new HashMap();
				map.put("key", key);
				map.put("value", tf);
				obList.add(map);
				updateMap.put(keyEn, tf);
			}
		}
		return obList;
	}

	/**�ύ�����޸�
	 * @throws Exception */
	public void commit(Map<String, TextField> updateMap, Unit unit,TreeItem<Unit> treeView) throws Exception {
		String shortName = unit.getShortName();

		List<Unit> subUnits = unit.getUnits();

		for (int i = 0; i < subUnits.size(); i++) {
			Unit subUnit = subUnits.get(i);
			String subShortName = subUnit.getShortName();
			TextField tf = updateMap.get(subShortName);
			String text = tf.getText();

			try{
				if(text == null || "".equals(text)){
					//���Ϊ�գ����ݿ����м�¼��ɾ����û�о�ʲô������
					String sql = SqlData.getTreeSortSql;
					sql = sql.replace("@condition", shortName == null ? " is null " : " = '" + shortName + "'");
					sql = sql.replace("@shortName", subShortName);
					List<Map> maps = CommonTools.getDBList(unEntityDao, sql);
					Integer sum = ((BigDecimal) maps.get(0).get("SUM")).intValue();
					if(maps != null && maps.size() >0 && sum == 1){
						String sql1 = SqlData.deleteSortSql;
						sql1 = sql1.replace("@condition", shortName == null ? " is null " : " = '" + shortName + "'");
						sql1 = sql1.replace("@shortName", subShortName);
						CommonTools.getDBList(unEntityDao, sql1);
					}
					//�޸�subUnit order ����
					subUnit.setOrder(9999);

				}else{
					Integer order = Integer.parseInt(text);
					//�����Ϊ����Ϊ��ֵ���ͣ����ݿ����м�¼���޸ģ�û�о�����
					String sql = SqlData.getTreeSortSql;
					sql = sql.replace("@condition", shortName == null ? " is null " : " = '" + shortName + "'");
					sql = sql.replace("@shortName", subShortName);
					List<Map> maps = CommonTools.getDBList(unEntityDao, sql);
					Integer sum = ((BigDecimal) maps.get(0).get("SUM")).intValue();
					if(maps != null && maps.size() > 0 && sum == 1 ){
						String sql1 = SqlData.updateSortSql;
						sql1 = sql1.replace("@condition", shortName == null ? " is null " : " = '" + shortName + "'");
						sql1 = sql1.replace("@shortName", subShortName);
						sql1 = sql.replace("@order", order.toString());
						CommonTools.getDBList(unEntityDao, sql1);
					}else{
						String sql2 = SqlData.insertSortSql;
						sql2 = sql2.replace("@v1", shortName == null ? "null" : "'" + shortName + "'");
						sql2 = sql2.replace("@v2", "'" + subShortName + "'");
						sql2 = sql2.replace("@v3", order.toString());
						CommonTools.getDBList(unEntityDao, sql2);
					}
					//�޸�subUnit order ����
					subUnit.setOrder(order);
				}

			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("���������ʽ����ȷ");
			}
		}

		//�޸�unit��˳��
		Collections.sort(subUnits, new Comparator<Unit>() {

			@Override
			public int compare(Unit o1, Unit o2) {
				return o1.getOrder() - o2.getOrder();
			}
		});
		ObservableList<TreeItem<Unit>> tis = treeView.getChildren();
		Collections.sort(tis, new Comparator<TreeItem<Unit>>() {

			@Override
			public int compare(TreeItem<Unit> o1, TreeItem<Unit> o2) {
				Unit u1 = o1.getValue();
				Unit u2 = o2.getValue();
				return u1.getOrder() - u2.getOrder();
			}
		});

	}

}
