/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Entreprise;
import com.hypocampus.models.User;
import com.hypocampus.services.ServiceEntreprise;
import com.hypocampus.services.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


/**
 * FXML Controller class
 *
 * @author Houcem
 */
public class ReadUserController implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		READ();
	}	

	@FXML
    private AnchorPane apU;

    @FXML
    private AnchorPane apUuu;

    @FXML
    private TableView<User> tabU;

    @FXML
    private TableColumn<User, String> clmUid;

    @FXML
    private TableColumn<User, String> clmUun;

    @FXML
    private TableColumn<User, String> clmUem;

    @FXML
    private TableColumn<User, String> clmUrl;

    @FXML
    private Button btnUdelete;

    @FXML
    private Button btnUnew;

    @FXML
    void btnUdelete(ActionEvent event) {
		User E = tabU.getSelectionModel().getSelectedItem();
		
                                if (E == null) {

                                    Image img = new Image("/com/hypocampus/uploads/error.png");
                                    Notifications n = Notifications.create()
                                                     .title("Error")
                                                     .text("  Choix invalide")
                                                     .graphic(new ImageView(img))
                                                     .position(Pos.TOP_CENTER)
                                                      .hideAfter(Duration.seconds(1));
                                    n.darkStyle();
                                    n.show();
                                }
                                   else{
                                        ServiceUser sE = new ServiceUser();
										Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
										alert.setTitle("Confirmation ");
										alert.setHeaderText(null);
										alert.setContentText("Vous voulez vraiment supprimer ce User");
										Optional<ButtonType> action = alert.showAndWait();
									   if (action.get() == ButtonType.OK) {
											sE.supprimer(E);
											READ();
									   }
                                  }
    }

    @FXML
    void btnUnew(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/createUser.fxml"));
        apU.getChildren().setAll(pane);
		
    }
	
	private void READ()
	{
		ServiceUser sT = new ServiceUser();

                    clmUid.setCellValueFactory(new PropertyValueFactory<>("id"));
                    clmUem.setCellValueFactory(new PropertyValueFactory<>("email"));
                    clmUun.setCellValueFactory(new PropertyValueFactory<>("username"));
                    clmUrl.setCellValueFactory(new PropertyValueFactory<>("roles"));
                    tabU.setItems((ObservableList<User>) sT.afficher());
	}
	
}
