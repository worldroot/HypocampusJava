/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.services;

import com.hypocampus.models.Backlog;
import com.hypocampus.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author mehdibehira
 */
public class ServiceBacklog {
    
    Connection cnx = DataSource.getInstance().getCnx();
    
    
    public void ajouter(Backlog B){
        try {
            String requete = "INSERT INTO backlog (points_to_do,points_in_progress,points_done,project_id) VALUES (?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, B.getPoints_to_do());
            pst.setInt(2, B.getPoints_in_progress());
            pst.setInt(3, B.getPoints_done());
            pst.setInt(4, B.getProject_id());
            pst.executeUpdate();
            System.out.println("Backlog ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
  
    }

    
    
}
