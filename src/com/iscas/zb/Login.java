/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iscas.zb;

import org.apache.log4j.Logger;

import com.iscas.zb.controller.LoginController;
import com.iscas.zb.init.SpringInit;
import com.iscas.zb.init.StaticDataInit;
import com.iscas.zb.tools.SpringFxmlLoader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
*@date: 2017年2月24日
*@author: zhuquanwen
*@desc: 程序入口
*/
public class Login extends Application {
	Logger log = Logger.getLogger(Login.class);
    @Override
    public void start(Stage stage) throws Exception {
    	StaticDataInit.init();
    	SpringInit.initSpring();
    	SpringFxmlLoader loader = new SpringFxmlLoader();
    	AnchorPane root = (AnchorPane) loader.springLoad("view/login.fxml", Main.class);
        LoginController controller = loader.getController();
        controller.setStage(stage);
        controller.addCloseListener();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("登录");
        stage.show();

        //this.stop();
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
