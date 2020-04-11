/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Event;
import com.hypocampus.services.ServiceEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class EventController implements Initializable {

    @FXML
    private TextField TitreEvent;
    @FXML
    private TextField numEvent;
    @FXML
    private ComboBox<String> typeEvent;
    @FXML
    private Button btnvalider;
    @FXML
    private DatePicker dateEvent;
    @FXML
    private DatePicker endDateEvent;
    @FXML
    private AnchorPane SmallPane;
    @FXML
    private ImageView clearAction;
    @FXML
    private ImageView RetourAction;
    @FXML
    private ImageView upload;
    @FXML
    private TextField path;
    
    private String imgg="empty.png" ;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      typeEvent.getItems().addAll("Cours", "Formation", "Workshop"); 
    }    

    @FXML
    private void AddAction(ActionEvent event) throws IOException {
        
        //boolean test = false;
        //Date dateevent = new Date(dateEvent.getValue().getYear()-1900, dateEvent.getValue().getMonthValue()-1, dateEvent.getValue().getDayOfMonth());
        //Date enddateEvent = new Date(endDateEvent.getValue().getYear()-1900, endDateEvent.getValue().getMonthValue()-1, endDateEvent.getValue().getDayOfMonth());
        
        if (!TitreEvent.getText().equals("") && !numEvent.getText().equals("") && !dateEvent.getEditor().equals("") && !endDateEvent.getEditor().equals("") ) {
            
           ServiceEvent ev = new ServiceEvent();
           
           LocalDate ds= dateEvent.getValue();
           LocalDate df= endDateEvent.getValue();
           
           Date dateE=Date.valueOf(ds.toString());  //converting string into sql date
           Date datef=Date.valueOf(df.toString());
           
            if(ds.compareTo(df) < 0){

                Event e = new Event(TitreEvent.getText(), Integer.parseInt(numEvent.getText()),typeEvent.getSelectionModel().getSelectedItem(),dateE, datef, path.getText());
                ev.ajouter(e);
                Image img = new Image("/com/hypocampus/uploads/Check.png");
                                Notifications n = Notifications.create()
                                  .title("SUCCESS")
                                  .text("  Event ajouté")
                                  .graphic(new ImageView(img))
                                  .position(Pos.TOP_CENTER)
                                  .hideAfter(Duration.seconds(5));
                            n.darkStyle();
                            n.show();
                            
                AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/EventAffichage.fxml"));
                SmallPane.getChildren().setAll(pane);
                
                }
            
            else{
                Image img = new Image("/com/hypocampus/uploads/error.png");
                Notifications n = Notifications.create()
                              .title("ERROR")
                              .text("  Vérifier date de création d'event !")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(5));
                        n.darkStyle();
                        n.show();
            }
                
        
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
        
        
        
             
    }

 
    
    public static final LocalDate NOW_LOCAL_DATE (){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date , formatter);
        return localDate;
    }

    @FXML
    private void btnclearAction(MouseEvent event) {
        TitreEvent.clear();
        typeEvent.getSelectionModel().clearSelection();
        numEvent.clear();
        path.clear();
        dateEvent.getEditor().clear();
        endDateEvent.getEditor().clear();
    }

    @FXML
    private void btnRetourAction(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/MenuEvent.fxml"));
        SmallPane.getChildren().setAll(pane);
    }

    @FXML
    private void uploadAction(MouseEvent event) {
        
        final FileChooser fileChooser = new FileChooser();

            Window stage = null;
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                imgg=file.toString();
                // pc mehdi 
                path.setText(file.getName());
                // pcc ghassen
                //path.setText(imgg.substring(88));
            }
        }
        
    
    
}
