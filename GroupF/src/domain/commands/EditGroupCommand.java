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

import domain.model.group.mappers.GroupInputMapper;
import domain.model.group.mappers.GroupOutputMapper;
import domain.model.group.Group;
import domain.model.group.IGroup;
import domain.model.user.NamedUser;


public class EditGroupCommand extends DomainCommand {
	
	public EditGroupCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void setUp() throws CommandException {
	}

	@Override
	public void process() throws CommandException {
		
		long GROUPid = Long.parseLong(helper.getString("mygroupID"));
		
		String name = helper.getString("newTitle");
		String description = helper.getString("newDescription");

		try {

			if (GROUPid == 0){
				
				System.err.print("no Group FOUND !!!! ");
		}
		
			
			
			
			System.err.print("new name is "+name+" new descrption is "+description);
	
			UoW.newCurrent();
			Group group = GroupInputMapper.find(GROUPid);
			helper.setRequestAttribute("group", group);
			helper.setRequestAttribute("action", "mygroup");
			group.setVersion(group.getVersion());
			group.setName(name);
			group.setDescription(description);
			GroupOutputMapper gom = new GroupOutputMapper();
			gom.update(group);
			
		} catch (SQLException e) {
			throw new CommandException(e);
		} catch (DomainObjectNotFoundException e) {
			throw new CommandException(e);
		} catch (LostUpdateException e) {
			throw new CommandException(e);
		} catch (MapperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	@Override
	public void tearDown() throws CommandError {
	}

	
	
	
}

