package services.groupmembership;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.logging.SQLLogger;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

import com.mysql.jdbc.PreparedStatement;

public class GroupMembershipFinder {

	public static String SELECT_BY_ID_SQL = "SELECT gm.id,gm.version,gm.member,"
			+ "gm._group,gm.status,gm.lastUpdated FROM "
			+ GroupMembershipTDG.TABLE + " AS gm " + "WHERE gm.id=?;";
	
	public static String SELECT_BY_MEMBER_SQL = "SELECT gm.id,gm.version,gm.member,"
			+ "gm._group,gm.status,gm.lastUpdated FROM "
			+ GroupMembershipTDG.TABLE + " AS gm " + "WHERE gm.member=?;";

	public static String SELECT_ALL_SQL = "SELECT gm.id FROM "
			+ GroupMembershipTDG.TABLE + " AS gm;";

	public static String SELECT_BY_GROUP_SQL = "SELECT gm.id,gm.member FROM "
			+ GroupMembershipTDG.TABLE + " AS gm " + "WHERE gm._group=?";

	public static ResultSet find(long id) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = (PreparedStatement) con
				.prepareStatement(SELECT_BY_ID_SQL);
		ps.setLong(1, id);
		return SQLLogger.processQuery(ps);
	}
	
	public static ResultSet findByMember(long member) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = (PreparedStatement) con
				.prepareStatement(SELECT_BY_MEMBER_SQL);
		ps.setLong(1, member);
		return SQLLogger.processQuery(ps);
	}

	public static ResultSet findAll() throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = (PreparedStatement) con
				.prepareStatement(SELECT_ALL_SQL);
		return SQLLogger.processQuery(ps);
	}

	public static ResultSet findByGroup(Long group) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = (PreparedStatement) con
				.prepareStatement(SELECT_BY_GROUP_SQL);
		ps.setLong(1, group);
		return SQLLogger.processQuery(ps);
	}

}
