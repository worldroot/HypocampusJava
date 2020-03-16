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
    private String description;
    private Date date_creation;

    public Commentaire(int id, int task_id, String description, Date date_creation) {
        this.id = id;
        this.task_id = task_id;
        this.description = description;
        this.date_creation = date_creation;
    }

    public Commentaire(int task_id, String description, Date date_creation) {
        this.task_id = task_id;
        this.description = description;
        this.date_creation = date_creation;
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
        return "Commentaire{" + "id=" + id + ", task_id=" + task_id + ", description=" + description + ", date_creation=" + date_creation + '}';
    }
    
    
    
}
