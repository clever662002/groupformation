package services.group;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.dsrg.soenea.service.UniqueIdFactory;
import org.dsrg.soenea.service.logging.SQLLogger;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class GroupTDG {
	
	public static final String BASE_NAME = "Group";
	public static final String TABLE = DbRegistry.getTablePrefix()+BASE_NAME;
	public final static String CREATE_TABLE = 
		"CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
		"id BIGINT,"+
		"version int,"+
		"name varchar(128),"+
		"description varchar(256),"+
		"PRIMARY KEY(id)"+
		") ENGINE=InnoDB;";
	 
	public final static String DROP_TABLE = 
		"DROP TABLE IF EXISTS " + TABLE + ";";

	public static final String INSERT = 
		"INSERT INTO " + TABLE + " (id,version,name, description) VALUES (?,?,?,?);";
	
	public static final String UPDATE = 
		"UPDATE " + TABLE + " SET name=?, description=?, version=version+1 WHERE id=? AND version=?;";
	
	public static final String DELETE = 
		"DELETE FROM " + TABLE + " WHERE id=? AND version=?;";
	
	public static void createTable() throws SQLException {
		SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(), CREATE_TABLE);
	}
		  
	public static void dropTable() throws SQLException {
		SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(), DROP_TABLE);
	}

	public static void insert(long id, int version, String name, String description) throws SQLException {
		PreparedStatement ps = DbRegistry.getDbConnection().prepareStatement(INSERT);
		ps.setLong(1, id);
		ps.setInt(2, version);
		ps.setString(3, name);
		ps.setString(4, description);
		ps.executeUpdate();
		ps.close();
	}

	public static int update(long id, int version, String name, String description) throws SQLException {
		PreparedStatement ps = DbRegistry.getDbConnection().prepareStatement(UPDATE);
		ps.setString(1, name);
		ps.setString(2,  description);
		ps.setLong(3, id);
		ps.setInt(4, version);		
		int count = ps.executeUpdate();
		ps.close();
		return count;
	}

	public static int delete(long id, int version) throws SQLException {
		PreparedStatement ps = DbRegistry.getDbConnection().prepareStatement(DELETE);
		ps.setLong(1, id);
		ps.setInt(2, version);
		int count = ps.executeUpdate();
		ps.close();
		return count;
	}
	
	public static long maxId() throws SQLException {	
		return UniqueIdFactory.getMaxId(BASE_NAME, "id");
	}

}
