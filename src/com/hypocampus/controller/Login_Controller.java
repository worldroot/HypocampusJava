/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.User;
import com.hypocampus.services.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
/**
 * FXML Controller class
 *
 * @author Houcem
 */
public class Login_Controller implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	
	Alert alert = new Alert(Alert.AlertType.NONE);

	@FXML
    private AnchorPane apLogin;
	 
    @FXML
    private Button btnLogin;

    @FXML
    private TextField leLogin;

    @FXML
    private PasswordField lePwd;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}	
	
	@FXML
    void btnLogin_clic(ActionEvent event) throws IOException {
		String inputUsername = leLogin.getText();
		String inputPassword = lePwd.getText();
		lePwd.setText(""); 
		ServiceUser su = new ServiceUser();
//		User u = su.login(inputUsername,inputPassword);
		
//		if(u.getRoles().equals("a:1:{i:0;s:10:\"ROLE_ADMIN\";}")) {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/backback_.fxml"));
			apLogin.getChildren().setAll(pane);
//		}
//		else if(u.getRoles().equals("a:1:{i:0;s:14:\"ROLE_DEVELOPER\";}") || u.getRoles().equals("a:1:{i:0;s:17:\"ROLE_SCRUM_MASTER\";}") ){
//			AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/backend.fxml"));
//			apLogin.getChildren().setAll(pane);
//		}  
    }
	
}
