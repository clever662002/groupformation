package domain.commands;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.DomainCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.uow.UoW;

import domain.model.invite.Invites;
import domain.model.user.NamedUser;
import domain.model.invite.mappers.InvitesInputMapper;
import domain.model.invite.mappers.InvitesOutputMapper;

public class DeclineInviteCommand extends DomainCommand {

	private HttpServletResponse response;
	
	public DeclineInviteCommand(Helper helper, HttpServletResponse response) {
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
		
		// delete entry from invites table
		
		try {
			UoW.newCurrent();
			InvitesOutputMapper iom = new InvitesOutputMapper();
			Invites invite = InvitesInputMapper.find(user.getId(), groupid);
		
			iom.delete(invite);
			response.getWriter().write("Declined");
			return;
		} catch (MapperException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			response.getWriter().write("Error, please try again.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void tearDown() throws CommandError {
		// TODO Auto-generated method stub
		
	}}
