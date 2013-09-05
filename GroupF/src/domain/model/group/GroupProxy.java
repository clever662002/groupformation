package domain.model.group;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;

import domain.model.group.mappers.GroupInputMapper;
import domain.model.groupmembership.IGroupMembership;

public class GroupProxy extends DomainObjectProxy<Long, Group> implements IGroup {

	public GroupProxy(Long id) {
		super(id);
	}

	@Override
	protected Group getFromMapper(Long id) throws DomainObjectCreationException {
		try {
			return GroupInputMapper.find(id);
		} catch (MapperException e) {
			throw new DomainObjectCreationException(e.getMessage(), e);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getName() {
		return getInnerObject().getName();
	}
	
	@Override
	public void setName(String name) {
		getInnerObject().setName(name);
	}
	
	@Override
	public String getDescription() {
		return getInnerObject().getDescription();
	}
	
	@Override
	public void setDescription(String description) {
		getInnerObject().setDescription(description);
	}

	@Override
	public String toString() {
		return getInnerObject().toString();
	}

	@Override
	public List<IGroupMembership> getMembers() {
		return getInnerObject().getMembers();
	}

	@Override
	public void setMembers(List<IGroupMembership> members) {
		getInnerObject().setMembers(members);		
	}

}
