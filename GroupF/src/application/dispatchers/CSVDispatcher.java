package application.dispatchers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.domain.command.CommandException;

import domain.commands.UploadCSVCommand;

public class CSVDispatcher extends Dispatcher {
	
	@Override
	public void init(HttpServletRequest req, HttpServletResponse res) {
		this.myRequest = req;
		this.myResponse = res;
		this.myHelper = new MultipartHttpServletHelper(this.myRequest);
	}
	
	@Override
	public void execute() throws ServletException, IOException {
		try {
			new UploadCSVCommand(myHelper).process();
			
			forward("/WEB-INF/jsp/admin.jsp");
		}
		catch(CommandException e) {
			e.printStackTrace();
		}
	}

}
