/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.services;

import com.hypocampus.models.Participant;
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
public class ServiceParticipant implements IService<Participant> {
    
 Connection cnx = DataSource.getInstance().getCnx();
    
 @Override
    public void ajouter(Participant p) {
        
    try {
            
            Statement stm = cnx.createStatement();
            /* String query  =""
                + "SELECT p.nomp, p.prenomp, p.choix, p.email, p.password, p.choix, p.review "
                + "FROM participant p"
                + "JOIN events_admin e ON e.idev = p.choix"; 
            */
            
            String query = ""
                    + "INSERT INTO participant "
                    + "(nomp,prenomp,email,passwordp,choix,review) VALUES ('"+p.getNomp()+"','"+p.getPrenomp()+"','"+p.getEmail()+"',"
                    + "'"+p.getPasswordp()+"', '"+p.getChoix()+"' ,'"+p.getReview()+"' )";
            
            stm.executeUpdate(query);
            System.out.println("Participant ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }   
    }
    
 @Override
    public List<Participant> afficher() {
        
        List<Participant> list = new  ArrayList<>();
        String req = "select * from participant";
         /* String req = "SELECT  participant.nomp as 'Nom', participant.prenomp as 'Prenom', participant.email as 'Email', participant.passwordp as 'Password', "
                        + "participant.choix as 'Choix', participant.review as 'Review'  FROM participant INNER JOIN "
                        + "events_admin ON participant.nomp=events_admin.idev ";
                        */
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs =  pst.executeQuery();
            while(rs.next()){
                Participant p = new Participant (rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6));
                list.add(p);
            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        } 
        return list;
    }

    @Override
    public void supprimer(Participant t) {
try {
            String requete = "DELETE FROM participant WHERE nomp=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1,t.getNomp());
            pst.executeUpdate();
            System.out.println("Participant supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }     
    }

    @Override
    public void modifier(Participant p) {
        try {
            Statement stm = cnx.createStatement();
            String query = "UPDATE participant SET  prenomp= '"+p.getPrenomp()+"', email= '"+p.getEmail()+"', passwordp= '"+p.getPasswordp()+"', review= '"+p.getReview()+"' WHERE nomp='"+p.getNomp()+"' ";
            stm.executeUpdate(query);     
            System.out.println("Participant Modifié !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }  
        
    }
    
    public String GetById(int id) {
        
        Event list = new Event();
        String req = "select * from participant where nomp="+id+"";
        
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
    
    
    
    
    
}


    

