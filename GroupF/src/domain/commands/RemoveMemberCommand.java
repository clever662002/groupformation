package domain.commands;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.DomainCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.LostUpdateException;
import org.dsrg.soenea.uow.UoW;

import domain.model.group.Group;
import domain.model.groupmembership.GroupMembership;
import domain.model.user.NamedUser;
import domain.model.group.mappers.GroupInputMapper;
import domain.model.groupmembership.mappers.GroupMembershipInputMapper;
import domain.model.group.mappers.GroupOutputMapper;

public class RemoveMemberCommand extends DomainCommand {
	
	public RemoveMemberCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void setUp() throws CommandException {
	}

	@Override
	public void process() throws CommandException {
		
		try {
			long id = (Long) helper.getAttribute("id");

			UoW.newCurrent();
			
			GroupMembership member = GroupMembershipInputMapper.find(id);
			//String username = member.getMember().getUsername();
			
			//GroupOutputMapper.delete(group);	
			//helper.setRequestAttribute("group", group);			
			UoW.getCurrent().registerRemoved(member);
			UoW.getCurrent().commit();
			helper.setRequestAttribute("action", "mygroup");
			//this.helper.setRequestAttribute("message", "Member '"+ username  +"' deleted.");
			
		} catch (DomainObjectNotFoundException e) {
			throw new CommandException(e);
		} catch (LostUpdateException e) {
			throw new CommandException(e);
		}  catch (MapperException e) {
			this.helper.setRequestAttribute("error", "Failed to remove member.\n" + e.getMessage());
		} catch (SQLException e) {
			throw new CommandException(e);
		} catch (InstantiationException e) {
			throw new CommandException(e);
		} catch (IllegalAccessException e) {
			throw new CommandException(e);
		}
	}
	
	

	@Override
	public void tearDown() throws CommandError {
	}

	
	
	
}

