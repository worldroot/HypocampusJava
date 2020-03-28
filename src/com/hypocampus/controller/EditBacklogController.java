/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Backlog;
import com.hypocampus.models.Project;
import com.hypocampus.services.ServiceBacklog;
import com.hypocampus.services.ServiceProject;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author mehdibehira
 */
public class EditBacklogController implements Initializable {

    @FXML
    private Button ModifierBacklog;
    @FXML
    private Button BackBacklog;
    @FXML
    private ComboBox<Project> comboBox;
    @FXML
    private TextField BacklogId;
    @FXML
    private AnchorPane ContentPane;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void ModifierBacklog(ActionEvent event) {
        
        ServiceBacklog sb = new ServiceBacklog();
        Project pr = comboBox.getSelectionModel().getSelectedItem();
        String id = BacklogId.getText();
        Backlog B = new Backlog(Integer.parseInt(id),pr.getId());
        sb.modifierProject(B);
    }

    @FXML
    private void BackBacklog(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/backlog.fxml"));
        ContentPane.getChildren().setAll(pane);
        
    }
    
    public void inflateUI(Backlog B) throws IOException {
        System.out.println(B.toString());
        ServiceProject sp = new ServiceProject(); 

        System.out.println(B.getProject_id());
 
     final ObservableList<Project> options = FXCollections.observableArrayList();
          List<Project> projects= sp.afficher(); 
              for(int i=0;i<projects.size();i++) 
                { 
                    options.add(projects.get(i)); 

                }
     comboBox.setItems(options);
                Project default_project= sp.GetByIdProject(B.getProject_id());
               
       
          comboBox.getSelectionModel().select(default_project);
          BacklogId.setText(Integer.toString(B.getId()));
          
    
    }  
    
}
