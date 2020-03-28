/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.services;

import com.hypocampus.models.Commentaire;
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
//need to add the user id later
public class ServiceCommentaire implements IService<Commentaire>{
    Connection cnx = DataSource.getInstance().getCnx();
    
    @Override
    public void ajouter(Commentaire c) {
        try {
            long millis=System.currentTimeMillis();
            java.sql.Date date=new java.sql.Date(millis);
            String requete = "INSERT INTO commentaire (task_id,description,user_id, image_name,date_creation) VALUES (?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, c.getTask_id());
            pst.setString(2, c.getDescription());
            pst.setInt(3, c.getUser_id());
            pst.setString(4, c.getImage_name());
            pst.setDate(5, date);
            pst.executeUpdate();
            System.out.println("Commentaire ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

    @Override
    public void supprimer(Commentaire c) {
        try {
            String requete = "DELETE FROM commentaire WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,c.getId());
            pst.executeUpdate();
            System.out.println("Commentaire supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Commentaire c) {
        try {
        String requete = "UPDATE Commentaire SET description=?, image_name=?, description_technique=? WHERE id=?";
        PreparedStatement pst = cnx.prepareStatement(requete);
        pst.setString(1, c.getDescription());
        pst.setString(2, c.getImage_name());

        pst.executeUpdate();
        System.out.println("Commentaire modifiée !");

        } catch (SQLException ex) {
        System.err.println(ex.getMessage());
        }
        
    }

    @Override
    public List<Commentaire> afficher() {
                List<Commentaire> list = new ArrayList<>();

        try {
            String requete = "SELECT * FROM commentaire";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Commentaire(rs.getInt("id"), rs.getInt("task_id"),rs.getInt("user_id"),
                rs.getString("image_name"), rs.getString("description"), rs.getDate("date_creation")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }



    
}
