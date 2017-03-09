package com.iscas.zb.tools;

import java.util.List;
import java.util.Optional;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.LoginDialog;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
@SuppressWarnings("deprecation")
public class DialogTools {
	/**提示*/
	public static  void info(Stage stage, String title,
			String masthead, String message ){
		new ControlsFxDialog().info(stage, title, masthead, message);

	}
	/**警告*/
	public static void warn(Stage stage, String title,
			String masthead, String message){
		new ControlsFxDialog().warn(stage, title, masthead, message);
	}


	/**错误*/
	public static void error(Stage stage, String title,
			String masthead, String message){

		new ControlsFxDialog().error(stage, title, masthead, message);
	}

//	/**异常*/
//	public static void exception(Stage stage, String title,
//			String masthead, String message, Exception e){
//
//	}

//	/**confirm*/
//	public static Action confirm(Stage stage, String title,
//			String masthead, String message){
////		Action response = Dialogs.create()
////		        .owner(stage)
////		        .title(title)
////		        .masthead(masthead)
////		        .message(message)
////		        .showConfirm();
////		return response;
//		return null;
//
//
////		if (response == Dialog.Actions.YES) {
////		    // ... user chose YES
////		} else {
////		    // ... user chose NO, CANCEL, or closed the dialog
////		}
//	}
//	/**confirm*/
//	public static Action confirm( String title,
//			String masthead, String message){
////		Action response = Dialogs.create()
////		        .title(title)
////		        .masthead(masthead)
////		        .message(message)
////		        .showConfirm();
////		return response;
//		return null;
////		if (response == Dialog.Actions.YES) {
////		    // ... user chose YES
////		} else {
////		    // ... user chose NO, CANCEL, or closed the dialog
////		}
//	}
//
//	/**textInput*/
//	public static Optional<String> textInput(Stage stage, String title,
//			String masthead, String message , String defaultInput){
////		Optional<String> response = Dialogs.create()
////		        .owner(stage)
////		        .title(title)
////		        .masthead(masthead)
////		        .message(message)
////		        .showTextInput(defaultInput);
////		return response;
//		return null;
//		// One way to get the response value.
////		if (response.isPresent()) {
////		    System.out.println("Your name: " + response.get());
////		}
////
////		// The Java 8 way to get the response value (with lambda expression).
////		response.ifPresent(name -> System.out.println("Your name: " + name));
//	}
//	/**textInput*/
//	public static Optional<String> textInput( String title,
//			String masthead, String message , String defaultInput){
////		Optional<String> response = Dialogs.create()
////		        .title(title)
////		        .masthead(masthead)
////		        .message(message)
////		        .showTextInput(defaultInput);
////		return response;
//		return null;
//		// One way to get the response value.
////		if (response.isPresent()) {
////		    System.out.println("Your name: " + response.get());
////		}
////
////		// The Java 8 way to get the response value (with lambda expression).
////		response.ifPresent(name -> System.out.println("Your name: " + name));
//	}
//
//	/**choiceInput*/
//	public static Optional<String> choiceInput(Stage stage, String title,
//			String masthead, String message , List<String> choices){
////		List<String> choices = new ArrayList<>();
////		choices.add("a");
////		choices.add("b");
////		choices.add("c");
//
////		Optional<String> response = Dialogs.create()
////		        .owner(stage)
////		        .title(title)
////		        .masthead(masthead)
////		        .message(message)
////		        .showChoices(choices);
////		return response;
//		return null;
////		// One way to get the response value.
////		if (response.isPresent()) {
////		    System.out.println("The user chose: " + response.get());
////		}
////
////		// The Java 8 way to get the response value (with lambda expression).
////		response.ifPresent(chosen -> System.out.println("The user chose: " + chosen));
//	}
//	/**choiceInput*/
//	public static Optional<String> choiceInput( String title,
//			String masthead, String message , List<String> choices){
////		List<String> choices = new ArrayList<>();
////		choices.add("a");
////		choices.add("b");
////		choices.add("c");
//
////		Optional<String> response = Dialogs.create()
////		        .title(title)
////		        .masthead(masthead)
////		        .message(message)
////		        .showChoices(choices);
////		return response;
//		return null;
////		// One way to get the response value.
////		if (response.isPresent()) {
////		    System.out.println("The user chose: " + response.get());
////		}
////
////		// The Java 8 way to get the response value (with lambda expression).
////		response.ifPresent(chosen -> System.out.println("The user chose: " + chosen));
//	}
}
