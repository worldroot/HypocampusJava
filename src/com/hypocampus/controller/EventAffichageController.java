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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
public class EventAffichageController implements Initializable {
    
    ServiceEvent ev = new ServiceEvent();
    public ObservableList<Event> list = FXCollections.observableArrayList(ev.afficher());
        
    List listev = new ArrayList();
    Event e;
    
    private int current_id;
    private String imgp="empty.png";
    
    @FXML
    private ImageView btnRetourAction;
    @FXML
    private AnchorPane SmallPane;
    @FXML
    private TableView<Event> listEvent;
    @FXML
    private TableColumn<Event, String> colTitre;
    @FXML
    private TableColumn<Event, Integer> colCap;
    @FXML
    private TableColumn<Event, String> colType;
    @FXML
    private TableColumn<Event, String> coldd;
    @FXML
    private TableColumn<Event, String> coldf;
    @FXML
    private TableColumn<Event, String> colimg;
    @FXML
    private Button SupAction;

    
    @FXML
    private TextField search;
    @FXML
    private ImageView Stat;
    @FXML
    private Button ModifAction;
    @FXML
    private TextField UpTitre;
    @FXML
    private TextField UpCapa;
    @FXML
    private TextField UpType;
    @FXML
    private DatePicker UpDatedb;
    @FXML
    private DatePicker UpDatefn;
    @FXML
    private ImageView IMAGE;
    @FXML
    private TextField path;
    @FXML
    private ImageView upload;
    
    
    
    
  public void views() throws SQLException {  
      
        listev = ev.afficher();
        ObservableList<Event> l = FXCollections.observableArrayList(listev);  
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titreEvent"));
        colCap.setCellValueFactory(new PropertyValueFactory<>("numeroEvent"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeEvent"));
        coldd.setCellValueFactory(new PropertyValueFactory<>("dateEvent"));
        coldf.setCellValueFactory(new PropertyValueFactory<>("enddateEvent"));
        colimg.setCellValueFactory(new PropertyValueFactory<>("image_name"));
            System.out.println("Perfect Event Affichage!");
        listEvent.setEditable(true);
        listEvent.setItems(l);
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
        //Recherche Event
        FilteredList<Event> filteredData = new FilteredList<>(list, e -> true);
        search.setOnKeyReleased(e -> {
            search.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Event>) Event -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lower = newValue.toLowerCase();
                    if (Event.getTitreEvent().toLowerCase().contains(lower)) {
                        return true;
                    }

                    return false;
                });
            });
            SortedList<Event> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(listEvent.comparatorProperty());
            listEvent.setItems(sortedData);
        });
        
        //Modifier Event
        
        listEvent.setOnKeyReleased((KeyEvent e) -> {
             if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
                
                 // Show Image
                 int selectedIndex = listEvent.getSelectionModel().getSelectedIndex();
                    if (selectedIndex!=-1) {                     
                    Event pi = (Event) listEvent.getSelectionModel().getSelectedItem();    
                    // pc mehdi
                    IMAGE.setImage(new Image("file:/Users/mehdibehira/Desktop/Esprit_3/ProjetPi/Hypocampus/web/uploads/commentaires/files/"+pi.getImage_name()));                 

                    //pc ghassen
                    //IMAGE.setImage(new Image("file:/C:/Users/ASUS/Desktop/PiDev/Sprint%20Java/HypocampusJava/src/com/hypocampus/uploads/Event/"+pi.getImage_name()));                 
                         }  
                 // 
                 Event rowData = listEvent.getSelectionModel().getSelectedItem();
                 
                 /* fill the fields with the selected data */ 
                 UpTitre.setText(rowData.getTitreEvent());
                 UpType.setText(rowData.getTypeEvent());
                 UpCapa.setText(Integer.toString(rowData.getNumeroEvent()));
                 UpDatedb.setValue(LOCAL_DATE(rowData.getDateEvent().toString()));
                 UpDatefn.setValue(LOCAL_DATE(rowData.getEnddateEvent().toString()));
                 path.setText(rowData.getImage_name());
                 current_id = rowData.getIdev();    
             }
        });
        
        //IMG
        /*listEvent.setOnMouseClicked((MouseEvent e)->{
                                 
                    });*/
        
        
          
}   

    @FXML
    private void RetourAction(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/MenuEvent.fxml"));
        SmallPane.getChildren().setAll(pane);
    }

    @FXML
    private void btnSupAction(ActionEvent event) throws SQLException {
        
        Event ET = listEvent.getSelectionModel().getSelectedItem();
        
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
                ServiceEvent Sv = new ServiceEvent();
                Event Ev = new Event(ET.getIdev());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                              alert.setTitle("Confirmation ");
                              alert.setHeaderText(null);
                              alert.setContentText("Vous voulez vraiment supprimer ce Event ");
                              Optional<ButtonType> action = alert.showAndWait();
                              if (action.get() == ButtonType.OK) {
                                    Sv.supprimer(Ev);
                                    views();
                                    }
                              
                                  }     
    }


    @FXML
    private void ViewStat(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/EventStat.fxml"));
        SmallPane.getChildren().setAll(pane);
    }

    @FXML
    private void btnModifAction(ActionEvent event) throws IOException {
        
        if (!UpTitre.getText().equals("") && !UpType.getText().equals("") && !UpCapa.getText().equals("")) {
            
            LocalDate dd= UpDatedb.getValue();
            Date dateDb=Date.valueOf(dd.toString());
            
            LocalDate df= UpDatefn.getValue();
            Date dateFn=Date.valueOf(df.toString());

            if(dd.compareTo(df) < 0){
                
                 Event a = new Event(current_id,UpTitre.getText(),Integer.parseInt(UpCapa.getText()),UpType.getText(),dateDb,dateFn,path.getText(),null);
                 ev.modifier(a);
        
                AnchorPane redirected;
                       redirected = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/EventAffichage.fxml")); 
                       SmallPane.getChildren().setAll(redirected);
                /*refreshing the table view */
                listEvent.setItems(list);
                
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
                              .text("  Vérifier les champs vides ")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(5));
                        n.darkStyle();
                        n.show();
        }
      
            
       
 }
    
    public static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    @FXML
    private void uploadAction(MouseEvent event) {
        final FileChooser fileChooser = new FileChooser();

            Window stage = null;
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
               
                imgp=file.toString();
                // pc mehdi
                path.setText(file.getName());
              //pc ghassen
                // path.setText(imgp.substring(88));
                
            }
    }











    
}


