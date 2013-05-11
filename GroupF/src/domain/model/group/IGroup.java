package domain.model.group;

import java.util.List;

import org.dsrg.soenea.domain.interf.IDomainObject;

import domain.model.groupmembership.IGroupMembership;

public interface IGroup extends IDomainObject<Long> {
	
	public abstract String getName();
	
	public abstract void setName(String name);
	
	public List<IGroupMembership> getMembers();
	
	public void setMembers(List<IGroupMembership> members);

}
