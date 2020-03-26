/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Event;
import com.hypocampus.services.ServiceEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class EventController implements Initializable {

    @FXML
    private TextField TitreEvent;
    @FXML
    private TextField numEvent;
    @FXML
    private TextField typeEvent;
    @FXML
    private Button btnvalider;
    @FXML
    private DatePicker dateEvent;
    @FXML
    private DatePicker endDateEvent;
    @FXML
    private AnchorPane SmallPane;
    @FXML
    private Button btnRetourAction;
    @FXML
    private Button clearAction;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void AddAction(ActionEvent event) throws IOException {
        
        boolean test = false;
        Date dateevent = new Date(dateEvent.getValue().getYear()-1900, dateEvent.getValue().getMonthValue()-1, dateEvent.getValue().getDayOfMonth());
        Date enddateEvent = new Date(endDateEvent.getValue().getYear()-1900, endDateEvent.getValue().getMonthValue()-1, endDateEvent.getValue().getDayOfMonth());
        
        if (TitreEvent.getText().equals("")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Le champs TitreEvent est vide");
            alert.showAndWait();
            test = false;
            }
        
        if (typeEvent.getText().equals("")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Le champs Type est vide");
            alert.showAndWait();
            test = false;
            }
        
        if (numEvent.getText().equals("")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setHeaderText("Le champs Capacité est vide");
            alert.showAndWait();
            test = false;
            }
        
        else{
             
        Event e = new Event(TitreEvent.getText(), Integer.parseInt(numEvent.getText()),typeEvent.getText(),dateevent,enddateEvent);
        ServiceEvent ev = new ServiceEvent();
        
        ev.ajouter(e);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Done ");
        alert.setHeaderText("Event bien ajouté <3");
        alert.showAndWait();
        test = false;
        
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/EventAffichage.fxml"));
        SmallPane.getChildren().setAll(pane);
        }      
    }

    @FXML
    private void RetourAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/MenuEvent.fxml"));
        SmallPane.getChildren().setAll(pane);

    }

    @FXML
    private void btnclearAction(ActionEvent event) {
        TitreEvent.clear();
        typeEvent.clear();
        numEvent.clear();
        dateEvent.getEditor().clear();
        endDateEvent.getEditor().clear();
        }
    
}
