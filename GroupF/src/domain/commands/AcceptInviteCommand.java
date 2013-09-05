package domain.commands;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.DomainCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.uow.UoW;

import domain.model.group.Group;
import domain.model.groupmembership.GroupMembership;
import domain.model.invite.Invites;
import domain.model.user.NamedUser;
import domain.model.group.mappers.GroupInputMapper;
import domain.model.groupmembership.mappers.GroupMembershipOutputMapper;
import domain.model.invite.mappers.InvitesInputMapper;
import domain.model.invite.mappers.InvitesOutputMapper;

public class AcceptInviteCommand extends DomainCommand {

	private HttpServletResponse response;
	
	public AcceptInviteCommand(Helper helper, HttpServletResponse response) {
		super(helper);
		this.response = response;
	}



	@Override
	public void setUp() throws CommandException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process() throws CommandException {
		NamedUser user = (NamedUser) helper.getSessionAttribute("user");
		long groupid = (long) helper.getLong("groupid");
		
		response.setContentType("text/plain");
		response.setStatus(200);
		try {
			// delete all invites for this user
			UoW.newCurrent();
			InvitesOutputMapper iom = new InvitesOutputMapper();
			List<Invites> invites = InvitesInputMapper.findInvites(user.getId());
			Iterator<Invites> itr = invites.iterator();
			
			while (itr.hasNext()) {
				Invites i = (Invites) itr.next();
				iom.delete(i);
			}
			
			// add this user to group
			Group group = GroupInputMapper.find(groupid);
			GroupMembership gm = new GroupMembership(user.getId(), groupid, user, group);
			GroupMembershipOutputMapper gom = new GroupMembershipOutputMapper();
			gom.insert(gm);
			response.getWriter().write("Accepted");
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MapperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			response.getWriter().write("Declined");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void tearDown() throws CommandError {
		// TODO Auto-generated method stub
		
	}}
