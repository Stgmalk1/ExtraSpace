
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
public class Register {
    // Function that checks if the same username is trying to register or not.
    public static boolean checkExist(String user){
		Connection con = null;
		PreparedStatement ps = null;
                
                    try {
                        con = DataConnect.getConnection();
                        ps = con.prepareStatement("Select uname from Users where uname = ?");
                        ps.setString(1, user);
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
    
    // Refered function in userbean.java that registers a user
    public static boolean insert(int uid, String uname, String pwd, String sectype, String secans){
        Connection con = null;
		PreparedStatement ps = null;
                
                    try {
                        con = DataConnect.getConnection();
                        if (con != null) {
                        String sql = "INSERT INTO Users(uid, uname, pwd, sectype, secans) VALUES(?,?,?,?,?)";
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, uid);
                        ps.setString(2, uname);
                        ps.setString(3, pwd);
                        ps.setString(4, sectype);
                        ps.setString(5, secans);
                        ps.executeUpdate();
                        System.out.println("Data Added Successfully");
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
    
    // Functions that counts the last uid and give new username lastid + 1
    // Example last username id was 99 then new username id will be 100
    public static int countUID(){
		Connection con = null;
		PreparedStatement ps = null;
                
                    try {
                        con = DataConnect.getConnection();
                        ps = con.prepareStatement("Select count(*) from Users");
                        ResultSet rs = ps.executeQuery();
                        int val = 0;
                        while(rs.next()){
                        val = ((Number) rs.getObject(1)).intValue();
                        }
                        return val;
                    }    
                    catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
                    } 
                    finally {
        		DataConnect.close(con);
                    }
                    return 1;
    }
}
