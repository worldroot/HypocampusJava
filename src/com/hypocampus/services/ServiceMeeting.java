/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.services;

import com.hypocampus.models.Sprint;
import com.hypocampus.models.meeting;
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
public class ServiceMeeting implements IService<meeting>{
 Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(meeting S) {
                    try {
                
            String requete = "INSERT INTO meeting (team_id,description,duration,nbrmeeting) VALUES (?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, S.getTeam_id());
            pst.setString(2, S.getDescription());
            pst.setString(3, S.getDuration());
            pst.setInt(4, S.getNbrmeeting());
           
            pst.executeUpdate();
            System.out.println("meeting ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    
    
    
    }

    @Override
    public void supprimer(meeting S) {
                        try {
            String requete = "DELETE FROM meeting WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,S.getId());
            pst.executeUpdate();
            System.out.println("meeting supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(meeting S) {
                        try {
            String requete = "UPDATE meeting SET team_id=?,description=?,duration=?,nbrmeeting=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(5, S.getId());
            pst.setInt(1, S.getTeam_id());
            pst.setString(2, S.getDescription());
            pst.setString(3, S.getDuration());
            pst.setInt(4, S.getNbrmeeting());
            pst.executeUpdate();
            System.out.println("meeting modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<meeting> afficher() {
         ObservableList <meeting> Listmeeting =FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM meeting";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Listmeeting.add(new meeting(rs.getInt("id"), rs.getInt("team_id")
                ,rs.getString("description"),rs.getString("duration"),rs.getInt("nbrmeeting")));
            }
            Listmeeting.sort((o1, o2) -> o1.getDuration().compareTo(o2.getDuration()));

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return Listmeeting;
    }
       
}
