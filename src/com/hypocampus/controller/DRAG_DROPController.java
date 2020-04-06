/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Project;
import com.hypocampus.models.Sprint;
import com.hypocampus.models.Task;
import com.hypocampus.services.ServiceProject;
import com.hypocampus.services.ServiceSprint;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author 21694
 */
public class DRAG_DROPController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficher();
    }    
    @FXML
    private AnchorPane ContentPane;
    @FXML
    private Button backAffsprint;

    @FXML
    private ListView<String> to_do;

    @FXML
    private ListView<String> in_Progress;

    @FXML
    private ListView<String> Done;
        
        
         public void inflateUI(Sprint S) {      
         ServiceSprint ss =new ServiceSprint();
         //tab.setItems((ObservableList<Task>) ss.afficher_Sprintask(new Sprint(S.getId())));
         to_do.setItems((ObservableList<String>) ss.afficher_Sprintask_toDo(new Sprint(S.getId())));
         in_Progress.setItems((ObservableList<String>) ss.afficher_Sprintask_INProgress(new Sprint(S.getId())));
         Done.setItems((ObservableList<String>) ss.afficher_Sprintask_Done(new Sprint(S.getId())));
         //data2.add(P);
    } 
         public void afficher()
     {
                       
  

                              
     }
      
         
         
    @FXML
    void backAffsprint(ActionEvent event) throws IOException {
    AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/afficherProject.fxml"));
    
        ContentPane.getChildren().setAll(pane);
    }
}
