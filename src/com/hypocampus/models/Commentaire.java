/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.models;

import java.sql.Date;

/**
 *
 * @author mehdibehira
 */
public class Commentaire {
    private int id;
    private int task_id;
    private int user_id;
    private String image_name;
    private String description;
    private Date date_creation;

    public Commentaire(int id, int task_id,int user_id,String image_name, String description, Date date_creation) {
        this.id = id;
        this.task_id = task_id;
        this.image_name= image_name;
        this.user_id= user_id;
        this.description = description;
        this.date_creation = date_creation;
    }

    public Commentaire(int task_id,int user_id,String image_name, String description, Date date_creation) {
        this.task_id = task_id;
        this.user_id = user_id;
        this.image_name = image_name;
        this.description = description;
        this.date_creation = date_creation;
    }
    
    public Commentaire(int task_id,int user_id, String description, Date date_creation){
        this.task_id = task_id;
        this.user_id = user_id;

        this.description = description;
        this.date_creation = date_creation;
    }

    public Commentaire() {
    }

    public Commentaire(int task_id, int user_id, String description, Date created_date, String img) {
        this.task_id = task_id;
        this.user_id = user_id;
        this.image_name = img;
        this.description = description;
        this.date_creation = created_date;
    
        
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    @Override
    public String toString() {
        return "Commentaire{" + "id=" + id + ", task_id=" + task_id + ", user_id=" + user_id + ", image_name=" + image_name + ", description=" + description + ", date_creation=" + date_creation + '}';
    }



    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }
    

}
