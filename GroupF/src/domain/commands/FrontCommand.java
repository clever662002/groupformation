package domain.commands;

import javax.servlet.http.HttpServletRequest;

public abstract class FrontCommand {
	public abstract String execute(HttpServletRequest req);
}
