/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.services;

import com.hypocampus.models.User;
import com.hypocampus.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCrypt;


/**
 *
 * @author Houcem
 */
public class ServiceUser implements IService<User> {
	
	Connection cnx = DataSource.getInstance().getCnx();


	@Override
	public void ajouter(User t) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void supprimer(User t) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void modifier(User t) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public List<User> afficher() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public User login(String inputUsername, String inputPassword) {
		User scarra = new User();
		scarra.setId(-1);
		scarra.setRoles("NULL");
		scarra.setUsername("NULL");
		scarra.setPassword("NULL");

		String hashedPassword = "";
		
        try {
            String requete = "SELECT password FROM fos_user where username=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
			pst.setString(1, inputUsername);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
				hashedPassword = rs.getString("password");
                                System.out.println(hashedPassword);
                                System.out.println(inputPassword);
            }
			
			if(BCrypt.checkpw(inputPassword, hashedPassword)) {
				System.out.println("It matches");
				requete = "SELECT * FROM fos_user where username=?";
				pst = cnx.prepareStatement(requete);
				pst.setString(1, inputUsername);
				rs = pst.executeQuery();
				while (rs.next()) {
					scarra.setId(rs.getInt("id"));
					scarra.setUsername(rs.getString("username"));
					scarra.setPassword(rs.getString("password"));
					scarra.setRoles(rs.getString("roles"));
				}
			}
			else {
				System.out.println("tiiiiiiiiiiiiiiiiiiiiit");
			}

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
	
		
		return scarra;
	}
	
}
