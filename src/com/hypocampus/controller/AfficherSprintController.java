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
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class AfficherSprintController implements Initializable {

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
    private TableView<Sprint> tab;

    @FXML
    private TableColumn<Sprint, String> Colsprintname;

    @FXML
    private TableColumn<Sprint, Date> Colstartdate;

    @FXML
    private TableColumn<Sprint, Date> Colenddate;

    @FXML
    private TableColumn<Sprint, String> Colnameproject;

    @FXML
    private Button supprimerSprint;

    @FXML
    private Button newSprint;
    @FXML
    private TextField filterField;



    @FXML
    void newSprintAction(ActionEvent event) throws IOException {
   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/Sprint.fxml"));
        ContentPane.getChildren().setAll(pane);
    }
     public void afficher()
     {
           //ObservableList <Project> data =FXCollections.observableArrayList();
             ServiceSprint ss =new ServiceSprint();
             ServiceProject sP =new ServiceProject();
                       
                    Colsprintname.setCellValueFactory(new PropertyValueFactory<>("name"));
                    Colstartdate.setCellValueFactory(new PropertyValueFactory<>("start_date_sprint"));
                    Colenddate.setCellValueFactory(new PropertyValueFactory<>("end_date_sprint"));
                    Colnameproject.setCellValueFactory((CellDataFeatures<Sprint, String> p) -> new ReadOnlyObjectWrapper(sP.getById(p.getValue().getProject_id()))); 
                    //Colnameproject.setCellValueFactory(new PropertyValueFactory<>("project_id"));
                    tab.setItems((ObservableList<Sprint>) ss.afficher());
     }
     
     void recherche(){
    
        ServiceSprint sP =new ServiceSprint();
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Sprint> filteredData = new FilteredList<>((ObservableList<Sprint>) sP.afficher(), b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Sprint -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Sprint.getName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} 
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Sprint> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tab.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tab.setItems(sortedData);
               
        
    
    }
    @FXML
    void supprimerSprint(ActionEvent event) {

       Sprint s = tab.getSelectionModel().getSelectedItem();
             

   
   
   
                                  if (s == null) {

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
                                        ServiceSprint sP =new ServiceSprint();
                                        Sprint ss =new Sprint(s.getId());
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Confirmation ");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Vous voulez vraiment supprimer ce sprint ");
                                        Optional<ButtonType> action = alert.showAndWait();
                                        if (action.get() == ButtonType.OK) {
                                           sP.supprimer(ss);
                                           afficher();

                                          }
                                       }
    }
    @FXML
    void editsprint(ActionEvent event) throws IOException {
        
         Sprint ss = tab.getSelectionModel().getSelectedItem();
//             data2.removeAll(Pr);

   
   
   
                                  if (ss == null) {

                                    Image img = new Image("/com/hypocampus/uploads/error.png");
                                    Notifications n = Notifications.create()
                                                     .title("Error")
                                                     .text("No Sprint selected :  Please select a Sprint for edit")
                                                     .graphic(new ImageView(img))
                                                     .position(Pos.TOP_CENTER)
                                                      .hideAfter(Duration.seconds(2));
                                    n.darkStyle();
                                    n.show();
                                }
     else{
                                      
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/EditSprint.fxml"));              
        Parent parent = loader.load();
        ContentPane.getChildren().setAll(parent);

           EditSprintController controller =(EditSprintController) loader.getController();
            controller.inflateUI(ss);
            controller.add(ss);

            
          }
    }
    
}
