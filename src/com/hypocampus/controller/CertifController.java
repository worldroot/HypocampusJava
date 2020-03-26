/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;


import com.hypocampus.models.Certif;
import com.hypocampus.models.Event;
import com.hypocampus.services.ServiceCertif;
import com.hypocampus.services.ServiceEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CertifController implements Initializable {

    @FXML
    private AnchorPane SmallPane;
    @FXML
    private TextField pointc;
    @FXML
    private DatePicker datec;
    @FXML
    private ComboBox<Event> ComboTitre;
    @FXML
    private Button validerAction;
    @FXML
    private Button retourAction;
    @FXML
    private Button clearAction;
    
     ServiceEvent ev = new ServiceEvent();
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ObservableList<Event> options = FXCollections.observableArrayList();
       List<Event> Events= ev.afficher();
       for(int i=0;i<Events.size();i++)
       {
           options.add(Events.get(i));
           
       } 
       ComboTitre.setItems(options);
       System.out.println("Options:"+ options);
    }   
    
    

    @FXML
    private void btnvaliderAction(ActionEvent event) {  
       Date dateevent = new Date(datec.getValue().getYear()-1900, datec.getValue().getMonthValue()-1, datec.getValue().getDayOfMonth());
       Event et = ComboTitre.getSelectionModel().getSelectedItem();
       Certif e = new Certif(et.getIdev(),Integer.parseInt(pointc.getText()),dateevent);
       ServiceCertif ev = new ServiceCertif();
       
       ev.ajouter(e);
        System.out.println("Youssef");
    }      

    @FXML
    private void btnretourAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/MenuEvent.fxml"));
         SmallPane.getChildren().setAll(pane);
    }

    @FXML
    private void btnclearAction(ActionEvent event) {
        
    }



}

    

