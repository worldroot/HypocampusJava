/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.tests;

import com.hypocampus.models.Certif;
import com.hypocampus.models.Event;
import com.hypocampus.models.Participant;
import com.hypocampus.services.ServiceCertif;
import com.hypocampus.services.ServiceEvent;
import com.hypocampus.services.ServiceParticipant;
import java.sql.Date;


/**
 *
 * @author ASUS
 */
public class ghassentest {
    
    
    public static void main(String[] args) {
        
        Date d = new Date(1998, 23, 05);
        Date de = new Date(2022, 11, 20);

        //************Event********************
        //Event e = new Event(49,"SawSaw", 1,"Cours",d,de,"",de);
        //ServiceEvent ev = new ServiceEvent();
        
        //ev.ajouter(e);
        //ev.supprimer(e);
        //ev.modifier(e);
        //ev.afficher().forEach(System.out::println);
        
        //************Certif*******************  
        Certif c = new Certif(2,45,33,d);
        ServiceCertif cc = new ServiceCertif();
        
        //cc.ajouter(c);
        //cc.supprimer(c);
        //cc.modifier(c);
        //cc.afficher().forEach(System.out::println);
        
        //************Participant**************   
        Participant p = new Participant ("NB","ffrr","f@f.com","dfz",50,0);
        ServiceParticipant sp = new ServiceParticipant();
        
        //sp.ajouter(p);
        //sp.supprimer(p);
        //sp.modifier(p);
        //sp.afficher().forEach(System.out::println);
        

        System.out.println("***************************");
        

        //stage.setFullScreen(true);

    }


}
