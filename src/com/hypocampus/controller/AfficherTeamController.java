/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Project;
import com.hypocampus.models.Sprint;
import com.hypocampus.models.team;
import com.hypocampus.services.ServiceTeam;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author 21694
 */
public class AfficherTeamController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        afficher();
        recherche();
    }

  @FXML
    private AnchorPane ContentPane;

    @FXML
    private TableView<team> tab;


    @FXML
    private TableColumn<team, String> ColTeamname;

    @FXML
    private TableColumn<team,Date> Colcreationdate;
    @FXML
    private TextField filterField;

    @FXML
    private Button supprimerTeam;

    @FXML
    private Button newTeam;
    // alert
      Alert alert = new Alert(Alert.AlertType.NONE);

     public void afficher()
     {
           
                   ServiceTeam st =new ServiceTeam();

                    //ColTeamid.setCellValueFactory(new PropertyValueFactory<>("id"));
                    ColTeamname.setCellValueFactory(new PropertyValueFactory<>("teamname"));
                    Colcreationdate.setCellValueFactory(new PropertyValueFactory<>("dateofcreation"));
                    tab.setItems((ObservableList<team>) st.afficher());
     }
  
    @FXML
    void newTeamAction(ActionEvent event) throws IOException {
   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/Team.fxml"));
        ContentPane.getChildren().setAll(pane);
    }

    
    
    
    @FXML
    void supprimerTeam(ActionEvent event) {
            team tr = tab.getSelectionModel().getSelectedItem();
             

   
   
   
                                  if (tr == null) {

                                   alert.setAlertType(Alert.AlertType.WARNING);
           
                                   // set content text
                                   alert.setContentText(" Choix invalide ");
           
                                   // show the dialog
                                   alert.show();
         
                                }
                                   else{
                                       ServiceTeam st =new ServiceTeam();
                                     team t =new team(tr.getId());
                                     
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation ");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Vous voulez vraiment supprimer ce groupe ");
                                    Optional<ButtonType> action = alert.showAndWait();
                                    if (action.get() == ButtonType.OK) {
                                       st.supprimer(t);
                                       afficher();

                                    }
                                  }
    }
    @FXML
    void editTeam(ActionEvent event) throws IOException {
    team tr = tab.getSelectionModel().getSelectedItem();

   
   
   
                                  if (tr == null) {
                                   alert.setAlertType(Alert.AlertType.WARNING);
           
                                   // set content text
                                   alert.setContentText(" No Team selected : Please select a Team for edit ");
           
                                   // show the dialog
                                   alert.show();
             
                                }
     else{
                                      
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/ModifierTeam.fxml"));              
        Parent parent = loader.load();
        ContentPane.getChildren().setAll(parent);

            ModifierTeamController controller =(ModifierTeamController) loader.getController();
            controller.getTeamm(tr);
            controller.add(tr);
            //System.out.println(tr);
           
          }
     
    }

    
      void recherche(){
    
    ServiceTeam st =new ServiceTeam();

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<team> filteredData = new FilteredList<>((ObservableList<team>) st.afficher(), b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(team -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (team.getTeamname().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} 
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<team> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tab.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tab.setItems(sortedData);
               
        
    
    }
    
}
