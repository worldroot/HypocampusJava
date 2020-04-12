/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import static com.hypocampus.controller.ProjectController.NOW_LOCAL_DATE;
import com.hypocampus.models.Entreprise;
import com.hypocampus.models.Project;
import com.hypocampus.services.ServiceEntreprise;
import com.hypocampus.services.ServiceProject;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


/**
 * FXML Controller class
 *
 * @author Houcem
 */
public class CreateEntrepriseController implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	
	Alert alert = new Alert(Alert.AlertType.NONE);
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}	
	
	@FXML
    private AnchorPane apEcreate;

    @FXML
    private Pane pE1;

    @FXML
    private TextField leEemail;

    @FXML
    private TextField leEname;

    @FXML
    private DatePicker leEdate;

    @FXML
    private Button btnEcreate;

    @FXML
    private Button btnE_cancel;

    @FXML
    void btnE_cancel_clic(ActionEvent event) throws IOException {
   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/readEntreprise.fxml"));
        apEcreate.getChildren().setAll(pane);
    }

    @FXML
    void btnEcreate_clic(ActionEvent event) throws IOException {
		
		ServiceEntreprise sP = new ServiceEntreprise();
           LocalDate ds= leEdate.getValue();
           
           Date dateS=Date.valueOf(ds.toString());//converting string into sql date

		     
       if (!leEname.getText().equals("") && !leEemail.getText().equals(""))
       {
          
           
            
               sP.ajouter(new Entreprise(leEname.getText(),leEemail.getText(),dateS));
               Image img = new Image("/com/hypocampus/uploads/Check.png");
                            Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  Entreprise ajouté")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(1));
               n.darkStyle();
               n.show();
               leEname.setText("");
               leEemail.setText("");
               leEdate.setValue(NOW_LOCAL_DATE());
              
               /*
               Alert alertA = new Alert(Alert.AlertType.NONE,"Project ajouté",ButtonType.APPLY);
               
               // show the dialog
               alertA.show();
               //System.out.println();
               */
			   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/readEntreprise.fxml"));
        apEcreate.getChildren().setAll(pane);
          
          
           
       }
       else {
           
           alert.setAlertType(Alert.AlertType.WARNING);
           
           // set content text
           alert.setContentText("verifié vos parametre ");
           
           // show the dialog
           alert.show();
       }
    }
}
