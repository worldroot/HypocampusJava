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
public class Task {
    private int id;
    private int backlog_id;
    private String title;
    private String description_fonctionnel;
    private String description_technique;
    private int story_points;
    private Date created_date;
    private Date finished_date;
    private String state;
    private int priority;
    private int archive;
    private int sprint_id;

    public Task(int id, int backlog_id, String title, String description_fonctionnel, String description_technique, int story_points, Date created_date, Date finished_date, String state, int priority, int archive, int sprint_id) {
        this.id = id;
        this.backlog_id = backlog_id;
        this.title = title;
        this.description_fonctionnel = description_fonctionnel;
        this.description_technique = description_technique;
        this.story_points = story_points;
        this.created_date = created_date;
        this.finished_date = finished_date;
        this.state = state;
        this.priority = priority;
        this.archive = archive;
        this.sprint_id = sprint_id;
    }

    public Task(int backlog_id, String title, String description_fonctionnel, String description_technique, int story_points, Date created_date, Date finished_date, String state, int priority, int archive, int sprint_id) {
        this.backlog_id = backlog_id;
        this.title = title;
        this.description_fonctionnel = description_fonctionnel;
        this.description_technique = description_technique;
        this.story_points = story_points;
        this.created_date = created_date;
        this.finished_date = finished_date;
        this.state = state;
        this.priority = priority;
        this.archive = archive;
        this.sprint_id = sprint_id;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBacklog_id() {
        return backlog_id;
    }

    public void setBacklog_id(int backlog_id) {
        this.backlog_id = backlog_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription_fonctionnel() {
        return description_fonctionnel;
    }

    public void setDescription_fonctionnel(String description_fonctionnel) {
        this.description_fonctionnel = description_fonctionnel;
    }

    public String getDescription_technique() {
        return description_technique;
    }

    public void setDescription_technique(String description_technique) {
        this.description_technique = description_technique;
    }

    public int getStory_points() {
        return story_points;
    }

    public void setStory_points(int story_points) {
        this.story_points = story_points;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getFinished_date() {
        return finished_date;
    }

    public void setFinished_date(Date finished_date) {
        this.finished_date = finished_date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getArchive() {
        return archive;
    }

    public void setArchive(int archive) {
        this.archive = archive;
    }

    public int getSprint_id() {
        return sprint_id;
    }

    public void setSprint_id(int sprint_id) {
        this.sprint_id = sprint_id;
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", backlog_id=" + backlog_id + ","
                + " title=" + title + ", description_fonctionnel="
                + description_fonctionnel + ", description_technique=" + description_technique +
                ", story_points=" + story_points + ", created_date=" + created_date +
                ", finished_date=" + finished_date + ", state=" + state +
                ", priority=" + priority + ", archive=" + archive + ", sprint_id=" + sprint_id + '}';
    }
    
    
    
    
}
