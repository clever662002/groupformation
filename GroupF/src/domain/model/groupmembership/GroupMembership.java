package domain.model.groupmembership;

import java.util.Calendar;

import org.dsrg.soenea.domain.DomainObject;
import org.dsrg.soenea.domain.user.IUser;

import domain.model.group.IGroup;

public class GroupMembership extends DomainObject<Long> implements
		IGroupMembership {

	private IUser member;
	private IGroup group;
	private MembershipStatus status;
	private Calendar lastUpdated;

	public GroupMembership(Long id, long version, IUser member, IGroup group,
			MembershipStatus status, Calendar lastUpdated) {
		super(id, version);
		this.member = member;
		this.group = group;
		this.status = status;
		this.lastUpdated = lastUpdated;
	}

	public IUser getMember() {
		return member;
	}

	public void setMember(IUser member) {
		this.member = member;
	}

	public IGroup getGroup() {
		return group;
	}

	public void setGroup(IGroup group) {
		this.group = group;
	}

	public MembershipStatus getStatus() {
		return status;
	}

	public void setStatus(MembershipStatus status) {
		this.status = status;
	}

	public Calendar getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Calendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
