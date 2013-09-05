package application.dispatchers;

import java.io.IOException;

import javax.servlet.ServletException;

public class IndexDispatcher extends Dispatcher {
	
	@Override
	public void execute() throws ServletException, IOException {
		forward("/WEB-INF/jsp/index.jsp");
	}

}
