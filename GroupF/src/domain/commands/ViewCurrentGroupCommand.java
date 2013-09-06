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
import domain.model.groupmembership.GroupMembership;
import domain.model.groupmembership.mappers.GroupMembershipInputMapper;

public class ViewCurrentGroupCommand extends DomainCommand {

	public ViewCurrentGroupCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void setUp() throws CommandException {
	}

	@Override
	public void process() throws CommandException {
		try {
			UoW.newCurrent();
			User user = (User) helper.getSessionAttribute("CurrentUser");
			long userid = user.getId();		
			GroupMembership gm = GroupMembershipInputMapper.findByMember(userid);
			if (gm == null)
				helper.setRequestAttribute("isGroupMember", "false");
			else
				helper.setRequestAttribute("isGroupMember", "true");
			Group group = (Group) gm.getGroup();
			helper.setRequestAttribute("group", group);
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

