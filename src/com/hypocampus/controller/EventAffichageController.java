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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class EventAffichageController implements Initializable {

    @FXML
    private Button btnRetourAction;
    @FXML
    private AnchorPane SmallPane;
    @FXML
    private TableView<Event> listEvent;
    @FXML
    private TableColumn<Event, String> colTitre;
    @FXML
    private TableColumn<Event, Integer> colCap;
    @FXML
    private TableColumn<Event, String> colType;
    @FXML
    private TableColumn<Event, String> coldd;
    @FXML
    private TableColumn<Event, String> coldf;
    
    List listev = new ArrayList();
    Event e;
    ServiceEvent ev = new ServiceEvent();
    
    
  public void views() throws SQLException {  
      
        listev = ev.afficher();
        ObservableList<Event> l = FXCollections.observableArrayList(listev);  
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titreEvent"));
        colCap.setCellValueFactory(new PropertyValueFactory<>("numeroEvent"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeEvent"));
        coldd.setCellValueFactory(new PropertyValueFactory<>("dateEvent"));
        coldf.setCellValueFactory(new PropertyValueFactory<>("enddateEvent"));
        System.out.println("Perfect Affichage!");
        listEvent.setEditable(true);
        listEvent.setItems(l);
  }
  
  
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
           views();
        
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
    } 
          
}   

    @FXML
    private void RetourAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/MenuEvent.fxml"));
        SmallPane.getChildren().setAll(pane);
    }
    
}
