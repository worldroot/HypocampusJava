/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Entreprise;
import com.hypocampus.models.Project;
import com.hypocampus.services.ServiceEntreprise;
import com.hypocampus.services.ServiceProject;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Houcem
 */
public class ReadEntrepriseController implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		cbSearch.getItems().addAll(
			"Name",
			"Email"
		);
		cbSearch.setValue("Name");
		READ();
	}	
	
	 @FXML
    private AnchorPane apEread;

    @FXML
    private AnchorPane viewEntreprise2;

    @FXML
    private TableView<Entreprise> tabE;

    @FXML
    private TableColumn<Entreprise, String> clmEid;

    @FXML
    private TableColumn<Entreprise, String> clmEname;

    @FXML
    private TableColumn<Entreprise, String> clmEemail;

    @FXML
    private TableColumn<Entreprise, Date> clmEdate;

    @FXML
    private Button btnEdelete;

    @FXML
    private Button btnEcreate;
	
	@FXML
    private TextField leSearch;
	
	@FXML
	private ComboBox<String> cbSearch;

    @FXML
    void btnEcreate_clic(ActionEvent event) throws IOException {
		
   AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/createEntreprise.fxml"));
        apEread.getChildren().setAll(pane);
    }

    @FXML
    void btnEdelete_clic(ActionEvent event) {
		Entreprise E = tabE.getSelectionModel().getSelectedItem();
		
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
                                        ServiceEntreprise sE = new ServiceEntreprise();
										Entreprise E1 = new Entreprise(E.getId());
										Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
										alert.setTitle("Confirmation ");
										alert.setHeaderText(null);
										alert.setContentText("Vous voulez vraiment supprimer ce Entreprise");
										Optional<ButtonType> action = alert.showAndWait();
									   if (action.get() == ButtonType.OK) {
											sE.supprimer(E1);
											READ();
									   }
                                  }

    }

    @FXML
    void upadateE_clic(ActionEvent event) throws IOException {
		Entreprise Pr = tabE.getSelectionModel().getSelectedItem();
//             data2.removeAll(Pr);

   
   
   
                                  if (Pr == null) {

                                    Image img = new Image("/com/hypocampus/uploads/error.png");
                                    Notifications n = Notifications.create()
                                                     .title("Error")
                                                     .text("No Project selected :  Please select a Project for edit")
                                                     .graphic(new ImageView(img))
                                                     .position(Pos.TOP_CENTER)
                                                      .hideAfter(Duration.seconds(5));
                                    n.darkStyle();
                                    n.show();
                                }
     else{
                                      
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/updateEntreprise.fxml"));              
        Parent parent = loader.load();
        apEread.getChildren().setAll(parent);

           UpdateEntrepriseController controller = (UpdateEntrepriseController) loader.getController();
            controller.inflateUI(Pr);
            controller.add(Pr);
    
            
          }

    }
	
	private void READ()
	{
		ServiceEntreprise sE = new ServiceEntreprise();

                    clmEid.setCellValueFactory(new PropertyValueFactory<>("id"));
                    clmEname.setCellValueFactory(new PropertyValueFactory<>("name"));
                    clmEemail.setCellValueFactory(new PropertyValueFactory<>("email"));
                    clmEdate.setCellValueFactory(new PropertyValueFactory<>("createdate"));
                    tabE.setItems((ObservableList<Entreprise>) sE.afficher());
	}
	
	@FXML
    void leSearch_text_changed(InputMethodEvent event) {
		//hethi khatya :p
    }
	
	@FXML
    void leSearch_key_released(KeyEvent event) {
		ServiceEntreprise sE = new ServiceEntreprise();
		if(leSearch.getText().equals(""))
		{
			tabE.setItems((ObservableList<Entreprise>) sE.afficher());
		}
		else 
		{
			tabE.setItems((ObservableList<Entreprise>) sE.search(leSearch.getText(),cbSearch.getValue()));
		}
    }
	
}
