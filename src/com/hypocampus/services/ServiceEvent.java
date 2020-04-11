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
            String query = "INSERT INTO events_admin (idev,TitreEvent,NumeroEvent,TypeEvent,DateEvent,enddateEvent,image_name) VALUES (NULL, '"+e.getTitreEvent()+"'"
                    + ", '"+e.getNumeroEvent()+"', '"+e.getTypeEvent()+"' "
                    + ", '"+e.getDateEvent()+"', '"+e.getEnddateEvent()+"',  '"+e.getImage_name()+"'  )";
            stm.executeUpdate(query);
            
            System.out.println("Event ajouté !");

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
            System.out.println("Event supprimé !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }    
    }

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
            System.out.println("Event modifié !");

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
    
    
    
    public String GetById(int id) {
        
        Event list = new Event();
        String req = "select * from events_admin where idev="+id+"";
        
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs =  pst.executeQuery();
            while(rs.next()){
                Event e = new Event(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getString(7), rs.getDate(8));
                list=e;
            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        } 
        return list.getTitreEvent();
    } 
    
    public int getTitreId(String TitreEvent) throws SQLException {
        Statement stm = cnx.createStatement();
        String query = "select idev  from events_admin where TitreEvent='"+TitreEvent+"'";
        ResultSet rst = stm.executeQuery(query);
        int id = 0;
        while (rst.next()) {
            id = rst.getInt("idev");
        }
        return id;
    }
     public Event getEvent(String email ) throws SQLException {
        Statement stm = cnx.createStatement();
        String query = "select events_admin.* from events_admin ,participant where participant.choix=events_admin.idev and participant.email='"+email+"'";
        ResultSet rst = stm.executeQuery(query);

        Event pst = new Event();

        while (rst.next()) {

            pst.setIdev(rst.getInt(1));
            pst.setTitreEvent(rst.getString(2));
            pst.setNumeroEvent(rst.getInt(3));
            pst.setTypeEvent(rst.getString(4));
            pst.setDateEvent(rst.getDate(5));
            pst.setEnddateEvent(rst.getDate(6));
            pst.setImage_name(rst.getString(7));
            

        }
        return pst;
    }
}


    
    






    

