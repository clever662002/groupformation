package domain.model.groupmembership;

import java.util.List;

import org.dsrg.soenea.domain.proxy.ListProxy;

import domain.model.group.IGroup;
import domain.model.groupmembership.mappers.GroupMembershipInputMapper;

public class MembershipListProxy extends ListProxy<IGroupMembership> {
	private IGroup parent;

	public MembershipListProxy(IGroup parent) {
		super();
		this.parent = parent;
	}

	@Override
	public List<IGroupMembership> getActualList() throws Exception {
		return GroupMembershipInputMapper.find(parent);
	}
}
