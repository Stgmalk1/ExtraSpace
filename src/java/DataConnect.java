/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xhunter
 */
public class DataConnect {
    public static Connection getConnection() {
		try {
                    Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/extraspace", "root", "1");
                    return con;
                } catch (SQLException ex) {
            Logger.getLogger(DataConnect.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database.getConnection() Error -->"
					+ ex.getMessage());
			return null;
        }
}
    public static void close(Connection con) {
		try {
			con.close();
		} catch (Exception ex) {
		}
	}   
}
