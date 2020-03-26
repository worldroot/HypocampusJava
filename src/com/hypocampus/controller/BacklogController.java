/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Backlog;
import com.hypocampus.models.Project;
import com.hypocampus.services.ServiceBacklog;
import com.hypocampus.utils.DataSource;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    private ChoiceBox<?> ListProjetAction;

    /**
     * Initializes the controller class.
     */
    
   
    @FXML
    private Button SubmitBacklogAction;
    @FXML
    private TableView<Backlog> TabBacklog;
    @FXML
    private TableColumn<Backlog, Integer> Backlog_id_column;
    @FXML
    private TableColumn<Backlog, Integer> Project_id_column;
    @FXML
    private TableColumn<Backlog, Integer> Points_to_do_column;
    @FXML
    private TableColumn<Backlog, Integer> Points_in_progress_column;
    @FXML
    private TableColumn<Backlog, Integer> Points_done_column;
    @FXML
    private MenuItem EditAction;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficherBacklogs();
        // TODO
    }
    
    
    public void afficherBacklogs(){
        TabBacklog.setVisible(true);
        ServiceBacklog sb = new ServiceBacklog();
         Backlog_id_column.setCellValueFactory(new PropertyValueFactory<>("id")); 
         Project_id_column.setCellValueFactory(new PropertyValueFactory<>("project_id")); 
         Points_done_column.setCellValueFactory(new PropertyValueFactory<>("points_to_do")); 
         Points_in_progress_column.setCellValueFactory(new PropertyValueFactory<>("points_in_progress")); 
         Points_done_column.setCellValueFactory(new PropertyValueFactory<>("points_done"));
         
         TabBacklog.setItems((ObservableList<Backlog>)sb.afficher());
    }

    @FXML
    private void AjouterBacklog(MouseEvent event) throws SQLException {
         final ObservableList options = FXCollections.observableArrayList();
         TabBacklog.setVisible(false);
        AjoutBacklogPane.setVisible(true);
        Connection cnx = DataSource.getInstance().getCnx();
                // first I execute the query that select name of department one by one
        String query = "select projet_name from projets";
        PreparedStatement statement = cnx.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        while(set.next()){
            options.add(set.getString("projet_name"));
        }
        ListProjetAction.setItems(options);
        statement.close();
        set.close();
        
        
       
    }

    @FXML
    private void SubmitBacklogBtn(ActionEvent event) throws SQLException {
        ServiceBacklog sb = new ServiceBacklog();
        String project_name = (String) ListProjetAction.getSelectionModel().getSelectedItem();
        Connection cnx = DataSource.getInstance().getCnx();
        String query = "select * from projets where projet_name= ?";
        PreparedStatement statement = cnx.prepareStatement(query);
        System.out.println(project_name);
        statement.setString(1, project_name);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
        System.out.println(set.getInt("id"));
        Backlog B = new Backlog(0,0,0,set.getInt("id"));
        sb.ajouter(B);
        }
       statement.close();
       set.close();
       
       

        
    }

    @FXML
    private void EditBacklog(ActionEvent event) {
        
    }
    
    
}
