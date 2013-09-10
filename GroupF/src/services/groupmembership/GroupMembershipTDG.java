package services.groupmembership;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.dsrg.soenea.service.UniqueIdFactory;
import org.dsrg.soenea.service.logging.SQLLogger;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class GroupMembershipTDG {

	public static final String BASE_NAME = "groupf_GroupMembership";
	public final static String TABLE = DbRegistry.getTablePrefix() + BASE_NAME;

	public final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE + " (" + "id BIGINT," + "version int," + "member BIGINT,"
			+ "_group BIGINT," + "status int," + "lastUpdated BIGINT,"
			+ "PRIMARY KEY(id)," + "INDEX (_group)" + ") ENGINE=InnoDB;";

	public final static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE
			+ ";";

	public final static String DELETE_BYID_SQL = "DELETE FROM " + TABLE
			+ " WHERE id=? AND version=?;";

	public final static String INSERT_BYID_SQL = "INSERT INTO "
			+ TABLE
			+ " (id,version,member,_group,status,lastUpdated) values(?,?,?,?,?,?);";

	public final static String UPDATE_BYID_SQL = "UPDATE "
			+ TABLE
			+ " "
			+ "SET version=version+1,member=?,_group=?,status=?,lastUpdated=? WHERE id=? and version=?;";

	public static void createTable() throws SQLException {
		SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(),
				CREATE_TABLE);
	}

	public static void dropTable() throws SQLException {
		SQLLogger.processUpdate(DbRegistry.getDbConnection().createStatement(),
				DROP_TABLE);
	}

	public static int insert(long id, int version, Long member, Long group,
			Integer status, Long lastUpdated) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(INSERT_BYID_SQL);
		ps.setLong(1, id);
		ps.setInt(2, version);
		ps.setLong(3, member);
		ps.setLong(4, group);
		ps.setInt(5, status);
		ps.setLong(6, lastUpdated);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		return count;
	}

	public static int update(long id, int version, Long member, Long group,
			Integer status, Long lastUpdated) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(UPDATE_BYID_SQL);
		ps.setLong(1, member);
		ps.setLong(2, group);
		ps.setInt(3, status);
		ps.setLong(4, lastUpdated);
		ps.setLong(5, id);
		ps.setInt(6, version);
		int count = SQLLogger.processUpdate(ps);
		ps.close();
		return count;
	}

	public static int delete(long id, int version) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(DELETE_BYID_SQL);
		ps.setLong(1, id);
		ps.setInt(2, version);
		int count = SQLLogger.processUpdate(ps);
		return count;
	}

	public static long maxId() throws SQLException {
		return UniqueIdFactory.getMaxId(BASE_NAME, "id");
	}

}
