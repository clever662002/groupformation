package application.dispatchers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.domain.command.CommandException;
import domain.commands.ViewCurrentGroupCommand;

public class ViewCurrentGroupDispatcher extends Dispatcher {
	@Override
	public void execute() throws ServletException, IOException {
		try {
			new ViewCurrentGroupCommand(this.myHelper).execute();
			forward("/WEB-INF/jsp/BrowseGroups.jsp");
		} catch (final CommandException e) {
			throw new ServletException(e);
		}
	}

}
