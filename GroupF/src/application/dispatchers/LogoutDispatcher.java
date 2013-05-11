package application.dispatchers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.domain.user.GuestUser;

public class LogoutDispatcher extends Dispatcher {
	@Override
	public void execute() throws ServletException, IOException {
		try {
			myRequest.getSession().invalidate();
			myHelper.setSessionAttribute("CurrentUser", new GuestUser());
			forward("/WEB-INF/JSP/html/TryLogin.jsp");
		} catch (Exception e) {
			forward("/WEB-INF/JSP/html/TryLogin.jsp");
		}
	}

}
