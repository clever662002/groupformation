package domain.model.user;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;

import services.user.UserTDG;

public class UserFactory {
	public static User createNew(String userName, String firstName, String lastName, String password, String role)
			throws SQLException, MissingMappingException, MapperException {
		User result = new User(UserTDG.maxId(), 1, userName, firstName, lastName, password, role);
		UoW.getCurrent().registerNew(result);
		return result;
	}

	public static User createClean(long id, long version, String userName, String firstName, 
			String lastName, String password, String role) throws SQLException {
		User result = new User(id, version, userName, firstName, lastName, password, role);
		UoW.getCurrent().registerClean(result);
		return result;
	}

}
