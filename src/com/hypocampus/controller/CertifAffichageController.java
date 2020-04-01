/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import static com.hypocampus.controller.EditProjectController.LOCAL_DATE;
import com.hypocampus.models.Certif;
import com.hypocampus.models.Event;
import com.hypocampus.services.ServiceCertif;
import com.hypocampus.services.ServiceEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
public class CertifAffichageController implements Initializable {

    List listev = new ArrayList();
    Certif c;
    ServiceCertif ev = new ServiceCertif();
    ServiceEvent sv = new ServiceEvent();
    public ObservableList<Certif> list = FXCollections.observableArrayList(ev.afficher());
    
    @FXML
    private TableView<Certif> TableC;
    @FXML
    private TableColumn<Certif, String> coltitre;
    @FXML
    private TableColumn<Certif, Integer> colpoint;
    @FXML
    private TableColumn<Certif, Date> colDatec;
    @FXML
    private AnchorPane SmallPane;
    @FXML
    private Button SuppAction;
    @FXML
    private TextField Uppoint;
    @FXML
    private DatePicker Updatec;
    
    @FXML
    private ImageView Retour;
    @FXML
    private Button ModifAction;
    @FXML
    private ComboBox<String> ComTitre;
     private int current_id;
    @FXML
    private TextField search;
   
  public void views() throws SQLException {  
      
        
        ServiceEvent se =new ServiceEvent();
        listev = ev.afficher();
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
            //For Combo List des titres d'event
                final ObservableList options=FXCollections.observableArrayList();
                List<Event> Events= sv.afficher();
                for(int i=0;i<Events.size();i++)
                {
                    options.add(Events.get(i).getTitreEvent());

                } 
                ComTitre.setItems(options);
                System.out.println("Options:"+ options);
       
       views();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
    }
         TableC.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
                    
                    Certif rowData = TableC.getSelectionModel().getSelectedItem();
                    /**
                     * fill the fields with the selected data *
                     */
                    //Event et = ComTitre.getSelectionModel().getSelectedItem();
                      // LocalDate df= Updatec.getValue();
           
                      String t=sv.GetById(rowData.getTitrec());
                    ComTitre.setValue(t);
                    Uppoint.setText(Integer.toString(rowData.getPointc()));
                    Updatec.setValue(LOCAL_DATE(rowData.getDatec().toString()));
                    current_id = rowData.getIdc();
    
                }
            }
        });
         
       
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

   
    
    
        @FXML
    private void btnModifAction(ActionEvent event) throws IOException, SQLException {    
        
        
           
            ServiceCertif ps = new ServiceCertif();
            String event_combo=ComTitre.getValue();
            LocalDate df= Updatec.getValue();
            Date dateE=Date.valueOf(df.toString());
           
        
            int idEvent=sv.getTitreId(event_combo);
            Certif a =new Certif(current_id,idEvent, Integer.parseInt(Uppoint.getText()),dateE);
            ps.modifier(a);
            AnchorPane redirected;
                        redirected = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/CertifAffichage.fxml")); 
                        SmallPane.getChildren().setAll(redirected);
            /**
             * refreshing the table view *
             */
          
            TableC.setItems(list);


                             
    }

    @FXML
    private void RetourAction(MouseEvent event) throws IOException {
         AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/MenuEvent.fxml"));
         SmallPane.getChildren().setAll(pane);
    }
    
    public static final LocalDate NOW_LOCAL_DATE (){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date , formatter);
        return localDate;
    }




    
}
