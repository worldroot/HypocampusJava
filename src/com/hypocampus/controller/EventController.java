/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Event;
import com.hypocampus.services.ServiceEvent;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class EventController implements Initializable {

    @FXML
    private Pane panemain;
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
    private ImageView back;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        back.setImage(new Image("/com/hypocampus/uploads/back.png"));
        back.setPreserveRatio(false);
    }    

    @FXML
    private void AddAction(ActionEvent event) {
        Date dateevent = new Date(dateEvent.getValue().getYear()-1900, dateEvent.getValue().getMonthValue()-1, dateEvent.getValue().getDayOfMonth());
        Date enddateEvent = new Date(endDateEvent.getValue().getYear()-1900, endDateEvent.getValue().getMonthValue()-1, endDateEvent.getValue().getDayOfMonth());
        
        Event e = new Event(TitreEvent.getText(), Integer.parseInt(numEvent.getText()),typeEvent.getText(),dateevent,enddateEvent);
        
        ServiceEvent ev = new ServiceEvent();
        
        ev.ajouter(e);
        ev.afficher().forEach(System.out::println);        
    }
    
}
