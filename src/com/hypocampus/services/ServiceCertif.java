/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.services;

import com.hypocampus.models.Certif;
import com.hypocampus.utils.DataSource;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class ServiceCertif implements IService<Certif>{

    Connection cnx = DataSource.getInstance().getCnx();
    
    @Override
    public void ajouter(Certif t) {
            try {
            
            Statement stm = cnx.createStatement();
            String query = ""
            + "INSERT INTO certif (idc,titrec,pointc,datec,image_name) VALUES (NULL,'"+t.getTitrec()+"','"+t.getPointc()+"','"+t.getDatec()+"', '"+t.getImage_name()+"' )";
            stm.executeUpdate(query);
            
            System.out.println("Certif ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } 
    }

    @Override
    public void supprimer(Certif t) {
                try {
            String requete = "DELETE FROM certif WHERE idc=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,t.getIdc());
            pst.executeUpdate();
            System.out.println("Certif supprimé !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Certif t) {
        
                    try {
            String requete = "UPDATE certif SET titrec=?,pointc=?,datec=?,image_name=?,updated_at=? WHERE idc=?";
                   
            PreparedStatement pst = cnx.prepareStatement(requete);
            
            pst.setInt(1, t.getTitrec());
            pst.setInt(2, t.getPointc());
            pst.setDate(3, t.getDatec());
            pst.setString(4, t.getImage_name());
            pst.setDate(5, t.getUpdated_at());
            pst.setInt(6, t.getIdc());
            
            pst.executeUpdate();
            System.out.println("Certif modifié !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Certif> afficher() {
        
        List<Certif> list = new  ArrayList<>();
        String req = "select * from certif";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs =  pst.executeQuery();
            while(rs.next()){
                Certif e = new Certif (rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4), rs.getString(5));
                list.add(e);
            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        } 
        return list;
    }
           
}
