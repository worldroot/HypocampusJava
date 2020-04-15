/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.User;
import com.hypocampus.services.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
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
public class CreateUserController implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	
	Alert alert = new Alert(Alert.AlertType.NONE);
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		cmbUrl.getItems().addAll(
			"Developer",
			"ScrumMaster",
			"Admin"
		);
		cmbUrl.setValue("Developer");
	}	
	
	@FXML
    private AnchorPane apU;
	
	@FXML
    private Pane pE1;

    @FXML
    private TextField leUun;

    @FXML
    private Button btnUcreate;

    @FXML
    private Button btnUcancel;

    @FXML
    private ComboBox<String> cmbUrl;

    @FXML
    private TextField leUem;

    @FXML
    private PasswordField leUpw;

    @FXML
    void btnUcancelAction(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/readUser.fxml"));
        apU.getChildren().setAll(pane);
    }

    @FXML
    void btnUcreateAction(ActionEvent event) throws IOException {		
		ServiceUser sU = new ServiceUser();
		
		       if (!leUun.getText().equals("") && !leUpw.getText().equals("") && !leUem.getText().equals("") )
       {
          
           
            
				sU.ajouter(new User(leUun.getText(),leUpw.getText(),cmbUrl.getValue(),leUem.getText()));
               Image img = new Image("/com/hypocampus/uploads/Check.png");
                            Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  User ajouté")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(1));
               n.darkStyle();
               n.show();
          
			   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/readUser.fxml"));
        apU.getChildren().setAll(pane);
          
          
           
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
