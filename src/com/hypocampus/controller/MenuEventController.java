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
 * @author ASUS
 */
public class MenuEventController implements Initializable {

    @FXML
    private AnchorPane SmallPane;
    @FXML
    private Button AddEventAction;
    @FXML
    private Button ListEventsAction;
    @FXML
    private Button ListCertifAction;
    @FXML
    private Button CertifAction;
    @FXML
    private Button ListParAction;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAddEventAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/Event.fxml"));
        SmallPane.getChildren().setAll(pane);
    }

    @FXML
    private void btnListEventAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/EventAffichage.fxml"));
        SmallPane.getChildren().setAll(pane);
    }

    @FXML
    private void btnListCertifAction(ActionEvent event) {
        
    }

    @FXML
    private void btnCertifAction(ActionEvent event) {
    }

    @FXML
    private void btnListParAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/ParticipantAffichage.fxml"));
        SmallPane.getChildren().setAll(pane);
    }
    
}
