/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Backlog;
import com.hypocampus.models.Task;
import com.hypocampus.services.ServiceTask;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author mehdibehira
 */
public class IndexTaskController implements Initializable {

    @FXML
    private AnchorPane ContentMaine;
    @FXML
    private Text TitreBacklog;
    @FXML
    private Text TitreListeDesTaches;
    @FXML
    private HBox TaskContainer;
    @FXML
    private Button AjoutTaskbtn;
    @FXML
    private Text BackogId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         ServiceTask ST  = new ServiceTask();
         
        
        // TODO
       // affichageTasks(ST.afficher(0,8) );
        System.out.println(ST.afficher());
    }    
    
    public void setBacklogId(String id){
        BackogId.setText(id);
    }
    public void affichageTasks(List<Task> tasks){
       
        
       TaskContainer.getChildren().clear();
       
       for(int i=0 ; i < tasks.size(); i++ ){
           
                       Task current = tasks.get(i);
            
            Pane postpane = new Pane(); //div post
            postpane.setBackground(Background.EMPTY);
            postpane.setBackground(new Background(new BackgroundFill(Color.web("#0c0d20"),new CornerRadii(23), Insets.EMPTY)));            
            postpane.setPrefWidth(300);
            
            
            // opacity de l'arriere plan
            Pane postopacity = new Pane(); 
            postopacity.setLayoutX(7);
            postopacity.setLayoutY(8);             
            postopacity.setPrefHeight(390);
            postopacity.setPrefWidth(285);
            Random r = new Random();
            int n = r.nextInt(6);
            if (n==0) {
            postopacity.setBackground(new Background(new BackgroundFill(Color.BROWN,CornerRadii.EMPTY, Insets.EMPTY)));                
            }
            else if (n==1) {
            postopacity.setBackground(new Background(new BackgroundFill(Color.PURPLE,CornerRadii.EMPTY, Insets.EMPTY)));                
            }
            else if (n==2) {
            postopacity.setBackground(new Background(new BackgroundFill(Color.POWDERBLUE,CornerRadii.EMPTY, Insets.EMPTY)));                
            }
            else if (n==3) {
            postopacity.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET,CornerRadii.EMPTY, Insets.EMPTY)));                
            }
            else if (n==4) {
            postopacity.setBackground(new Background(new BackgroundFill(Color.ORANGE,CornerRadii.EMPTY, Insets.EMPTY)));                
            }
            else if (n==5) {
            postopacity.setBackground(new Background(new BackgroundFill(Color.CADETBLUE,CornerRadii.EMPTY, Insets.EMPTY)));                
            }
            postopacity.setOpacity(0.4);         
            postpane.getChildren().add(postopacity);
            
            
            /// opacity de la description de la task
            
            Pane postdes = new Pane();
            int len = tasks.get(i).getDescription_fonctionnel().length();
            postdes.setPrefWidth(230);
            postdes.setLayoutX(18);
            if (len<=25) {
                postdes.setPrefHeight(59);
                postdes.setLayoutY(330);
            } else if (len<=50){
                postdes.setPrefHeight(91);
                postdes.setLayoutY(315);                
            }
            else{
                postdes.setPrefHeight(135);
                postdes.setLayoutY(270);
            }
    
            if (n==0) {
            postdes.setBackground(new Background(new BackgroundFill(Color.BROWN,new CornerRadii(7), Insets.EMPTY)));                
            }
            else if (n==1) {
            postdes.setBackground(new Background(new BackgroundFill(Color.PURPLE,new CornerRadii(7), Insets.EMPTY)));                
            }
            else if (n==2) {
            postdes.setBackground(new Background(new BackgroundFill(Color.POWDERBLUE,new CornerRadii(7), Insets.EMPTY)));                
            }
            else if (n==3) {
            postdes.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET,new CornerRadii(7), Insets.EMPTY)));                
            }
            else if (n==4) {
            postdes.setBackground(new Background(new BackgroundFill(Color.ORANGE,new CornerRadii(7), Insets.EMPTY)));                
            }
            else if (n==5) {
            postdes.setBackground(new Background(new BackgroundFill(Color.CADETBLUE,new CornerRadii(7), Insets.EMPTY)));                
            }   
            postdes.setOpacity(0.6);   
            
            // Description Fonctionnele
            Label description = new Label();
            description.setPrefHeight(110);
            description.setPrefWidth(230);
            description.setLayoutX(8);
            description.setLayoutY(14);
            description.setTextFill(Color.WHITE);
            description.setStyle("-fx-font-size :15");
            description.setText(tasks.get(i).getDescription_fonctionnel());
            description.setAlignment(Pos.TOP_LEFT);
            description.setWrapText(true);
            postdes.getChildren().add(description);
            postpane.getChildren().add(postdes);
            
            /// Titre
            
            Pane TitreTask = new Pane();
            TitreTask.setPrefWidth(190);
            TitreTask.setLayoutX(10);
            
            Label Titre = new Label();
            Titre.setPrefHeight(110);
            Titre.setPrefWidth(230);
            Titre.setLayoutX(8);
            Titre.setLayoutY(14);
            Titre.setTextFill(Color.WHITE);
            Titre.setStyle("-fx-font-size :15");
            Titre.setText(tasks.get(i).getTitle());
            Titre.setAlignment(Pos.TOP_LEFT);
            Titre.setWrapText(true);
            TitreTask.getChildren().add(Titre);
            postpane.getChildren().add(TitreTask);
            
            // Story Points
            Pane StoryPointTextPane = new Pane();
            StoryPointTextPane.setPrefWidth(190);
            StoryPointTextPane.setLayoutX(10);
            
            Label StoryPointLabel = new Label();
            StoryPointLabel.setPrefHeight(110);
            StoryPointLabel.setPrefWidth(230);
            StoryPointLabel.setLayoutX(8);
            StoryPointLabel.setLayoutY(80);
            StoryPointLabel.setTextFill(Color.WHITE);
            StoryPointLabel.setStyle("-fx-font-size :15");
            StoryPointLabel.setText("Story Points : "+tasks.get(i).getStory_points());
            StoryPointLabel.setAlignment(Pos.TOP_LEFT);
            StoryPointLabel.setWrapText(true);
            StoryPointTextPane.getChildren().add(StoryPointLabel);
            postpane.getChildren().add(StoryPointTextPane);
            
            //Deadline
             Pane DeadlineTextPane = new Pane();
            DeadlineTextPane.setPrefWidth(190);
            DeadlineTextPane.setLayoutX(10);
            
            Label DeadlineLabel = new Label();
            DeadlineLabel.setPrefHeight(110);
            DeadlineLabel.setPrefWidth(230);
            DeadlineLabel.setLayoutX(8);
            DeadlineLabel.setLayoutY(120);
            DeadlineLabel.setTextFill(Color.WHITE);
            DeadlineLabel.setStyle("-fx-font-size :15");
            DeadlineLabel.setText("Deadline : "+tasks.get(i).getFinished_date());
            DeadlineLabel.setAlignment(Pos.TOP_LEFT);
            DeadlineLabel.setWrapText(true);
            DeadlineTextPane.getChildren().add(DeadlineLabel);
            postpane.getChildren().add(DeadlineTextPane);
            
            // State
            Pane StateTextPane = new Pane();
            StateTextPane.setPrefWidth(190);
            StateTextPane.setLayoutX(10);
            
            Label StateLabel = new Label();
            StateLabel.setPrefHeight(110);
            StateLabel.setPrefWidth(230);
            StateLabel.setLayoutX(8);
            StateLabel.setLayoutY(160);
            StateLabel.setTextFill(Color.WHITE);
            StateLabel.setStyle("-fx-font-size :15");
            StateLabel.setText("Etat : "+tasks.get(i).getState());
            StateLabel.setAlignment(Pos.TOP_LEFT);
            StateLabel.setWrapText(true);
            StateTextPane.getChildren().add(StateLabel);
            postpane.getChildren().add(StateTextPane);
            
            
            // Priority
            Pane PriorityTextPane = new Pane();
            PriorityTextPane.setPrefWidth(190);
            PriorityTextPane.setLayoutX(10);
            
            String priority;
           
           switch (tasks.get(i).getPriority()) {
               case 1:
                   priority= "Low";
                   break;
               case 2:
                   priority= "Medium";
                   break;
               default:
                   priority= "High";
                   break;
           }
            
            Label PriorityLabel = new Label();
            PriorityLabel.setPrefHeight(110);
            PriorityLabel.setPrefWidth(230);
            PriorityLabel.setLayoutX(8);
            PriorityLabel.setLayoutY(200);
            PriorityLabel.setTextFill(Color.WHITE);
            PriorityLabel.setStyle("-fx-font-size :15");
            PriorityLabel.setText("Priority : "+priority);
            PriorityLabel.setAlignment(Pos.TOP_LEFT);
            PriorityLabel.setWrapText(true);
            PriorityTextPane.getChildren().add(PriorityLabel);
            postpane.getChildren().add(PriorityTextPane);
            
            
            /// To add after sprint + user + desTechnique
            //Edit bouton task
            Image editIcon = new Image("/com/hypocampus/uploads/settings1.png");
            editIcon.getRequestedHeight();
            editIcon.getRequestedWidth();
            ImageView edit = new ImageView(editIcon);
            Button editbtn = new Button("", edit);
            editbtn.setBackground(Background.EMPTY);
            editbtn.setPrefWidth(1);
            editbtn.setPrefHeight(1);
            editbtn.setLayoutX(228);
            editbtn.setLayoutY(5);
            postpane.getChildren().add(editbtn);
            
            editbtn.setOnMouseClicked((MouseEvent e) -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/EditTask.fxml"));
                        Parent parent = loader.load();
                        ContentMaine.getChildren().setAll(parent);
                        EditTaskController controller =(EditTaskController) loader.getController();
                        controller.inflateUI(current);
                    } catch (IOException ex) {
                        Logger.getLogger(IndexTaskController.class.getName()).log(Level.SEVERE, null, ex);
                    }
           
            });
            
            //Delete bouton task
            Image DeleteIcon = new Image("/com/hypocampus/uploads/error.png");
            editIcon.getRequestedHeight();
            editIcon.getRequestedWidth();
            ImageView delete = new ImageView(DeleteIcon);
            Button DeletButton = new Button("", delete);
            DeletButton.setBackground(Background.EMPTY);
            DeletButton.setPrefWidth(1);
            DeletButton.setPrefHeight(1);
            DeletButton.setLayoutX(170);
            DeletButton.setLayoutY(5);
            postpane.getChildren().add(DeletButton);
            
            DeletButton.setOnMouseClicked((MouseEvent e) -> {
                ServiceTask ST = new ServiceTask();
                ST.supprimer(current);
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/IndexTask.fxml"));
                try {
                 Parent root = loader.load();
                IndexTaskController ITC = loader.getController();
                ITC.affichageTasks(ST.afficherParBacklogId(0, 0, current.getBacklog_id()));
                ITC.setBacklogId(Integer.toString(current.getBacklog_id()));
                ContentMaine.getChildren().setAll(root);
                  } catch (IOException ex) {
                               Logger.getLogger(IndexTaskController.class.getName()).log(Level.SEVERE, null, ex);
                           }
                });
            
            
            
            
            TaskContainer.getChildren().add(postpane); 
       }
    }

    
    @FXML
    private void AjoutTask(ActionEvent event) throws IOException {
               
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/AjoutTask.fxml"));              
        Parent parent = loader.load();
        ContentMaine.getChildren().setAll(parent);
        AjoutTaskController ATC = loader.getController();
        ATC.setBacklogId(BackogId.getText());
 
    }

}
