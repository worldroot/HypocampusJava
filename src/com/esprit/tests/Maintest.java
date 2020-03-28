/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.tests;

import com.hypocampus.models.Backlog;
import com.hypocampus.models.Project;
import com.hypocampus.services.ServiceBacklog;
import com.hypocampus.services.ServiceProject;
import com.hypocampus.services.ServiceTask;
import java.sql.Date;

/**
 *
 * @author mehdibehira
 */
public class Maintest {
    
        public static void main(String[] args) {
        System.out.println("test");
        ServiceBacklog sb = new ServiceBacklog();
        ServiceTask st = new ServiceTask();

       //sb.ajouter(new Backlog(0, 0, 0, 4));
        System.out.println("99");
       // sb.afficher().forEach(System.out::println);
      //  Backlog B = new Backlog(24, 0, 10, 5, 4);
        //sb.modifier(B);
        sb.afficher().forEach(System.out::println);
        System.out.println("***************************");
        st.afficher().forEach(System.out::println);
        
       System.out.println("************** Project *************"); 
      ServiceProject sP =new ServiceProject();
       Date dateS=Date.valueOf("2020-02-10");
       Date datef=Date.valueOf("2025-01-01");
       // ajouter
        sP.ajouter(new Project("Projet 4","ahmedddd",dateS,datef,"oki",00));
        //afficher
        sP.afficher().forEach(System.out::println);
        //supp
       // sP.supprimer(new Project(30));
       //modif
       sP.modifier(new  Project ("Projet4","ahmed ben said",dateS,datef,"oki",00));

    }
    
}
