package application.dispatchers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.domain.command.CommandException;

import domain.commands.RemoveGroupCommand;

public class RemoveGroupDispatcher extends Dispatcher {
	@Override
	public void execute() throws ServletException, IOException {
		Long id = getParamAndSetAsLongAttr("id");
		//getParamAndSetAsLongAttr("id");
		try {
			new RemoveGroupCommand(this.myHelper).execute();
			relativeRedirect("BrowseGroups");
			//forward("WEB-INF/jsp/groups.jsp");
			return;
		} catch (final CommandException e) {
			throw new ServletException(e);
		}
	}

}