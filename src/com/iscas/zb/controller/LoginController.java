/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iscas.zb.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.iscas.zb.Main;
import com.iscas.zb.init.MenuInit;
import com.iscas.zb.init.TableRelationInit;
import com.iscas.zb.init.TranslateInit;
import com.iscas.zb.init.XmlToObjectInit;
import com.iscas.zb.service.MainService;
import com.iscas.zb.tools.DialogTools;
import com.iscas.zb.tools.JdbcTools;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
*@date: 2017��2��25��
*@author: zhuquanwen
*@desc: ��¼ҳ���Ӧ������
*/
@Controller
@Scope("prototype")
public class LoginController implements Initializable {
	Logger logger = Logger.getLogger(LoginController.class);
    @FXML
    private Label label;

    @FXML
    private Button goButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button resetButton;

    @FXML
    private TextField urlTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private ProgressIndicator pi;
    @Autowired
    private MainService mainService;

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**ʹ��ťʧЧ*/
    public void disabledButtons(boolean disableFlag){
    	resetButton.setDisable(disableFlag);
    	loginButton.setDisable(disableFlag);
    	usernameTextField.setDisable(disableFlag);
    	passwordTextField.setDisable(disableFlag);
    	if(disableFlag){
    		pi.setVisible(true);
    	}else{
    		pi.setVisible(false);
    	}
    }

    /**����*/
    public void processReset(ActionEvent e){
    	 usernameTextField.setText("");
         passwordTextField.setText("");
    }
    /**��¼*/
    public void processLogin(ActionEvent e){
    	 new Thread(new Runnable() {
			 @Override public void run() {
			 Platform.runLater(new Runnable() {
				@Override public void run() {
					disabledButtons(true);
				}
			 });
			 try{
				//spring
					//SpringInit.initSpring();

					//
					MenuInit.menuInit();
					//XML jaxbת��
					XmlToObjectInit.xmlToObjectInit();
					//鏁版嵁搴撹繛鎺ュ垵濮嬪寲
					//JdbcInit.connectionInit();
					//�����ʼ��
					TranslateInit.translateInit();
					TableRelationInit.tableRelationInit();
			 }catch(Exception e){
				 e.printStackTrace();
				 Platform.runLater(new Runnable() {
						@Override public void run() {
							 DialogTools.error(stage, "����", "������","��ʼ����Ϣ����!");
							 return;
						}
				 });
				 return;
			 }finally {
				 Platform.runLater(new Runnable() {
						@Override
						public void run() {
							disabledButtons(false);
						}
					});
			}

			 try{
				 Connection conn = JdbcTools.getConnection(usernameTextField.getText(), passwordTextField.getText());
				 conn.close();
			 }catch(Exception e){
				 e.printStackTrace();
				 Platform.runLater(new Runnable() {
						@Override public void run() {
							 DialogTools.error(stage, "����", "������","��ȡ���ݿ����ӳ���!");
							 return;
						}
				 });
				 return;
			 }finally {
				 Platform.runLater(new Runnable() {
						@Override
						public void run() {
							disabledButtons(false);
						}
					});
			}
			recordLogin();
			  Platform.runLater(new Runnable() {
					@Override public void run() {
						Stage stagex = new Stage();
						String ip = mainService.getIp();
				    	new Main().start(stagex,ip);
				    	stage.close();
					}
				 });
			 }


			}).start();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        readRecordLogin();
        pi.setVisible(false);
    }

    public void addCloseListener(){
    	 stage.setOnCloseRequest(e -> {
         	System.exit(0);
         });
    }

    private void readRecordLogin() {
    	File file = new File("recordLogin");
    	if(file.exists()){
    		try {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String username = br.readLine();
				String password = br.readLine();
				br.close();
				if(username != null){
					usernameTextField.setText(username);
					passwordTextField.setText(password);
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("��ȡ�˺ż�����Ϣ����!");
			}

    	}

	}

	private void recordLogin() {
		File file = new File("recordLogin");
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.println(username);
			pw.println(password);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("��¼�˺���Ϣ����");
		}

	}
}
