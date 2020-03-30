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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class EventAffichageController implements Initializable {

    @FXML
    private ImageView btnRetourAction;
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
    @FXML
    private Button SupAction;
    
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
    private void RetourAction(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/MenuEvent.fxml"));
        SmallPane.getChildren().setAll(pane);
    }

    @FXML
    private void btnSupAction(ActionEvent event) throws SQLException {
        
        Event ET = listEvent.getSelectionModel().getSelectedItem();
        
        if (ET == null) {

                Image img = new Image("/com/hypocampus/uploads/error.png");
                Notifications n = Notifications.create()
                                  .title("Error")
                                  .text("  Choix invalide")
                                  .graphic(new ImageView(img))
                                  .position(Pos.TOP_CENTER)
                                  .hideAfter(Duration.seconds(5));
                                  n.darkStyle();
                                  n.show();
                                }
        else{
                ServiceEvent Sv = new ServiceEvent();
                Event Ev = new Event(ET.getIdev());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                              alert.setTitle("Confirmation ");
                              alert.setHeaderText(null);
                              alert.setContentText("Vous voulez vraiment supprimer ce Event ");
                              Optional<ButtonType> action = alert.showAndWait();
                              if (action.get() == ButtonType.OK) {
                                    Sv.supprimer(Ev);
                                    views();
                                    }
                              
                                  }
        
        
    }











    
}


