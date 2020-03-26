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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author mehdibehira
 */
public class BackendController implements Initializable {

    @FXML
    private AnchorPane BackendPane;
    @FXML
    private Button BacklogAction;
    @FXML
    private AnchorPane ContentPane;
    @FXML
    private Button EventsAction;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO     
    }    

    @FXML
    private void btnBacklogAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/backlog.fxml"));
        ContentPane.getChildren().setAll(pane);
    }
    
    @FXML
    private void btnEventsAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/MenuEvent.fxml"));
        ContentPane.getChildren().setAll(pane);
    }
    
}
