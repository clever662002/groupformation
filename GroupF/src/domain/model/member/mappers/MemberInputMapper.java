package domain.model.member.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.user.IUser;
import org.dsrg.soenea.domain.user.UserProxy;

import services.groupmembership.GroupMembershipFinder;

import domain.model.group.IGroup;

public class MemberInputMapper {

	public static List<IUser> buildCollection(ResultSet rs, String idString)
			throws SQLException, MapperException, DomainObjectCreationException {
		ArrayList<IUser> l = new ArrayList<IUser>();
		while (rs.next()) {
			l.add(new UserProxy(rs.getLong(idString)));
		}
		return l;
	}

	public static List<IUser> find(IGroup myGroup) throws SQLException,
			MapperException, DomainObjectCreationException {
		ResultSet rs = GroupMembershipFinder.findByGroup(myGroup.getId());
		return buildCollection(rs, "gm.member");
	}

}
