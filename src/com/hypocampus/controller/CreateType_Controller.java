/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Type;
import com.hypocampus.services.ServiceType;
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
public class CreateType_Controller implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	
	Alert alert = new Alert(Alert.AlertType.NONE);
	
	@FXML
    private AnchorPane apT;

    @FXML
    private Pane pE11;

    @FXML
    private TextField leTname;

    @FXML
    private Button btnTcreate;

    @FXML
    private Button btnT_cancel;

    @FXML
    private TextField leTnp;

    @FXML
    private TextField leTnu;

    @FXML
    private TextField leTvalue;

    
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}	
	
	@FXML
    void btnT_cancel_clic(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/readType.fxml"));
        apT.getChildren().setAll(pane);
    }

    @FXML
    void btnTcreate_clic(ActionEvent event) throws IOException {
		ServiceType sT = new ServiceType();
           
		     
       if (!leTname.getText().equals("") && !leTvalue.getText().equals("") && !leTnp.getText().equals("") && !leTnu.getText().equals(""))
       {
		   int value = Integer.parseInt(leTvalue.getText());
           int np = Integer.parseInt(leTnp.getText());
		   int nu = Integer.parseInt(leTnu.getText());

            
               sT.ajouter(new Type(leTname.getText(),value,np,nu));
               Image img = new Image("/com/hypocampus/uploads/Check.png");
                            Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  Type ajouté")
                              .graphic(new ImageView(img))
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(1));
               n.darkStyle();
               n.show();
               leTname.setText("");
			   leTvalue.setText("");
               leTnp.setText("");
               leTnu.setText("");
              
               /*
               Alert alertA = new Alert(Alert.AlertType.NONE,"Project ajouté",ButtonType.APPLY);
               
               // show the dialog
               alertA.show();
               //System.out.println();
               */
			   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/readType.fxml"));
        apT.getChildren().setAll(pane);
          
          
           
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
