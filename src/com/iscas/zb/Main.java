package com.iscas.zb;

import java.sql.SQLException;

import org.controlsfx.dialog.Dialogs;

import com.iscas.zb.DisplayShelfSample.DisplayShelf;
import com.iscas.zb.controller.MainController;
import com.iscas.zb.data.StaticData;
import com.iscas.zb.init.JdbcInit;
import com.iscas.zb.init.MenuInit;
import com.iscas.zb.init.SpringInit;
import com.iscas.zb.init.TableRelationInit;
import com.iscas.zb.init.TranslateInit;
import com.iscas.zb.init.XmlToObjectInit;
import com.iscas.zb.resource.ClassLoad;
import com.iscas.zb.tools.SpringFxmlLoader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {
	public static Stage stage;
	@Override
	public void start(Stage primaryStage) {
		try {
			//spring 初始化
			SpringInit.initSpring();

			//菜单初始化
			MenuInit.menuInit();
			//XML jaxb初始化
			XmlToObjectInit.xmlToObjectInit();
			//数据库连接初始化
			//JdbcInit.connectionInit();
			//翻译Map初始化
			TranslateInit.translateInit();
			TableRelationInit.tableRelationInit();

			AnchorPane root = new AnchorPane();

			SpringFxmlLoader loader = new SpringFxmlLoader();
            //loader.setLocation(Main.class.getResource("view/MainView.fxml"));
            root = (AnchorPane) loader.springLoad("view/MainView.fxml", Main.class);
            MainController controller = loader.getController();
            controller.setMainApp(this);
            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setTitle("数据整编工具");
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
                images[i] = new Image( ClassLoad.class.getResource("menu"+(i+1)+".jpg").toExternalForm(),false);
            }
            // create display shelf
            DisplayShelf displayShelf = new DisplayShelf(images,controller);
            displayShelf.setPrefSize(700, 290);
            root.getChildren().add(displayShelf);

            stage = primaryStage;
//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();

		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
