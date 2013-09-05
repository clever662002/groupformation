package domain.commands;

import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.DomainCommand;
import org.dsrg.soenea.domain.helper.Helper;

public class LogoutCommand extends DomainCommand {
	
	public LogoutCommand(Helper helper) {
		super(helper);
	}
	
	@Override
	public void setUp() throws CommandException {
	}

	public void process() throws CommandException {
		//Cannot invalidate session here....
		
		//this.
	}
	
	@Override
	public void tearDown() throws CommandError {
	}

}
