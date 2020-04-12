/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author Houcem
 */
public class Backback_Controller implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	@FXML
    private AnchorPane ContentPane;

    @FXML
    private Button btnE;
	
	@FXML
    private Button btnA;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}	
	
	@FXML
    void btnEAction(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/readEntreprise.fxml"));
        ContentPane.getChildren().setAll(pane);
    }
	
	@FXML
    void btnAAction(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/readAbonnement.fxml"));
        ContentPane.getChildren().setAll(pane);

    }
	
}
