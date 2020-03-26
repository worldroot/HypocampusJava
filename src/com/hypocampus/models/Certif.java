/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.models;

import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class Certif {
    
    private int idc;
    private int titrec;
    private int pointc;
    private Date datec;
    private String image_name;
    private Date updated_at;

    public Certif(int idc, int titrec, int pointc, Date datec, String image_name, Date updated_at) {
        this.idc = idc;
        this.titrec = titrec;
        this.pointc = pointc;
        this.datec = datec;
        this.image_name = image_name;
        this.updated_at = updated_at;
    }

    public Certif(int titrec, int pointc, Date datec) {
        this.titrec = titrec;
        this.pointc = pointc;
        this.datec = datec;
    }
    

    public Certif(int titrec, int pointc, Date datec, String image_name, Date updated_at) {
        this.titrec = titrec;
        this.pointc = pointc;
        this.datec = datec;
        this.image_name = image_name;
        this.updated_at = updated_at;
    }
        
    public Certif(int idc, int titrec, int pointc, Date datec) {
        this.idc = idc;
        this.titrec = titrec;
        this.pointc = pointc;
        this.datec = datec;
    }

    public Certif() {}

    public int getTitrec() {
        return titrec;
    }

    public void setTitrec(int titrec) {
        this.titrec = titrec;
    }
    
    
    @Override
    public String toString() {
        return "Certif{" + "idc=" + idc + ", titrec=" + titrec + ", pointc=" + pointc + ", datec=" + datec + ", image_name=" + image_name + ", updated_at=" + updated_at + '}';
    }

    public int getIdc() {
        return idc;
    }

    public void setIdc(int idc) {
        this.idc = idc;
    }

    public int getPointc() {
        return pointc;
    }

    public void setPointc(int pointc) {
        this.pointc = pointc;
    }

    public Date getDatec() {
        return datec;
    }

    public void setDatec(Date datec) {
        this.datec = datec;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
    
    
    
}
