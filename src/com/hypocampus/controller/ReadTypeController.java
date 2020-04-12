/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.models.Entreprise;
import com.hypocampus.models.Type;
import com.hypocampus.services.ServiceEntreprise;
import com.hypocampus.services.ServiceType;
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
public class ReadTypeController implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	
    @FXML
    private AnchorPane viewType;
	
	@FXML
    private AnchorPane viewType2;

    @FXML
    private TableView<Type> tabT;

    @FXML
    private TableColumn<Type, String> clmTid;

    @FXML
    private TableColumn<Type, String> clmTname;

    @FXML
    private TableColumn<Type, String> clmTvalue;

    @FXML
    private TableColumn<Type, String> clmTnp;

    @FXML
    private TableColumn<Type, String> clmTnu;

    @FXML
    private Button btnTdelete;

    @FXML
    private Button btnTcreate;

    
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		READ();
	}	
	
	private void READ()
	{
		ServiceType sT = new ServiceType();

                    clmTid.setCellValueFactory(new PropertyValueFactory<>("id"));
                    clmTname.setCellValueFactory(new PropertyValueFactory<>("name"));
                    clmTvalue.setCellValueFactory(new PropertyValueFactory<>("value"));
                    clmTnp.setCellValueFactory(new PropertyValueFactory<>("np"));
					clmTnu.setCellValueFactory(new PropertyValueFactory<>("nu"));
                    tabT.setItems((ObservableList<Type>) sT.afficher());
	}
	
	@FXML
    void btnEcreate_clic(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/createType_.fxml"));
        viewType.getChildren().setAll(pane);
		
    }

    @FXML
    void btnEdelete_clic(ActionEvent event) {
		Type T = tabT.getSelectionModel().getSelectedItem();
		
                                if (T == null) {

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
                                        ServiceType sT = new ServiceType();
										Type T1 = new Type(T.getId());
										Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
										alert.setTitle("Confirmation ");
										alert.setHeaderText(null);
										alert.setContentText("Vous voulez vraiment supprimer ce Type");
										Optional<ButtonType> action = alert.showAndWait();
									   if (action.get() == ButtonType.OK) {
											sT.supprimer(T1);
											READ();
									   }
                                  }
    }

    @FXML
    void upadateT_clic(ActionEvent event) throws IOException  {
		Type T = tabT.getSelectionModel().getSelectedItem();
//             data2.removeAll(Pr);

   
   
   
                                  if (T == null) {

                                    Image img = new Image("/com/hypocampus/uploads/error.png");
                                    Notifications n = Notifications.create()
                                                     .title("Error")
                                                     .text("No Project selected :  Please select a Type for edit")
                                                     .graphic(new ImageView(img))
                                                     .position(Pos.TOP_CENTER)
                                                      .hideAfter(Duration.seconds(5));
                                    n.darkStyle();
                                    n.show();
                                }
     else{
                                      
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hypocampus/gui/updateType.fxml"));              
        Parent parent = loader.load();
        viewType.getChildren().setAll(parent);

           UpdateTypeController controller = (UpdateTypeController) loader.getController();
            controller.inflateUI(T);
            controller.add(T);
    
            
          }

    }
	
}
