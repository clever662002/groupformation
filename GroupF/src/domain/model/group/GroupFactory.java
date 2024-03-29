package domain.model.group;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;

import services.group.GroupTDG;

import domain.model.groupmembership.IGroupMembership;

public class GroupFactory {
	public static Group createNew(String name, String description, List<IGroupMembership> members)
			throws SQLException, MissingMappingException, MapperException {
		Group result = new Group(GroupTDG.maxId(), 1, name, description, members);
		UoW.getCurrent().registerNew(result);
		return result;
	}

	public static Group createClean(long id, long version, String name, String description,
			List<IGroupMembership> members) throws SQLException {
		Group result = new Group(id, version, name, description, members);
		UoW.getCurrent().registerClean(result);
		return result;
	}
}
