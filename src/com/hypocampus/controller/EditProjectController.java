/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import static com.hypocampus.controller.ProjectController.NOW_LOCAL_DATE;
import com.hypocampus.models.Project;
import com.hypocampus.services.ServiceProject;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author 21694
 */
public class EditProjectController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
 
 @FXML
    private AnchorPane ContentPaneEditP;

    @FXML
    private TextField NameEmployee;

    @FXML
    private TextField NameProject;

    @FXML
    private DatePicker StartDateProject;

    @FXML
    private DatePicker EndDateProject;

    @FXML
    private TextArea description;

    @FXML
    private Text Description;
    @FXML
    private Button ModifierProjet;
    @FXML
    private Button MPbackAffprojet;
    List <Project> data2 =new ArrayList() ;

    @FXML
    void MPbackAffprojet(ActionEvent event) throws IOException {
   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/afficherProject.fxml"));
        ContentPaneEditP.getChildren().setAll(pane);
    }
    
    
     public void inflateUI(Project P) {      

        NameEmployee.setText(P.getOwner());
        NameProject.setText(P.getName());
      
        StartDateProject.setValue(LOCAL_DATE(P.getStart_date().toString()));
        EndDateProject.setValue(LOCAL_DATE(P.getEnd_date().toString()));
        description.setText(P.getDescription());
    
    }    
    public void add(Project P) {
         
         data2.add(P);
    
    }
     public static final LocalDate LOCAL_DATE (String dateString){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(dateString, formatter);
    return localDate;
}
     
         @FXML
    void ModifierProjet(ActionEvent event) throws IOException {
           LocalDate ds= StartDateProject.getValue();
           LocalDate df= EndDateProject.getValue();
           
           Date dateS=Date.valueOf(ds.toString());//converting string into sql date
           Date datef=Date.valueOf(df.toString());

           if(ds.compareTo(df) < 0)
           {
               ServiceProject sP =new ServiceProject();
             
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/afficherProject.fxml"));              
        Parent parent = loader.load();
        //ContentPaneEditP.getChildren().setAll(parent);
        AfficherProjectController idd =(AfficherProjectController) loader.getController();

idd.afficher();
//System.out.println(data2.get(0).getId());
               
               sP.modifier(new Project(data2.get(0).getId(),NameProject.getText(),NameEmployee.getText(),dateS,datef,description.getText(),00));
               Image img = new Image("/com/hypocampus/uploads/Check.png");
                            Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  Project modifier")
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
                              .text("  vérifier date de création du projet")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(5));
               n.darkStyle();
               n.show();
     
               
           }
    }
      
}
