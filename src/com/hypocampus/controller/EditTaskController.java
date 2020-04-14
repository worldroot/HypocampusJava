/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import static com.hypocampus.controller.AjoutTaskController.LOCAL_DATE;
import static com.hypocampus.controller.EditProjectController.LOCAL_DATE;
import com.hypocampus.models.Backlog;
import com.hypocampus.models.Project;
import com.hypocampus.models.Sprint;
import com.hypocampus.models.Task;
import com.hypocampus.services.ServiceProject;
import com.hypocampus.services.ServiceSprint;
import com.hypocampus.services.ServiceTask;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author mehdibehira
 */
public class EditTaskController implements Initializable {

    @FXML
    private Text TitreBacklog;
    @FXML
    private TextArea DescriptionTask;
    @FXML
    private TextField TItreTask;
    @FXML
    private Text TitreTask;
    @FXML
    private Text DescriptionFTitre;
    @FXML
    private ComboBox<Integer> StoryPointsList;
    @FXML
    private Text StoryPointsTitre;
    @FXML
    private DatePicker Deadline;
    @FXML
    private Text DeadlineTitre;
    @FXML
    private Text UserTItre;
    @FXML
    private Text EtatTitre;
    @FXML
    private ComboBox<String> EtatTask;
    @FXML
    private Text PriorityTitre;
    @FXML
    private ComboBox<Integer> Priority;
    @FXML
    private Label BacklogId;
    @FXML
    private AnchorPane ContentPane;

    int taskid;
    public int  backlog_project_id;
    @FXML
    private ComboBox<Sprint> SprintTask2;
    @FXML
    private Text SprintTitre1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initForm();
    }
    
        public void initForm(){
        //init etat task
        EtatTask.getItems().addAll("To Do", "Doing","Done");
        // init priority
        Priority.getItems().addAll(1, 2, 3);
        // init story points list
        StoryPointsList.getItems().addAll(0,1,2,3,5,8,13,21); 
    }
        
                public void set_backlog_project_id(int id){
        backlog_project_id = id;
            System.out.println(backlog_project_id);
    }
    
        public void inflateUI(Task T) throws IOException {
            TItreTask.setText(T.getTitle());
            DescriptionTask.setText(T.getDescription_fonctionnel());
            StoryPointsList.getSelectionModel().select(T.getStory_points());
            Deadline.setValue(LOCAL_DATE(T.getFinished_date().toString()));
           // Deadline.setValue(LocalDate.of(T.getFinished_date().getYear()+1900, T.getFinished_date().getMonth()+1, T.getFinished_date().getDay()));
            EtatTask.getSelectionModel().select(T.getState());
            Priority.getSelectionModel().select(T.getPriority()-1);
            BacklogId.setText(Integer.toString(T.getBacklog_id()));
            taskid = T.getId();

    }
        
            public void liste_sprints(int backlog_project_id, int sprint_id){
                ServiceSprint SS = new ServiceSprint();
                 final ObservableList<Sprint> options = FXCollections.observableArrayList();
                 
     
          List <Sprint>sprints= SS.afficher_SprintProject_GetById(backlog_project_id);
          
             for(int i=0;i<sprints.size();i++) 
                { 
                    options.add(sprints.get(i)); 

              }
              SprintTask2.setItems(options);
             
              Sprint my_sprint= SS.GetByIdSprint(sprint_id);
              SprintTask2.getSelectionModel().select(my_sprint);
    }
        
             public static final LocalDate LOCAL_DATE (String dateString){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(dateString, formatter);
    return localDate;
}

    @FXML
    private void SubmitTask(ActionEvent event) throws IOException, SQLException {
        ServiceTask  ST =new ServiceTask();
        String title = TItreTask.getText();
        String description_fonctionnel = DescriptionTask.getText();
        String description_technique = "";
        int story_points = StoryPointsList.getSelectionModel().getSelectedItem();
        long millis=System.currentTimeMillis();
        Date created_date =new java.sql.Date(millis);
        Date finished_date = Date.valueOf(Deadline.getValue().toString());

        
        String state = EtatTask.getSelectionModel().getSelectedItem();
        int priority= Priority.getSelectionModel().getSelectedItem();
        int archive = 0;
        // to implement sprint id after 
        Sprint sprint_id = SprintTask2.getSelectionModel().getSelectedItem();
        
        
              // controle saisie date
        
         LocalDate created= LOCAL_DATE(created_date.toString());
         LocalDate deadline= Deadline.getValue();
         boolean conditionTitre = false;
        boolean conditionDescription = false;
        boolean conditionDate = false;
        boolean conditionStoryPoints = false;
        //condition titre
        if (title.length() < 5){
            conditionTitre= false;
            Image img = new Image("/com/hypocampus/uploads/error.png");
            Notifications n = Notifications.create()
           .title("Echec")
           .text("Verifier Longeur Titre > 5")
           .graphic(new ImageView(img))
           .position(Pos.TOP_CENTER)
           .hideAfter(Duration.seconds(5));
               n.darkStyle();
               n.show();
            return;
        }else{
            conditionTitre= true;
        }
        
        //condition description
        if (description_fonctionnel.length() < 5){
            conditionDescription= false;
            conditionTitre= false;
            Image img = new Image("/com/hypocampus/uploads/error.png");
            Notifications n = Notifications.create()
           .title("Echec")
           .text("Verifier Longeur Description > 5")
           .graphic(new ImageView(img))
           .position(Pos.TOP_CENTER)
           .hideAfter(Duration.seconds(5));
               n.darkStyle();
               n.show();
            return;
        }else{
            conditionDescription= true;
        }
           
           
           
        if (created.compareTo(deadline) < 0){
           
           
        
        Task oldTask = ST.getTaskbyid(taskid);
        
                
        // alghorithme points false to remove old points
        ST.point_algortime_1(oldTask, false);
        
        Task t = new Task(taskid,Integer.parseInt(BacklogId.getText()), title, description_fonctionnel, description_technique, story_points, created_date, finished_date, state, priority, archive, sprint_id.getId());
        ST.modifier(t);
        // ALGHO points ture to add new points
        ST.point_algortime_1(t, true);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/IndexTask.fxml"));
        Parent root = loader.load();
       
        IndexTaskController ITC = loader.getController();
        ITC.affichageTasks(ST.afficherParBacklogId(0, 0, t.getBacklog_id()));
        ITC.setBacklogId(Integer.toString(t.getBacklog_id()));

        
        // AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/IndexTask.fxml"));
        ContentPane.getChildren().setAll(root);
                       Image img = new Image("/com/hypocampus/uploads/Check.png");
                            Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  Tache modifier")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(5));
               n.darkStyle();
               n.show();
    } else{
                            Image img = new Image("/com/hypocampus/uploads/Check.png");
                            Notifications n = Notifications.create()
                              .title("Echec")
                              .text("Verifier La date Merci")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(5));
               n.darkStyle();
               n.show();
        }
    }
    
}
