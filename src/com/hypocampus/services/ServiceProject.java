/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.services;

import com.hypocampus.models.Project;
import com.hypocampus.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 21694
 */
public class ServiceProject  {
    
    
    Connection cnx = DataSource.getInstance().getCnx();
    
    public void ajouter(Project P)              
    {
            try {
                
            String requete = "INSERT INTO projets (projet_name,owner,start_date,end_date,description,history) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, P.getName());
            pst.setString(2, P.getOwner());
            pst.setDate(3, P.getStart_date());
            pst.setDate(4, P.getEnd_date());
            pst.setString(5, P.getDescription());
            pst.setInt(6, P.getHistory());
            pst.executeUpdate();
            System.out.println("Project ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    
    
    
    
    }



 
    public void supprimer(Project P) {
                try {
            String requete = "DELETE FROM projets WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,P.getId());
            pst.executeUpdate();
            System.out.println("Projets supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }


    public void modifier(Project P) {
                try {
            String requete = "UPDATE projets SET projet_name=?, owner=?,start_date=?,end_date=?,description=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(6, P.getId());
            pst.setString(1, P.getName());
            pst.setString(2, P.getOwner());
            pst.setDate(3, P.getStart_date());
            pst.setDate(4, P.getEnd_date());
            pst.setString(5, P.getDescription());
            pst.executeUpdate();
            System.out.println("Project modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }


    public List<Project> afficher() {
       
   ObservableList <Project> ListProject =FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM projets";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ListProject.add(new Project(rs.getInt("id"), rs.getString("projet_name"),rs.getString("owner")
                ,rs.getDate("start_date"), rs.getDate("end_date"),rs.getString("description")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return ListProject;
    }
                   
    public List<Project> afficherN() {
       
   ObservableList <Project> ListProject =FXCollections.observableArrayList();

        try {
            String requete = "SELECT projet_name FROM projets";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ListProject.add(new Project( rs.getString("projet_name")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return ListProject;
    }
                                   
    	public Project getById(int idp) {
		 Project pp = new Project();

        try {
            String requete = "SELECT * FROM projets where id="+idp+"";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                pp = new Project(rs.getInt("id"),rs.getString("projet_name"));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return pp;
	}               
       public int affPagination() {
       
         int count =0;
        try {
            String requete = "SELECT count(*) FROM projets";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            rs.first();
         count=rs.getInt(1);

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return count;
    }                
    
}
