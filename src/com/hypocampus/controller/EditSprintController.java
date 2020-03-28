/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

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
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
public class EditSprintController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       afficherP();
    }    
    @FXML
    private AnchorPane ContentPanemodifS;
    @FXML
    private TextField SprintName;

    @FXML
    private DatePicker StartDateSprint;

    @FXML
    private DatePicker EndDateSprint;

    @FXML
    private Button ModifierSprint;

    @FXML
    private Button backEAffsprint;

    @FXML
    private ComboBox<Project> ProjectName;
    List <Sprint> data2 =new ArrayList() ;
   Connection cnx = DataSource.getInstance().getCnx();
    ObservableList  dataB =FXCollections.observableArrayList();

        
    
     public void inflateUI(Sprint s) {      
        ServiceProject sP =new ServiceProject();
        SprintName.setText(s.getName()); 
        ProjectName.setValue(sP.getById(s.getProject_id()));
        StartDateSprint.setValue(LOCAL_DATE(s.getStart_date_sprint().toString()));
        EndDateSprint.setValue(LOCAL_DATE(s.getEnd_date_sprint().toString()));
    
    }
     
    public void add(Sprint s) {
         
         data2.add(s);
    
    }
    
    public static final LocalDate LOCAL_DATE (String dateString){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(dateString, formatter);
    return localDate;
    }
    
    
    
    
    @FXML
    void ModifierSprint(ActionEvent event) throws IOException {
           LocalDate ds= StartDateSprint.getValue();
           LocalDate df= EndDateSprint.getValue();
           
           Date dateS=Date.valueOf(ds.toString());//converting string into sql date
           Date datef=Date.valueOf(df.toString());

           if(ds.compareTo(df) < 0)
           {
                       ServiceSprint ss =new ServiceSprint();

                      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/AfficherSprint.fxml"));              
                      Parent parent = loader.load();
                      //ContentPaneEditP.getChildren().setAll(parent);
                      AfficherSprintController idd =(AfficherSprintController) loader.getController();

                      idd.afficher();
                      System.out.println(data2.get(0).getId());
                      
                      
                 Project pr = ProjectName.getSelectionModel().getSelectedItem();
                 System.out.println(pr.getId());
                 ss.modifier(new Sprint(data2.get(0).getId(),SprintName.getText(),dateS,datef,pr.getId()));
                 Image img = new Image("/com/hypocampus/uploads/Check.png");
                            Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  Sprint modifier")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(5));
                              n.darkStyle();
                              n.show();
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
    void backEAffsprint(ActionEvent event)throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/AfficherSprint.fxml"));
        ContentPanemodifS.getChildren().setAll(pane);
    }
}
