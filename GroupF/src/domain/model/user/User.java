package domain.model.user;

import org.dsrg.soenea.domain.DomainObject;

public class User extends DomainObject<Long>{
	
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String role;
	
	protected User(Long id, long version, String userName, String firstName, String lastName, String password, String role) {
		super(id, version);
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.role = role;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
