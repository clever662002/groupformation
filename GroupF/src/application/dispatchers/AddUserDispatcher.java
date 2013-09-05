package application.dispatchers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.domain.command.CommandException;

import domain.commands.AddUserCommand;

public class AddUserDispatcher extends Dispatcher {
	@Override
	public void execute() throws ServletException, IOException {
		try {
			new AddUserCommand(this.myHelper).execute();
			
			forward("/WEB-INF/jsp/admin.jsp");
		} catch (final CommandException e) {
			throw new ServletException(e);
		}
	}

}
