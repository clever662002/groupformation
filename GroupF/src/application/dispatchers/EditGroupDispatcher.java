package application.dispatchers;

import java.io.IOException;

import javax.servlet.ServletException;
import org.dsrg.soenea.domain.command.CommandException;
import domain.commands.EditGroupCommand;

public class EditGroupDispatcher extends Dispatcher {
	@Override
	public void execute() throws ServletException, IOException {
		try {
			new EditGroupCommand(this.myHelper).execute();
			
			relativeRedirect("BrowseGroups");

			//forward("/WEB-INF/jsp/groups.jsp");
		} catch (final CommandException e) {
			throw new ServletException(e);
		}
	}

}