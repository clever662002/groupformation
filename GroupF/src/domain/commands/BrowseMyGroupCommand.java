package domain.commands;

import java.sql.SQLException;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.DomainCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.uow.UoW;

import domain.model.group.Group;
import domain.model.user.NamedUser;
import domain.model.group.mappers.GroupInputMapper;
import domain.model.member.mappers.MemberInputMapper;
import domain.model.user.mappers.NamedUserInputMapper;
import domain.model.user.mappers.NamedUserOutputMapper;

public class BrowseMyGroupCommand extends DomainCommand {

	public BrowseMyGroupCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void setUp() throws CommandException {
	}

	@Override
	public void process() throws CommandException {
		try {
			NamedUser userr = (NamedUser) helper.getSessionAttribute("user");
			//long userid = (long) helper.getSessionAttribute("user_id");
			System.out.print(userr);
			long userid = userr.getId();		
			helper.setRequestAttribute("action", "mygroup");
			UoW.newCurrent();
			
			Group mygroupp = GroupInputMapper.findMyGroup(userid);
			helper.setRequestAttribute("Mygroup", mygroupp);
			/*if (mygroupp == null)
				helper.setRequestAttribute("Mygroup", "none");
			else
				helper.setRequestAttribute("Mygroup", "group");*/
			
			if (mygroupp!=null) {
				System.err.print("GROUOPP id"+ mygroupp.getId()) ;
				helper.setRequestAttribute("myGroupMembers", NamedUserInputMapper.findAllGroupMembers(mygroupp.getId()));
			}
			
			//helper.setRequestAttribute("myGroupMembers", MemberInputMapper.find((Group)helper.getRequestAttribute("Mygroup")));
			
			
			//helper.setRequestAttribute("user", NamedUserInputMapper.findAllGroupMembers(userid));
		} catch (SQLException e) {
			throw new CommandException(e);
		} /*catch (DomainObjectNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MapperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	@Override
	public void tearDown() throws CommandError {
	}

}
