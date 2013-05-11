package application.dispatchers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;

import domain.commands.LoginCommand;

public class LoginDispatcher extends Dispatcher {

	@Override
	public void execute() throws ServletException, IOException {
		try {
			new LoginCommand(myHelper).execute();
			forward("/WEB-INF/JSP/html/Menu.jsp");
		} catch (Exception e) {
			forward("/WEB-INF/JSP/html/TryLogin.jsp");
		}
	}

}
