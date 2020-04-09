/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.services;

import com.hypocampus.models.Entreprise;
import com.hypocampus.models.Type;
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
public class ServiceType implements IService<Type>{
	
	Connection cnx = DataSource.getInstance().getCnx();

	@Override
	public void ajouter(Type t) {
		try {
                
            String requete = "INSERT INTO tarif (name,value,projectnbr,usernbr) VALUES (?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, t.getName());
            pst.setInt(2, t.getValue());
            pst.setInt(3, t.getNp());
            pst.setInt(4, t.getNu());
			pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
	}
	
	@Override
	public void supprimer(Type t) {
		  try {
            String requete = "DELETE FROM tarif WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,t.getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
	}
	
	@Override
	public void modifier(Type t) {
		   try {
            String requete = "UPDATE tarif SET name=?, value=?, projectnbr=?, usernbr=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(5, t.getId());
            pst.setString(1, t.getName());
            pst.setInt(2, t.getValue());
            pst.setInt(3, t.getNp());
            pst.setInt(4, t.getNu());
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
	}
	
	@Override
	public List<Type> afficher() {
		 ObservableList <Type> ListProject = FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM tarif";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ListProject.add(new Type(rs.getInt("id"),rs.getString("name"),rs.getInt("value"),rs.getInt("projectnbr"),rs.getInt("usernbr")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return ListProject;
	}
}
