/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;


import com.hypocampus.models.team;
import com.hypocampus.services.ServiceTeam;
import com.hypocampus.utils.DataSource;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.sms.MessageStatus;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.messages.TextMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
public class TeamController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    @FXML
    private AnchorPane ContentPaneAjouT;
    @FXML
    private TextField NameTeam;

    @FXML
    private DatePicker StartDateteam;

    @FXML
    private Button AjouterTeam;

    @FXML
    private Button backAffTeam;
   Connection cnx = DataSource.getInstance().getCnx();
// alert
      Alert alert = new Alert(Alert.AlertType.NONE);
      
    @FXML
    void ajouterTeam(ActionEvent event) throws SQLException {
   
           
          Statement stmt = cnx.createStatement();
          String SQL = "SELECT * FROM team WHERE teamname ='" +NameTeam.getText()+"'";
           ResultSet rs = stmt.executeQuery(SQL);
        if(!rs.next()){  
       if (!NameTeam.getText().equals(""))
       {
          
           ServiceTeam st =new ServiceTeam();
           
           LocalDate ds= StartDateteam.getValue();
           
           Date dateS=Date.valueOf(ds.toString());//converting string into sql date

           if(ds.compareTo(NOW_LOCAL_DATE()) > 0)
           {
            
               st.ajouter(new team(NameTeam.getText(),dateS));

            alert.setAlertType(Alert.AlertType.INFORMATION);
           
           // set content text
           alert.setContentText("Team ajouté ");
           
           // show the dialog
           alert.show();
           
           
          NameTeam.setText("");
        
           }
           else
           {
            alert.setAlertType(Alert.AlertType.INFORMATION);
           
           // set content text
           alert.setContentText("vérifier date de création du groupe ");
           
           // show the dialog
           alert.show();
        
               
           }
           
       }
       else {
           
           alert.setAlertType(Alert.AlertType.WARNING);
           
           // set content text
           alert.setContentText("verifié vos parametre ");
           
           // show the dialog
           alert.show();
       }
        }
               else {
           
           alert.setAlertType(Alert.AlertType.WARNING);
           
           // set content text
           alert.setContentText("Team déjà existe ");
           
           // show the dialog
           alert.show();
       }
 
       
       
    }
       public static final LocalDate NOW_LOCAL_DATE (){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date , formatter);
        return localDate;
    }

    @FXML
    void backAffprojet(ActionEvent event) throws IOException {
AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/AfficherTeam.fxml"));
        ContentPaneAjouT.getChildren().setAll(pane);
    }
}
