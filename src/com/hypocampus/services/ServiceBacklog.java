/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.services;

import com.hypocampus.models.Backlog;
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
 * @author mehdibehira
 */
public class ServiceBacklog implements IService<Backlog>{
    
    Connection cnx = DataSource.getInstance().getCnx();
    
    
    @Override
    public void ajouter(Backlog B){
        try {
            String requete = "INSERT INTO backlog (points_to_do,points_in_progress,points_done,project_id) VALUES (?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, B.getPoints_to_do());
            pst.setInt(2, B.getPoints_in_progress());
            pst.setInt(3, B.getPoints_done());
            pst.setInt(4, B.getProject_id());
            pst.executeUpdate();
            System.out.println("Backlog ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
  
    }
    
    @Override
    public void supprimer(Backlog B) {
        try {
            String requete = "DELETE FROM backlog WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,B.getId());
            pst.executeUpdate();
            System.out.println("Backlog supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Backlog B) {
        try {
            String requete = "UPDATE backlog SET points_to_do=?, points_in_progress=?, points_done=?, project_id=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(5, B.getId());
            pst.setInt(1, B.getPoints_to_do());
            pst.setInt(2, B.getPoints_in_progress());
            pst.setInt(3, B.getPoints_done());
            pst.setInt(4, B.getProject_id());
            pst.executeUpdate();
            System.out.println("Backlog modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void update_points_1(Backlog B, int step, int points, boolean math) throws SQLException{
        switch (step) {
            case 1:
                String requete = "UPDATE backlog SET points_to_do=? WHERE id=?";
                PreparedStatement pst = cnx.prepareStatement(requete);
                if (math == false){
                  pst.setInt(1, B.getPoints_to_do() - points);
                }else{
                    pst.setInt(1, B.getPoints_to_do() + points); 
                }
                pst.setInt(2, B.getId());
                pst.executeUpdate();
                System.out.println("Backlog  Update points to do done !");
                break;
            case 2:
                String requete2 = "UPDATE backlog SET points_in_progress=? WHERE id=?";
                PreparedStatement pst2 = cnx.prepareStatement(requete2);
                if (math == false){
                    pst2.setInt(1, B.getPoints_in_progress() - points);
                }
                else{
                    pst2.setInt(1, B.getPoints_in_progress() + points);
                }
                pst2.setInt(2, B.getId());
                pst2.executeUpdate();
                System.out.println("Backlog  Update points in progress done !");
                break;
            case 3:
                String requete3 = "UPDATE backlog SET points_done=? WHERE id=?";
                PreparedStatement pst3 = cnx.prepareStatement(requete3);
                if (math == false){
                    pst3.setInt(1, B.getPoints_done() - points);
                }else{
                    pst3.setInt(1, B.getPoints_done() + points);
                }
                pst3.setInt(2, B.getId());
                pst3.executeUpdate();
                System.out.println("Backlog  Update points  done !");
                break;
            default:
                System.out.println("Backlog  Update points  nothing to do !");
                break;
        }
        
    }

    public void modifierProject(Backlog B){
                try {
            String requete = "UPDATE backlog SET project_id=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(2, B.getId());
            pst.setInt(1, B.getProject_id());
            pst.executeUpdate();
            System.out.println("Backlog Project id modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        
    }
    @Override
    public List<Backlog> afficher() {
         ObservableList <Backlog> ListBacklog =FXCollections.observableArrayList(); 
        try {
            String requete = "SELECT * FROM backlog";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ListBacklog.add(new Backlog(rs.getInt("id"), rs.getInt("points_to_do"), rs.getInt("points_in_progress")
                ,rs.getInt("points_done"), rs.getInt("project_id")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return ListBacklog;
    }
    
    public Backlog getBacklogbyId(int id){
        Backlog ba = new Backlog(); 
        try {
            String requete = "SELECT * FROM backlog where id= ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ba = new Backlog(rs.getInt("id"), rs.getInt("points_to_do"), rs.getInt("points_in_progress")
                ,rs.getInt("points_done"), rs.getInt("project_id"));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return ba;
    
    }

    
}
