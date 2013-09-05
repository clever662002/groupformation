package application.dispatchers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.dsrg.soenea.domain.command.CommandException;

import domain.commands.DeclineInviteCommand;

public class DeclineInviteDispatcher extends Dispatcher {

	@Override
	public void execute() throws ServletException, IOException {
		try {
			new DeclineInviteCommand(this.myHelper, this.myResponse).execute();
		} catch (CommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
