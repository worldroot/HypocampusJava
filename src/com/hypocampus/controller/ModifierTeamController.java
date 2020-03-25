/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Project;
import com.hypocampus.models.team;
import com.hypocampus.services.ServiceTeam;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
public class ModifierTeamController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     @FXML
    private AnchorPane ContentPaneEditT;

    @FXML
    private TextField NameTeam;

    @FXML
    private DatePicker StartDateteam;

    @FXML
    private Button ModifierTeam;

    @FXML
    private Button MPbackAffteam;
    //list pour modifier
    List <team> teamtab =new ArrayList() ;
    // alert
      Alert alert = new Alert(Alert.AlertType.NONE);
    
   public void getTeamm(team T) {
     
        NameTeam.setText(T.getTeamname());  
        StartDateteam.setValue(LOCAL_DATE(T.getDateofcreation().toString()));    
    }
    
    public static final LocalDate LOCAL_DATE (String dateString){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(dateString, formatter);
    return localDate;
    }
    public static final LocalDate NOW_LOCAL_DATE (){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date , formatter);
        return localDate;
    }
    
   public void add(team T) {
         
         teamtab.add(T);
    
    }
    
    
    
    
    @FXML
    void MPbackAffteam(ActionEvent event) throws IOException {
         AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/AfficherTeam.fxml"));
        ContentPaneEditT.getChildren().setAll(pane);
    }

    @FXML
    void ModifierTeam(ActionEvent event) throws IOException {
           LocalDate ds= StartDateteam.getValue();
          
           
           Date dateS=Date.valueOf(ds.toString());//converting string into sql date
         

           if(ds.compareTo(NOW_LOCAL_DATE()) > 0)
           {
              ServiceTeam st =new ServiceTeam();
           
             
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/AfficherTeam.fxml"));              
        Parent parent = loader.load();
        //ContentPaneEditT.getChildren().setAll(parent);
        
        AfficherTeamController id =(AfficherTeamController) loader.getController();
        System.out.println(teamtab.get(0).getId());
               
         st.modifier(new team(teamtab.get(0).getId(),NameTeam.getText(),dateS));
           id.afficher();
         alert.setAlertType(Alert.AlertType.INFORMATION);
           
           // set content text
           alert.setContentText("Team modifier ");
           
           // show the dialog
           alert.show();
              
           }
           else
           {
           alert.setAlertType(Alert.AlertType.WARNING);
           
           // set content text
           alert.setContentText(" vérifier date de création du groupe ");
           
           // show the dialog
           alert.show();               
           }
    }

    
}
