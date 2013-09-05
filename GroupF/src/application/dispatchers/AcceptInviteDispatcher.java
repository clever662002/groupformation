package application.dispatchers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.domain.command.CommandException;

import domain.commands.AcceptInviteCommand;

public class AcceptInviteDispatcher extends Dispatcher {

	@Override
	public void execute() throws ServletException, IOException {
		try {
			new AcceptInviteCommand(this.myHelper, this.myResponse).execute();
		} catch (CommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
