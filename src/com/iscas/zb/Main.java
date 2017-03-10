package com.iscas.zb;

import java.sql.SQLException;

import com.iscas.zb.DisplayShelfSample.DisplayShelf;
import com.iscas.zb.controller.MainController;
import com.iscas.zb.data.StaticData;
import com.iscas.zb.resource.ClassLoad;
import com.iscas.zb.tools.DialogTools;
import com.iscas.zb.tools.SpringFxmlLoader;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main  {

	public  void start(Stage primaryStage,String ip) {
		try {


			AnchorPane root = new AnchorPane();

			SpringFxmlLoader loader = new SpringFxmlLoader();
            //loader.setLocation(Main.class.getResource("view/MainView.fxml"));
            root = (AnchorPane) loader.springLoad("view/MainView.fxml", Main.class);
            MainController controller = loader.getController();
            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            String title = "数据整编工具";
            if(ip != null){
            	title += "[IP:" + ip + "]";
            }

            primaryStage.setTitle(title);
            primaryStage.show();
            primaryStage.setOnCloseRequest((WindowEvent event) -> {
            	//关闭的时候系统退出
            	if(StaticData.conn != null){
            		try {
						StaticData.conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
            	}
            	System.exit(0);
            });

             // load images
            Image[] images = new Image[7];
            for (int i = 0; i < 7; i++) {
            	String fileName = "menu"+(i+1)+".jpg";
            	if(i == 6){
            		fileName = "menu"+(i+1)+".png";
            	}
                images[i] = new Image( ClassLoad.class.getResource(fileName).toExternalForm(),false);
            }
            // create display shelf
            DisplayShelf displayShelf = new DisplayShelf(images,controller);
            displayShelf.setPrefSize(700, 290);
            root.getChildren().add(displayShelf);

//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			DialogTools.error(primaryStage,"错误", "出错了", "进入主页面出错");
		}
	}


}
