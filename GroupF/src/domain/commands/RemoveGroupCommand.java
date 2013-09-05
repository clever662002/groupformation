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
import domain.model.group.mappers.GroupInputMapper;
import domain.model.group.mappers.GroupOutputMapper;

public class RemoveGroupCommand extends DomainCommand {
	
	public RemoveGroupCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void setUp() throws CommandException {
	}

	@Override
	public void process() throws CommandException {
		
		try {
			long id = (Long) helper.getAttribute("id");
			//long version = (Long) helper.getAttribute("version");
			//String description = (String) helper.getAttribute("description");
			//String name =  (String) helper.getAttribute("name");
			
			//Group group = new Group(id, version, name, description);
			UoW.newCurrent();
			Group group = GroupInputMapper.find(id);
			String name = group.getName(); 
			
			//helper.setRequestAttribute("group", group);
			
			//UoW.getCurrent().registerRemoved(group);
			//UoW.getCurrent().commit();
			GroupOutputMapper gom = new GroupOutputMapper();
			gom.delete(group);
			this.helper.setRequestAttribute("action", "allgroups");
			this.helper.setRequestAttribute("message", "Group '"+ name  +"' deleted.");
			
		} catch (DomainObjectNotFoundException e) {
			throw new CommandException(e);
		} catch (LostUpdateException e) {
			throw new CommandException(e);
		}  catch (MapperException e) {
			this.helper.setRequestAttribute("error", "Failed to delete group.\n" + e.getMessage());
		} catch (SQLException e) {
			throw new CommandException(e);
		}
	}
	
	

	@Override
	public void tearDown() throws CommandError {
	}

	
	
	
}
