/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Project;
import com.hypocampus.models.Sprint;
import com.hypocampus.models.Task;
import com.hypocampus.services.ServiceSprint;
import com.hypocampus.services.ServiceTask;
import com.hypocampus.utils.DataSource;
import com.hypocampus.utils.Email;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
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
    @FXML
    private AnchorPane ContentMaine;
    @FXML
    private Text SprintTitre1;
    
    public int  backlog_project_id;
    @FXML
    private ChoiceBox<Sprint> SprintTask2;
    

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
    
        public void set_backlog_project_id(int id){
        backlog_project_id = id;
            System.out.println(backlog_project_id);
    }
    
    
    public void initForm(){
        //init etat task
        EtatTask.getItems().addAll("To Do", "Doing","Done");
        // init priority
        Priority.getItems().addAll(1, 2, 3);
        // init story points list
        StoryPointsList.getItems().addAll(0,1,2,3,5,8,13,21); 
        

    }
    
    
    public void liste_sprints(int Backlog_project_id){
                ServiceSprint SS = new ServiceSprint();
                 final ObservableList<Sprint> options = FXCollections.observableArrayList();
                 
     
          List <Sprint>sprints= SS.afficher_SprintProject_GetById(backlog_project_id);
          
             for(int i=0;i<sprints.size();i++) 
                { 
                    options.add(sprints.get(i)); 

              }
              SprintTask2.setItems(options);
    }
    @FXML
    private void SubmitTask(ActionEvent event) throws Exception {
        System.out.println("hellooo");
        ServiceTask  ST =new ServiceTask();
        String title = TItreTask.getText();
        String description_fonctionnel = DescriptionTask.getText();
        String description_technique = "";
       
        int story_points = StoryPointsList.getSelectionModel().getSelectedItem();
        long millis=System.currentTimeMillis();
        Date created_date =new java.sql.Date(millis);
        // controle saisie date
        
         LocalDate created= LOCAL_DATE(created_date.toString());
           LocalDate deadline= Deadline.getValue();
           
      //  Date finished_date = new Date(Deadline.getValue().getYear()-1900, Deadline.getValue().getMonthValue()-1, Deadline.getValue().getDayOfMonth());
        Date finished_date = Date.valueOf(Deadline.getValue().toString());
        
        String state = EtatTask.getSelectionModel().getSelectedItem();
        int priority= Priority.getSelectionModel().getSelectedItem();
        int archive = 0;
        // to implement sprint id after 
        int sprint_id =  SprintTask2.getSelectionModel().getSelectedItem().getId();
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
            Connection cnx = DataSource.getInstance().getCnx(); 
            Statement stmt = cnx.createStatement(); 
          String SQL = "SELECT * FROM task WHERE  backlog_id='" +BacklogId.getText()+"'and title ='" +title+"'"; 
          ResultSet rs = stmt.executeQuery(SQL); 
                    if(!rs.next()){ 
                    Task t = new Task(Integer.parseInt(BacklogId.getText()), title, description_fonctionnel, description_technique, story_points, created_date, finished_date, state, priority, archive, sprint_id);

                    ST.ajouter(t);
                    // alghorithme points
                    ST.point_algortime_1(t, true);
                    String titreMail ="Vous Etes Affecter A Une Nouvelle Tache";
                    String text = "\"<h1> Bonsoir, \n </h1>"
                            + " <h2>Vous Etes Affecter A Une Nouvelle Tache,"
                            + " Vous Pouvez vous connecter est commencer a travailler\n </h2> "
                            + "<h2>Titre: "+t.getTitle()+"\n</h2> "
                            + "<h2>Description: "+t.getDescription_fonctionnel()+"\n</h2>"
                            + "<h2>Story Points: "+t.getStory_points()+"\n</h2>"
                            + "<h2>Etat: "+t.getState()+"\n</h2>"
                            + "<h2>Date Estimer: "+t.getFinished_date()+"\n</h2>"
                            + "<h3> ,Bonne Chance et Bon Courage </h3>";
                    // notification

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/IndexTask.fxml"));
                    Parent root = loader.load();

                    IndexTaskController ITC = loader.getController();
                    ITC.affichageTasks(ST.afficherParBacklogId(0, 0, t.getBacklog_id()));
                    ITC.setBacklogId(Integer.toString(t.getBacklog_id()));
                     ContentMaine.getChildren().setAll(root);

                     Image img = new Image("/com/hypocampus/uploads/Check.png");
                     Notifications n = Notifications.create()
                       .title("SUCCESS")
                       .text("Tache ajoutée")
                       .graphic(new ImageView(img))
                       .position(Pos.TOP_CENTER)
                       .hideAfter(Duration.seconds(5));
                           n.darkStyle();
                           n.show();
                    // email    
                    new Email("hypocampus.platforms@gmail.com", "3A192020", "mehdibehira@gmail.com", titreMail, text); // Send a message
                    // redirection
                    } else{
                                 Image img = new Image("/com/hypocampus/uploads/error.png");
                                Notifications n = Notifications.create()
                                  .title("Echec")
                                  .text("Tache Avec ce titre existe deja Merci")
                                  .graphic(new ImageView(img))
                                  .position(Pos.TOP_CENTER)
                                  .hideAfter(Duration.seconds(5));
                                      n.darkStyle();
                                      n.show();
                        
                    }
        } else
        {
         Image img = new Image("/com/hypocampus/uploads/error.png");
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
    
     public static final LocalDate LOCAL_DATE (String dateString){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            return localDate;
     }
    
}
