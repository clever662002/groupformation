package domain.model.groupmembership.mappers;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;

import services.groupmembership.GroupMembershipTDG;

import domain.model.groupmembership.GroupMembership;

public class GroupMembershipOutputMapper extends GenericOutputMapper<Long, GroupMembership> {
	
	@Override
	public void delete(GroupMembership membership) throws MapperException {
		try {
			int count = GroupMembershipTDG.delete(membership.getId(),
					membership.getVersion());
			if (count == 0)
				throw new LostUpdateException("GroupMembershipTDG: id "
						+ membership.getId() + " version "
						+ membership.getVersion());
			membership.setVersion(membership.getVersion() + 1);
		} catch (SQLException e) {
			throw new MapperException("Could not delete GroupMembership "
					+ membership.getId(), e);
		}
	}

	//@Override
	public void insert(GroupMembership membership) throws MapperException {
		try {
			GroupMembershipTDG.insert(membership.getId(), membership
					.getVersion(), membership.getMember().getId(), membership
					.getGroup().getId(), membership.getStatus().ordinal(),
					membership.getLastUpdated().getTimeInMillis());
		} catch (SQLException e) {
			throw new MapperException("Could not insert GroupMembership "
					+ membership.getId(), e);
		}
	}

	//@Override
	public void update(GroupMembership membership) throws MapperException {
		try {
			int count = GroupMembershipTDG.update(membership.getId(),
					membership.getVersion(), membership.getMember().getId(),
					membership.getGroup().getId(), membership.getStatus()
					.ordinal(), membership.getLastUpdated()
					.getTimeInMillis());
			if (count == 0)
				throw new LostUpdateException("GroupMembershipTDG: id "
						+ membership.getId() + " version "
						+ membership.getVersion());
			membership.setVersion(membership.getVersion() + 1);
		} catch (SQLException e) {
			throw new MapperException("Could not update GroupMembership "
					+ membership.getId(), e);
		}
	}
}
