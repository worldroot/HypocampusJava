/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.services;

import com.hypocampus.models.Event;
import com.hypocampus.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class ServiceEvent implements IService<Event>{
    
    Connection cnx = DataSource.getInstance().getCnx();
    
        @Override
    public void ajouter(Event e) {
        try {
           
            Statement stm = cnx.createStatement();
            String query = "INSERT INTO events_admin (idev,TitreEvent,NumeroEvent,TypeEvent,DateEvent,enddateEvent) VALUES (NULL, '"+e.getTitreEvent()+"', '"+e.getNumeroEvent()+"', '"+e.getTypeEvent()+"', '"+e.getDateEvent()+"', '"+e.getEnddateEvent()+"')";
            stm.executeUpdate(query);
            
            System.out.println("Event ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

    @Override
    public void supprimer(Event t) {
 try {
            String requete = "DELETE FROM events_admin WHERE idev=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,t.getIdev());
            pst.executeUpdate();
            System.out.println("Event supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }    }

    @Override
    public void modifier(Event t) {
        
            try {
            String requete = "UPDATE events_admin SET TitreEvent=?,NumeroEvent=?,TypeEvent=?,DateEvent=?,enddateEvent=?,image_name=?,updated_at=? WHERE idev=?";
                   
            PreparedStatement pst = cnx.prepareStatement(requete);
            
            pst.setString(1, t.getTitreEvent());
            pst.setInt(2, t.getNumeroEvent());
            pst.setString(3, t.getTypeEvent());
            pst.setDate(4, t.getDateEvent());
            pst.setDate(5, t.getEnddateEvent());
            pst.setString(6, t.getImage_name());
            pst.setDate(7, t.getUpdated_at());
            pst.setInt(8, t.getIdev());
            
            pst.executeUpdate();
            System.out.println("Event modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Event> afficher() {
        
        List<Event> list = new  ArrayList<>();
        String req = "select * from events_admin";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs =  pst.executeQuery();
            while(rs.next()){
                Event e = new Event(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getString(7), rs.getDate(8));
                list.add(e);
            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        } 
        return list;
    }    




}



    

