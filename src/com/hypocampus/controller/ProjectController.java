/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Project;
import com.hypocampus.services.ServiceProject;
import com.hypocampus.utils.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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
public class ProjectController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    afficherUser();
    }

    @FXML
    private AnchorPane ContentPaneAjouP;
    @FXML
    private Button ajouterProject;
    
    @FXML
    private Button backAffprojet;

    @FXML
    private ComboBox<String> NameEmployee;

    @FXML
    private TextField NameProject;
    @FXML
    private TextArea description;
    @FXML
    private Text Description;

    @FXML
    private DatePicker StartDateProject;

    @FXML
    private DatePicker EndDateProject;
    
// alert
      Alert alert = new Alert(Alert.AlertType.NONE);
      Connection cnx = DataSource.getInstance().getCnx();
     ObservableList  dataB =FXCollections.observableArrayList();
      
      
               public void afficherUser()
     {
         try {
            String requete = "SELECT username FROM fos_user";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dataB.add(rs.getString("username"));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         
         NameEmployee.setItems(dataB);
     }
      
      
      
    @FXML
    void ajouterProject(ActionEvent event) throws SQLException {
         String pr = NameEmployee.getSelectionModel().getSelectedItem();
            ServiceProject sP =new ServiceProject();
          Statement stmt = cnx.createStatement();
          String SQL = "SELECT * FROM projets WHERE  history=0 and projet_name ='" +NameProject.getText()+"'";
           ResultSet rs = stmt.executeQuery(SQL);
        if(!rs.next()){
       if (pr != null && !NameProject.getText().equals("")&& !description.getText().equals(""))
       {

           
           LocalDate ds= StartDateProject.getValue();
           LocalDate df= EndDateProject.getValue();
           
           Date dateS=Date.valueOf(ds.toString());//converting string into sql date
           Date datef=Date.valueOf(df.toString());

           if(ds.compareTo(df) < 0)
           {
            
               sP.ajouter(new Project(NameProject.getText(),pr,dateS,datef,description.getText(),00));
               Image img = new Image("/com/hypocampus/uploads/Check.png");
                            Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  Project ajouté")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(5));
               n.darkStyle();
               n.show();
               NameProject.setText("");
               EndDateProject.setValue(NOW_LOCAL_DATE());
               description.setText("");
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
       else {
           
           alert.setAlertType(Alert.AlertType.WARNING);
           
           // set content text
           alert.setContentText("verifié vos parametre ");
           
           // show the dialog
           alert.show();
           }
        }
        else {
             Image img = new Image("/com/hypocampus/uploads/error.png");
                              Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  Project déjà existe")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(5));
               n.darkStyle();
               n.show();

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
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/afficherProject.fxml"));
        ContentPaneAjouP.getChildren().setAll(pane);
    }
}
