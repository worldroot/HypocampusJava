/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import java.net.URL;
import com.hypocampus.models.Participant;
import com.hypocampus.services.ServiceEvent;
import com.hypocampus.services.ServiceParticipant;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
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
public class ParticipantAffichageController implements Initializable {

    @FXML
    private TableView<Participant> Ptab;
    @FXML
    private TableColumn<Participant, String> colnomp;
    @FXML
    private TableColumn<Participant, String> colPrenom;
    @FXML
    private TableColumn<Participant, String> colmail;
    @FXML
    private TableColumn<Participant, String> colpass;
    @FXML
    private TableColumn<Participant, String> colchoix;
    @FXML
    private TableColumn<Participant, Integer> colreview;
    
    List listp = new ArrayList();
    
    Participant p = new Participant ();
    ServiceParticipant sp = new ServiceParticipant();
    
    @FXML
    private AnchorPane SmallPane;
    @FXML
    private Button btnSuppAction;

    
      public void views() throws SQLException {  
      
        listp = sp.afficher();
        ServiceEvent se =new ServiceEvent();
        ObservableList<Participant> l = FXCollections.observableArrayList(listp);  
        colnomp.setCellValueFactory(new PropertyValueFactory<>("nomp"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenomp"));
        colmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colpass.setCellValueFactory(new PropertyValueFactory<>("passwordp"));
        colchoix.setCellValueFactory((CellDataFeatures<Participant, String> pp) -> new ReadOnlyObjectWrapper(se.GetById(pp.getValue().getChoix()))); 
        colreview.setCellValueFactory(new PropertyValueFactory<>("review"));
        System.out.println("Perfect View Participants !");
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
    private void SuppAction(ActionEvent event) throws SQLException {
        Participant ET = Ptab.getSelectionModel().getSelectedItem();
        
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
                ServiceParticipant Sv = new ServiceParticipant();
                Participant Ev = new Participant(ET.getNomp());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                              alert.setTitle("Confirmation ");
                              alert.setHeaderText(null);
                              alert.setContentText("Vous voulez vraiment supprimer cette Certif ");
                              Optional<ButtonType> action = alert.showAndWait();
                              if (action.get() == ButtonType.OK) {
                                    Sv.supprimer(Ev);
                                    views();
                                    }
                              
                                  }
       
    }

    @FXML
    private void RetourAction(MouseEvent event) throws IOException {
         AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/MenuEvent.fxml"));
         SmallPane.getChildren().setAll(pane);
    }
    
}
