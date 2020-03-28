/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import static com.hypocampus.controller.ProjectController.NOW_LOCAL_DATE;
import com.hypocampus.models.Project;
import com.hypocampus.models.Sprint;
import com.hypocampus.services.ServiceProject;
import com.hypocampus.services.ServiceSprint;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author 21694
 */
public class SprintController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       afficherP();
    }  
    
    @FXML
    private AnchorPane ContentPaneAjouS;

    @FXML
    private TextField SprintName;

    @FXML
    private DatePicker StartDateSprint;

    @FXML
    private DatePicker EndDateSprint;

    @FXML
    private Button AjouterSprint;

    @FXML
    private Button backAffsprint;

    @FXML
    private ComboBox<Project> ProjectName;
    
    ObservableList  dataB =FXCollections.observableArrayList();
    Connection cnx = DataSource.getInstance().getCnx();
    // alert
      Alert alert = new Alert(Alert.AlertType.NONE);
    
         public void afficherP()
     {
         try {
            String requete = "SELECT id,projet_name FROM projets";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dataB.add(new Project(rs.getInt("id"),rs.getString("projet_name")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         
         ProjectName.setItems(dataB);
     }
         
         
         
         
    @FXML
    void ajouterSprint(ActionEvent event) {           
           
       if (!SprintName.getText().equals(""))
       {
   
           ServiceSprint ss =new ServiceSprint();
           LocalDate ds= StartDateSprint.getValue();
           LocalDate df= EndDateSprint.getValue();
           
           Date dateS=Date.valueOf(ds.toString());//converting string into sql date
           Date datef=Date.valueOf(df.toString());

           if(ds.compareTo(df) < 0)
           {
             Project pr = ProjectName.getSelectionModel().getSelectedItem();
               ss.ajouter(new Sprint(SprintName.getText(),dateS,datef,pr.getId(),00));
             
               Image img = new Image("/com/hypocampus/uploads/Check.png");
                            Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  Sprint ajouté")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(5));
               n.darkStyle();
               n.show();
               SprintName.setText("");
               StartDateSprint.setValue(NOW_LOCAL_DATE());
           }
           else
           {
             Image img = new Image("/com/hypocampus/uploads/error.png");
             Notifications n = Notifications.create()
                              .title("Error")
                              .text("  vérifier date de création du Sprint")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(5));
               n.darkStyle();
               n.show();
               
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

    @FXML
    void backAffsprint(ActionEvent event) throws IOException {
   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/AfficherSprint.fxml"));
        ContentPaneAjouS.getChildren().setAll(pane);
    }

    
}
