/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.tests;

import com.hypocampus.models.Event;
import com.hypocampus.models.Participant;
import com.hypocampus.services.ServiceEvent;
import com.hypocampus.services.ServiceParticipant;
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
        
        Date d = new Date(1998, 23, 05);
        Date de = new Date(2022, 11, 20);

        //************Event********************
        Event e = new Event(49,"SawSaw", 1,"Cours",d,de,"",de);
        ServiceEvent ev = new ServiceEvent();
        
        //ev.ajouter(e);
        //ev.supprimer(e);
        ev.modifier(e);
        ev.afficher().forEach(System.out::println);
        
        //************Participant**************   
        Participant p = new Participant ("G","ffrr","f@f.com","dfz",0);
        ServiceParticipant sp = new ServiceParticipant();
        //sp.ajouter(p);
        //sp.supprimer(p);
        //sp.modifier(p);
        //sp.afficher().forEach(System.out::println);
        
        //************Certif*******************   
      
        System.out.println("***************************");
        

        //stage.setFullScreen(true);

    }


}
