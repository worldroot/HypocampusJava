/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Event;
import java.net.URL;
import com.hypocampus.models.Participant;
import com.hypocampus.services.ServiceEvent;
import com.hypocampus.services.ServiceParticipant;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    @FXML
    private AnchorPane SmallPane;
    @FXML
    private Button btnSuppAction;
    @FXML
    private TextField UpMail;
    @FXML
    private TextField UpPrenom;
    @FXML
    private TextField UpNom;
    @FXML
    private Button ModifAction;
    @FXML
    private TextField UpPass;
    @FXML
    private TextField UpRev;
    @FXML
    private ComboBox<String> UpTitre;
    
    
    List listp = new ArrayList();
    Participant p = new Participant ();
    ServiceParticipant sp = new ServiceParticipant();
    ServiceEvent se =new ServiceEvent();
    
    public ObservableList<Participant> list = FXCollections.observableArrayList(sp.afficher());

    
      public void views() throws SQLException {  
      
        listp = sp.afficher();
        
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
           
          //For Combo List des titres d'event
                final ObservableList options=FXCollections.observableArrayList();
                List<Event> Events= se.afficher();
                for(int i=0;i<Events.size();i++)
                {
                    options.add(Events.get(i).getTitreEvent());

                } 
                UpTitre.setItems(options);
                System.out.println("Options:"+ options);
         
           //Modifier Starts Here
         Ptab.setOnKeyReleased((KeyEvent e) -> {
             if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
                 
                 Participant rowData = Ptab.getSelectionModel().getSelectedItem();
                 /**
                  * fill the fields with the selected data *
                  */
                 //Event et = UpTitre.getSelectionModel().getSelectedItem();
                 // LocalDate df= Updatec.getValue();
                 
                 String t=se.GetById(rowData.getChoix());
                 UpTitre.setValue(t);
                 UpNom.setText(rowData.getNomp());
                 UpPrenom.setText(rowData.getPrenomp());
                 UpMail.setText(rowData.getEmail());
                 UpPass.setText(rowData.getPasswordp());
                 UpRev.setText(Integer.toString(rowData.getReview()));
                
                 
             }
        });
           
           
           
           
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

    @FXML
    private void btnModifAction(ActionEvent event) throws SQLException, IOException {
            
        
        
        if (!UpNom.getText().equals("") && !UpPrenom.getText().equals("") && !UpMail.getText().equals("") && !UpPass.getText().equals("") ) {
            
           String event_combo=UpTitre.getValue();
            int idEvent=se.getTitreId(event_combo);
            Participant a =new Participant(UpNom.getText(),UpPrenom.getText(), UpMail.getText(), UpPass.getText(), idEvent, Integer.parseInt(UpRev.getText()) );
            // public Participant(String nomp, String prenomp, String email, String passwordp, int choix, int review) 
            sp.modifier(a);
            AnchorPane redirected;
                        redirected = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/ParticipantAffichage.fxml")); 
                        SmallPane.getChildren().setAll(redirected);
            /*refreshing the table view */
            Ptab.setItems(list); 
            
            Image img = new Image("/com/hypocampus/uploads/Check.png");
                                Notifications n = Notifications.create()
                                  .title("SUCCESS")
                                  .text("  Event Modifié")
                                  .graphic(new ImageView(img))
                                  .position(Pos.TOP_CENTER)
                                  .hideAfter(Duration.seconds(5));
                            n.darkStyle();
                            n.show();    
        }
        else{
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
            
            
        
    }
    
}
