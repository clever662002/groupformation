package application.dispatchers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.domain.command.CommandException;

import domain.commands.RemoveMemberCommand;

public class RemoveMemberDispatcher extends Dispatcher {
	@Override
	public void execute() throws ServletException, IOException {
		Long id = getParamAndSetAsLongAttr("id");
		//getParamAndSetAsLongAttr("version");
		try {
			new RemoveMemberCommand(this.myHelper).execute();
			relativeRedirect("BrowseMyGroup");
			//forward("/groups.jsp");
			return;
		} catch (final CommandException e) {
			throw new ServletException(e);
		}
	}

}
