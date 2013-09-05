package services.group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.logging.SQLLogger;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class GroupFinder {
	
	public static String SELECT_BY_ID_SQL = 
		"SELECT g.id,g.version,g.name,g.description FROM " + GroupTDG.TABLE + " AS g "+
		"WHERE g.id=?;";
			   
	public static String SELECT_ALL_SQL = 
	    "SELECT g.id FROM " + GroupTDG.TABLE + " AS g;";
			  
	public static String SELECT_BY_NAME_SQL = 
	    "SELECT g.id,g.version,g.name,g.description FROM " + GroupTDG.TABLE + " AS g "+
	    "WHERE g.name=?;";
			 
	public static ResultSet find(long id) throws SQLException{
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_ID_SQL);
		ps.setLong(1, id);
		return SQLLogger.processQuery(ps);
	}
			  
	public static ResultSet findAll() throws SQLException{
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_ALL_SQL);
		return SQLLogger.processQuery(ps);
	}
			  
	public static ResultSet findByName(String name) throws SQLException{
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_NAME_SQL);
		ps.setString(1, name);
		return SQLLogger.processQuery(ps);
	}

}
