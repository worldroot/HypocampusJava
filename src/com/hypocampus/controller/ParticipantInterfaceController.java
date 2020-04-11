/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Event;
import com.hypocampus.services.ServiceEvent;
import com.hypocampus.services.ServiceParticipant;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ParticipantInterfaceController implements Initializable {

    @FXML
    private AnchorPane ContentPane;
    @FXML
    private ImageView Re;
    @FXML
    private Label titre;
    @FXML
    private Label type;
    @FXML
    private Label cap;
    @FXML
    private Label dated;
    @FXML
    private Label datef;
    @FXML
    private Label image;
    @FXML
    private ImageView IMAGE;
    @FXML
    private ImageView imgcertif;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceEvent SE = new ServiceEvent();
        ServiceParticipant SP= new ServiceParticipant();
        try {
            Event v = SE.getEvent(SP.getMailConnected());
            titre.setText(v.getTitreEvent());
            type.setText(v.getTypeEvent());
            cap.setText(Integer.toString(v.getNumeroEvent()));
            dated.setText(v.getDateEvent().toString());
            datef.setText(v.getEnddateEvent().toString());
            IMAGE.setImage(new Image("file:/C:/Users/ASUS/Desktop/PiDev/Sprint%20Java/HypocampusJava/src/com/hypocampus/uploads/Event/"+v.getImage_name())); 
            if(SP.test(SP.getMailConnected())==true)
            {
            titre.setVisible(false);
            type.setVisible(false);
            cap.setVisible(false);
            dated.setVisible(false);
            datef.setVisible(false);
            IMAGE.setVisible(false);
            String typeE="";
            if("Formation".equals(v.getTypeEvent())==true)
            {
                typeE="Formation";
           imgcertif.setImage(new Image("file:/C:/Users/ASUS/Desktop/PiDev/Sprint%20Java/HypocampusJava/src/com/hypocampus/uploads/Event/"+SP.getImageCertif(typeE,v.getIdev()))); 
                    }
             else if("Cours".equals(v.getTypeEvent())==true)
            {
                typeE="Cours";
           imgcertif.setImage(new Image("file:/C:/Users/ASUS/Desktop/PiDev/Sprint%20Java/HypocampusJava/src/com/hypocampus/uploads/Event/"+SP.getImageCertif(typeE,v.getIdev()))); 
                    }
             else if("Workshop".equals(v.getTypeEvent())==true)
            {
                typeE="Workshop";
           imgcertif.setImage(new Image("file:/C:/Users/ASUS/Desktop/PiDev/Sprint%20Java/HypocampusJava/src/com/hypocampus/uploads/Event/"+SP.getImageCertif(typeE,v.getIdev()))); 
                    }
            else 
            { 
           imgcertif.setImage(new Image("file:/C:/Users/ASUS/Desktop/PiDev/Sprint%20Java/HypocampusJava/src/com/hypocampus/uploads/Event/")); 
                    }
            
            }
           
            
        } catch (SQLException ex) {
            Logger.getLogger(ParticipantInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void ReAction(MouseEvent event) throws IOException {
        
          ServiceParticipant SP= new ServiceParticipant();
          SP.Deconnected();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/Participant.fxml"));
        ContentPane.getChildren().setAll(pane);
    }
    
}
