
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
public class Forgot {
    public static boolean validateUsernameSecans(String username, String sectype, String secans){
        Connection con = null;
	PreparedStatement ps = null;
        String dbsectype = null;
        String dbsecans = null;
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select sectype, secans from Users where uname = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            dbsectype = rs.getString("sectype");
            dbsecans = rs.getString("secans");
            }
            System.out.print(dbsectype);
            System.out.print(dbsecans);
            if(dbsectype.equals(sectype) && dbsecans.equals(secans))
                return true;
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