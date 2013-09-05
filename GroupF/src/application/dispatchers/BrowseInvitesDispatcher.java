package application.dispatchers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.domain.command.CommandException;

import domain.commands.BrowseInvitesCommand;

public class BrowseInvitesDispatcher extends Dispatcher {
	
	public void execute() throws ServletException, IOException {
		try {
			
			new BrowseInvitesCommand(this.myHelper).execute();
			forward("/WEB-INF/jsp/BrowseInvitesTV.jsp");
		} catch (final CommandException e) {
			throw new ServletException(e);
		}
	}
}
