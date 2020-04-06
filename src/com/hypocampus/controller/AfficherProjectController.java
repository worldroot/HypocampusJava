/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Project;
import com.hypocampus.models.Sprint;
import com.hypocampus.services.ServiceProject;
import com.hypocampus.utils.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
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

    }    
    
    @FXML
    private AnchorPane ContentPane;
    @FXML
    private Pagination pagination;
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
    private TableColumn<Sprint, String> Colprogress;
    @FXML
    private Button affSprint;
    @FXML
    private Button history;
    
    
    private List<Project> L = new ArrayList();
    public List <Project> data2 ;
    private final ObservableList<Project> dataList = FXCollections.observableArrayList();
        
    int from =0,to=0;
    int intempage=3;
    Connection cnx = DataSource.getInstance().getCnx();
    
    
    
     public void afficher()
     {
           //ObservableList <Project> data =FXCollections.observableArrayList();
             ServiceProject sP =new ServiceProject();

                    Colprojetname.setCellValueFactory(new PropertyValueFactory<>("name"));
                    Colowner.setCellValueFactory(new PropertyValueFactory<>("owner"));
                    Colstartdate.setCellValueFactory(new PropertyValueFactory<>("start_date"));
                    Colenddate.setCellValueFactory(new PropertyValueFactory<>("end_date"));
                    Coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
                    Colprogress.setCellValueFactory(new PropertyValueFactory<>("progressbar"));
                    
                
                            
                    
                    //Pagination
                    int pagecount=(sP.affPagination()/intempage)+1;
                    pagination.setPageCount(pagecount);
                    pagination.setPageFactory(this::creatPage);
                    tab.setItems((ObservableList<Project>) sP.afficher());
     }
    @FXML
    void newProjectAction(ActionEvent event) throws IOException {
   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/Project.fxml"));
        ContentPane.getChildren().setAll(pane);
    }
    
        @FXML
    void historyAction(ActionEvent event) throws IOException {
   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/historyProject.fxml"));
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
                 Project P =new Project(Pr.getId(),1);
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation ");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Vous voulez vraiment supprimer ce projet ");
                                    Optional<ButtonType> action = alert.showAndWait();
                    if (action.get() == ButtonType.OK) {
                                       sP.modifier_history(P);
                                       afficher();

                                                       }
                                  }
 
    }
    
    
    
    @FXML
    void editprojet(ActionEvent event) throws IOException {
        
         Project Pr = tab.getSelectionModel().getSelectedItem();

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

            
          }
     
    }

        @FXML
    void affSprint(ActionEvent event) throws IOException {
         Project Pr = tab.getSelectionModel().getSelectedItem();

                                  if (Pr == null) {

                                    Image img = new Image("/com/hypocampus/uploads/error.png");
                                    Notifications n = Notifications.create()
                                                     .title("Error")
                                                     .text("No Project selected :  Please select a project to see their sprint")
                                                     .graphic(new ImageView(img))
                                                     .position(Pos.TOP_CENTER)
                                                      .hideAfter(Duration.seconds(5));
                                    n.darkStyle();
                                    n.show();
                                }
     else{
                                      
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/AfficherSprint.fxml"));              
            Parent parent = loader.load();
            ContentPane.getChildren().setAll(parent);

            AfficherSprintController controllerPS =(AfficherSprintController) loader.getController();
           controllerPS.inflateUI(Pr);
           controllerPS.recherche(Pr);

            
          }
     

    }

   
    
    
    
    
    
    
    
    
    
    
    
       public List<Project> gettabdata() {
       
        ObservableList <Project> ListProject =FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM projets WHERE history=0 LIMIT "+from+","+to+"";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
              ServiceProject sP =new ServiceProject();
             Image progress =new Image("/com/hypocampus/uploads/in progress.png");
            Image completed =new Image("/com/hypocampus/uploads/completed.png"); 
            ImageView progressbar ;
            while (rs.next()) {
                 if ((sP.getProgressC(rs.getInt("id"))== sP.getProgressI(rs.getInt("id"))) && (sP.getProgressI(rs.getInt("id"))!=0))
                                         {
                                       progressbar=new ImageView(completed);
                                         }
                                         /*
                                         else if(sP.getProgressI(projects.get(i).getId())==0) {
                                         
                                         Colprogress.setCellValueFactory((TableColumn.CellDataFeatures<String, String> p) -> new ReadOnlyObjectWrapper(new ImageView(progress)));
                                         }
                                         */
                                         else{
                                        progressbar=new ImageView(progress);
                                         }
                
                
                ListProject.add(new Project(rs.getInt("id"), rs.getString("projet_name"),rs.getString("owner")
                ,rs.getDate("start_date"), rs.getDate("end_date"),rs.getString("description"),rs.getInt("history"),progressbar));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return ListProject;
    }
   private Node creatPage(int pageIndex) 
   {
       from=pageIndex *intempage;
       to=intempage;
       tab.setItems((ObservableList<Project>) gettabdata());

       return tab;
   }

    
    //recherche 
       /*  
    void recherche(){
    
           ServiceProject sP =new ServiceProject();
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Project> filteredData = new FilteredList<>((ObservableList<Project>) gettabdata(), b -> true);
		
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
*/
}
