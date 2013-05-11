package domain.model.groupmembership.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.ObjectRemovedException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.dsrg.soenea.domain.user.UserProxy;

import services.groupmembership.GroupMembershipFinder;

import domain.model.group.GroupProxy;
import domain.model.group.IGroup;
import domain.model.groupmembership.GroupMembership;
import domain.model.groupmembership.GroupMembershipFactory;
import domain.model.groupmembership.GroupMembershipProxy;
import domain.model.groupmembership.IGroupMembership;
import domain.model.groupmembership.MembershipStatus;

public class GroupMembershipInputMapper {

	public static List<IGroupMembership> buildCollection(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		return buildCollection(rs, "gm.id");
	}

	public static List<IGroupMembership> buildCollection(ResultSet rs,String idString) throws SQLException, MapperException, DomainObjectCreationException {
		ArrayList<IGroupMembership> l = new ArrayList<IGroupMembership>();
		while (rs.next()) {
			l.add(new GroupMembershipProxy(rs.getLong(idString)));
		}
		return l;
	}

	public static List<IGroupMembership> findAll() throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = GroupMembershipFinder.findAll();
		return buildCollection(rs);
	}

	public static List<IGroupMembership> find(IGroup myGroup)
			throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = GroupMembershipFinder.findByGroup(myGroup.getId());
		return buildCollection(rs);
	}

	public static GroupMembership find(long id) throws SQLException,
			MapperException, DomainObjectCreationException {
		try {
			return IdentityMap.get(id, GroupMembership.class);
		} catch (DomainObjectNotFoundException e) {
		} catch (ObjectRemovedException e) {
		}
		ResultSet rs = GroupMembershipFinder.find(id);
		if (!rs.next())
			throw new MapperException(
					"The record for this GroupMembership id doesn't exist");
		return getGroupMembership(rs);
	}

	private static GroupMembership getGroupMembership(ResultSet rs)
			throws SQLException, MapperException, DomainObjectCreationException {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(rs.getLong("gm.lastUpdated"));
		GroupMembership result = GroupMembershipFactory.createClean(
				rs.getLong("gm.id"), rs.getLong("gm.version"),
				new UserProxy(rs.getLong("gm.member")),
				new GroupProxy(rs.getLong("gm._group")),
				MembershipStatus.values()[rs.getInt("gm.status")], cal);
		return result;
	}

}
