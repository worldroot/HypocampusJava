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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mehdibehira
 */
public class ServiceBacklog implements IService<Backlog>{
    
    Connection cnx = DataSource.getInstance().getCnx();
    
    
    @Override
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
    
    @Override
    public void supprimer(Backlog B) {
        try {
            String requete = "DELETE FROM backlog WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,B.getId());
            pst.executeUpdate();
            System.out.println("Backlog supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Backlog B) {
        try {
            String requete = "UPDATE backlog SET points_to_do=?, points_in_progress=?, points_done=?, project_id=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(5, B.getId());
            pst.setInt(1, B.getPoints_to_do());
            pst.setInt(2, B.getPoints_in_progress());
            pst.setInt(3, B.getPoints_done());
            pst.setInt(4, B.getProject_id());
            pst.executeUpdate();
            System.out.println("Backlog modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    @Override
    public List<Backlog> afficher() {
        List<Backlog> list = new ArrayList<>();

        try {
            String requete = "SELECT * FROM backlog";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Backlog(rs.getInt("id"), rs.getInt("points_to_do"), rs.getInt("points_in_progress")
                ,rs.getInt("points_done"), rs.getInt("project_id")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }

    
    
}
