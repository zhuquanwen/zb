/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iscas.zb;

import java.io.InputStream;

import org.apache.log4j.Logger;

import com.iscas.zb.controller.LoginController;
import com.iscas.zb.controller.MainController;
import com.iscas.zb.init.SpringInit;
import com.iscas.zb.init.StaticDataInit;
import com.iscas.zb.tools.SpringFxmlLoader;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author dell
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
        stage.setTitle("µÇÂ¼");
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
