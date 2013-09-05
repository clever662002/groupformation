package domain.model.user;

import org.dsrg.soenea.domain.interf.IDomainObject;

public interface IUser extends IDomainObject<Long>{
	
	public abstract String getUserName();
	public abstract void setUserName(String userName);
	
	public abstract String getFirstName();
	public abstract void setFirstName(String firstName);

	public abstract String getLastName();
	public abstract void setLastName(String lastName);

	public abstract String getPassword();
	public abstract void setPassword(String password);

	public abstract String getRole();
	public abstract void setRole(String role);
}
