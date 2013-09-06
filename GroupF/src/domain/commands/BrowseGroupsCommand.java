package domain.commands;

import java.sql.SQLException;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.DomainCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.user.User;
import org.dsrg.soenea.uow.UoW;

import domain.model.group.Group;
import domain.model.group.mappers.GroupInputMapper;

public class BrowseGroupsCommand extends DomainCommand {

	public BrowseGroupsCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void setUp() throws CommandException {
	}

	@Override
	public void process() throws CommandException {
		try {
			
			UoW.newCurrent();
			User user = (User) helper.getSessionAttribute("user");
			long userid = user.getId();		
			Group group = GroupInputMapper.findMyGroup(userid);
			if (group == null)
				helper.setRequestAttribute("isGroupMember", "false");
			else
				helper.setRequestAttribute("isGroupMember", "true");
			
			helper.setRequestAttribute("action", "allgroups");
			helper.setRequestAttribute("groups", GroupInputMapper.findAll());
		} catch (SQLException e) {
			throw new CommandException(e);
		} catch (DomainObjectCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MapperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void tearDown() throws CommandError {
	}

}
