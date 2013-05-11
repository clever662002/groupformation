package domain.model.group;

import java.util.List;

import org.dsrg.soenea.domain.DomainObject;

import domain.model.groupmembership.IGroupMembership;

public class Group extends DomainObject<Long> implements IGroup {
	private String name;
	private List<IGroupMembership> members;

	protected Group(Long id, long version, String name, List<IGroupMembership> members) {
		super(id, version);
		this.name = name;
		this.members = members;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<IGroupMembership> getMembers() {
		return members;
	}

	public void setMembers(List<IGroupMembership> members) {
		this.members = members;
	}
}
