/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Certif;
import com.hypocampus.models.Event;
import com.hypocampus.services.ServiceCertif;
import com.hypocampus.services.ServiceEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CertifAffichageController implements Initializable {

    @FXML
    private TableView<Certif> TableC;
    @FXML
    private TableColumn<Certif, String> coltitre;
    @FXML
    private TableColumn<Certif, Integer> colpoint;
    @FXML
    private TableColumn<Certif, Date> colDatec;
    @FXML
    private Button RetourAction;
    
    List listev = new ArrayList();
    Certif c;
    ServiceCertif ev = new ServiceCertif();
    @FXML
    private AnchorPane SmallPane;
    @FXML
    private Button SuppAction;

    
        
    
  public void views() throws SQLException {  
      
        listev = ev.afficher();
        ServiceEvent se =new ServiceEvent();
        ObservableList<Certif> l = FXCollections.observableArrayList(listev);  
        coltitre.setCellValueFactory((CellDataFeatures<Certif, String> p) -> new ReadOnlyObjectWrapper(se.GetById(p.getValue().getTitrec())));
        colpoint.setCellValueFactory(new PropertyValueFactory<>("pointc"));
        colDatec.setCellValueFactory(new PropertyValueFactory<>("datec"));
        System.out.println("View Certif !");
        TableC.setEditable(true);
        TableC.setItems(l);
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
    private void btnRetourAction(ActionEvent event) throws IOException {
         AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/MenuEvent.fxml"));
         SmallPane.getChildren().setAll(pane);
    }

    @FXML
    private void btnSuppAction(ActionEvent event) throws SQLException {
                Certif ET = TableC.getSelectionModel().getSelectedItem();
        
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
                ServiceCertif Sv = new ServiceCertif();
                Certif Ev = new Certif(ET.getIdc());
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
    
}
