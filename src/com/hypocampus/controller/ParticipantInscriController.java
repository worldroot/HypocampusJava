/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Event;
import com.hypocampus.models.Participant;
import com.hypocampus.services.ServiceEvent;
import com.hypocampus.services.ServiceParticipant;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
public class ParticipantInscriController implements Initializable {

    @FXML
    private AnchorPane ContentPane;
    @FXML
    private Button btnValiderAction;
    @FXML
    private ImageView btnRetourAction;
    @FXML
    private ComboBox<Event> listTitre;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField mail;
    @FXML
    private PasswordField pass;
    
    ServiceEvent ev = new ServiceEvent();
    ServiceParticipant pa = new ServiceParticipant();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ObservableList<Event> options = FXCollections.observableArrayList();
       List<Event> Events= ev.afficher();
       for(int i=0;i<Events.size();i++)
       {
           options.add(Events.get(i));
           
       } 
       listTitre.setItems(options);
       System.out.println("Options:"+ options);
    }    

    private void btnRetourAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/Participant.fxml"));
        ContentPane.getChildren().setAll(pane);    
    }

    @FXML
    private void ValiderAction(ActionEvent event) throws IOException {
        
         if (!nom.getText().equals("") && !prenom.getText().equals("") && !pass.getText().equals("") && !mail.getText().equals("")) {
             
             Event et = listTitre.getSelectionModel().getSelectedItem();
             System.out.println("et:"+et.getIdev());
             Participant p;
             
                     Participant p2 = new Participant( nom.getText(),  prenom.getText(),  mail.getText(),  pass.getText(),  et.getIdev(), 0, 0);
                     pa.ajouter(p2);
                     System.out.println("Done");
                      Image img = new Image("/com/hypocampus/uploads/Check.png");
                                    Notifications n = Notifications.create()
                                       .title("SUCCESS")
                                       .text("  Participant ajouté")
                                       .graphic(new ImageView(img))
                                       .position(Pos.TOP_CENTER)
                                       .hideAfter(Duration.seconds(5));
                                   n.darkStyle();
                                   n.show();
                   
                        
           
                          
             //AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/EventAffichage.fxml"));
             //ContentPane.getChildren().setAll(pane);
             }
         
         else{
                Image img = new Image("/com/hypocampus/uploads/error.png");
                Notifications n = Notifications.create()
                              .title("ERROR")
                              .text("  Vérifier vos champs !")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(5));
                        n.darkStyle();
                        n.show();
               }
         AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/Participant.fxml"));
        ContentPane.getChildren().setAll(pane);
         
         
        
    }

    @FXML
    private void RetourAction(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/Participant.fxml"));
        ContentPane.getChildren().setAll(pane);
        
    }
    
}
