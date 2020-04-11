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
import com.hypocampus.utils.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
/**
 * FXML Controller class
 *
 * @author 21694
 */
public class MeetingController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
           afficherP();
    }    
        @FXML
    private AnchorPane ContentPaneAjouM;
       @FXML
    private TextField duration;

    @FXML
    private Button Ajoutermeeting;

    @FXML
    private Button backAffmeeting;

    @FXML
    private ComboBox<team> teamName;

    @FXML
    private TextArea description;

    @FXML
    private TextField nbrmeeting;

      
    ObservableList  dataB =FXCollections.observableArrayList();
    Connection cnx = DataSource.getInstance().getCnx();
    // alert
      Alert alert = new Alert(Alert.AlertType.NONE);
    
         public void afficherP()
     {
         try {
            String requete = "SELECT id,teamname FROM team";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dataB.add(new team(rs.getInt("id"),rs.getString("teamname")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         
         teamName.setItems(dataB);
     }
         
         
         
    @FXML
    void ajoutermeeting(ActionEvent event) {
        
     if (!description.getText().equals("")&&!duration.getText().equals(""))
       {
   
           ServiceMeeting ss =new ServiceMeeting();

             team pr = teamName.getSelectionModel().getSelectedItem();
               ss.ajouter(new meeting(pr.getId(),description.getText(),duration.getText(),Integer.parseInt(nbrmeeting.getText())));
             
         alert.setAlertType(Alert.AlertType.INFORMATION);
           
           // set content text
           alert.setContentText("meeting ajouté ");
           
           // show the dialog
           alert.show();
               description.setText("");
               duration.setText("");
        
           

           
       }
       else {
           
           alert.setAlertType(Alert.AlertType.WARNING);
           
           // set content text
           alert.setContentText("verifié vos parametre ");
           
           // show the dialog
           alert.show();
       }
        
    }

    @FXML
    void backAffmeeting(ActionEvent event) throws IOException {
       AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/Affichermeeting.fxml"));
        ContentPaneAjouM.getChildren().setAll(pane);
    }
}
