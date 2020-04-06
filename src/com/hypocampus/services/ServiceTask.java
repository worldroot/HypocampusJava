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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.mail.Session;

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
    
    public void point_algortime_1(Task T, boolean math) throws SQLException{
        ServiceBacklog SB = new ServiceBacklog();
        Backlog B = SB.getBacklogbyId(T.getBacklog_id());
        int old_points = T.getStory_points();
     
        if ("To Do".equals(T.getState())){
           SB.update_points_1(B, 1, old_points, math);
        }else if("Doing".equals(T.getState())){
            SB.update_points_1(B, 2, old_points, math);
        }else{
            SB.update_points_1(B, 3, old_points, math);
        }
        
    }
    
    public Task getTaskbyid(int id){
                Task ta = new Task();

        try {
            String requete = "SELECT * FROM task where id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ta = new Task(rs.getInt("id"), rs.getInt("backlog_id"), rs.getString("title")
                ,rs.getString("description_fonctionnel"), rs.getString("description_technique")
                ,rs.getInt("story_points"), rs.getDate("created_date"), rs.getDate("finished_date")
                ,rs.getString("state"), rs.getInt("priority"), rs.getInt("archive"), rs.getInt("sprint_id"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
          
         return ta;

        
       
    }
    
    
    
    public List<Task> SearchTasksAdvanced(String character) throws SQLException  {
        Statement stm = cnx.createStatement();
        String req="select * from task where title  LIKE '%"+character+"%'" ;
        ResultSet rst = stm.executeQuery(req);
        List<Task> tasks = new ArrayList<>();
        while (rst.next()) {
            Task p2 = new Task();
            p2.setId(rst.getInt("id"));
            p2.setBacklog_id(rst.getInt("backlog_id"));
            p2.setTitle(rst.getString("title"));
            p2.setDescription_fonctionnel(rst.getString("description_fonctionnel"));            
            p2.setDescription_technique(rst.getString("description_technique"));
            p2.setStory_points(rst.getInt("story_points"));
            p2.setCreated_date(rst.getDate("created_date"));
            p2.setFinished_date(rst.getDate("finished_date"));
            p2.setState(rst.getString("state"));
            p2.setPriority(rst.getInt("priority"));
            p2.setArchive(rst.getInt("archive"));
            p2.setSprint_id(rst.getInt("sprint_id"));


            tasks.add(p2);
        }
     return tasks;
    }
   
}
