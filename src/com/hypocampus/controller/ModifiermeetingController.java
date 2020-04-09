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
import com.hypocampus.services.ServiceTeam;
import com.hypocampus.utils.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
public class ModifiermeetingController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherM();
    }    
    
    @FXML
    private AnchorPane ContentPanemodifM;

    @FXML
    private TextField duration;

    @FXML
    private Button modifiermeeting;

    @FXML
    private Button backAffmeeting;

    @FXML
    private ComboBox<team> teamName;

    @FXML
    private TextArea description;

    @FXML
    private TextField nbrmeeting;
        // alert
      Alert alert = new Alert(Alert.AlertType.NONE);
List <meeting> data2 =new ArrayList() ;
   Connection cnx = DataSource.getInstance().getCnx();
    ObservableList  dataB =FXCollections.observableArrayList();

        
    
     public void inflateUI(meeting s) {      
        ServiceTeam sP =new ServiceTeam();
        duration.setText(s.getDuration()); 
       
       //Integer.parseInt(nbrmeeting.getText());
      // nbrmeeting.setText(Integer.parseInt(nbrmeeting.getText())); 
        description.setText(s.getDescription()); 
        teamName.setValue(sP.getById(s.getTeam_id()));
System.out.println(sP.getById(s.getTeam_id()));
    
    }
     
    public void add(meeting s) {
         
         data2.add(s);
    
    }
    @FXML
    void backAffmeeting(ActionEvent event) throws IOException {
       AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/Affichermeeting.fxml"));
        ContentPanemodifM.getChildren().setAll(pane);
    }
    public void afficherM()
     {
         try {
            String requete = "SELECT * FROM team";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
         dataB.add(new team(rs.getInt("id"), rs.getString("teamname")));

            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         
         teamName.setItems(dataB);
     } 
    @FXML
    void modifiermeetingAction(ActionEvent event) throws IOException {
                      ServiceMeeting ss =new ServiceMeeting();

                      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/Affichermeeting.fxml"));              
                      Parent parent = loader.load();
                      //ContentPaneEditP.getChildren().setAll(parent);
                      AffichermeetingController idd =(AffichermeetingController) loader.getController();

                      idd.afficher();
                      System.out.println(data2.get(0).getId());
                      
                      
                 team pr = teamName.getSelectionModel().getSelectedItem();
                 System.out.println(pr.getId());
                 ss.modifier(new meeting(data2.get(0).getId(),pr.getId(),description.getText(),duration.getText(),Integer.parseInt(nbrmeeting.getText())));
                             
         alert.setAlertType(Alert.AlertType.INFORMATION);
           
           // set content text
           alert.setContentText("Meeting modifier ");
           
           // show the dialog
           alert.show();
    }

}
