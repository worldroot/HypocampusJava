/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.services;

import com.hypocampus.models.Backlog;
import com.hypocampus.models.Task;
import com.hypocampus.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author mehdibehira
 */
public class ServiceTask implements IService<Task>{
        Connection cnx = DataSource.getInstance().getCnx();


    @Override
    public void ajouter(Task t) {
        try {
            long millis=System.currentTimeMillis();
            java.sql.Date date=new java.sql.Date(millis);
            String requete = "INSERT INTO Task (backlog_id,title,description_fonctionnel,description_technique,"
                    + "story_points, created_date,finished_date, state, priority, archive, sprint_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getBacklog_id());
            pst.setString(2, t.getTitle());
            pst.setString(3, t.getDescription_fonctionnel());
            pst.setString(4, t.getDescription_technique());
            pst.setInt(5, t.getStory_points());
            pst.setDate(6, date);
            pst.setDate(7, t.getFinished_date());
            pst.setString(8, t.getState());
            pst.setInt(9, t.getPriority());
            pst.setInt(10, t.getArchive());
            pst.setInt(11, t.getSprint_id());
            
            pst.executeUpdate();
            System.out.println("Tache ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

    @Override
    public void supprimer(Task t) {
        try {
            String requete = "DELETE FROM task WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,t.getId());
            pst.executeUpdate();
            System.out.println("Tache supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Task t) {
    try {
            String requete = "UPDATE task SET title=?, description_fonctionnel=?, description_technique=?,"
                    + " story_points=?, finished_date=?, state=?, priority=?, archive=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, t.getTitle());
            pst.setString(2, t.getDescription_fonctionnel());
            pst.setString(3, t.getDescription_technique());
            pst.setInt(4, t.getStory_points());
            pst.setDate(5, t.getFinished_date());
            pst.setString(6, t.getState());
            pst.setInt(7, t.getPriority());
            pst.setInt(8, t.getArchive());
            pst.setInt(9, t.getId());

            pst.executeUpdate();
            System.out.println("Tache modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void modifier_etat(Task t){
            try {
            String requete = "UPDATE task SET state=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, t.getState());
            pst.setInt(2, t.getId());

            pst.executeUpdate();
            System.out.println("Etat Tache modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

    @Override
    public List<Task> afficher() {
        List<Task> list = new ArrayList<>();

        try {
            String requete = "SELECT * FROM task";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Task(rs.getInt("id"), rs.getInt("backlog_id"), rs.getString("title")
                ,rs.getString("description_fonctionnel"), rs.getString("description_technique")
                ,rs.getInt("story_points"), rs.getDate("created_date"), rs.getDate("finished_date")
                ,rs.getString("state"), rs.getInt("priority"), rs.getInt("archive"), rs.getInt("sprint_id")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        

        return list;
    }
    
    public List<Task> afficher(int first, int limit){
        
              List<Task> list = new ArrayList<>();

        try {
            String requete = "SELECT * FROM task";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Task(rs.getInt("id"), rs.getInt("backlog_id"), rs.getString("title")
                ,rs.getString("description_fonctionnel"), rs.getString("description_technique")
                ,rs.getInt("story_points"), rs.getDate("created_date"), rs.getDate("finished_date")
                ,rs.getString("state"), rs.getInt("priority"), rs.getInt("archive"), rs.getInt("sprint_id")));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
          
        if ((first==0)&&(limit==0)) {
           return list; 
        } else if ((first==0)&&(limit!=0)) {
            return  list.stream().limit(limit).collect(Collectors.toList());
        } else if ((first!=0)&&(limit==0)) {
            return  list.stream().skip(first).collect(Collectors.toList());
        }
        else{
         return  list.stream().skip(first).limit(limit).collect(Collectors.toList());
        }

        
        
    }
    
    
       public List<Task> afficherParBacklog(int first, int limit, Backlog b){
        
              List<Task> list = new ArrayList<>();

        try {
            String requete = "SELECT * FROM task where task.backlog_id ="+b.getId()+"";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Task(rs.getInt("id"), rs.getInt("backlog_id"), rs.getString("title")
                ,rs.getString("description_fonctionnel"), rs.getString("description_technique")
                ,rs.getInt("story_points"), rs.getDate("created_date"), rs.getDate("finished_date")
                ,rs.getString("state"), rs.getInt("priority"), rs.getInt("archive"), rs.getInt("sprint_id")));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
          
        if ((first==0)&&(limit==0)) {
           return list; 
        } else if ((first==0)&&(limit!=0)) {
            return  list.stream().limit(limit).collect(Collectors.toList());
        } else if ((first!=0)&&(limit==0)) {
            return  list.stream().skip(first).collect(Collectors.toList());
        }
        else{
         return  list.stream().skip(first).limit(limit).collect(Collectors.toList());
        }

        
        
    }
       
    public List<Task> afficherParBacklogId(int first, int limit, int id){
        
              List<Task> list = new ArrayList<>();

        try {
            String requete = "SELECT * FROM task where task.backlog_id ="+id+"";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Task(rs.getInt("id"), rs.getInt("backlog_id"), rs.getString("title")
                ,rs.getString("description_fonctionnel"), rs.getString("description_technique")
                ,rs.getInt("story_points"), rs.getDate("created_date"), rs.getDate("finished_date")
                ,rs.getString("state"), rs.getInt("priority"), rs.getInt("archive"), rs.getInt("sprint_id")));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
          
        if ((first==0)&&(limit==0)) {
           return list; 
        } else if ((first==0)&&(limit!=0)) {
            return  list.stream().limit(limit).collect(Collectors.toList());
        } else if ((first!=0)&&(limit==0)) {
            return  list.stream().skip(first).collect(Collectors.toList());
        }
        else{
         return  list.stream().skip(first).limit(limit).collect(Collectors.toList());
        }

        
        
    }

}
