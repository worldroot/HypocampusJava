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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.security.crypto.bcrypt.BCrypt;


/**
 *
 * @author Houcem
 */
public class ServiceUser implements IService<User> {
	
	Connection cnx = DataSource.getInstance().getCnx();


	@Override
	public void ajouter(User t) {
		try {
                
            String requete = "INSERT INTO `fos_user`"
					+ " ( `username`, `username_canonical`, `email`, `email_canonical`,"
					+ " `enabled`, `salt`, `password`, `last_login`, `confirmation_token`,"
					+ " `password_requested_at`, `roles`) VALUES " +
					"( ?, ?, ?, ?, 1, NULL, ?, NULL, NULL, NULL, ?);";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, t.getUsername());
			pst.setString(2, t.getUsername());
			pst.setString(3, t.getEmail());
			pst.setString(4, t.getEmail());
			String hashedPassword = BCrypt.hashpw(t.getPassword(), BCrypt.gensalt(13));
            pst.setString(5, hashedPassword);
			String role;
			if(t.getRoles().equals("Admin")) {
				role = "a:1:{i:0;s:10:\"ROLE_ADMIN\";}";
			}
			else if(t.getRoles().equals("ScrumMaster")) {
				role = "a:1:{i:0;s:17:\"ROLE_SCRUM_MASTER\";}";
			}
			else {
				role = "a:1:{i:0;s:14:\"ROLE_DEVELOPER\";}";
			}
			pst.setString(6, role);
           
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
	}

	@Override
	public void supprimer(User t) {
		try {
            String requete = "DELETE FROM fos_user WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,t.getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
	}

	@Override
	public void modifier(User t) {
		throw new UnsupportedOperationException("Manich khademha ye... !!"); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public List<User> afficher() {
		ObservableList <User> ListUsers = FXCollections.observableArrayList();
		
		try {
            String requete = "SELECT id, username, email, roles FROM fos_user";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
				String role;
				if(rs.getString("roles").equals("a:1:{i:0;s:10:\"ROLE_ADMIN\";}")) {
					role = "Admin";
				}
				else if(rs.getString("roles").equals("a:1:{i:0;s:17:\"ROLE_SCRUM_MASTER\";}")) {
					role = "ScrumMaster";
				}
				else {
					role = "Developer";
				}
                ListUsers.add(new User(rs.getInt("id"),rs.getString("username"),role,rs.getString("email")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
		return ListUsers;
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
				requete = "SELECT id, username, password, roles FROM fos_user where username=?";
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
