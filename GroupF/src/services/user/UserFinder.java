package services.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.logging.SQLLogger;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class UserFinder {
	
	public static String SELECT_BY_ID_SQL = 
			"SELECT u.id,u.version,u.username, u.firstname,u.lastname,u.password,u.role FROM " 
			+ UserTDG.TABLE + " AS u WHERE u.id=?;";
				   
	public static String SELECT_ALL_SQL = 
		    "SELECT u.id FROM " + UserTDG.TABLE + " AS u;";
				  
	public static String SELECT_BY_USERNAME_SQL = 
		    "SELECT u.id,u.version,u.username,u.firstname,u.lastname,u.password,u.role FROM " + 
		    UserTDG.TABLE + " AS u WHERE u.username=?;";
				 
	public static ResultSet find(long id) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_ID_SQL);
		ps.setLong(1, id);
		return SQLLogger.processQuery(ps);
	}
				  
	public static ResultSet findAll() throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_ALL_SQL);
		return SQLLogger.processQuery(ps);
	}
				  
	public static ResultSet findByUserName(String userName) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_USERNAME_SQL);
		ps.setString(1, userName);
		return SQLLogger.processQuery(ps);
	}

}
