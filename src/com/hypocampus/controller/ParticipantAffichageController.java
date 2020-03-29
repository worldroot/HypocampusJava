/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Backlog;
import com.hypocampus.models.Event;
import java.net.URL;
import com.hypocampus.models.Participant;
import com.hypocampus.services.ServiceEvent;
import com.hypocampus.services.ServiceParticipant;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ParticipantAffichageController implements Initializable {

    @FXML
    private TableView<Participant> Ptab;
    @FXML
    private TableColumn<Participant, String> colnomp;
    @FXML
    private Button RetourAction;
    @FXML
    private TableColumn<Participant, String> colPrenom;
    @FXML
    private TableColumn<Participant, String> colmail;
    @FXML
    private TableColumn<Participant, String> colpass;
    @FXML
    private TableColumn<Participant, Integer> colchoix;
    @FXML
    private TableColumn<Participant, Integer> colreview;
    
    List listp = new ArrayList();
    
    Participant p = new Participant ();
    ServiceParticipant sp = new ServiceParticipant();
    
    @FXML
    private AnchorPane SmallPane;

    
      public void views() throws SQLException {  
      
        listp = sp.afficher();
        ServiceEvent se =new ServiceEvent();
        ObservableList<Participant> l = FXCollections.observableArrayList(listp);  
        colnomp.setCellValueFactory(new PropertyValueFactory<>("nomp"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenomp"));
        colmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colpass.setCellValueFactory(new PropertyValueFactory<>("passwordp"));
        colchoix.setCellValueFactory(new PropertyValueFactory<>("choix"));
        //colchoix.setCellValueFactory((CellDataFeatures<Participant, String> x) -> new ReadOnlyObjectWrapper(se.GetById(x.getValue().getChoix())));
        colreview.setCellValueFactory(new PropertyValueFactory<>("review"));
        System.out.println("Perfect Participant !");
        Ptab.setEditable(true);
        //Ptab.setItems((ObservableList<Participant>)sp.afficher());
        Ptab.setItems(l);
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
    private void btnRetourAction(ActionEvent event) throws IOException {
         AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/MenuEvent.fxml"));
         SmallPane.getChildren().setAll(pane);
    }
    
}
