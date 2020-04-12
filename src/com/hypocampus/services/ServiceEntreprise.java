/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.services;

import com.hypocampus.models.Entreprise;
import com.hypocampus.models.Project;
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
 * @author Houcem
 */
public class ServiceEntreprise implements IService<Entreprise> {
	
	Connection cnx = DataSource.getInstance().getCnx();

	@Override
	public void ajouter(Entreprise t) {
		try {
                
            String requete = "INSERT INTO Entreprise (name,email,createdate) VALUES (?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, t.getName());
            pst.setString(2, t.getEmail());
            pst.setDate(3, t.getCreatedate());
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    
	}

	@Override
	public void supprimer(Entreprise t) {
		  try {
            String requete = "DELETE FROM entreprise WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,t.getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
	}

	@Override
	public void modifier(Entreprise t) {
		   try {
            String requete = "UPDATE entreprise SET name=?, email=?,createdate=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(4, t.getId());
            pst.setString(1, t.getName());
            pst.setString(2, t.getEmail());
            pst.setDate(3, t.getCreatedate());
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
	}

	@Override
	public List<Entreprise> afficher() {
		 ObservableList <Entreprise> ListProject =FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM entreprise";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ListProject.add(new Entreprise(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getDate("createdate")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return ListProject;
	}
	
	public Entreprise getById(Entreprise t) {
		 Entreprise e = new Entreprise();

        try {
            String requete = "SELECT * FROM entreprise where id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
			pst.setInt(1, t.getId());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                e = new Entreprise(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getDate("createdate"));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return e;
	}
	
	public List<Entreprise> search(String x,String y) {
		 ObservableList <Entreprise> ListProject = FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM entreprise where "+ y +" like ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
			pst.setString(1, "%"+x+"%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ListProject.add(new Entreprise(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getDate("createdate")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return ListProject;
	}
	
}
