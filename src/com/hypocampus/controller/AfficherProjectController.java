/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Project;
import com.hypocampus.services.ServiceProject;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author 21694
 */
public class AfficherProjectController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        afficher();
//data2.clear();
    }    
    /*
        @FXML
    private GridPane afficheProject;

    @FXML
    private Label projetname;

    @FXML
    private Label owner;

    @FXML
    private Label startdate;

    @FXML
    private Label enddate;

    @FXML
    private Label description;

    @FXML
    private FlowPane tab;
    */
    
    
    @FXML
    private AnchorPane ContentPane;
    @FXML
    private TableView<Project> tab;

    @FXML
    private TableColumn<Project, String> Colprojetname;

    @FXML
    private TableColumn<Project, String> Colowner;

    @FXML
    private TableColumn<Project, Date> Colstartdate;

    @FXML
    private TableColumn<Project, Date> Colenddate;

    @FXML
    private TableColumn<Project, String> Coldescription;
    private List<Project> L = new ArrayList();
    public List <Project> data2 ;
    
     public void afficher()
     {
           //ObservableList <Project> data =FXCollections.observableArrayList();
             ServiceProject sP =new ServiceProject();

                    Colprojetname.setCellValueFactory(new PropertyValueFactory<>("name"));
                    Colowner.setCellValueFactory(new PropertyValueFactory<>("owner"));
                    Colstartdate.setCellValueFactory(new PropertyValueFactory<>("start_date"));
                    Colenddate.setCellValueFactory(new PropertyValueFactory<>("end_date"));
                    Coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
                    tab.setItems((ObservableList<Project>) sP.afficher());
     }
    @FXML
    void newProjectAction(ActionEvent event) throws IOException {
   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/Project.fxml"));
        ContentPane.getChildren().setAll(pane);
    }
    @FXML
    void supprimerProject(ActionEvent event) {
            Project Pr = tab.getSelectionModel().getSelectedItem();
             

   
   
   
                                  if (Pr == null) {

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
                                         ServiceProject sP =new ServiceProject();
                                     Project P =new Project(Pr.getId());
                                       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation ");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Vous voulez vraiment supprimer ce projet ");
                                    Optional<ButtonType> action = alert.showAndWait();
                                    if (action.get() == ButtonType.OK) {
                                       sP.supprimer(P);
                                       afficher();

                                    }
                                  }
 
    }
    
    
    
    @FXML
    void editprojet(ActionEvent event) throws IOException {
        
         Project Pr = tab.getSelectionModel().getSelectedItem();
//             data2.removeAll(Pr);

   
   
   
                                  if (Pr == null) {

                                    Image img = new Image("/com/hypocampus/uploads/error.png");
                                    Notifications n = Notifications.create()
                                                     .title("Error")
                                                     .text("No Project selected :  Please select a Project for edit")
                                                     .graphic(new ImageView(img))
                                                     .position(Pos.TOP_CENTER)
                                                      .hideAfter(Duration.seconds(5));
                                    n.darkStyle();
                                    n.show();
                                }
     else{
                                      
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/EditProject.fxml"));              
        Parent parent = loader.load();
        ContentPane.getChildren().setAll(parent);

           EditProjectController controller =(EditProjectController) loader.getController();
            controller.inflateUI(Pr);
            controller.add(Pr);
          // controller.getIdPr1(Pr);
//          data2.add(Pr);
            System.out.println(Pr);
          /*
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit Project");
            stage.setScene(new Scene(parent));
            stage.show();
          */
            
          }
     
    }

    
   /*
    public void getidl()
    {
    for(int i = 0; i < data2.size(); i++)
    {  
        System.out.println(data2.get(0).getId()) ;
           
    }

    }
*/
}