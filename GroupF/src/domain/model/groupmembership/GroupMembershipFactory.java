package domain.model.groupmembership;

import java.sql.SQLException;
import java.util.Calendar;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.user.IUser;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;

import services.groupmembership.GroupMembershipTDG;

import domain.model.group.IGroup;

public class GroupMembershipFactory {

	public static GroupMembership createNew(IUser member, IGroup group,
			MembershipStatus status, Calendar lastUpdated) throws SQLException, MissingMappingException, MapperException {
		GroupMembership result = new GroupMembership(
				GroupMembershipTDG.maxId(), 01, member, group, status,
				lastUpdated);
		UoW.getCurrent().registerNew(result);
		return result;
	}

	public static GroupMembership createClean(long id, long version,
			IUser member, IGroup group, MembershipStatus status,
			Calendar lastUpdated) throws SQLException {
		GroupMembership result = new GroupMembership(id, version, member,
				group, status, lastUpdated);
		UoW.getCurrent().registerClean(result);
		return result;
	}

}
