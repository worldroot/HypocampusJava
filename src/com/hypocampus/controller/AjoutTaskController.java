/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Task;
import com.hypocampus.services.ServiceTask;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author mehdibehira
 */
public class AjoutTaskController implements Initializable {

    @FXML
    private Text TitreBacklog;
    @FXML
    private TextArea DescriptionTask;
    @FXML
    private TextField TItreTask;
    @FXML
    private ComboBox<Integer> StoryPointsList;
    @FXML
    private DatePicker Deadline;
    @FXML
    private ComboBox<String> EtatTask;
    @FXML
    private ComboBox<Integer> Priority;
    @FXML
    private Text TitreTask;
    @FXML
    private Text DescriptionFTitre;
    @FXML
    private Text StoryPointsTitre;
    @FXML
    private Text DeadlineTitre;
    @FXML
    private Text UserTItre;
    @FXML
    private Text EtatTitre;
    @FXML
    private Text PriorityTitre;
    @FXML
    private Label BacklogId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initForm();
        
    }    
    
    public void setBacklogId(String id){
        BacklogId.setText(id);
    }
    
    
    
    public void initForm(){
        //init etat task
        EtatTask.getItems().addAll("To Do", "Doing","Done");
        // init priority
        Priority.getItems().addAll(1, 2, 3);
        // init story points list
        StoryPointsList.getItems().addAll(0,1,2,3,5,8,13,21); 
    }
    
    @FXML
    private void SubmitTask(ActionEvent event) {
        ServiceTask  ST =new ServiceTask();
        String title = TItreTask.getText();
        String description_fonctionnel = DescriptionTask.getText();
        String description_technique = "";
        int story_points = StoryPointsList.getSelectionModel().getSelectedItem();
        long millis=System.currentTimeMillis();
        Date created_date =new java.sql.Date(millis);
        
      //  Date finished_date = new Date(Deadline.getValue().getYear()-1900, Deadline.getValue().getMonthValue()-1, Deadline.getValue().getDayOfMonth());
        Date finished_date = Date.valueOf(Deadline.getValue().toString());
        
        String state = EtatTask.getSelectionModel().getSelectedItem();
        int priority= Priority.getSelectionModel().getSelectedItem();
        int archive = 0;
        // to implement sprint id after 
        int sprint_id =2;
                
        Task t = new Task(Integer.parseInt(BacklogId.getText()), title, description_fonctionnel, description_technique, story_points, created_date, finished_date, state, priority, archive, sprint_id);
        
        ST.ajouter(t);
        
    }
    
}
