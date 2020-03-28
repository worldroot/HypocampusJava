/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.services;

import com.hypocampus.models.Sprint;
import com.hypocampus.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 21694
 */
public class ServiceSprint implements IService<Sprint> {
Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Sprint S) {
                    try {
                
            String requete = "INSERT INTO sprint (sprintname,startDatesprint,endDatesprint,etat,projets_id) VALUES (?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, S.getName());
            pst.setDate(2, S.getStart_date_sprint());
            pst.setDate(3, S.getEnd_date_sprint());
            pst.setInt(4, S.getEtat());
            pst.setInt(5, S.getProject_id());
            pst.executeUpdate();
            System.out.println("sprint ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    
    
    
    }

    @Override
    public void supprimer(Sprint S) {
                        try {
            String requete = "DELETE FROM sprint WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,S.getId());
            pst.executeUpdate();
            System.out.println("sprint supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Sprint S) {
                        try {
            String requete = "UPDATE sprint SET sprintname=?,startDatesprint=?,endDatesprint=?,projets_id=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(5, S.getId());
            pst.setString(1, S.getName());
            pst.setDate(2, S.getStart_date_sprint());
            pst.setDate(3, S.getEnd_date_sprint());
            pst.setInt(4, S.getProject_id());
            pst.executeUpdate();
            System.out.println("sprint modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Sprint> afficher() {
         ObservableList <Sprint> ListSprint =FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM sprint";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ListSprint.add(new Sprint(rs.getInt("id"), rs.getString("sprintname")
                ,rs.getDate("startDatesprint"), rs.getDate("endDatesprint"),rs.getInt("projets_id")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return ListSprint;
    }
    
}
