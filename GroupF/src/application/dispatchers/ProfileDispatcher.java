package application.dispatchers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.domain.command.CommandException;

import domain.commands.ProfileCommand;

public class ProfileDispatcher extends Dispatcher {
	
	@Override
	public void execute() throws ServletException, IOException {
		try {
			new ProfileCommand(this.myHelper).execute();
			forward("/WEB-INF/jsp/profile.jsp");
		} catch (CommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
