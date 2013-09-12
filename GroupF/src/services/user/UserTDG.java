package services.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.dsrg.soenea.service.UniqueIdFactory;
import org.dsrg.soenea.service.logging.SQLLogger;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class UserTDG {
	
	public static final String BASE_NAME = "User";
	public final static String TABLE = DbRegistry.getTablePrefix() + BASE_NAME;
	public final static String CREATE_TABLE = 
			"CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
			"id BIGINT," +
			"version int," +
			"username varchar(128)," +
			"firstname varchar(128)," +
			"lastname varchar(128)," +
			"password varchar(128)," +
			"role varchar(128)," +
			"PRIMARY KEY(id)" +
			") ENGINE=InnoDB;";
	
	public final static String DROP_TABLE = 
			"DROP TABLE IF EXISTS " + TABLE + ";";

		public static final String INSERT = 
			"INSERT INTO " + TABLE + " (id,version,username,firstname,lastname,password,role) VALUES (?,?,?,?,?,?,?);";
		
		public static final String UPDATE = 
			"UPDATE " + TABLE + " SET username=?, firstname=?, lastname=?, password=?, role=?, " +
					"version=version+1 WHERE id=? AND version=?;";
		
		public static final String DELETE = 
			"DELETE FROM " + TABLE + " WHERE id=? AND version=?;";
		
		public static void createTable() throws SQLException {
			SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(), CREATE_TABLE);
		}
			  
		public static void dropTable() throws SQLException {
			SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(), DROP_TABLE);
		}
		
		public static void insert(long id, long version, String userName, String firstName, String lastName, 
				String password, String role) throws SQLException {
			PreparedStatement ps = DbRegistry.getDbConnection().prepareStatement(INSERT);
			ps.setLong(1, id);
			ps.setLong(2, version);
			ps.setString(3, userName);
			ps.setString(4, firstName);
			ps.setString(5, lastName);
			ps.setString(6, password);
			ps.setString(7, role);
			ps.executeUpdate();
			ps.close();
		}
		
		public static int delete(long id, long version) throws SQLException {
			PreparedStatement ps = DbRegistry.getDbConnection().prepareStatement(DELETE);
			ps.setLong(1, id);
			ps.setLong(2, version);
			int count = ps.executeUpdate();
			ps.close();
			return count;
		}
		
		public static long maxId() throws SQLException {	
			return UniqueIdFactory.getMaxId(BASE_NAME, "id");
		}

}
