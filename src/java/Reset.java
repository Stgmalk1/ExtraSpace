
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author xhunter
 */
public class Reset {
    //Referred Function that resets the password in the userbean.java file
    public static void resetPassword(String username, String password){
		Connection con = null;
		PreparedStatement ps = null;
                
                    try {
                        con = DataConnect.getConnection();
                        ps = con.prepareStatement("update users set pwd = ? where uname = ?");
                        ps.setString(1, password);
			ps.setString(2, username);
                        ps.executeUpdate();
                    }    
                    catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());		
                    } 
                    finally {
        		DataConnect.close(con);
                    }
                             
                }
}
