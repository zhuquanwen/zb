package com.iscas.zb.tools;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.iscas.zb.model.TableMenu;
import com.iscas.zb.model.jaxb.JTableMenu;
import com.iscas.zb.model.jaxb.JTables;

public class JaxbTools {
	@SuppressWarnings({ "unchecked"})
	public static <T> T  xmlToObj(String path,T clazz) throws Exception{
		File file = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz.getClass());
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        T t = (T) jaxbUnmarshaller.unmarshal(file);
        return t;
	}
	public static void main(String[] args) {
		try {
			JTableMenu jtm =   xmlToObj("config/table_menu.xml",new JTableMenu());
			System.out.println(jtm.getTm().get(0).getTable().get(0).getName());


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
