/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;

import com.hypocampus.utils.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author tibh
 */
public class EventStatController implements Initializable {

    @FXML
    private PieChart pieChart;
    Connection cnx;
    Statement ste;
       ObservableList < PieChart.Data > piechartdata;
    ArrayList < String > p = new ArrayList <  > ();
    ArrayList < Double > c = new ArrayList <> ();
    @FXML
    private ImageView Retour;
    @FXML
    private AnchorPane SmallPane;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
                
  pieChart.setData(piechartdata);
   
    }
    
    public void loadData() {
     
   
    //String query="Select entrainement.categorie ,SUM(entrainement.id)*100/Tot.total as pourcentage from entrainement ,(select SUM(id) as total from entrainement) as Tot Group By entrainement.categorie";
    String query="Select events_admin.titreEvent, SUM(events_admin.NumeroEvent)*100/Tot.total as pourcentage from events_admin ,"
            + "(select SUM(NumeroEvent) as total from events_admin) as Tot Group By events_admin.TitreEvent ";
         
    piechartdata = FXCollections.observableArrayList();

      Connection cnx = DataSource.getInstance().getCnx();
 
    try {
      
      ResultSet rs = cnx.createStatement().executeQuery(query);
  
      while (rs.next()) {
       
        piechartdata.add(new PieChart.Data(rs.getString("titreEvent")+"("+Double.toString(rs.getDouble("pourcentage"))+")", rs.getDouble("pourcentage")));
        String pour=Double.toString(rs.getDouble("pourcentage"));
        p.add(rs.getString("titreEvent")+pour);
        c.add(rs.getDouble("pourcentage"));
        
        
      }
      
    } catch (SQLException e) {
  System.out.println(e.getMessage());
    }
    
    }

    @FXML
    private void RetourAction(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/hypocampus/gui/EventAffichage.fxml"));
        SmallPane.getChildren().setAll(pane);
    }
    
    
    
}
