package domain.commands;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.DomainCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.user.User;
import org.dsrg.soenea.uow.UoW;

import services.invite.InvitesTDG;
import domain.model.group.Group;
import domain.model.groupmembership.GroupMembership;
import domain.model.invite.Invites;
import domain.model.group.mappers.GroupInputMapper;
import domain.model.groupmembership.mappers.GroupMembershipInputMapper;
import domain.model.invite.mappers.InvitesInputMapper;
import domain.model.invite.mappers.InvitesOutputMapper;
import domain.model.user.mappers.NamedUserInputMapper;

public class InviteUserCommand extends DomainCommand {

	private HttpServletResponse response;
	
	public InviteUserCommand(Helper helper, HttpServletResponse response) {
		super(helper);
		this.response = response;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setUp() throws CommandException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process() throws CommandException {
		String username = (String) helper.getString("username");
		long mygroupid = (long) helper.getLong("mygroupid");
		User user = null;
		
		response.setContentType("text/plain");
		// check if user exists
		try {
			

			user = (User) NamedUserInputMapper.find(username);
			
			if (user == null) {
				response.setStatus(404);
				response.getWriter().write("User does not exist!");
				return;
			}
			UoW.newCurrent();
			Group group = (Group) GroupInputMapper.find(mygroupid);
			// is user already in a group?
			GroupMembership member;
			try {
				member = (GroupMembership) GroupMembershipInputMapper.find(user.getId());
				if (member != null) {
					response.setStatus(404);
					response.getWriter().write("User already in a group!");
					return;
				}
			} catch (MapperException e) {
				// user is NOT in a group
				// do nothing, ugly I know...
			}
			
			// check if invite is already there
			Invites invited = InvitesInputMapper.find(user.getId(), mygroupid);
			if (invited != null) {
				response.setStatus(404);
				response.getWriter().write("User already invited!");
				return;
			}
			
			// save to invites table
			long nextId = InvitesTDG.maxId();
			UoW.newCurrent();
			
			Invites invite = new Invites(nextId, user.getId(), group.getId());
			InvitesOutputMapper iom = new InvitesOutputMapper();
			iom.insert(invite);
			response.setStatus(200);
			response.getWriter().write("Invited user " + username);
					
		} catch (MapperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void tearDown() throws CommandError {
		// TODO Auto-generated method stub
		
	}

}
