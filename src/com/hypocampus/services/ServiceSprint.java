/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.services;

import com.hypocampus.models.Project;
import com.hypocampus.models.Sprint;
import com.hypocampus.models.Task;
import com.hypocampus.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 21694
 */
public class ServiceSprint implements IService<Sprint> {
Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Sprint S) {
                    try {
                
            String requete = "INSERT INTO sprint (sprintname,startDatesprint,endDatesprint,etat,projets_id) VALUES (?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, S.getName());
            pst.setDate(2, S.getStart_date_sprint());
            pst.setDate(3, S.getEnd_date_sprint());
            pst.setInt(4, S.getEtat());
            pst.setInt(5, S.getProject_id());
            pst.executeUpdate();
            System.out.println("sprint ajoutée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    
    
    
    }

    @Override
    public void supprimer(Sprint S) {
                        try {
            String requete = "DELETE FROM sprint WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,S.getId());
            pst.executeUpdate();
            System.out.println("sprint supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Sprint S) {
                        try {
            String requete = "UPDATE sprint SET sprintname=?,startDatesprint=?,endDatesprint=?,projets_id=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(5, S.getId());
            pst.setString(1, S.getName());
            pst.setDate(2, S.getStart_date_sprint());
            pst.setDate(3, S.getEnd_date_sprint());
            pst.setInt(4, S.getProject_id());
            pst.executeUpdate();
            System.out.println("sprint modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Sprint> afficher() {
         ObservableList <Sprint> ListSprint =FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM sprint";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ListSprint.add(new Sprint(rs.getInt("id"), rs.getString("sprintname")
                ,rs.getDate("startDatesprint"), rs.getDate("endDatesprint"),rs.getInt("projets_id")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return ListSprint;
    }
      public List<Sprint> afficher_SprintProject(Project p){
        
              ObservableList <Sprint> ListSprint =FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM sprint where projets_id ="+p.getId()+"";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            String Completed_Task;
            while (rs.next()) {
                
                
                Completed_Task=getDone(rs.getInt("id")) +"/"+ getDoneT(rs.getInt("id"));
                
                
                
               ListSprint.add(new Sprint(rs.getInt("id"), rs.getString("sprintname")
                ,rs.getDate("startDatesprint"), rs.getDate("endDatesprint"),rs.getInt("projets_id"),Completed_Task));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
          return ListSprint; 
      }
      
      public int getTodo(int ids) {
       
         int count =0;
        try {
            String requete = "SELECT COUNT(*)  FROM task where sprint_id="+ids+" AND (state='To Do' OR state='In Progress' )";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            rs.first();
         count=rs.getInt(1);

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return count;
    }     
    public int getDone(int ids) {
       
         int count =0;
        try {
            String requete = "SELECT COUNT(*) as Done FROM task where  	sprint_id="+ids+" AND state='Done' ";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            rs.first();
         count=rs.getInt(1);

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return count;
    }     
     public int getDoneT(int ids) {
       
         int count =0;
        try {
            String requete = "SELECT COUNT(*) as DoneT FROM task where  sprint_id="+ids+"";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            rs.first();
         count=rs.getInt(1);

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return count;
    }    
     
     
     
     
           public List<Task> afficher_Sprintask(Sprint s){
        
              ObservableList <Task> ListSprint =FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM task where sprint_id ="+s.getId()+"";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
           
            while (rs.next()) {
                     
               ListSprint.add(new Task(rs.getInt("id"), rs.getInt("backlog_id"), rs.getString("title")
                ,rs.getString("description_fonctionnel"), rs.getString("description_technique")
                ,rs.getInt("story_points"), rs.getDate("created_date"), rs.getDate("finished_date")
                ,rs.getString("state"), rs.getInt("priority"), rs.getInt("archive"), rs.getInt("sprint_id")));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
          return ListSprint; 
      }
           
           
           
      //drag and drop      
           
    public List<Task> afficher_Sprintask_toDo2(Sprint s) {
        
         ObservableList <Task> ListSprint =FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM task where sprint_id ="+s.getId()+" AND state= 'To Do'";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ListSprint.add(new Task(rs.getInt("id"), rs.getInt("backlog_id"), rs.getString("title")
                ,rs.getString("description_fonctionnel"), rs.getString("description_technique")
                ,rs.getInt("story_points"), rs.getDate("created_date"), rs.getDate("finished_date")
                ,rs.getString("state"), rs.getInt("priority"), rs.getInt("archive"), rs.getInt("sprint_id")));
            }
           ListSprint.sort((o1, o2) -> o1.getFinished_date().compareTo(o2.getFinished_date()));
           //ListSprint.sorted(comparator);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return ListSprint;
    }

    public List<Task> afficher_Sprintask_INProgress2(Sprint s) {
         ObservableList <Task> ListSprint =FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM task where sprint_id ="+s.getId()+" AND state= 'In Progress'";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ListSprint.add(new Task(rs.getInt("id"), rs.getInt("backlog_id"), rs.getString("title")
                ,rs.getString("description_fonctionnel"), rs.getString("description_technique")
                ,rs.getInt("story_points"), rs.getDate("created_date"), rs.getDate("finished_date")
                ,rs.getString("state"), rs.getInt("priority"), rs.getInt("archive"), rs.getInt("sprint_id")));
            }
         ListSprint.sort((o1, o2) -> o1.getFinished_date().compareTo(o2.getFinished_date()));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return ListSprint;
    }
  
    public List<Task> afficher_Sprintask_Done2(Sprint s) {
         ObservableList <Task> ListSprint =FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM task where sprint_id ="+s.getId()+" AND state= 'Done'";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ListSprint.add(new Task(rs.getInt("id"), rs.getInt("backlog_id"), rs.getString("title")
                ,rs.getString("description_fonctionnel"), rs.getString("description_technique")
                ,rs.getInt("story_points"), rs.getDate("created_date"), rs.getDate("finished_date")
                ,rs.getString("state"), rs.getInt("priority"), rs.getInt("archive"), rs.getInt("sprint_id")));
            }
         ListSprint.sort((o1, o2) -> o1.getFinished_date().compareTo(o2.getFinished_date()));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
      
        return ListSprint;
    }
    
    
    
        public void modifierstate_todo(Task t) {
                        try {
            String requete = "UPDATE task SET state ='To Do'  WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.executeUpdate();
            System.out.println("task_To Do modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void modifierstate_InProgress(Task t) {
                        try {
            String requete = "UPDATE task SET state ='In Progress' WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.executeUpdate();
            System.out.println("task In Progress modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void modifierstate_Done(Task t) {
                        try {
            String requete = "UPDATE task SET state='Done' WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.executeUpdate();
            System.out.println("task_Done modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
        public void updatesprintetat(Task t) {
                        try {
            String requete = "UPDATE sprint s \n" +
"JOIN task t ON t.sprint_id = s.id \n" +
"SET etat = 1\n" +
"WHERE \n" +
"  t.state ='Done' AND t.sprint_id =? AND s.id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getSprint_id());
            pst.setInt(2, t.getSprint_id());
            pst.executeUpdate();
            System.out.println("task_state_Done modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
        public void updatesprintetat2(Task t) {
                        try {
            String requete = "UPDATE sprint s \n" +
"JOIN task t ON t.sprint_id = s.id \n" +
"SET etat = 0\n" +
"WHERE \n" +
"  t.state ='To Do' OR t.state ='In Progress' AND t.sprint_id =? AND s.id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, t.getSprint_id());
            pst.setInt(2, t.getSprint_id());
            pst.executeUpdate();
            System.out.println("task_state_To Do modifiée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
