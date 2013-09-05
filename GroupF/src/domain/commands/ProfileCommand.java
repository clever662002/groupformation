package domain.commands;

import java.sql.SQLException;

import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.DomainCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.uow.UoW;

import domain.model.group.Group;
import domain.model.user.NamedUser;
import domain.model.group.mappers.GroupInputMapper;

public class ProfileCommand extends DomainCommand {
	
	public ProfileCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void setUp() throws CommandException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process() throws CommandException {
		// Get user object
		NamedUser user = (NamedUser) helper.getSessionAttribute("user"); 
		
		try {
			UoW.newCurrent();
			Group group = GroupInputMapper.findMyGroup(user.getId());
			helper.setRequestAttribute("group", group);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void tearDown() throws CommandError {
		// TODO Auto-generated method stub
		
	}

}
