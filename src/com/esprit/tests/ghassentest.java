/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.tests;

import com.hypocampus.models.Event;
import com.hypocampus.services.ServiceEvent;
import java.sql.Date;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ASUS
 */
public class ghassentest {
    
    
    public static void main(String[] args) {
        
        Date d = new Date(1998, 11, 31);
        Date de = new Date(2022, 11, 20);

        Event e = new Event(38,"X", 1,"Workshops",d,de,"",de);
        ServiceEvent ev = new ServiceEvent();
        
        //ev.ajouter(e);
        //ev.afficher().forEach(System.out::println);
        //ev.supprimer(e);
        //ev.modifier(e);
        ev.afficher().forEach(System.out::println);
        
        System.out.println("***************************");
        

        //stage.setFullScreen(true);

    }


}
