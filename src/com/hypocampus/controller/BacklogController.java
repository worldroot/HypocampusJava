/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.services.ServiceBacklog;
import com.hypocampus.utils.DataSource;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author mehdibehira
 */


public class BacklogController implements Initializable {

    @FXML
    private Button AjoutAction;
    @FXML
    private AnchorPane AjoutBacklogPane;
    @FXML
    private ChoiceBox<?> ListProjetAction;

    /**
     * Initializes the controller class.
     */
    
    final ObservableList options = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AjouterBacklog(MouseEvent event) throws SQLException {
        ServiceBacklog sb = new ServiceBacklog();
        AjoutBacklogPane.setVisible(true);
        Connection cnx = DataSource.getInstance().getCnx();
                // first I execute the query that select name of department one by one
        String query = "select projet_name from projets";
        PreparedStatement statement = cnx.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        while(set.next()){
            options.add(set.getString("projet_name"));
        }
        ListProjetAction.setItems(options);
        statement .close();
        set.close();
        
       
    }
    
    
}
