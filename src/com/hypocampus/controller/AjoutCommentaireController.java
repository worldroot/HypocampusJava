/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Commentaire;
import com.hypocampus.models.Task;
import com.hypocampus.services.ServiceCommentaire;
import com.hypocampus.services.ServiceTask;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
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
public class AjoutCommentaireController implements Initializable {

    @FXML
    private AnchorPane ContentMain;
    @FXML
    private TextArea DescriptionCommentaire;
    @FXML
    private Text AjoutCommentaireTitre;
    @FXML
    private Text DescriptionTitre;
    @FXML
    private Text TaskId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void SubmitCommentaire(ActionEvent event) {
                ServiceCommentaire  SC =new ServiceCommentaire();
        String description = DescriptionCommentaire.getText();
        int task_id = Integer.parseInt(TaskId.getText());
        // add user id later
        int user_id =2;
        long millis=System.currentTimeMillis();
        Date created_date =new java.sql.Date(millis);
        
      //  Date finished_date = new Date(Deadline.getValue().getYear()-1900, Deadline.getValue().getMonthValue()-1, Deadline.getValue().getDayOfMonth());
        Commentaire c = new Commentaire(task_id, user_id, description, created_date);
        SC.ajouter(c);
                       Image img = new Image("/com/hypocampus/uploads/Check.png");
                            Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("Commentaire ajouté")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(5));
               n.darkStyle();
               n.show();
               RetourTask(event);
    }

    @FXML
    private void RetourTask(ActionEvent event) {
        int task_id = Integer.parseInt(TaskId.getText());
        
                        try {
                    ServiceCommentaire SC  = new ServiceCommentaire();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/IndexCommentaire.fxml"));
                        Parent parent = loader.load();
                        ContentMain.getChildren().setAll(parent);
                        IndexCommentaireController controller =(IndexCommentaireController) loader.getController();
                     
                        controller.affichageCommentaires(SC.afficherParTask(task_id));
                        
        controller.setTaskId(Integer.toString(task_id));

        

                    } catch (IOException ex) {
                        Logger.getLogger(IndexTaskController.class.getName()).log(Level.SEVERE, null, ex);
                    }
        
    }
    
     public void setTaskId(String id){
        TaskId.setText(id);
    }
    
}
