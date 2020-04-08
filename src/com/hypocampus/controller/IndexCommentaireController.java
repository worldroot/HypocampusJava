/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Commentaire;
import com.hypocampus.services.ServiceCommentaire;
import com.hypocampus.services.ServiceTask;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author mehdibehira
 */
public class IndexCommentaireController implements Initializable {

    @FXML
    private Text TitreListeDesCommentaires;
    @FXML
    private Button AjoutCommentaireBtn;
    @FXML
    private Text TaskId;
    @FXML
    private VBox CommentairesContainer;
    @FXML
    private AnchorPane ContentMaine;
    @FXML
    private Button RetourTasksbtn1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AjoutCommentaire(ActionEvent event) throws IOException {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/AjoutCommentaire.fxml"));              
        Parent parent = loader.load();
        ContentMaine.getChildren().setAll(parent);
        AjoutCommentaireController ACC = loader.getController();
        ACC.setTaskId(TaskId.getText());
        
    }
    
    
        public void setTaskId(String id){
        TaskId.setText(id);
    }
    
    public void affichageCommentaires(List<Commentaire> commentaires){
        CommentairesContainer.getChildren().clear();

        
        for(int i=0; i< commentaires.size(); i++){
            Commentaire current = commentaires.get(i);
            
            
            Pane commentairepane = new Pane();
            commentairepane.setBackground(Background.EMPTY);
            commentairepane.setBackground(new Background(new BackgroundFill(Color.web("#0c0d20"),new CornerRadii(23), Insets.EMPTY)));            
            commentairepane.setPrefWidth(300);
            commentairepane.setPrefHeight(100);
                        // opacity de l'arriere plan
            Pane postopacity = new Pane(); 
            postopacity.setLayoutX(7);
            postopacity.setLayoutY(8);             
            postopacity.setPrefHeight(90);
            postopacity.setPrefWidth(285);
            Random r = new Random();
            int n = r.nextInt(6);
            if (n==0) {
            postopacity.setBackground(new Background(new BackgroundFill(Color.BROWN,new CornerRadii(23), Insets.EMPTY)));                
            }
            else if (n==1) {
            postopacity.setBackground(new Background(new BackgroundFill(Color.PURPLE,new CornerRadii(23), Insets.EMPTY)));                
            }
            else if (n==2) {
            postopacity.setBackground(new Background(new BackgroundFill(Color.POWDERBLUE,new CornerRadii(23), Insets.EMPTY)));                
            }
            else if (n==3) {
            postopacity.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET,new CornerRadii(23), Insets.EMPTY)));                
            }
            else if (n==4) {
            postopacity.setBackground(new Background(new BackgroundFill(Color.ORANGE,new CornerRadii(23), Insets.EMPTY)));                
            }
            else if (n==5) {
            postopacity.setBackground(new Background(new BackgroundFill(Color.CADETBLUE,new CornerRadii(23), Insets.EMPTY)));                
            }
            postopacity.setOpacity(0.4);         
            commentairepane.getChildren().add(postopacity);
            
            
            
             /// opacity de la description du commentaire
            
            Pane postdes = new Pane();
            int len = commentaires.get(i).getDescription().length();
            postdes.setPrefWidth(250);
            postdes.setLayoutX(18);
            if (len<=25) {
                postdes.setPrefHeight(59);
                postdes.setLayoutY(32);
            } else if (len<=50){
                postdes.setPrefHeight(91);
                postdes.setLayoutY(7);                
            }
            else{
                postdes.setPrefHeight(135);
                postdes.setLayoutY(5);
            }
    
            if (n==0) {
            postdes.setBackground(new Background(new BackgroundFill(Color.BROWN,new CornerRadii(15), Insets.EMPTY)));                
            }
            else if (n==1) {
            postdes.setBackground(new Background(new BackgroundFill(Color.PURPLE,new CornerRadii(15), Insets.EMPTY)));                
            }
            else if (n==2) {
            postdes.setBackground(new Background(new BackgroundFill(Color.POWDERBLUE,new CornerRadii(15), Insets.EMPTY)));                
            }
            else if (n==3) {
            postdes.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET,new CornerRadii(15), Insets.EMPTY)));                
            }
            else if (n==4) {
            postdes.setBackground(new Background(new BackgroundFill(Color.ORANGE,new CornerRadii(15), Insets.EMPTY)));                
            }
            else if (n==5) {
            postdes.setBackground(new Background(new BackgroundFill(Color.CADETBLUE,new CornerRadii(15), Insets.EMPTY)));                
            }   
            postdes.setOpacity(0.6);
            
            
            
            
            // Description 
            Label description = new Label();
            description.setPrefHeight(110);
            description.setPrefWidth(230);
            description.setLayoutX(8);
            description.setLayoutY(2);
            description.setTextFill(Color.WHITE);
            description.setStyle("-fx-font-size :15");
            description.setText(commentaires.get(i).getDescription());
            description.setAlignment(Pos.TOP_LEFT);
            description.setWrapText(true);
            postdes.getChildren().add(description);
            commentairepane.getChildren().add(postdes);
            
            // Bouton delete
            Image DeleteIcon = new Image("/com/hypocampus/uploads/error.png");
            ImageView delete = new ImageView(DeleteIcon);
            Button DeletButton = new Button("", delete);
            DeletButton.setBackground(Background.EMPTY);
            DeletButton.setPrefWidth(1);
            DeletButton.setPrefHeight(1);
            DeletButton.setLayoutX(220);
            DeletButton.setLayoutY(2);
            commentairepane.getChildren().add(DeletButton);
            
            DeletButton.setOnMouseClicked((MouseEvent e) -> {
                
                       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation ");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Vous voulez vraiment supprimer ce commentaire? ");
                                    Optional<ButtonType> action = alert.showAndWait();
                                    if (action.get() == ButtonType.OK) {
                        try {
                            ServiceCommentaire SC = new ServiceCommentaire();

                            SC.supprimer(current);
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/IndexCommentaire.fxml"));
                            Parent parent = loader.load();
                            ContentMaine.getChildren().setAll(parent);
                            IndexCommentaireController controller =(IndexCommentaireController) loader.getController();
                            controller.affichageCommentaires(SC.afficherParTask(current.getTask_id()));
                            controller.setTaskId(Integer.toString(current.getTask_id()));
                        } catch (IOException ex) {
                            Logger.getLogger(IndexCommentaireController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                                               Image img = new Image("/com/hypocampus/uploads/Check.png");
                            Notifications not = Notifications.create()
                              .title("SUCCESS")
                              .text("Commentaire Supprimé")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(5));
               not.darkStyle();
               not.show();
                                    }
                });
            
            CommentairesContainer.getChildren().add(commentairepane);
        }
        
    }

    @FXML
    private void RetourTasks(ActionEvent event) throws IOException {

     
    }
}
