/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.hypocampus.models.Project;
import com.hypocampus.models.Sprint;
import com.hypocampus.models.meeting;
import com.hypocampus.models.team;
import com.hypocampus.services.ServiceMeeting;
import com.hypocampus.services.ServiceTeam;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.File;

/**
 * FXML Controller class
 *
 * @author 21694
 */
public class AffichermeetingController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficher();
    }    
        // alert
      Alert alert = new Alert(Alert.AlertType.NONE);
    @FXML
    private AnchorPane ContentPane;
  @FXML
    private TableView<meeting> tab;

    @FXML
    private TableColumn<meeting, String> Colteamname;

    @FXML
    private TableColumn<meeting, String> Coldescription;

    @FXML
    private TableColumn<meeting, String> Colduration;

    @FXML
    private TableColumn<meeting, String> Colnbrmeeting;

    @FXML
    private Button supprimermeeting;

    @FXML
    private Button newmeeting;
    @FXML
    private Button pdf;

    @FXML
    void editmeeting(ActionEvent event) throws IOException {
    meeting ss = tab.getSelectionModel().getSelectedItem();
//             data2.removeAll(Pr);

   
   
   
                                  if (ss == null) {

                                  alert.setAlertType(Alert.AlertType.WARNING);
           
                                   // set content text
                                   alert.setContentText(" No meeting selected : Please select a meeting for edit ");
           
                                   // show the dialog
                                   alert.show();
                                }
     else{
                                      
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/modifiermeeting.fxml"));              
        Parent parent = loader.load();
        ContentPane.getChildren().setAll(parent);

            ModifiermeetingController controller =(ModifiermeetingController) loader.getController();
            controller.inflateUI(ss);
            controller.add(ss);

            
          }
    }
    
    @FXML
    void newmeetingAction(ActionEvent event) throws IOException {
   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/meeting.fxml"));
        ContentPane.getChildren().setAll(pane);
}
     public void afficher()
     {
           //ObservableList <Project> data =FXCollections.observableArrayList();
             ServiceMeeting sm =new ServiceMeeting();
             ServiceTeam st =new ServiceTeam();
     
                       
                    Colteamname.setCellValueFactory(new PropertyValueFactory<>("team_id"));
                    Coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
                    Colteamname.setCellValueFactory((TableColumn.CellDataFeatures<meeting, String> p) -> new ReadOnlyObjectWrapper(st.getById(p.getValue().getTeam_id()))); 
                    Colduration.setCellValueFactory(new PropertyValueFactory<>("duration"));
                    Colnbrmeeting.setCellValueFactory(new PropertyValueFactory<>("nbrmeeting"));
                    tab.setItems((ObservableList<meeting>) sm.afficher());
     }

    @FXML
    void supprimermeeting(ActionEvent event) {
            meeting m = tab.getSelectionModel().getSelectedItem();
             

   
   
   
                                  if (m == null) {

                                  alert.setAlertType(Alert.AlertType.WARNING);
           
                                   // set content text
                                   alert.setContentText(" Choix invalide ");
           
                                   // show the dialog
                                   alert.show();
                                }
                                   else{
                                         ServiceMeeting sP =new ServiceMeeting();
                                     meeting P =new meeting(m.getId());
                                       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation ");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Vous voulez vraiment supprimer ce meeting ");
                                    Optional<ButtonType> action = alert.showAndWait();
                                    if (action.get() == ButtonType.OK) {
                                       sP.supprimer(P);
                                       afficher();

                                    }
                                  }
    }
    
        @FXML
    void pdfAction(ActionEvent event) {
           meeting ss = tab.getSelectionModel().getSelectedItem();
        if (ss == null) {

              alert.setAlertType(Alert.AlertType.WARNING);
           
              alert.setContentText(" Choix invalide ");
           
              alert.show();
                       }
        else {
                                     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation ");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Voulez-vous vraiment fichier pdf pour cette réunion?");
                                    Optional<ButtonType> action = alert.showAndWait();
                                    if (action.get() == ButtonType.OK) {
                                      pdf(ss);

                                    }
        }
           
    }
     void pdf(meeting m )
     {

try {
           //Path 
         OutputStream file = new FileOutputStream(new File("C:\\Users\\utilisateur\\Desktop\\HypocampusJava\\src\\com\\hypocampus\\uploads\\meeting.pdf"));



            Document document = new Document();

            PdfWriter.getInstance(document, file);
           

            document.open();
            ServiceTeam sP =new ServiceTeam();
             document.add(new Paragraph("------------------------------------------------------------BIENVENU-------------------------------------------------------"));
             document.add(new Paragraph("Nom de groupe :"+ sP.getById(m.getTeam_id())));
             System.out.println(sP.getById(m.getId()));
             document.add(new Paragraph("**************************************************************************"));
             document.add(new Paragraph(" Duration:  "+m.getDuration()));
             document.add(new Paragraph(" Nombre de réunions:  "+m.getNbrmeeting()));
             document.add(new Paragraph("*************"
                                       + "**************************"));
             document.add(new Paragraph(" Description:  "+m.getDescription()));
             
/*
             PdfPTable my_first_table = new PdfPTable(3);
             PdfPCell table_cell;
             table_cell=new PdfPCell(new Phrase("aza"));
              my_first_table.addCell(table_cell);
              table_cell=new PdfPCell(new Phrase("zaazd"));
               my_first_table.addCell(table_cell);
               table_cell=new PdfPCell(new Phrase(5));
                my_first_table.addCell(table_cell);
        
             // my_first_table.addCell(table_cell);
              // document.add(new Paragraph(getquantite(4)));

           document.add( my_first_table);    

  */
            document.close();
             
            file.close();


        } catch (Exception e) {


            e.printStackTrace();


     }
}
    
    
    
}
