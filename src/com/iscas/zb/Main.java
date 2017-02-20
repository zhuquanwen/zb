package com.iscas.zb;

import com.iscas.zb.DisplayShelfSample.DisplayShelf;
import com.iscas.zb.controller.MainController;
import com.iscas.zb.init.MenuInit;
import com.iscas.zb.resource.ClassLoad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	public static Stage stage;
	@Override
	public void start(Stage primaryStage) {
		try {
			//菜单初始化
			MenuInit.menuInit();

			AnchorPane root = new AnchorPane();

			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MainView.fxml"));
            root = (AnchorPane) loader.load();
            MainController controller = loader.getController();
            controller.setMainApp(this);
            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setTitle("数据整编工具");
            primaryStage.show();



             // load images
            Image[] images = new Image[6];
            for (int i = 0; i < 6; i++) {
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
