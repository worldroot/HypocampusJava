/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import static com.hypocampus.controller.ProjectController.NOW_LOCAL_DATE;
import com.hypocampus.models.Abonnement;
import com.hypocampus.models.Entreprise;
import com.hypocampus.models.Type;
import com.hypocampus.services.ServiceAbonnement;
import com.hypocampus.services.ServiceEntreprise;
import com.hypocampus.services.ServiceType;
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
import javafx.scene.control.ComboBox;
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
public class CreateAbonnementController implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	
	Alert alert = new Alert(Alert.AlertType.NONE);
	
    @FXML
    private AnchorPane apAcc;

    @FXML
    private Pane pE1;

    @FXML
    private TextField leAname;

    @FXML
    private DatePicker leAdate;

    @FXML
    private Button btnAcreate;

    @FXML
    private Button btnA_cancel;

    @FXML
    private ComboBox<Type> cmbAtype;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ServiceType st = new ServiceType();
		cmbAtype.getItems().addAll(st.afficher());
	}	
	
	@FXML
    void btnA_cancel_clic(ActionEvent event)throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/readAbonnement.fxml"));
        apAcc.getChildren().setAll(pane);
    }

    @FXML
    void btnAcreate_clic(ActionEvent event) throws IOException {
		ServiceAbonnement sP = new ServiceAbonnement();
           LocalDate ds= leAdate.getValue();
           
           Date dateS=Date.valueOf(ds.toString());//converting string into sql date

		     
       if (!leAname.getText().equals("") && !cmbAtype.getValue().equals(""))
       {
          
           
            
               sP.ajouter(new Abonnement(leAname.getText(),dateS,1,cmbAtype.getValue()));
               Image img = new Image("/com/hypocampus/uploads/Check.png");
                            Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  Abonne ajouté")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(1));
               n.darkStyle();
               n.show();
              
               /*
               Alert alertA = new Alert(Alert.AlertType.NONE,"Project ajouté",ButtonType.APPLY);
               
               // show the dialog
               alertA.show();
               //System.out.println();
               */
			   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/readAbonnement.fxml"));
        apAcc.getChildren().setAll(pane);
          
          
           
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
