package domain.model.group.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.ObjectRemovedException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;

import services.group.GroupFinder;

import domain.model.group.Group;
import domain.model.group.GroupFactory;
import domain.model.group.GroupProxy;
import domain.model.group.IGroup;
import domain.model.groupmembership.MembershipListProxy;

public class GroupInputMapper {

	public static List<IGroup> buildCollection(ResultSet rs)
			throws SQLException, MapperException, DomainObjectCreationException {
		return buildCollection(rs, "g.id");
	}

	public static List<IGroup> buildCollection(ResultSet rs, String idString)
			throws SQLException, MapperException, DomainObjectCreationException {
		ArrayList<IGroup> l = new ArrayList<IGroup>();
		while (rs.next()) {
			long id = rs.getLong(idString);
			GroupProxy gp = new GroupProxy(id);
			l.add(gp);
		}

		return l;
	}

	public static List<IGroup> findAll() throws SQLException, MapperException,
			DomainObjectCreationException {
		ResultSet rs = GroupFinder.findAll();
		return buildCollection(rs);
	}

	public static Group find(long id) throws SQLException, MapperException,
			DomainObjectCreationException {
		try {
			return IdentityMap.get(id, Group.class);
		} catch (DomainObjectNotFoundException e) {
		} catch (ObjectRemovedException e) {
		}
		ResultSet rs = GroupFinder.find(id);
		if (!rs.next())
			throw new MapperException(
					"The record for this Group id doesn't exist");
		return getGroup(rs);
	}

	public static Group findByName(String name) throws SQLException,
			MapperException, DomainObjectCreationException {
		ResultSet rs = GroupFinder.findByName(name);

		if (!rs.next())
			throw new MapperException(
					"The record for this Group id doesn't exist");
		try {
			return IdentityMap.get(rs.getLong("g.id"), Group.class);
		} catch (DomainObjectNotFoundException e) {
		} catch (ObjectRemovedException e) {

		}

		return getGroup(rs);
	}

	private static Group getGroup(ResultSet rs) throws SQLException, MapperException {
		long id = rs.getLong("g.id");
		IGroup thisGroup = new GroupProxy(id);
		Group result = null;
		try {
			result = GroupFactory.createClean(id, rs.getLong("g.version"),
					rs.getString("g.name"), rs.getString("g.description"),
					(new MembershipListProxy(thisGroup)).getActualList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

}
