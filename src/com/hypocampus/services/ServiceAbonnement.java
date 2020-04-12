/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.services;

import com.hypocampus.models.Abonnement;
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
public class ServiceAbonnement implements IService<Abonnement>{
	
	Connection cnx = DataSource.getInstance().getCnx();

	@Override
	public void ajouter(Abonnement t) {
		try {
                
            String requete = "INSERT INTO subscription (name,date,active,tarif) VALUES (?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, t.getName());
            pst.setDate(2, t.getDate());
            pst.setInt(3, t.getActive());
			pst.setInt(4, t.getT().getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
	}
	
	@Override
	public void supprimer(Abonnement t) {
		  try {
            String requete = "DELETE FROM subscription WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,t.getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
	}
	
	
	@Override
	public void modifier(Abonnement t) {
		   try {
            String requete = "UPDATE subscription SET name=?, date=?, active=?, tarif=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(5, t.getId());
            pst.setString(1, t.getName());
            pst.setDate(2, t.getDate());
            pst.setInt(3, t.getActive());
			pst.setInt(4, t.getT().getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
	}

	@Override
	public List<Abonnement> afficher() {
		 ObservableList <Abonnement> ListProject =FXCollections.observableArrayList();
        try {
            String requete = "SELECT * FROM subscription";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
				Type t = getType(rs.getInt("tarif"));
                ListProject.add(new Abonnement(rs.getInt("id"),rs.getString("name"),rs.getDate("date"),rs.getInt("active"),t));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return ListProject;
	}
	
	public Type getType(int id) {
		Type t = new Type();
        try {
            String requete = "SELECT * FROM tarif where id =?";
            PreparedStatement pst = cnx.prepareStatement(requete);
			pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
				t = new Type(rs.getInt("id"),rs.getString("name"),rs.getInt("value"),rs.getInt("projectnbr"),rs.getInt("usernbr"));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return t;
	}
}
