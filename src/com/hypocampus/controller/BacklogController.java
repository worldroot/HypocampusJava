/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Backlog;
import com.hypocampus.models.Project;
import com.hypocampus.models.Task;
import com.hypocampus.services.ServiceBacklog;
import com.hypocampus.services.ServiceCommentaire;
import com.hypocampus.services.ServiceProject;
import com.hypocampus.services.ServiceTask;
import com.hypocampus.utils.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author mehdibehira
 */


public class BacklogController implements Initializable {

    @FXML
    private Button AjoutAction;
    @FXML
    private AnchorPane AjoutBacklogPane;
    @FXML
    private ChoiceBox<Project> ListProjetAction;

    /**
     * Initializes the controller class.
     */
    
   
    @FXML
    private Button SubmitBacklogAction;
    @FXML
    private TableView<Backlog> TabBacklog;
    @FXML
    private TableColumn<Backlog, String> Project_id_column;
    @FXML
    private TableColumn<Backlog, Integer> Points_to_do_column;
    @FXML
    private TableColumn<Backlog, Integer> Points_in_progress_column;
    @FXML
    private TableColumn<Backlog, Integer> Points_done_column;
    @FXML
    private MenuItem EditAction;
    
    ServiceProject sp = new ServiceProject(); 
    @FXML
    private AnchorPane ContentPane;
    @FXML
    private MenuItem deleteAction;
    @FXML
    private MenuItem Taches;
    @FXML
    private Text TitreListeBacklog;
    @FXML
    private Text TitreAjoutBacklog;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        afficherBacklogs();
        // TODO
    }
    
    
    public void afficherBacklogs(){
        TitreAjoutBacklog.setVisible(false);
        TitreListeBacklog.setVisible(true);
        TabBacklog.setVisible(true);
        ServiceBacklog sb = new ServiceBacklog();
        ServiceProject sp =new ServiceProject(); 
        // Backlog_id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
         Project_id_column.setCellValueFactory(new PropertyValueFactory<>("project_id"));
         Project_id_column.setCellValueFactory((CellDataFeatures<Backlog, String> p) -> new ReadOnlyObjectWrapper(sp.GetById(p.getValue().getProject_id()))); 
         Points_to_do_column.setCellValueFactory(new PropertyValueFactory<>("points_to_do")); 
         Points_in_progress_column.setCellValueFactory(new PropertyValueFactory<>("points_in_progress")); 
         Points_done_column.setCellValueFactory(new PropertyValueFactory<>("points_done"));
         
         TabBacklog.setItems((ObservableList<Backlog>)sb.afficher());
    }

    @FXML
    private void AjouterBacklog(MouseEvent event) throws SQLException {
  
       
         final ObservableList<Project> options = FXCollections.observableArrayList();
          List<Project> projects= sp.afficher(); 
              for(int i=0;i<projects.size();i++) 
                { 
                    options.add(projects.get(i)); 

                }
              ListProjetAction.setItems(options);
         
         TabBacklog.setVisible(false);
        AjoutBacklogPane.setVisible(true);
        TitreAjoutBacklog.setVisible(true);
        TitreListeBacklog.setVisible(false);
 
    }

    @FXML
    private void SubmitBacklogBtn(ActionEvent event) throws SQLException, IOException {
        ServiceBacklog sb = new ServiceBacklog();
        Project pr = ListProjetAction.getSelectionModel().getSelectedItem();
        
    
        Connection cnx = DataSource.getInstance().getCnx();

        Backlog B = new Backlog(0,0,0,pr.getId());
        sb.ajouter(B);
       
         Image img = new Image("/com/hypocampus/uploads/Check.png");
         Notifications n = Notifications.create()
           .title("SUCCESS")
           .text("Backlog Ajouté")
           .graphic(new ImageView(img))
           .position(Pos.TOP_CENTER)
           .hideAfter(Duration.seconds(5));
               n.darkStyle();
               n.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/backlog.fxml"));              
        Parent parent = loader.load();
        ContentPane.getChildren().setAll(parent);
                BacklogController controller =(BacklogController) loader.getController();
        controller.afficherBacklogs();
        
    }

    @FXML
    private void EditBacklog(ActionEvent event) throws IOException {
        
               
         Backlog ba = TabBacklog.getSelectionModel().getSelectedItem();
                         
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/EditBacklog.fxml"));              
        Parent parent = loader.load();
        ContentPane.getChildren().setAll(parent);
        EditBacklogController controller =(EditBacklogController) loader.getController();
        controller.inflateUI(ba);
         System.out.println(ba);

            
        
    }

    @FXML
    private void DeleteBacklog(ActionEvent event) {
        Backlog ba = TabBacklog.getSelectionModel().getSelectedItem();
        ServiceBacklog sb =new ServiceBacklog();
        ServiceTask ST = new ServiceTask();
        ServiceCommentaire SC = new ServiceCommentaire();
   
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("Confirmation ");
       alert.setHeaderText(null);
       alert.setContentText("Vous voulez vraiment supprimer ce Backlog ainsi que toutes ces taches et commentaires?? ");
       Optional<ButtonType> action = alert.showAndWait();
       if (action.get() == ButtonType.OK) {
           
           List<Task> tasks = ST.afficherParBacklog(0, 0, ba);
           for(int i =0; i< tasks.size(); i++){
               SC.supprimerAllCommentaireFromTask(i);
           }
          ST.supprimerAllTaskFromBacklog(ba.getId());
          sb.supprimer(ba);
          afficherBacklogs();
                   Image img = new Image("/com/hypocampus/uploads/Check.png");
         Notifications n = Notifications.create()
           .title("SUCCESS")
           .text("Backlog, taches et commentaires Supprimés")
           .graphic(new ImageView(img))
           .position(Pos.TOP_CENTER)
           .hideAfter(Duration.seconds(5));
               n.darkStyle();
               n.show();
       }
        
        
    }

    @FXML
    public void BacklogTasks(ActionEvent event) throws IOException {
        ServiceTask ST  = new ServiceTask();
         Backlog ba = TabBacklog.getSelectionModel().getSelectedItem();
        ServiceBacklog sb =new ServiceBacklog();
        
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/IndexTask.fxml"));
           Parent root = loader.load();
       
        IndexTaskController ITC = loader.getController();
        ITC.affichageTasks(ST.afficherParBacklog(0, 0, ba));
        ITC.setBacklogId(Integer.toString(ba.getId()));
        ITC.setProjectBacklogId(ba.getProject_id());

        
        // AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/IndexTask.fxml"));
        ContentPane.getChildren().setAll(root);
        
       
        
    }
    
    
}
