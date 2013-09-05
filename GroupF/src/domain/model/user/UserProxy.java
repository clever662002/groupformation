package domain.model.user;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;

public class UserProxy extends DomainObjectProxy<Long, User> implements IUser {
	
	public UserProxy(Long id) {
		super(id);
	}
	
	@Override
	public String getUserName() {
		return getInnerObject().getUserName();
	}
	
	@Override
	public void setUserName(String userName) {
		getInnerObject().setUserName(userName);
	}

	@Override
	public String getFirstName() {
		return getInnerObject().getFirstName();
	}

	@Override
	public void setFirstName(String firstName) {
		getInnerObject().setFirstName(firstName);
		
	}

	@Override
	public String getLastName() {
		return getInnerObject().getLastName();
	}

	@Override
	public void setLastName(String lastName) {
		getInnerObject().setLastName(lastName);
		
	}

	@Override
	public String getPassword() {
		return getInnerObject().getPassword();
	}

	@Override
	public void setPassword(String password) {
		getInnerObject().setPassword(password);
		
	}

	@Override
	public String getRole() {
		return getInnerObject().getRole();
	}

	@Override
	public void setRole(String role) {
		getInnerObject().setRole(role);
		
	}

	@Override
	protected User getFromMapper(Long id) throws MapperException, DomainObjectCreationException {
		// TODO Auto-generated method stub
		return null;
	}

}
