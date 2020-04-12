/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import static com.hypocampus.controller.EditProjectController.LOCAL_DATE;
import com.hypocampus.models.Abonnement;
import com.hypocampus.models.Entreprise;
import com.hypocampus.models.Type;
import com.hypocampus.services.ServiceAbonnement;
import com.hypocampus.services.ServiceEntreprise;
import com.hypocampus.services.ServiceType;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
public class ReadAbonnementController implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	
	@FXML
    private AnchorPane apA;

    @FXML
    private AnchorPane apAa;

    @FXML
    private TableView<Abonnement> tabA;

    @FXML
    private TableColumn<Abonnement, String> clmAid;

    @FXML
    private TableColumn<Abonnement, String> clmAname;

    @FXML
    private TableColumn<Abonnement, Date> clmAdate;

    @FXML
    private TableColumn<Abonnement, Type> clmAtype;

    @FXML
    private TableColumn<Abonnement, String> clmAactive;

    @FXML
    private Button btnAdelete;

    @FXML
    private Button btnAtoT;

    @FXML
    private Button btnAcreate;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		READ();
	}	
	
	private void READ()
	{
		ServiceAbonnement sE = new ServiceAbonnement();

                    clmAid.setCellValueFactory(new PropertyValueFactory<>("id"));
                    clmAname.setCellValueFactory(new PropertyValueFactory<>("name"));
                    clmAdate.setCellValueFactory(new PropertyValueFactory<>("date"));
					clmAtype.setCellValueFactory(new PropertyValueFactory<>("T"));
                    clmAactive.setCellValueFactory(new PropertyValueFactory<>("active"));
					
					
                    tabA.setItems((ObservableList<Abonnement>) sE.afficher());
			//		System.out.println(sE.afficher());
	}
	
	@FXML
    void btnAcreate_clic(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/createAbonnement.fxml"));
        apA.getChildren().setAll(pane);
    }

    @FXML
    void btnAdelete_clic(ActionEvent event) {
				Abonnement A = tabA.getSelectionModel().getSelectedItem();

		                                if (A == null) {

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
                                        ServiceAbonnement sA = new ServiceAbonnement();
										Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
										alert.setTitle("Confirmation ");
										alert.setHeaderText(null);
										alert.setContentText("Vous voulez vraiment supprimer ce Abonnement");
										Optional<ButtonType> action = alert.showAndWait();
									   if (action.get() == ButtonType.OK) {
											sA.supprimer(A);
											READ();
									   }
                                  }

    }

    @FXML
    void btnAtoT_clic(ActionEvent event)  throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/readType.fxml"));
        apA.getChildren().setAll(pane);
    }

    @FXML
    void upadateA_clic(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/updateAbonnement.fxml"));
        apA.getChildren().setAll(pane);
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/updateAbonnement.fxml"));              
        Parent parent = loader.load();
        apA.getChildren().setAll(parent);

			UpdateAbonnementController controller = (UpdateAbonnementController) loader.getController();
			Abonnement A = tabA.getSelectionModel().getSelectedItem();
			controller.setAbonnement(A);
			controller.leAname.setText(A.getName());
			controller.leAdate.setValue(LOCAL_DATE(A.getDate().toString()));
			ServiceType st = new ServiceType();
			controller.cmbAtype.getItems().addAll(st.afficher());
			controller.cmbAtype.setValue(A.getT());

    }
	
}
