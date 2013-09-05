package domain.commands;

import java.sql.SQLException;
import java.util.ArrayList;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.DomainCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.LostUpdateException;
import org.dsrg.soenea.uow.UoW;

import domain.model.group.mappers.GroupOutputMapper;
import domain.model.group.Group;
import domain.model.group.GroupFactory;
import domain.model.groupmembership.GroupMembership;
import domain.model.groupmembership.GroupMembershipFactory;
import domain.model.group.GroupProxy;
import domain.model.group.IGroup;
import domain.model.groupmembership.IGroupMembership;
import domain.model.user.NamedUser;


public class CreateGroupCommand extends DomainCommand {
	
	public CreateGroupCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void setUp() throws CommandException {
	}

	@Override
	public void process() throws CommandException {
		
		//try {
			/*long id = (Long) helper.getAttribute("id");
			long version = (Long) helper.getAttribute("version");
			String description = (String) helper.getAttribute("description");
			String name =  (String) helper.getAttribute("name");
			
			Group group = new Group(id, version, name, description);
			helper.setRequestAttribute("group", group);
			helper.setRequestAttribute("action", "creategroup");
			
			GroupOutputMapper.insert(group);*/
			
		String name = helper.getString("name");
		String description = helper.getString("description");

		try {
			UoW.newCurrent();
			Group group = GroupFactory.createNew(name, description, new ArrayList<IGroupMembership>());
			NamedUser user = (NamedUser) helper.getSessionAttribute("user");
			GroupMembership gm = GroupMembershipFactory.createNew(user, group);
			GroupOutputMapper gom = new GroupOutputMapper();
			gom.insert(group, gm);
			
			//UoW.getCurrent().commit();
			//this.helper.setRequestAttribute("group", group);
			this.helper.setRequestAttribute("action", "allgroups");
			this.helper.setRequestAttribute("message", "Group '"+ group.getName()+"' successfully created.");
			
		} catch (MapperException e) {
			this.helper.setRequestAttribute("error", "Failed to create group.\n" + e.getMessage());
		} /*catch (IllegalAccessException e) { 
			
		} catch (InstantiationException e) {
			
		}*/ catch (SQLException e) {
			throw new CommandException(e);
		}
		
	}
	
	

	@Override
	public void tearDown() throws CommandError {
	}

	
	
	
}

