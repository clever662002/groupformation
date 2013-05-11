package domain.model.groupmembership;

import java.util.Calendar;

import org.dsrg.soenea.domain.interf.IDomainObject;
import org.dsrg.soenea.domain.user.IUser;

import domain.model.group.IGroup;

public interface IGroupMembership extends IDomainObject<Long> {
	
	public abstract IUser getMember();
	
	public abstract void setMember(IUser member);
	
	public abstract IGroup getGroup();
	 
	public abstract void setGroup(IGroup group);
	
	public abstract MembershipStatus getStatus();
	 
	public abstract void setStatus(MembershipStatus status);
	 
	public abstract Calendar getLastUpdated();
	 
	public abstract void setLastUpdated(Calendar lastUpdated);

}
