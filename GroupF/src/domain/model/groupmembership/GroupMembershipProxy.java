package domain.model.groupmembership;

import java.sql.SQLException;
import java.util.Calendar;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;
import org.dsrg.soenea.domain.user.IUser;

import domain.model.group.IGroup;
import domain.model.groupmembership.mappers.GroupMembershipInputMapper;

public class GroupMembershipProxy extends
		DomainObjectProxy<Long, GroupMembership> implements IGroupMembership {

	public GroupMembershipProxy(Long id) {
		super(id);
	}

	@Override
	protected GroupMembership getFromMapper(Long id)
			throws DomainObjectCreationException {
		try {
			return GroupMembershipInputMapper.find(id);
		} catch (MapperException e) {
			throw new DomainObjectCreationException(e.getMessage(), e);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public IGroup getGroup() {
		return getInnerObject().getGroup();
	}

	public Calendar getLastUpdated() {
		return getInnerObject().getLastUpdated();
	}

	public IUser getMember() {
		return getInnerObject().getMember();
	}

	public MembershipStatus getStatus() {
		return getInnerObject().getStatus();
	}

	public void setGroup(IGroup group) {
		getInnerObject().setGroup(group);
	}

	public void setLastUpdated(Calendar lastUpdated) {
		getInnerObject().setLastUpdated(lastUpdated);
	}

	public void setMember(IUser member) {
		getInnerObject().setMember(member);
	}

	public void setStatus(MembershipStatus status) {
		getInnerObject().setStatus(status);
	}

}
