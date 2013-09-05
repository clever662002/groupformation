package domain.commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.DomainCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.uow.UoW;

import domain.model.group.Group;
import domain.model.group.IGroup;
import domain.model.invite.Invites;
import domain.model.user.NamedUser;
import domain.model.group.mappers.GroupInputMapper;
import domain.model.invite.mappers.InvitesInputMapper;

public class BrowseInvitesCommand extends DomainCommand {
	
	public BrowseInvitesCommand(Helper helper) {
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
	
		UoW.newCurrent();
		
		// check if user is in group
		long userid = user.getId();		
		Group userGroup;
		try {
			userGroup = GroupInputMapper.findMyGroup(userid);
			if (userGroup != null) {
				// already in group
				helper.setRequestAttribute("message", "Already in group or no pending invites.");
				helper.setRequestAttribute("groups", null);
				return;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			List<Invites> invites = InvitesInputMapper.findInvites(user.getId());
			Iterator<Invites> itr = invites.iterator();
			
			ArrayList<IGroup> groups = new ArrayList<IGroup>();
			Invites i;
			Group group;
			while (itr.hasNext()) {
			    i = (Invites) itr.next();
				group = (Group) GroupInputMapper.find(i.getGroupId());
				groups.add(group);
			}
			helper.setRequestAttribute("groups", groups);
		} catch (SQLException
				| MapperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void tearDown() throws CommandError {
		// TODO Auto-generated method stub
		
	}
		
}
