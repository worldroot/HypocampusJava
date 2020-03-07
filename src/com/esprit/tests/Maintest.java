/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.tests;

import com.hypocampus.models.Backlog;
import com.hypocampus.services.ServiceBacklog;
import com.hypocampus.services.ServiceTask;

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


    }
    
}
