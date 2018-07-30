/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author xhunter
 */
public class Login {
    // Refered function in userbean.java for validate the correct username and password
    public static boolean validate(String user, String password){
		Connection con = null;
		PreparedStatement ps = null;
                
                    try {
                        con = DataConnect.getConnection();
                        ps = con.prepareStatement("Select uname, pwd from Users where uname = ? and pwd = ?");
                        ps.setString(1, user);
			ps.setString(2, password);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
				return true;
			}
                    }    
                    catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return false;
                    } 
                    finally {
        		DataConnect.close(con);
                    }
                               return false;
                    }
    }