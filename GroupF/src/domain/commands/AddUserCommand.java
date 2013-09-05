package domain.commands;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.DomainCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.uow.UoW;

import domain.model.user.NamedUser;
import domain.model.group.mappers.GroupInputMapper;
import domain.model.user.mappers.NamedUserOutputMapper;

public class AddUserCommand extends DomainCommand {

	public AddUserCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void setUp() throws CommandException {
	}

	@Override
	public void process() throws CommandException {
		try {
			if(this.helper.getString("username")!=null) {
				NamedUser user = new NamedUser(0, this.helper.getString("username"), this.helper.getString("l_name"), this.helper.getString("f_name"));
				user.setPassword(helper.getString("pwd"));
				
				UoW.newCurrent();
				UoW.getCurrent().registerNew(user);
				UoW.getCurrent().commit();
				
				this.helper.setRequestAttribute("message", "User '"+user.getUsername()+"' added successfully.");
			}
			
			
		}
		catch (MapperException e) {
			this.helper.setRequestAttribute("error", "Failed to add user.\n" + e.getMessage());
		}
		catch(IllegalAccessException e) {
			
		}
		catch(InstantiationException e) {
			
		}
		catch (SQLException e) {
			throw new CommandException(e);
		}
	}

	@Override
	public void tearDown() throws CommandError {
	}

}
