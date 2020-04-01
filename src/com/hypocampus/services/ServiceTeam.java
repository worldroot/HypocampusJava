/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.services;

import com.hypocampus.models.Project;
import com.hypocampus.models.team;
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
public class ServiceTeam implements IService<team> {
        
    Connection cnx = DataSource.getInstance().getCnx();
     @Override
    public void ajouter(team T)              
    {
            try {
                
            String requete = "INSERT INTO team (teamname,dateofcreation) VALUES (?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, T.getTeamname());
            pst.setDate(2, T.getDateofcreation());
            pst.executeUpdate();
            System.out.println("team ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    
    
    
    
    }



    @Override
    public void supprimer(team P) {
                try {
            String requete = "DELETE FROM team WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,P.getId());
            pst.executeUpdate();
            System.out.println("team supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(team T) {
                try {
            String requete = "UPDATE team SET teamname=?, dateofcreation=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(3, T.getId());
            pst.setString(1, T.getTeamname());
            pst.setDate(2, T.getDateofcreation());
            pst.executeUpdate();
            System.out.println("team modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<team> afficher() {
       
   ObservableList <team> Listteam =FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM team";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Listteam.add(new team(rs.getInt("id"), rs.getString("teamname"),
               rs.getDate("dateofcreation")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return Listteam;
    }
                   
   public team getById(int idp) {
		 team pp = new team();

        try {
            String requete = "SELECT * FROM team where id="+idp+"";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                pp = new team(rs.getInt("id"),rs.getString("teamname"));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return pp;
	}               
                   
                    
   
}
