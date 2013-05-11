package domain.commands;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.Command;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.user.mapper.UserInputMapper;

public class LoginCommand extends Command {

	public LoginCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void execute() throws CommandException {
		String username = helper.getString("username");
		String password = helper.getString("password");

		if (username == null && password == null)
			throw new CommandException("");

		try {
			helper.setSessionAttribute("CurrentUser",
					UserInputMapper.find(username, password));

		} catch (SQLException e) {

			e.printStackTrace();
			throw new CommandException(e);
		} catch (MapperException e) {
			getNotifications()
					.add("Sorry, no user for that "
							+ "username and password combo.");
			throw new CommandException("Sorry, no user for that "
					+ "username and password combo.");
		}

	}

	@Override
	public void setUp() throws CommandException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process() throws CommandException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tearDown() throws CommandError {
		// TODO Auto-generated method stub
		
	}
}
