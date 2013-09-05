package application.dispatchers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.domain.command.CommandException;
import domain.commands.BrowseGroupsCommand;

public class BrowseGroupsDispatcher extends Dispatcher {
	@Override
	public void execute() throws ServletException, IOException {
		try {
			new BrowseGroupsCommand(this.myHelper).execute();
			forward("/WEB-INF/jsp/groups.jsp");
		} catch (final CommandException e) {
			throw new ServletException(e);
		}
	}

}
