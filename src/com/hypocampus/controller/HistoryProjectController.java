/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Project;
import com.hypocampus.services.ServiceProject;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author 21694
 */
public class HistoryProjectController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficher();
        recherche();
    }    
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

    @FXML
    private Button SupprimerProject;

    @FXML
    private Button Restaurer;

    @FXML
    private TextField filterField;

    
    
    
    
         public void afficher()
     {
           //ObservableList <Project> data =FXCollections.observableArrayList();
             ServiceProject sP =new ServiceProject();

                    Colprojetname.setCellValueFactory(new PropertyValueFactory<>("name"));
                    Colowner.setCellValueFactory(new PropertyValueFactory<>("owner"));
                    Colstartdate.setCellValueFactory(new PropertyValueFactory<>("start_date"));
                    Colenddate.setCellValueFactory(new PropertyValueFactory<>("end_date"));
                    Coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
                    tab.setItems((ObservableList<Project>) sP.afficherN());
     }
    
    
    @FXML
    void RestaurerAction(ActionEvent event) {
         
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
                 Project P =new Project(Pr.getId(),0);
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation ");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Vous voulez vraiment Restaurer ce projet ");
                                    Optional<ButtonType> action = alert.showAndWait();
                    if (action.get() == ButtonType.OK) {
                                       sP.modifier_history(P);
                                       afficher();

                                                       }
                                  }
 
    }

    @FXML
    void SupprimerProject(ActionEvent event) {
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
    
     void recherche(){
    
           ServiceProject sP =new ServiceProject();
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Project> filteredData = new FilteredList<>((ObservableList<Project>) sP.afficherN(), b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Project -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Project.getName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} 
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Project> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tab.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tab.setItems(sortedData);
               
        
    
    }
}
