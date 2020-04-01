/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Project;
import com.hypocampus.models.Sprint;
import com.hypocampus.models.meeting;
import com.hypocampus.models.team;
import com.hypocampus.services.ServiceMeeting;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author 21694
 */
public class AffichermeetingController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficher();
    }    
        // alert
      Alert alert = new Alert(Alert.AlertType.NONE);
    @FXML
    private AnchorPane ContentPane;
  @FXML
    private TableView<meeting> tab;

    @FXML
    private TableColumn<meeting, String> Colteamname;

    @FXML
    private TableColumn<meeting, String> Coldescription;

    @FXML
    private TableColumn<meeting, String> Colduration;

    @FXML
    private TableColumn<meeting, String> Colnbrmeeting;

    @FXML
    private Button supprimermeeting;

    @FXML
    private Button newmeeting;

    @FXML
    void editmeeting(ActionEvent event) throws IOException {
    meeting ss = tab.getSelectionModel().getSelectedItem();
//             data2.removeAll(Pr);

   
   
   
                                  if (ss == null) {

                                  alert.setAlertType(Alert.AlertType.WARNING);
           
                                   // set content text
                                   alert.setContentText(" No meeting selected : Please select a meeting for edit ");
           
                                   // show the dialog
                                   alert.show();
                                }
     else{
                                      
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/modifiermeeting.fxml"));              
        Parent parent = loader.load();
        ContentPane.getChildren().setAll(parent);

            ModifiermeetingController controller =(ModifiermeetingController) loader.getController();
            controller.inflateUI(ss);
            controller.add(ss);

            
          }
    }
    
    @FXML
    void newmeetingAction(ActionEvent event) throws IOException {
   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/meeting.fxml"));
        ContentPane.getChildren().setAll(pane);
}
     public void afficher()
     {
           //ObservableList <Project> data =FXCollections.observableArrayList();
             ServiceMeeting sm =new ServiceMeeting();
     
                       
                    Colteamname.setCellValueFactory(new PropertyValueFactory<>("team_id"));
                    Coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
                    //Colnameproject.setCellValueFactory((TableColumn.CellDataFeatures<Sprint, String> p) -> new ReadOnlyObjectWrapper(sP.getById(p.getValue().getProject_id()))); 
                    Colduration.setCellValueFactory(new PropertyValueFactory<>("duration"));
                    Colnbrmeeting.setCellValueFactory(new PropertyValueFactory<>("nbrmeeting"));
                    tab.setItems((ObservableList<meeting>) sm.afficher());
     }

    @FXML
    void supprimermeeting(ActionEvent event) {
            meeting m = tab.getSelectionModel().getSelectedItem();
             

   
   
   
                                  if (m == null) {

                                  alert.setAlertType(Alert.AlertType.WARNING);
           
                                   // set content text
                                   alert.setContentText(" Choix invalide ");
           
                                   // show the dialog
                                   alert.show();
                                }
                                   else{
                                         ServiceMeeting sP =new ServiceMeeting();
                                     meeting P =new meeting(m.getId());
                                       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation ");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Vous voulez vraiment supprimer ce meeting ");
                                    Optional<ButtonType> action = alert.showAndWait();
                                    if (action.get() == ButtonType.OK) {
                                       sP.supprimer(P);
                                       afficher();

                                    }
                                  }
    }  
}
