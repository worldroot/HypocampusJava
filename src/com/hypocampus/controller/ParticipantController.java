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
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ParticipantController implements Initializable {

    @FXML
    private ImageView Home;
    @FXML
    private AnchorPane ContentPane;
    @FXML
    private Button InscAction;
    @FXML
    private PasswordField mdp;
    @FXML
    private PasswordField email;
    @FXML
    private Button ValiderAction;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // SmallPane : 996 x 695
    }    

    @FXML
    private void GoHomeAction(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/Front.fxml"));
        ContentPane.getChildren().setAll(pane);
    }

    @FXML
    private void btnInscAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/ParticipantInscri.fxml"));
        ContentPane.getChildren().setAll(pane);
        
    }

    @FXML
    private void btnValiderAction(ActionEvent event) {
        
    }
    
}
