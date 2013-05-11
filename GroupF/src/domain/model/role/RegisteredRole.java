package domain.model.role;

import org.dsrg.soenea.domain.role.IRole;
import org.dsrg.soenea.domain.role.Role;

public class RegisteredRole extends Role implements IRole {

	private static final long ROLE_ID = RoleIds.ADMIN;
	private static final String ROLE_NAME = "RegisteredRole";

	public RegisteredRole() {
		super(ROLE_ID, 1, ROLE_NAME);
	}

	@Override
	public String getName() {
		return ROLE_NAME;
	}

	@Override
	public Long getId() {
		return ROLE_ID;
	}

	@Override
	public long getVersion() {
		return 1;
	}

}
