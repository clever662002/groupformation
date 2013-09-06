package domain.model.group;

import java.util.List;

import org.dsrg.soenea.domain.DomainObject;

import domain.model.groupmembership.IGroupMembership;

public class Group extends DomainObject<Long> implements IGroup {
	private String name;
	private String description;
	private List<IGroupMembership> members;

	protected Group(Long id, int version, String name, String description, List<IGroupMembership> members) {
		super(id, version);
		this.name = name;
		this.description = description;
		this.members = members;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<IGroupMembership> getMembers() {
		return members;
	}

	public void setMembers(List<IGroupMembership> members) {
		this.members = members;
	}
}
