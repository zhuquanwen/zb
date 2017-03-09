package com.iscas.zb.tools;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.ApplicationContext;

import com.iscas.zb.data.StaticData;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

/**
*@date: 2017��2��22��
*@author: zhuquanwen
*@desc: �Զ���fxmlloader,ʹ֮��spring����
*/
public class SpringFxmlLoader extends FXMLLoader{

	 private static  ApplicationContext applicationContext = null;

	 @SuppressWarnings("rawtypes")
	public  Object springLoad(String url,Class cl) throws Exception{
		 applicationContext = StaticData.context;
		  try  {
			  InputStream fxmlStream = cl.
					    getResourceAsStream(url);
			   FXMLLoader loader = this;
			   loader.setControllerFactory(new Callback<Class<?>, Object>() {
			    @Override
			    public Object call(Class<?> clazz) {
			     return applicationContext.getBean(clazz);
			    }
			   });
			   return loader.load(fxmlStream);
		  } catch (IOException ioException) {
		   throw new RuntimeException(ioException);
		  }
	 }
	}
